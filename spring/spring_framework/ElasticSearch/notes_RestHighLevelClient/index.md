
# SpringBoot调用ElasticSearch的RestHighLevelClient的功能详解 - 知乎

## 01 概念

JavaREST客户端有两种模式： Java低级REST客户端和高级REST客户端。低级别的客户端通过http与Elasticearch集群通信，版本兼容性好。高级REST客户端是基于低级客户端API的封装，版本兼容性差。需要的Java1.8以上的版本。Elasticsearch需要6.0以上。

## 02 Maven依赖

由于ElasticSearch版本不同，可能导致一些问题。自己要选择好适合自己的版本。

```text
<!-- elasticSearch -->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>6.3.2</version>
        </dependency>
        
        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>6.3.2</version>
            <scope>compile</scope>
        </dependency>
```

## 03 初始化客户端

本文使用SpringBoot结合RestHighLevelClient来讲解高级客户端的使用。

**配置文件**  
ElasticSearch的配置信息如下，包括ElasticSearch服务器地址、端口号、索引名字、类型名字。

```text
cloud:
  elasticsearch:
    host: xxx.xx.xx.xx
    port: 9200
    index: cloud
    type: tcc
```

**初始化ElasticSearchConfig对象**  
配置文件写好后，使用使用一个ElasticSearchConfig实例化创建一个RestHighLevelClient的Bean实例，使用注解将配置文件的值注入到类中，创建RestHighLevelClient对象时可以看到，内部其实是创建了一个低级的客户端RestClient来实现，传入ElasticSearch的服务器地址和端口号。

```text
@Component
public class ElasticSearchConfig {
    @Value("${cloud.elasticsearch.host}")
    private String esHost;

    @Value("${cloud.elasticsearch.port}")
    private int esPort;

    @Bean
    public RestHighLevelClient restClient() {
        RestHighLevelClient restClient = new RestHighLevelClient(RestClient.builder(new HttpHost(esHost, esPort)));
        return restClient;
    }
}
```

**引用RestHighLevelClient对象**  
在需要用到ElasticSearch的类中注入索引名字、类型名字和高级客户端对象。

```text
@Autowired
    private RestHighLevelClient restClient;
    
    @Value("${cloud.elasticsearch.index}")
    private String ES_INDEX;

    @Value("${cloud.elasticsearch.type}")
    private String ES_TYPE;
```

## 04 客户端API使用

在讲解完每个API的使用方法后，都会有实际SpringBoot项目中用到的对应的索引方法。

**1、索引创建**

**创建索引请求**

```text
CreateIndexRequest request = new CreateIndexRequest("test");
```

**索引设置**  
设置分片和副本数。

```text
request.settings(Settings.builder() 
    .put("index.number_of_shards", 5)
    .put("index.number_of_replicas", 1)
);
```

**索引映射**  
创建包含指定的type的映射,下例中tcc就是类型名字。

```text
request.mapping("tcc", 
    "  {\n" +
    "    \"tcc\": {\n" +
    "      \"properties\": {\n" +
    "        \"name\": {\n" +
    "          \"type\": \"keyword\"\n" +
    "        },\n" +
    "        \"age\": {\n" +
    "          \"type\": \"long\"\n" +
    "        }\n" +
    "      }\n" +
    "    }\n" +
    "  }", 
    XContentType.JSON);
```

**参数设置**  
超时时间设置为1m

```text
request.timeout("1m");
```

**执行索引**  
执行后，可以判断是否所有节点都已确认请求。

```text
CreateIndexResponse createIndexResponse = client.indices().create(request);

boolean acknowledged = createIndexResponse.isAcknowledged();
```

**2、索引删除**

**删除索引请求**

```text
DeleteIndexRequest request = new DeleteIndexRequest("test");
```

**执行索引**  
执行后，可以判断是否所有节点都已确认请求。

```text
DeleteIndexResponse deleteIndexResponse = client.indices().delete(request);

boolean acknowledged = deleteIndexResponse.isAcknowledged();
```

**实际案例**  
删除索引和创建索引的方法。

