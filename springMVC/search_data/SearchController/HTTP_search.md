#1.search

```
search
--zhbSearchService.search(keyword, sortedType, pageNumber, pageSize, searchType);
----ApiRequest apiRequest = ApiRequest.newInstance();
----Map<String, Float> weightMap = Maps.newHashMap();            
----weightMap.put(Searchfileds.TITLE.getName(), 0.9f);           
----weightMap.put(Searchfileds.CONTENT.getName(), 0.2f);         
----weightMap.put(Searchfileds.SUMMARY.getName(), 0.9f);         
----apiRequest.filterMultiMatchWithWeight(weightMap, keyword);   
------filter = new ApiRequestFilter(OperatorType.MULTI_MATCH_WITH_WEIGHT, weightMap, value);
------this.filterList.add(filter);
----ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(pageNumber, pageSize);
----apiRequest.filterEqual(Searchfileds.DATA_TYPE.getName(), type)
------this.filter(OperatorType.EQ, field, value)
--------if (OperatorType.isBinary(operator))
----------filterList.add(new ApiRequestFilter(operator, field, Lists.newArrayList(values)));
----convertRepositoryData(apiRequest, ZhbSearchIndexEntity.class, apiRequestPage)
------NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(convertQueryBuilder(apiRequest)).withPageable(convertPageable(apiRequestPage)).withSourceFilter(sourceFilter).build();
------searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, c);
------List<E> collect = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
------convertApiResponse(new PageImpl<>(collect, convertPageable(apiRequestPage), searchHits.getTotalHits()), c);
--------ApiResponse<E> apiResponse = new ApiResponse<>();                      
--------apiResponse.setPage(page.getNumber());                                 
--------apiResponse.setPageSize(page.getSize());                               
--------apiResponse.setTotal(page.getTotalElements());                         
--------apiResponse.setPagedData(BeanMapping.mapList(page.getContent(), c));   
```