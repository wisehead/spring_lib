#1.hotWords

```
 @GetMapping("/hotWords")
 
hotWords
--zhbSearchService.queryTopSearchHotWord(start, end, CacheType.HOT_KEY_WORDS);
----if HOT_KEY_WORDS
------redisTemplate.opsForZSet().reverseRangeWithScores(HOT_KEY_PREF, start, end);
----else
------redisTemplate.opsForZSet().reverseRangeWithScores(HOT_CLICK_PREF, start, end);
```

#2. reverseRangeWithScores

```
reverseRangeWithScores(K,start,end)：键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），倒序
```