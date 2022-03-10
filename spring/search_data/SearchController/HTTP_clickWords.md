#1.clickWords

```
clickWords
--zhbSearchService.queryTopSearchHotWord(start, end, CacheType.HOT_CLICK_WORDS);
----redisTemplate.opsForZSet().reverseRangeWithScores(HOT_CLICK_PREF, start, end);

```