```text
//创建索引方法，传入索引名和类型名
    public boolean reCreateIndex(String index, String type) {
        //删除索引请求，如果创建之前存在就需要删除掉
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(index);
        try {
            //删除索引
            DeleteIndexResponse deleteIndexResponse = restClient.indices().delete(deleteRequest);
            //如果没删除成功，抛出异常
            if (!deleteIndexResponse.isAcknowledged()) {
                throw new CommonException("delete index {} error", index);
            }
            //创建索引请求
            CreateIndexRequest request = new CreateIndexRequest(index);
            //设置分片和副本数
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 5)
                    .put("index.number_of_replicas", 1)
            );
            //创建映射，中间填写需要存到ES上的JavaDTO对象对应的JSON数据。
            request.mapping(type,假装这是一个JSON数据QAQ,XContentType.JSON);
            //创建索引
            CreateIndexResponse createIndexResponse = restClient.indices().create(request);
            //如果没成功，抛出异常
            if (!createIndexResponse.isAcknowledged()) {
                throw new CommonException("create index {} error", index);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
```

**3、新增数据**  
**新增数据请求**  
新增索引数据请求时需要传入索引名、类型名、ID和数据源。数据源可以是多种类型，内部都会自动转化为Json。

**以Json字符串的形式。**

```text
IndexRequest request = new IndexRequest("index","type","id");   
String jsonString = "{" +
        "\"name\":\"tcc\"," +
        "\"decription\":\"tcc\"" +
        "}";
request.source(jsonString, XContentType.JSON);
```

**以HashMap的形式。**

```text
Map<String, Object> jsonMap = new HashMap<>();
jsonMap.put("name", "tcc");
jsonMap.put("decription", "tcc");
IndexRequest indexRequest = new IndexRequest("index","type","id").source(jsonMap);
```

以XContentBuilder对象形式

```text
XContentBuilder builder = XContentFactory.jsonBuilder();
builder.startObject();
    {
    builder.field("name", "tcc");
    builder.field("decription","tcc");
    }
builder.endObject();
IndexRequest indexRequest = new IndexRequest("index","type","id").source(builder);
```

以Object键值对的形式。

```text
IndexRequest indexRequest = new IndexRequest("index","type","id")
        .source("name", "tcc",
                "decription", "tcc");
```

**可选参数**  
设置路由值、父值、超时时间等

```text
request.routing("routing"); 
request.parent("parent"); 
request.timeout("1s");
```

**执行索引**

执行新增数据后，可以查看返回响应状态码。

```text
IndexResponse indexResponse = client.index(request);
RestStatus restStatus = response.status();
```

**实际案例**

新增索引数据

```text
//先传入一个要存入EasticSearch的对象数据
    public boolean create(ElasticDTO elasticDTO) {
        //将对象转化为Json串，可以自定义一些转化规则
        String jsonParam = JSONObject.toJSONString(elasticDTO,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                                                //实体类的id。
        return create(ES_INDEX, ES_EVENT_ORDER, elasticDTO.getId().toString(), jsonParam);
    }

    //传入索引、类型、id和json字符串进行新增操作
    public boolean create(String index, String type, String id, String jsonParam) {
        //创建添加数据请求
        IndexRequest indexRequest = new IndexRequest(index, type, id).source(jsonParam, XContentType.JSON);
        try {
            //执行索引请求
            IndexResponse response = restClient.index(indexRequest);
            if (response.status().getStatus() == REST_STATUS_201) {
                return true;
            }
        } catch (IOException e) {
            throw new CommonException("restClient.create.error");
        }
        return false;
    }
```

**4、修改数据**

**更新数据请求**

新增索引数据请求时需要传入索引名、类型名、ID和数据源。数据源可以是多种类型，内部都会自动转化为Json，与新增数据请求相同，不同的是新增是request.source而修改是request.doc。

```text
UpdateRequest request = new UpdateRequest("index","type","id")
```

**可选参数**

设置路由值、父值、超时时间等

```text
request.routing("routing"); 
request.parent("parent"); 
request.timeout("1s");
```

**执行索引**

执行修改数据后，可以查看返回响应状态码。

```text
UpdateResponse updateResponse = client.update(request);
RestStatus restStatus = updateResponse.status();
```

