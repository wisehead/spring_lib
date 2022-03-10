#1.convertQueryBuilder

```
convertQueryBuilder
--BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
--for (ApiRequestFilter filter : request.getFilterList())
----case EQ:
------boolQueryBuilder.must(QueryBuilders.termQuery(filter.getField(), filter.getValue()));
----case LIKE:
------MatchPhraseQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery(filter.getField(), filter.getValue());
------boolQueryBuilder.must(matchQueryBuilder);
----other cases:
```


#2.caller

```
- convertHighLightResponse
- convertQueryBuilder
- convertRepositoryData
- count
- group
- sum

```