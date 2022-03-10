#1.queryTopSearchHotWord

```cpp
queryTopSearchHotWord
--if HOT_KEY_WORDS
----redisTemplate.opsForZSet().reverseRangeWithScores(HOT_KEY_PREF, start, end);
--else
----redisTemplate.opsForZSet().reverseRangeWithScores(HOT_CLICK_PREF, start, end);
```

#2.caller

```
- ZhbSearchController.hotWords
- ZhbSearchController.clickWords
```