**实际案例**

修改索引数据

```text
//逻辑跟新增相同
    public boolean update(ElasticDTO elasticDTO) {
        String idStr = elasticDTO.getId().toString();
        String jsonParam = JSONObject.toJSONString(elasticDTO,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        return update(ES_INDEX, ES_EVENT_ORDER, idStr, jsonParam);
    }

    public boolean update(String index, String type, String id, String jsonParam) {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(jsonParam, XContentType.JSON);
        try {
            UpdateResponse updateResponse = restClient.update(updateRequest);
            if (updateResponse.status().getStatus() == REST_STATUS_200) {
                return true;
            }
        } catch (IOException e) {
            throw new CommonException("restClient.update.error");
        }
        return false;
    }
```

**5、查找数据**

**获取索引请求**

```text
GetRequest getRequest = new GetRequest("index","type","id")
```

**可选参数**

**为数据源中特定字段设置为包含**

```text
String[] includes = new String[]{"tcc", "decription"};
String[] excludes = Strings.EMPTY_ARRAY;
FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
request.fetchSourceContext(fetchSourceContext);
```

**为数据源中特定字段设置为排除**

```text
String[] includes = Strings.EMPTY_ARRAY;
String[] excludes = new String[]{"tcc"};
FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
request.fetchSourceContext(fetchSourceContext);
```

**执行索引**

**执行查询数据后，可以查看返回响应状态码。**

```text
GetResponse getResponse = client.get(getRequest);
RestStatus restStatus = getResponse .status();
```

**6、删除数据**

**删除索引请求**

```text
DeleteRequest request = new DeleteRequest("index","type","id")
```

**可选参数**

设置路由值、父值、超时时间等

```text
request.routing("routing"); 
request.parent("parent"); 
request.timeout("1s");
```

**执行索引**

执行删除数据后，可以查看返回响应状态码。

```text
DeleteResponse deleteResponse = client.delete(request);
RestStatus restStatus = deleteResponse .status();
```

**7、批量处理数据**

**批量索引请求**

BulkRequest可以使用单个请求执行多个索引、更新和/或删除操作，BulkRequest它只支持JSON或SHAY源数据。

```text
BulkRequest request = new BulkRequest(); 
request.add(new IndexRequest("index","type","id1")  
        .source(XContentType.JSON,"tcc", "decription1"));
request.add(new IndexRequest("index","type","id2")  
        .source(XContentType.JSON,"tcc", "decription2"));
request.add(new IndexRequest("index","type","id3")  
        .source(XContentType.JSON,"tcc", "decription3"));
```

可以将不同的操作类型添加到相同的BulkRequest:

```text
BulkRequest request = new BulkRequest();
request.add(new DeleteRequest("index","type","id1")); 
request.add(new UpdateRequest("index","type","id1")  
        .doc(XContentType.JSON,"tcc", "decription3"));
request.add(new IndexRequest("index","type","id1")   
        .source(XContentType.JSON,"tcc", "decription3"));
```

**执行索引**

```text
BulkResponse bulkResponse = client.bulk(request);
RestStatus restStatus = bulkResponse .status();
```

**实际案例**

批量插入数据

```text
//传入list对象
    public boolean createBatch(List<ElasticDTO> elasticDTOList) {
        if (elasticDTOList == null || elasticDTOList.size() == 0) {
            return true;
        }
        //将id取出放入list
        List<String> ids = new ArrayList<>(elasticDTOList.size());
        //创建map存放id和对应数据
        Map<String, String> idJsonParamMap = new HashMap<>(elasticDTOList.size());
        for (ElasticDTO elasticDTO : elasticDTOList) {
            String idStr = elasticDTO.getId().toString();
            ids.add(idStr);
            String jsonParam = JSONObject.toJSONString(elasticDTO,
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            idJsonParamMap.put(idStr, jsonParam);
        }
        return createBatch(ES_INDEX, ES_EVENT_ORDER, ids, idJsonParamMap);
    }
    //传入索引、类型、id的list列表和<id json字符串数据>map进行新增操作
    public boolean createBatch(String index, String type, List<String> ids, Map<String, String> idJsonParamMap) {
        if (ids == null || ids.isEmpty()) {
            return true;
        }
        //创建BulkRequest
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1m");
        //便利id的list,插入到BulkRequest中。
        for (String id : ids) {
            IndexRequest indexRequest = new IndexRequest(index, type, id).source(idJsonParamMap.get(id), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
             //执行插入请求操作
            BulkResponse bulkResponse = restClient.bulk(bulkRequest);
            if (bulkResponse.status().getStatus() == REST_STATUS_200) {
                return true;
            }
        } catch (IOException e) {
            throw new CommonException("restClient.createBatch.error");
        }
        return false;
    }
```

