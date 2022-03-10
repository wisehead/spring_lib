# [ElasticSearch与SpringBoot集成-ElasticsearchRestTemplate](https://www.cnblogs.com/huanshilang/p/14382279.html)

**1\. 简介**

　　SpringBoot提供了与ElasticSearch的集成的starter包，并封装了ElasticsearchRestTemplate类，还实现了与Java对象与ElasticSearch索引的映射关系，可以采用与JPA相似的Repository接口，来操作ES数据。

　　需要使用maven引用以下依赖：

```plain
　　　　　<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.elasticsearch.client</groupId>
                    <artifactId>transport</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.elasticsearch.client</groupId>
                    <artifactId>elasticsearch-rest-high-level-client</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>transport</artifactId>
            <version>6.5.0</version>
        </dependency>
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>6.5.0</version>
        </dependency>
```

　　注意：以上的依赖版本可以根据你使用的ES的版本来定。比如我当前的ElasticSearch的版本是6.5.0，就需要手动替换成6.5.0版本的jar包。

**2\. 配置文件**

**2.1 application.yml**

　　在SpringBoot项目中application.yml文件中增加以下配置：

```plain
spring:
  elasticsearch:
    rest:
      uris: http://192.168.220.11:9200   ---ES的连接地址，多个地址用逗号分隔
      username:                          ---用户名
      password:                          ---密码
      connection-timeout: 1000           ---连接超时时间
      read-timeout: 1000                 ---读取超时时间
```

**2.2 创建映射对象**

　　我这里定义了一个员工类，并使用@Document定义员工类关联的index索引、typeso因类型，shards主分区，replicas副分区。

　　在@Document中有个属性是createIndex，表示当索引不存在时，操作这个对象会默认创建索引，默认为true。如果你的索引设置比较多，就把createIndex设置为false，再通过其他接口手动触发创建索引操作。

　　@Id 表示索引的主键

　　@Field 用来描述字段的ES数据类型，是否分词等配置，等于Mapping描述

```plain
/**
 * 员工对象
 * <p>
 * 注解：@Document用来声明Java对象与ElasticSearch索引的关系
 * indexName 索引名称
 * type      索引类型
 * shards    主分区数量，默认5
 * replicas  副本分区数量，默认1
 * createIndex 索引不存在时，是否自动创建索引，默认true*/
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "employee_index", type = "employee_type", shards = 1, replicas = 0，createIndex = true)
public class EmployeeBean {

    @Id
    private String id;

    /**
     * 员工编码
     */
    @Field(type = FieldType.Keyword)
    private String studentCode;

    /**
     * 员工姓名
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 员工简历
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String desc;

    /**
     * 员工住址
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private Integer type;

    /**
     * 手机号码
     */
    @Field(type = FieldType.Keyword)
    private String mobile;

}
```

**2.3 创建Repository接口**

　　Repository需要继承ElasticsearchRepository接口，参数<映射对象，主键ID的数据类型>。之后Repository类就可以使用类似JPA的方法操作ElasticSearch数据。

```plain
@Component
public interface EmployeeRepository extends ElasticsearchRepository<EmployeeBean, String> {


}
```

　　我们在操作索引和数据时，需要引用这2个类

```plain
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private EmployeeRepository repository;
```

**3\. 索引操作**

**3.1 判断索引是否存在**

```plain
    /**
     * 判断索引是否存在
     * @return boolean
     */
    public boolean indexExists() {
        return restTemplate.indexExists(EmployeeBean.class);
    }

    /**
     * 判断索引是否存在
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexExists(String indexName) {
        return restTemplate.indexExists(indexName);
    }
```

**3.2 创建索引**

```plain
    /**
     * 创建索引（推荐使用：因为Java对象已经通过注解描述了Setting和Mapping）
     * @return boolean
     */
    public boolean indexCreate() {
        return restTemplate.createIndex(EmployeeBean.class);
    }

    /**
     * 创建索引
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexCreate(String indexName) {
        return restTemplate.createIndex(indexName);
    }
```

 **3.3 删除索引**

```plain
/**
     * 索引删除     * 
@param
 indexName 索引名称     * 
@return
 boolean     
*/
public
boolean
 indexDelete(String indexName) {        
return
 restTemplate.deleteIndex(indexName);    }
```

**4\. 数据操作**

**4.1 新增数据**

```plain
    /**
     * 新增数据
     * @param bean 数据对象
     */
    public void save(EmployeeBean bean) {
        repository.save(bean);
    }

    /**
     * 批量新增数据
     * @param list 数据集合
     */
    public void saveAll(List<EmployeeBean> list) {
        repository.saveAll(list);
    }
```

**4.2 修改数据**

