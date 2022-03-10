#1.HTTP dayStat

```
getStatByDay
--zhbManagerService.statByDay(platform, option, start, end, "search");
--qBuilder = QueryBuilders.boolQuery()        //查询条件 可以任意组合                                       
          .must(QueryBuilders.rangeQuery("DataStandTime")                                         
                  .from(start + " 00:00:00")                                                      
                  .to(end + " 23:59:59")).filter(QueryBuilders.termQuery("DataType", dataType));  
--SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                    .query(qBuilder)
                    .size(0)
                    .timeout(new TimeValue(60000));                  
--//字段值项分组聚合                                                                           
--DateHistogramAggregationBuilder days = AggregationBuilders.                          
            dateHistogram("day").field("DataStandTime").dateHistogramInterval(type);     
--sourceBuilder.aggregation(days);                                                     
--searchRequest.source(sourceBuilder);                                                 
--searchRequest.indices(indexs);
--SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
--if (RestStatus.OK.equals(searchResponse.status())) {                         
      // 获取聚合结果                                                                
      Aggregations aggregations = searchResponse.getAggregations();            
      Aggregation day = aggregations.get("day");                               
      JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(day));        
      JSONArray buckets = jsonObject.getJSONArray("buckets");                  
      for (Object bucks : buckets) {                                           
          JSONObject buck = (JSONObject) bucks;                                
          String keyAsString = buck.getString("keyAsString");                  
          String docCount = buck.getString("docCount");                        
          // System.out.println(keyAsString + "\t-->\t"+docCount);             
          JSONObject obj = new JSONObject();                                   
          if (StrUtil.isBlank(option)) {                                       
              obj.put("key", keyAsString.replace("T00:00:00.000Z", ""));       
          } else {                                                             
              obj.put("key", keyAsString);                                     
          }                                                                    
                                                                               
          obj.put("cnt", docCount);                                            
          results.add(obj);                                                    
      }                                                                        
--}                                                                            
--//---排序---                                                        
--Collections.sort(results, (a, b) -> {                             
      int flag = a.getString("key").compareTo(b.getString("key"));  
      if (flag <= 0) {                                              
          return -1;                                                
      } else {                                                      
          return 1;                                                 
      }                                                             
--});                                                               
```