**8、搜索数据**

**搜索请求**

SearchRequest用于与搜索文档、聚合、建议等，还可以自定义设置高亮显示。  
创建SeachRequest，添加SearchSourceBuilder到SeachRequest，SearchSourceBuilder中添加要给查询所有操作。

```text
SearchRequest searchRequest = new SearchRequest(); 
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
searchRequest.source(searchSourceBuilder);
```

**可选参数**

**将请求限制为索引/类型**

```text
SearchRequest searchRequest = new SearchRequest("test"); 
searchRequest.types("type");
```

**使用SearchSourceBuilder**

大多数控制搜索行为的选项都可以使用SearchSourceBuilder，它或多或少地包含了RESTAPI的搜索请求选项。  
以下是一些常见选项的几个示例：  
创建一个SearchSourceBuilder默认选项。  
可以设置QueryBuilder的一些方法，可以设置开始搜索的索引和搜索的大小size,还可以设置搜索熔断时间。

```text
SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
sourceBuilder.query(QueryBuilders.termQuery("name", "tcc")); 
sourceBuilder.from(0); 
sourceBuilder.size(5); 
sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
```

然后将SearchSourceBuilder添加到SearchRequest对象中。

```text
SearchRequest searchRequest = new SearchRequest();
searchRequest.source(sourceBuilder);
```

**构建查询**

搜索查询使用QueryBuilder，它满足了许多查询的要求。  
使用构造函数创建：权威匹配字段name值为tcc的数据

```text
MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(“name”, “tcc”);
```

创建全文匹配查询与字段“user”上的文本“Kimchy”匹配。  
创建好MatchQueryBuilder后，可以给它设置一些策略：  
设置模糊查询，设置仅仅匹配前面几个字符，设置查询的展开层数。

```text
matchQueryBuilder.fuzziness(Fuzziness.AUTO); 
matchQueryBuilder.prefixLength(3); 
matchQueryBuilder.maxExpansions(10); 

//也可以这么写
QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "tcc")
                                                .fuzziness(Fuzziness.AUTO)
                                                .prefixLength(3)
                                                .maxExpansions(10);

searchSourceBuilder.query(matchQueryBuilder);
```

**指定排序**

SearchSourceBuilder可以通过字段排序，搜索得分情况排序，地理距离排序和ScriptSortBuilder排序。

```text
//按照得分情况降序
sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); 
//按照id字段情况升序
sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
```

**过滤字段**

可以通过接受一个或多个通配符模式的数组，以控制包含或排除哪些字段：

```text
String[] includeFields = new String[] {"title", "content""};
String[] excludeFields = new String[] {"_type"};
sourceBuilder.fetchSource(includeFields, excludeFields);
```

**请求突出显示**

高亮显示搜索结果可以通过设置HighlightBuilder，然后添加到SearchSourceBuilder中。HighlightBuilder.Field创建高亮对象，然后添加到HighlightBuilder中。

```text
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//创建一个新的HighlightBuilder
HighlightBuilder highlightBuilder = new HighlightBuilder(); 
//创建title的高亮
HighlightBuilder.Field highlightTitle =
        new HighlightBuilder.Field("title"); 
        //设置高亮类型
highlightTitle.highlighterType("unified");  
//将高亮类型加入到highlightBuilder中
highlightBuilder.field(highlightTitle);  
HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content");
highlightBuilder.field(highlightContent);
searchSourceBuilder.highlighter(highlightBuilder);
```

**请求集合**