```plain
    /**
     * 修改数据
     * @param indexName 索引名称
     * @param type      索引类型
     * @param bean 修改数据对象，ID不能为空
     */
    public void update(String indexName, String type, EmployeeBean bean) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.retryOnConflict(1);//冲突重试
        updateRequest.doc(JSONUtil.toJsonStr(bean), XContentType.JSON);
        updateRequest.routing(bean.getId());//默认是_id来路由的，用来路由到不同的shard，会对这个值做hash，然后映射到shard。所以分片
        UpdateQuery query = new UpdateQueryBuilder().withIndexName(indexName).withType(type).withId(bean.getId())
                .withDoUpsert(true)//不加默认false。true表示更新时不存在就插入
                .withClass(EmployeeBean.class).withUpdateRequest(updateRequest).build();
        UpdateResponse updateResponse = restTemplate.update(query);
    }
```

**4.3 删除数据**

```plain
    /**
     * 根据ID，删除数据
     * @param id 数据ID
     */public void deleteById(String id) {
        repository.deleteById(id);
    }

    /**
     * 根据对象删除数据，主键ID不能为空
     * @param bean 对象
     */public void deleteByBean(EmployeeBean bean) {
        repository.delete(bean);
    }

    /**
     * 根据对象集合，批量删除
     * @param beanList 对象集合
     */public void deleteAll(List<EmployeeBean> beanList) {
        repository.deleteAll(beanList);
    }

    /**
     * 删除所有
     */public void deleteAll() {
        repository.deleteAll();
    }
    
    /**
     * 根据条件，自定义删除（在setQuery中的条件，可以根据需求自由拼接各种参数，与查询方法一样）
     * @param indexName 索引
     * @param type      索引类型
     */public void delete(String indexName, String type) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setIndex(indexName);
        deleteQuery.setType(type);//建index没配置就是类名全小写
        deleteQuery.setQuery(new BoolQueryBuilder().must(QueryBuilders.termQuery("mobile","13526568454")));
        restTemplate.delete(deleteQuery);
    }
```

**4.4 批量操作**

```plain
    /**
     * 批量新增
     * @param indexName  索引名称
     * @param type       索引类型
     * @param beanList 新增对象集合
     */public void batchSave(String indexName, String type, List<EmployeeBean> beanList) {
        List<IndexQuery> queries = new ArrayList<>();
        IndexQuery indexQuery;
        int counter = 0;
        for (EmployeeBean item : beanList) {
            indexQuery = new IndexQuery();
            indexQuery.setId(item.getId());
            indexQuery.setSource(JSONUtil.toJsonStr(item));
            indexQuery.setIndexName(indexName);
            indexQuery.setType(type);
            queries.add(indexQuery);
            //分批提交索引
            if (counter != 0 && counter % 1000 == 0) {
                restTemplate.bulkIndex(queries);
                queries.clear();
                System.out.println("bulkIndex counter : " + counter);
            }
            counter++;
        }
        //不足批的索引最后不要忘记提交
        if (queries.size() > 0) {
            restTemplate.bulkIndex(queries);
        }
        restTemplate.refresh(indexName);
    }

    /**
     * 批量修改
     * @param indexName 索引名称
     * @param type      索引类型
     * @param beanList 修改对象集合
     */public void batchUpdate(String indexName, String type, List<EmployeeBean> beanList) {
        List<UpdateQuery> queries = new ArrayList<>();
        UpdateQuery updateQuery;
        UpdateRequest updateRequest;
        int counter = 0;
        for (EmployeeBean item : beanList) {
            updateRequest = new UpdateRequest();
            updateRequest.retryOnConflict(1);//冲突重试
            updateRequest.doc(item);
            updateRequest.routing(item.getId());

            updateQuery = new UpdateQuery();
            updateQuery.setId(item.getId());
            updateQuery.setDoUpsert(true);
            updateQuery.setUpdateRequest(updateRequest);
            updateQuery.setIndexName(indexName);
            updateQuery.setType(type);
            queries.add(updateQuery);
            //分批提交索引
            if (counter != 0 && counter % 1000 == 0) {
                restTemplate.bulkUpdate(queries);
                queries.clear();
                System.out.println("bulkIndex counter : " + counter);
            }
            counter++;
        }
        //不足批的索引最后不要忘记提交
        if (queries.size() > 0) {
            restTemplate.bulkUpdate(queries);
        }
        restTemplate.refresh(indexName);
    }
```

**4.5 数据查询**

