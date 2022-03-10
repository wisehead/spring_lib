#1.appSearch

```
appSearch
--zhbSearchService.appSearch(keyword, sortedType, 0, 30, searchType)
----apiRequest = ApiRequest.newInstance();
----weightMap.put(Searchfileds.TITLE.getName(), 0.9f);     
----weightMap.put(Searchfileds.CONTENT.getName(), 0.1f);   
----weightMap.put(Searchfileds.SUMMARY.getName(), 0.9f);                                                  
----apiRequest.filterMultiMatchWithWeight(weightMap, replaceKeyWord);
----JSONObject recJsons = requestIntent(keyword);
----if (!recJsons.isEmpty()) 
------Object id = recJsons.get("productId");
------apiRequest.filterNotIn(Searchfileds.DATA_ID.getName(), Lists.newArrayList(id));
----Map<String, Object> domainMap = new HashMap<>();
----Set<String> aggKey = domainMap.keySet();
----Optional.ofNullable(searchType).map(type -> apiRequest.filterEqual(Searchfileds.DATA_TYPE.getName(), type)).orElse(apiRequest.filterIn(Searchfileds.DATA_TYPE.getName(), aggKey));
----apiResponse = convertRepositoryData(apiRequest, ZhbSearchIndexEntity.class, apiRequestPage);
----Map<DataType, List<ZhbSearchIndexEntity>> typeListMap = apiResponse.getPagedData().stream().collect(Collectors.groupingBy(ZhbSearchIndexEntity::getDataType));

----for (Map.Entry<DataType, List<ZhbSearchIndexEntity>> mp : typeListMap.entrySet()) {                     
        log.info("==========" + mp.getKey().getName());                                                     
        List<JSONObject> lists = Lists.newArrayList();                                                      
        for (ZhbSearchIndexEntity zhbSearchIndexEntity : mp.getValue()) {                                   
            JSONObject jsonObject = new JSONObject();                                                       
            if (DataType.PRODUCT.getName().equals(mp.getKey().getName())) {                                 
                jsonObject.set("category", zhbSearchIndexEntity.getCategory());                             
                jsonObject.set("type", zhbSearchIndexEntity.getDataType());                                 
                jsonObject.set("productId", zhbSearchIndexEntity.getId());                                  
                jsonObject.set("cCode", zhbSearchIndexEntity.getnCode());                                   
                jsonObject.set("title", zhbSearchIndexEntity.getTitle());                                   
                jsonObject.set("url", zhbSearchIndexEntity.getSourceUrl());                                 
                String description = zhbSearchIndexEntity.getDescription();                                 
                if (StrUtil.isNotBlank(description)) {                                                      
                    description = DelTagsUtil.getTextFromHtml(description);                                 
                }                                                                                           
                jsonObject.set("content", JSONUtil.parse(description));                                     
                jsonObject.set("createTime", zhbSearchIndexEntity.getCreateTime());                         
                jsonObject.set("dataId", zhbSearchIndexEntity.getId());                                     
            } else if (DataType.SERVICE.getName().equals(mp.getKey().getName())) {                          
                jsonObject.set("category", zhbSearchIndexEntity.getCategory());                             
                jsonObject.set("type", zhbSearchIndexEntity.getDataType());                                 
                jsonObject.set("productId", zhbSearchIndexEntity.getId());                                  
                jsonObject.set("cCode", zhbSearchIndexEntity.getnCode());                                   
                jsonObject.set("title", zhbSearchIndexEntity.getTitle());                                   
                jsonObject.set("url", zhbSearchIndexEntity.getSourceUrl());                                 
                String content = zhbSearchIndexEntity.getContent();                                         
                if (StrUtil.isNotBlank(content)) {                                                          
                    content = DelTagsUtil.getTextFromHtml(zhbSearchIndexEntity.getContent());               
                }                                                                                           
                jsonObject.set("content", content);                                                         
                jsonObject.set("createTime", zhbSearchIndexEntity.getCreateTime());                         
                jsonObject.set("redirectUrl", zhbSearchIndexEntity.getRedirectUrl());                       
                jsonObject.set("summary", zhbSearchIndexEntity.getSummary());                               
                jsonObject.set("dataId", zhbSearchIndexEntity.getId());                                     
            } else {                                                                                        
                jsonObject.set("category", zhbSearchIndexEntity.getCategory());                             
                jsonObject.set("type", zhbSearchIndexEntity.getDataType());                                 
                // jsonObject.set("productId",zhbSearchIndexEntity.getId());                                
                //  jsonObject.set("cCode",zhbSearchIndexEntity.getNcode());                                
                jsonObject.set("title", zhbSearchIndexEntity.getTitle());                                   
                jsonObject.set("url", zhbSearchIndexEntity.getSourceUrl());                                 
                String content = zhbSearchIndexEntity.getContent();                                         
                if (StrUtil.isNotBlank(content)) {                                                          
                    content = DelTagsUtil.getTextFromHtml(content);                                         
                }                                                                                           
                jsonObject.set("content", content);                                                         
                jsonObject.set("createTime", zhbSearchIndexEntity.getCreateTime());                         
            }                                                                                               
            lists.add(jsonObject);                                                                          
        }                                                                                                   
        result.put(mp.getKey(), lists);                                                                     
----}//end for                                                                                              
----return ImmutableMap.of("recData", recJsons, "aggData", result, "domainMap", domainMap);
```

#2.requestIntent

```
requestIntent
--HttpUtil.get(intentUrl, params, 1000);
--intentResponse = JSONUtil.toBean(s, IntentResponse.class);
--List<IntentResponse.DataBean.RegexLinevBean> regexLinevBeans = intentResponse.getData().stream().map(IntentResponse.DataBean::getRegexLinev).collect(Collectors.toList());
--String data = regexLinevBeans.get(0).getData();
--JSONObject jsonObject = JSONUtil.parseObj(data);
--Integer productId = Integer.valueOf(String.valueOf(jsonObject.get("id")));
--ApiRequest apiRequest = ApiRequest.newInstance().filterEqual(Searchfileds.DATA_ID.getName(), productId);
--ApiResponse<ZhbSearchIndexEntity> intentRes = convertRepositoryData(apiRequest, ZhbSearchIndexEntity.class, ApiRequestPage.newInstance().paging(0, 10));
--List<ZhbSearchIndexEntity> pagedData = Lists.newArrayList(intentRes.getPagedData());
--ZhbSearchIndexEntity zhbSearchIndexEntity = pagedData.get(0);       
--recObj.set("type", zhbSearchIndexEntity.getDataType());             
--recObj.set("productId", zhbSearchIndexEntity.getDataId());          
--recObj.set("title", zhbSearchIndexEntity.getTitle());               
--recObj.set("url", zhbSearchIndexEntity.getSourceUrl());             
--recObj.set("createTime", zhbSearchIndexEntity.getCreateTime());     
--recObj.set("redirectUrl", zhbSearchIndexEntity.getRedirectUrl());   
--recObj.set("summary", zhbSearchIndexEntity.getSummary());           
--recObj.set("dataId", zhbSearchIndexEntity.getId());                 
--String description = zhbSearchIndexEntity.getDescription();         
--if (StrUtil.isNotBlank(description)) {                              
----description = DelTagsUtil.getTextFromHtml(description);         
--}                                                                   
--recObj.set("content", JSONUtil.parse(description));                 
--return recObj;
```