聚合可以通过首先创建适当的AggregationBuilder然后将其设置在SearchSourceBuilder。在下面的示例中，我们创建了一个terms对公司名称进行聚合，并对公司雇员的平均年龄进行次聚合：

```text
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
        .field("company.keyword");
aggregation.subAggregation(AggregationBuilders.avg("average_age")
        .field("age"));
searchSourceBuilder.aggregation(aggregation);
```

**分析查询和聚合**

```text
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
searchSourceBuilder.profile(true);
```

**同步执行**

在执行SearchRequest客户端以下列方式等待SearchResponse在继续执行代码之前返回：

```text
SearchResponse searchResponse = client.search(searchRequest);
```

**搜索响应**

这个SearchResponse它是通过执行搜索返回的，它提供了有关搜索执行本身的详细信息以及对返回的文档的访问。首先，有关于请求执行本身的有用信息，如HTTP状态代码、执行时间或请求是否提前终止或超时：

```text
RestStatus status = searchResponse.status();
TimeValue took = searchResponse.getTook();
Boolean terminatedEarly = searchResponse.isTerminatedEarly();
boolean timedOut = searchResponse.isTimedOut();
```

其次，响应还提供了有关碎片级执行的信息，提供了有关受搜索影响的碎片总数以及成功碎片和不成功碎片的统计信息。也可以通过迭代关闭数组来处理可能的故障。ShardSearchFailures如以下示例所示：

```text
int totalShards = searchResponse.getTotalShards();
int successfulShards = searchResponse.getSuccessfulShards();
int failedShards = searchResponse.getFailedShards();
for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
    // failures should be handled here
}
```

**检索SearchHits**

将返回的数据返回到SearchHits对象中

```text
SearchHits hits = searchResponse.getHits();
```

SearchHits包含了总点击次数、最大得分等信息：

```text
long totalHits = hits.getTotalHits();
float maxScore = hits.getMaxScore();
```

可以对SearchHits进行遍历得到单个的SearchHit对象，SearchHit包含索引、类型、数据id和数据的得分情况：

```text
SearchHit[] searchHits = hits.getHits();
for (SearchHit hit : searchHits) {
}

String index = hit.getIndex();
String type = hit.getType();
String id = hit.getId();
float score = hit.getScore();
```

它还可以将数据JSON字符串或键/值对映射的形式返回。

```text
String sourceAsString = hit.getSourceAsString();
Map<String, Object> sourceAsMap = hit.getSourceAsMap();
String documentTitle = (String) sourceAsMap.get("title");
List<Object> users = (List<Object>) sourceAsMap.get("name");
Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
```

**检索高亮**

如果之前设置了高亮显示，可以从SearchHit对象中访问HighlightField高亮实例，每个实例包含一个或多个突出显示的字段

```text
SearchHits hits = searchResponse.getHits();
for (SearchHit hit : hits.getHits()) {
    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
    //获取title高亮显示
    HighlightField highlight = highlightFields.get("title"); 
    //获取高亮显示的字段
    Text[] fragments = highlight.fragments();  
    String fragmentString = fragments[0].string();
}
```

**实际案例**

搜索指定参数的数据

