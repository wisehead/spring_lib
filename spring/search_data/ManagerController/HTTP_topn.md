#1.topn

```
topn
--this.zhbManagerService.topn(platform, option, keyword, start, end, type, NumberUtil.parseInt(pageNo), NumberUtil.parseInt(pageSize))
----qBuilder = QueryBuilders.boolQuery()        //查询条件 可以任意组合      
            .must(QueryBuilders.rangeQuery("DataStandTime")        
                    .from(start + " 00:00:00")                     
                    .to(end + " 23:59:59"))                        
            .must(QueryBuilders.matchQuery("Keywords", keyword))   
            .filter(QueryBuilders.termQuery("DataType", dataType));
----// 1、创建search请求                                                                                
----SearchRequest searchRequest = new SearchRequest();                                             
                                                                                                   
----// 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。                                     
----SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()                                  
            .query(qBuilder)                                                                       
            .size(0)                                                                               
            .timeout(new TimeValue(60000));                                                        
----//字段值项分组聚合                                                                                     
----AggregationBuilder days = AggregationBuilders.terms("countGp")                                 
            .field("Keywords.raw").size(100);//.order(BucketOrder.aggregation("count", true));     
                                                                                                   
----sourceBuilder.aggregation(days);                                                               
----searchRequest.source(sourceBuilder);                                                           
----log.info(sourceBuilder.toString());                                                            
----searchRequest.indices(indexs);
----searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
----//-----                                                                         
----int stnum = (pageNo - 1) * pageSize;                                            
----if (RestStatus.OK.equals(searchResponse.status())) {                            
        // 获取聚合结果                                                                   
        Aggregations aggregations = searchResponse.getAggregations();               
        //获取配置                                                                      
        ParsedStringTerms parsedStringTerms = aggregations.get("countGp");          
        List<? extends Terms.Bucket> buckets = parsedStringTerms.getBuckets();      
        int num = 0;                                                                
        int size = 0;                                                               
                                                                                    
                                                                                    
        for (Terms.Bucket bucket : buckets) {                                       
            //key的数据                                                                
            String key = bucket.getKey().toString();                                
            long docCount = bucket.getDocCount();                                   
            //获取数据                                                                  
            System.out.println(key + ":" + docCount);                               
            JSONObject obj = new JSONObject();                                      
            if (StringUtil.isBlank(key)) {                                          
                continue;                                                           
            }                                                                       
            sum++;                                                                  
            num = num + 1;                                                          
                                                                                    
            if (num <= stnum) {                                                     
                continue;                                                           
            }                                                                       
            size = size + 1;                                                        
            if (size <= pageSize) {                                                 
                obj.put("key", key);                                                
                obj.put("cnt", docCount);                                           
                results.add(obj);                                                   
            }                                                                       
                                                                                    
        }                                                                           
----}          
----return ImmutableMap.of("list", results, "total", sum, "pageNum", pageNo, "pageSize", pageSize);                                                                     
```