```plain
    /**
     * 数据查询，返回List
     * @param field 查询字段
     * @param value 查询值
     * @return List<EmployeeBean>
     */
    @Override
    public List<EmployeeBean> queryMatchList(String field, String value) {
        MatchQueryBuilder builder = QueryBuilders.matchQuery(field, value);
        SearchQuery searchQuery = new NativeSearchQuery(builder);
        return restTemplate.queryForList(searchQuery, EmployeeBean.class);
    }

    /**
     * 数据查询，返回Page
     * @param field 查询字段
     * @param value 查询值
     * @return AggregatedPage<EmployeeBean>
     */
    @Override
    public AggregatedPage<EmployeeBean> queryMatchPage(String field, String value) {
        MatchQueryBuilder builder = QueryBuilders.matchQuery(field, value);
        SearchQuery searchQuery = new NativeSearchQuery(builder).setPageable(PageRequest.of(0, 100));
        AggregatedPage<EmployeeBean> page = restTemplate.queryForPage(searchQuery, EmployeeBean.class);

        long totalElements = page.getTotalElements(); // 总记录数
        int totalPages = page.getTotalPages();  // 总页数
        int pageNumber = page.getPageable().getPageNumber(); // 当前页号
        List<EmployeeBean> beanList = page.toList();  // 当前页数据集
        Set<EmployeeBean> beanSet = page.toSet();  // 当前页数据集
        return page;
    }
```

　　QueryBuilders对象是用于创建查询方法的，支持多种查询类型，常用的查询API包括以下方法：

```plain
    /**
     * 关键字匹配查询
     *
     * @param name 字段的名称
     * @param value 查询值
     */
    public static TermQueryBuilder termQuery(String name, String value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, int value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, long value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, float value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, double value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, boolean value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, Object value) {
        return new TermQueryBuilder(name, value);
    }

    /**
     * 关键字查询，同时匹配多个关键字
     *
     * @param name   字段名称
     * @param values  查询值
     */
    public static TermsQueryBuilder termsQuery(String name, String... values) {
        return new TermsQueryBuilder(name, values);
    }

    /**
     * 创建一个匹配多个关键字的查询，返回boolean
     *
     * @param fieldNames 字段名称
     * @param text           查询值
     */
    public static MultiMatchQueryBuilder multiMatchQuery(Object text, String... fieldNames) {
        return new MultiMatchQueryBuilder(text, fieldNames); // BOOLEAN is the default
    }

    /**
     * 关键字，精确匹配
     *
     * @param name 字段名称
     * @param text    查询值
     */
    public static MatchQueryBuilder matchQuery(String name, Object text) {
        return new MatchQueryBuilder(name, text);
    }

    /**
     * 关键字范围查询（后面跟范围条件）
     *
     * @param name 字段名称
     */
    public static RangeQueryBuilder rangeQuery(String name) {
        return new RangeQueryBuilder(name);
    }

    /**
     * 判断字段是否有值
     *
     * @param name 字段名称
     */
    public static ExistsQueryBuilder existsQuery(String name) {
        return new ExistsQueryBuilder(name);
    }

    /**
     * 模糊查询
     *
     * @param name  字段名称
     * @param value  查询值
     */
    public static FuzzyQueryBuilder fuzzyQuery(String name, String value) {
        return new FuzzyQueryBuilder(name, value);
    }

    /**
     * 组合查询对象，可以同时引用上面的所有查询对象
     */
    public static BoolQueryBuilder boolQuery() {
        return new BoolQueryBuilder();
    }
```

**4.6 聚合查询**

　　AggregationBuilders对象是用于创建聚合方法的，支持多种查询类型，常用的查询API包括以下方法： 

```plain
    /**
     * 根据字段聚合，统计该字段的每个值的数量
     */
    public static TermsAggregationBuilder terms(String name) {
        return new TermsAggregationBuilder(name, null);
    }

    /**
     * 统计操作的，过滤条件
     */
    public static FilterAggregationBuilder filter(String name, QueryBuilder filter) {
        return new FilterAggregationBuilder(name, filter);
    }

    /**
     * 设置多个过滤条件
     */
    public static FiltersAggregationBuilder filters(String name, KeyedFilter... filters) {
        return new FiltersAggregationBuilder(name, filters);
    }

    /**
     * 统计该字段的数据总数
     */
    public static ValueCountAggregationBuilder count(String name) {
        return new ValueCountAggregationBuilder(name, null);
    }

    /**
     * 计算平均值
     */
    public static AvgAggregationBuilder avg(String name) {
        return new AvgAggregationBuilder(name);
    }

    /**
     * 计算最大值
     */
    public static MaxAggregationBuilder max(String name) {
        return new MaxAggregationBuilder(name);
    }

    /**
     * 计算最小值
     */
    public static MinAggregationBuilder min(String name) {
        return new MinAggregationBuilder(name);
    }

    /**
     * 计算总数
     */
    public static SumAggregationBuilder sum(String name) {
        return new SumAggregationBuilder(name);
    }
```

posted @ 2021-02-06 17:49  [闲人鹤](https://www.cnblogs.com/huanshilang/)  阅读(14719)  评论(0)  [编辑](https://i.cnblogs.com/EditPosts.aspx?postid=14382279)  [收藏](javascript:)  [举报](javascript:)