```text
public SearchEventResult selectEventPage(EventEsParam eventEsParam) {
        //设置ES索引和类型
        SearchRequest searchRequest = new SearchRequest(ES_INDEX);
        searchRequest.types(ES_EVENT_ORDER);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页条件
        if (eventEsParam.getPage() == 0 ){
            return new SearchEventResult();
        }
        searchSourceBuilder.from((eventEsParam.getPage() -1) * eventEsParam.getSize()).size(eventEsParam.getSize());
        //最大的查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //如果传入参数有项目，限制项目范围
        if (eventEsParam.getProjectId() != null){
            QueryBuilder queryBuilder = QueryBuilders.termQuery("projectId", eventEsParam.getProjectId());
            boolQueryBuilder.must(queryBuilder);
        }
        //如果查询参数有值则设置模糊查询的参数
        if (eventEsParam.getQueryContent() != null) {
            MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(eventEsParam.getQueryContent(),
                    ES_EVENTNUM, ES_TITLE, ES_DESCRIPTION, ES_ROOTCAUSECONTENT, ES_SOLUTIONCONTENT, ES_REPLYCONTENT);
            //通配模糊搜索
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(ES_EVENTNUM,
                    ES_FUZZY + eventEsParam.getQueryContent().toLowerCase() + ES_FUZZY);
            //权重倍数
            wildcardQueryBuilder.boost(5);
            BoolQueryBuilder boolFiledQuery = QueryBuilders.boolQuery().should(matchQueryBuilder).should(wildcardQueryBuilder);
            boolQueryBuilder.must(boolFiledQuery);
            //设置高亮字段
            this.setHighlightBuilder(searchSourceBuilder, eventEsParam);
        }
        //设置排序
        if (eventEsParam.getSort() != null) {
            searchSourceBuilder.sort(eventEsParam.getSort(), SortOrder.fromString(eventEsParam.getDirection()));
        } else {
            //默认按照分数排序
            searchSourceBuilder.sort("_score", SortOrder.DESC);
        }
        //将searchSourceBuilder放入searchRequest中
        searchRequest.source(searchSourceBuilder);
        SearchEventResult searchEventResult = new SearchEventResult();

        //搜索
        searchSourceBuilder.query(boolQueryBuilder);
        try {
            SearchResponse searchResponse = restClient.search(searchRequest);
            //搜索结果
            SearchHits searchHits = searchResponse.getHits();
            searchEventResult.setTotal(searchHits.getTotalHits());
            searchEventResult.setCurrentPage(eventEsParam.getPage());
            long pages = searchHits.totalHits / eventEsParam.getSize();
            pages = searchHits.totalHits % eventEsParam.getSize() == 0 ? pages : pages + 1;
            searchEventResult.setTotalPage(pages);
            List<EventEsResult> eventEsResultList = new ArrayList<>();
            for (SearchHit searchHit : searchHits) {
                Map eventMap = searchHit.getSourceAsMap();
                try {
                    //将map中数据转化为对象
                    EventEsResult eventEsResult = BeanMapUtils.mapToBean(eventMap, EventEsResult.class);
                    //替换高亮文字
                    eventEsResult.setEventNum(eventEsResult.getEventNum().replace(eventEsParam.getQueryContent(),
                            ES_HIGHLIGHT_PRE_TAGS + eventEsParam.getQueryContent() + ES_HIGHLIGHT_POST_TAGS));
                    this.initHighlightFields(searchHit, eventEsResult);

                    eventEsResultList.add(eventEsResult);
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
            searchEventResult.setElasticDTOList(eventEsResultList);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return searchEventResult;
    }
```

**扩展**

使用fastjson将对象转化为字符串时使用SerializerFeature的属性如下，可以根据需要设置。

```text
QuoteFieldNames,//输出key时是否使用双引号,默认为true

UseSingleQuotes,//使用单引号而不是双引号,默认为false

WriteMapNullValue,//是否输出值为null的字段,默认为false

WriteEnumUsingToString,//Enum输出name()或者original,默认为false

UseISO8601DateFormat,//Date使用ISO8601格式输出，默认为false

WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null

WriteNullStringAsEmpty,//字符类型字段如果为null,输出为"",而非null

WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null

WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null

SkipTransientField,//如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true

SortField,//按字段名称排序后输出。默认为false

WriteTabAsSpecial,//把\t做转义输出，默认为false

PrettyFormat,//结果是否格式化,默认为false

WriteClassName,//序列化时写入类型信息，默认为false。反序列化是需用到

DisableCircularReferenceDetect,//消除对同一对象循环引用的问题，默认为false

WriteSlashAsSpecial,//对斜杠'/'进行转义

BrowserCompatible,//将中文都会序列化为\uXXXX格式，字节数会多一些，但是能兼容IE 6，默认为false

WriteDateUseDateFormat,//全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";

NotWriteRootClassName,//暂不知，求告知

DisableCheckSpecialChar,//一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为fa
```

> 作者：吉诺比利20  
> 原文链接：[https://blog.csdn.net/tc979907461/article/details/105414317](https://link.zhihu.com/?target=https%3A//blog.csdn.net/tc979907461/article/details/105414317)