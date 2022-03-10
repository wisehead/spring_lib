
# (22条消息) RedisTemplate常用集合使用说明-opsForZSet(六)_凌辰1228的博客-CSDN博客_opsforzset

## 1、[add](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#add-K-V-double-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [V](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) value, double score)

添加元素到变量中同时指定元素的分值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  redisTemplate.opsForZSet().add("zSetValue","A",1); 
2.  redisTemplate.opsForZSet().add("zSetValue","B",3); 
3.  redisTemplate.opsForZSet().add("zSetValue","C",2); 
4.  redisTemplate.opsForZSet().add("zSetValue","D",5); 

##  2、[range](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#range-K-long-long-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, long start, long end)

 获取变量指定区间的元素。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1); 
2.  System.out.println("通过range(K key, long start, long end)方法获取指定区间的元素:" + zSetValue); 

##  3、[rangeByLex](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeByLex-K-org.springframework.data.redis.connection.RedisZSetCommands.Range-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [RedisZSetCommands.Range](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/connection/RedisZSetCommands.Range.html) range)

 用于获取满足非score的排序取值。这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  RedisZSetCommands.Range range = **new** RedisZSetCommands.Range(); 
2.  //range.gt("A"); 
3.  range.lt("D"); 
4.  zSetValue = redisTemplate.opsForZSet().rangeByLex("zSetValue", range); 
5.  System.out.println("通过rangeByLex(K key, RedisZSetCommands.Range range)方法获取满足非score的排序取值元素:" + zSetValue); 

##  4、[rangeByLex](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeByLex-K-org.springframework.data.redis.connection.RedisZSetCommands.Range-org.springframework.data.redis.connection.RedisZSetCommands.Limit-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [RedisZSetCommands.Range](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/connection/RedisZSetCommands.Range.html) range, [RedisZSetCommands.Limit](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/connection/RedisZSetCommands.Limit.html) limit)

 用于获取满足非score的设置下标开始的长度排序取值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  RedisZSetCommands.Limit limit = **new** RedisZSetCommands.Limit(); 
2.  limit.count(2); 
3.  //起始下标为0 
4.  limit.offset(1); 
5.  zSetValue = redisTemplate.opsForZSet().rangeByLex("zSetValue", range,limit); 
6.  System.out.println("通过rangeByLex(K key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit)方法获取满足非score的排序取值元素:" + zSetValue); 

##  5、[add](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#add-K-java.util.Set-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [Set](https://docs.oracle.com/javase/6/docs/api/java/util/Set.html?is-external=true)<[ZSetOperations.TypedTuple](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.TypedTuple.html)<[V](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html)\>> tuples)

 通过TypedTuple方式新增数据。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  ZSetOperations.TypedTuple<Object> typedTuple1 = **new** DefaultTypedTuple<Object>("E",6.0); 
2.  ZSetOperations.TypedTuple<Object> typedTuple2 = **new** DefaultTypedTuple<Object>("F",7.0); 
3.  ZSetOperations.TypedTuple<Object> typedTuple3 = **new** DefaultTypedTuple<Object>("G",5.0); 
4.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = **new** HashSet<ZSetOperations.TypedTuple<Object>>(); 
5.  typedTupleSet.add(typedTuple1); 
6.  typedTupleSet.add(typedTuple2); 
7.  typedTupleSet.add(typedTuple3); 
8.  redisTemplate.opsForZSet().add("typedTupleSet",typedTupleSet); 
9.  zSetValue = redisTemplate.opsForZSet().range("typedTupleSet",0,-1); 
10.  System.out.println("通过add(K key, Set<ZSetOperations.TypedTuple<V>> tuples)方法添加元素:" + zSetValue); 

##  6、[rangeByScore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeByScore-K-double-double-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max)`

 根据设置的score获取区间值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  zSetValue = redisTemplate.opsForZSet().rangeByScore("zSetValue",1,2); 
2.  System.out.println("通过rangeByScore(K key, double min, double max)方法根据设置的score获取区间值:" + zSetValue); 

## 7、[rangeByScore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeByScore-K-double-double-long-long-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max,long offset, long count)`

 根据设置的score获取区间值从给定下标和给定长度获取最终值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  zSetValue = redisTemplate.opsForZSet().rangeByScore("zSetValue",1,5,1,3); 
2.  System.out.println("通过rangeByScore(K key, double min, double max, long offset, long count)方法根据设置的score获取区间值:" + zSetValue); 

##  8、[rangeWithScores](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeWithScores-K-long-long-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, long start, long end)

 获取RedisZSetCommands.Tuples的区间值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().rangeWithScores("typedTupleSet",1,3); 
2.  Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTupleSet.iterator(); 
3.  **while** (iterator.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = iterator.next(); 
5.   Object value = typedTuple.getValue(); 
6.   **double** score = typedTuple.getScore(); 
7.   System.out.println("通过rangeWithScores(K key, long start, long end)方法获取RedisZSetCommands.Tuples的区间值:" + value + "---->" + score ); 
8.  } 

##  9、[rangeByScoreWithScores](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeByScoreWithScores-K-double-double-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max)

 获取RedisZSetCommands.Tuples的区间值通过分值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores("typedTupleSet",5,8); 
2.  iterator = typedTupleSet.iterator(); 
3.  **while** (iterator.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = iterator.next(); 
5.   Object value = typedTuple.getValue(); 
6.   **double** score = typedTuple.getScore(); 
7.   System.out.println("通过rangeByScoreWithScores(K key, double min, double max)方法获取RedisZSetCommands.Tuples的区间值通过分值:" + value + "---->" + score ); 
8.  } 

##  10、[rangeByScoreWithScores](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rangeByScoreWithScores-K-double-double-long-long-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max, long offset, long count)

 获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores("typedTupleSet",5,8,1,1); 
2.  iterator = typedTupleSet.iterator(); 
3.  **while** (iterator.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = iterator.next(); 
5.   Object value = typedTuple.getValue(); 
6.   **double** score = typedTuple.getScore(); 
7.   System.out.println("通过rangeByScoreWithScores(K key, double min, double max, long offset, long count)方法获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值:" + value + "---->" + score ); 
8.  } 

##  11、[count](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#count-K-double-double-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max)

 获取区间值的个数。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **long** count = redisTemplate.opsForZSet().count("zSetValue",1,5); 
2.  System.out.println("通过count(K key, double min, double max)方法获取区间值的个数:" + count); 

##  12、[rank](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#rank-K-java.lang.Object-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key,` `[Object](https://docs.oracle.com/javase/6/docs/api/java/lang/Object.html?is-external=true) o)`

 获取变量中元素的索引,下标开始位置为0。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **long** index = redisTemplate.opsForZSet().rank("zSetValue","B"); 
2.  System.out.println("通过rank(K key, Object o)方法获取变量中元素的索引:" + index); 

##  13、[scan](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#scan-K-org.springframework.data.redis.core.ScanOptions-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [ScanOptions](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ScanOptions.html) options)

 匹配获取键值对，ScanOptions.NONE为获取全部键值对；ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  //Cursor<Object> cursor = redisTemplate.opsForSet().scan("setValue", ScanOptions.NONE); 
2.  Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan("zSetValue", ScanOptions.NONE); 
3.  **while** (cursor.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = cursor.next(); 
5.   System.out.println("通过scan(K key, ScanOptions options)方法获取匹配元素:" + typedTuple.getValue() + "--->" + typedTuple.getScore()); 
6.  } 

##  14、[score](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#score-K-java.lang.Object-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key,` `[Object](https://docs.oracle.com/javase/6/docs/api/java/lang/Object.html?is-external=true) o)`

 获取元素的分值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **double** score = redisTemplate.opsForZSet().score("zSetValue","B"); 
2.  System.out.println("通过score(K key, Object o)方法获取元素的分值:" + score); 

##  15、[zCard](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#zCard-K-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key)`

 获取变量中元素的个数。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **long** zCard = redisTemplate.opsForZSet().zCard("zSetValue"); 
2.  System.out.println("通过zCard(K key)方法获取变量的长度:" + zCard); 

##  16、[incrementScore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#incrementScore-K-V-double-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [V](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) value, double delta)

 修改变量中的元素的分值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **double** incrementScore = redisTemplate.opsForZSet().incrementScore("zSetValue","C",5); 
2.  System.out.print("通过incrementScore(K key, V value, double delta)方法修改变量中的元素的分值:" + incrementScore); 
3.  score = redisTemplate.opsForZSet().score("zSetValue","C"); 
4.  System.out.print(",修改后获取元素的分值:" + score); 
5.  zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1); 
6.  System.out.println("，修改后排序的元素:" + zSetValue); 

##  17、[reverseRange](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRange-K-long-long-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, long start, long end)`

 索引倒序排列指定区间元素。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  zSetValue = redisTemplate.opsForZSet().reverseRange("zSetValue",0,-1); 
2.  System.out.println("通过reverseRange(K key, long start, long end)方法倒序排列元素:" + zSetValue); 

##  18、[reverseRangeByScore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRangeByScore-K-double-double-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max)`

 倒序排列指定分值区间元素。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue",1,5); 
2.  System.out.println("通过reverseRangeByScore(K key, double min, double max)方法倒序排列指定分值区间元素:" + zSetValue); 

##  19、[reverseRangeByScore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRangeByScore-K-double-double-long-long-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max, long offset, long count)

 倒序排列从给定下标和给定长度分值区间元素。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue",1,5,1,2); 
2.  System.out.println("通过reverseRangeByScore(K key, double min, double max, long offset, long count)方法倒序排列从给定下标和给定长度分值区间元素:" + zSetValue); 

##  20、[reverseRangeByScoreWithScores](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRangeByScoreWithScores-K-double-double-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max)

 倒序排序获取RedisZSetCommands.Tuples的分值区间值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("zSetValue",1,5); 
2.  iterator = typedTupleSet.iterator(); 
3.  **while** (iterator.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = iterator.next(); 
5.   Object value = typedTuple.getValue(); 
6.   **double** score1 = typedTuple.getScore(); 
7.   System.out.println("通过reverseRangeByScoreWithScores(K key, double min, double max)方法倒序排序获取RedisZSetCommands.Tuples的区间值:" + value + "---->" + score1 ); 
8.  } 

##  21、[reverseRangeByScoreWithScores](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRangeByScoreWithScores-K-double-double-long-long-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max, long offset, long count)`

 倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度分值区间值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("zSetValue",1,5,1,2); 
2.  iterator = typedTupleSet.iterator(); 
3.  **while** (iterator.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = iterator.next(); 
5.   Object value = typedTuple.getValue(); 
6.   **double** score1 = typedTuple.getScore(); 
7.   System.out.println("通过reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count)方法倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度区间值:" + value + "---->" + score1 ); 
8.  } 

##  22、[reverseRangeWithScores](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRangeWithScores-K-long-long-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, long start, long end)

 索引倒序排列区间值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeWithScores("zSetValue",1,5); 
2.  iterator = typedTupleSet.iterator(); 
3.  **while** (iterator.hasNext()){ 
4.   ZSetOperations.TypedTuple<Object> typedTuple = iterator.next(); 
5.   Object value = typedTuple.getValue(); 
6.   **double** score1 = typedTuple.getScore(); 
7.   System.out.println("通过reverseRangeWithScores(K key, long start, long end)方法索引倒序排列区间值:" + value + "----->" + score1); 
8.  } 

##  23、[reverseRank](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#reverseRank-K-java.lang.Object-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key,` `[Object](https://docs.oracle.com/javase/6/docs/api/java/lang/Object.html?is-external=true) o)`

 获取倒序排列的索引值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **long** reverseRank = redisTemplate.opsForZSet().reverseRank("zSetValue","B"); 
2.  System.out.println("通过reverseRank(K key, Object o)获取倒序排列的索引值:" + reverseRank); 

##  24、[intersectAndStore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#intersectAndStore-K-K-K-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) otherKey, [K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) destKey)

 获取2个变量的交集存放到第3个变量里面。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  redisTemplate.opsForZSet().intersectAndStore("zSetValue","typedTupleSet","intersectSet"); 
2.  zSetValue = redisTemplate.opsForZSet().range("intersectSet",0,-1); 
3.  System.out.println("通过intersectAndStore(K key, K otherKey, K destKey)方法获取2个变量的交集存放到第3个变量里面:" + zSetValue); 

##  25、[intersectAndStore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#intersectAndStore-K-java.util.Collection-K-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [Collection](https://docs.oracle.com/javase/6/docs/api/java/util/Collection.html?is-external=true)<[K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html)\> otherKeys, [K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) destKey)

 获取多个变量的交集存放到第3个变量里面。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  List list = **new** ArrayList(); 
2.  list.add("typedTupleSet"); 
3.  redisTemplate.opsForZSet().intersectAndStore("zSetValue",list,"intersectListSet"); 
4.  zSetValue = redisTemplate.opsForZSet().range("intersectListSet",0,-1); 
5.  System.out.println("通过intersectAndStore(K key, Collection<K> otherKeys, K destKey)方法获取多个变量的交集存放到第3个变量里面:" + zSetValue); 

##  26、[unionAndStore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#unionAndStore-K-K-K-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) otherKey, [K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) destKey)

 获取2个变量的合集存放到第3个变量里面。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  redisTemplate.opsForZSet().unionAndStore("zSetValue","typedTupleSet","unionSet"); 
2.  zSetValue = redisTemplate.opsForZSet().range("unionSet",0,-1); 
3.  System.out.println("通过unionAndStore(K key, K otherKey, K destKey)方法获取2个变量的交集存放到第3个变量里面:" + zSetValue); 

##  27、[unionAndStore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#unionAndStore-K-java.util.Collection-K-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [Collection](https://docs.oracle.com/javase/6/docs/api/java/util/Collection.html?is-external=true)<[K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html)\> otherKeys, [K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) destKey)

 获取多个变量的合集存放到第3个变量里面。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  redisTemplate.opsForZSet().unionAndStore("zSetValue",list,"unionListSet"); 
2.  zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1); 
3.  System.out.println("通过unionAndStore(K key, Collection<K> otherKeys, K destKey)方法获取多个变量的交集存放到第3个变量里面:" + zSetValue); 

##  28、[remove](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#remove-K-java.lang.Object...-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, [Object](https://docs.oracle.com/javase/6/docs/api/java/lang/Object.html?is-external=true)... values)

 批量移除元素根据元素值。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  **long** removeCount = redisTemplate.opsForZSet().remove("unionListSet","A","B"); 
2.  zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1); 
3.  System.out.print("通过remove(K key, Object... values)方法移除元素的个数:" + removeCount); 
4.  System.out.println(",移除后剩余的元素:" + zSetValue); 

##  29、[removeRangeByScore](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#removeRangeByScore-K-double-double-)([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, double min, double max)

 根据分值移除区间元素。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  removeCount = redisTemplate.opsForZSet().removeRangeByScore("unionListSet",3,5); 
2.  zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1); 
3.  System.out.print("通过removeRangeByScore(K key, double min, double max)方法移除元素的个数:" + removeCount); 
4.  System.out.println(",移除后剩余的元素:" + zSetValue); 

##  30、[removeRange](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html#removeRange-K-long-long-)`([K](http://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html) key, long start, long end)`

 根据索引值移除区间元素。

**Java代码  ![收藏代码](assets/1644980349-c2e391e07f05d09684b2d048bad40710.png)** 

1.  removeCount = redisTemplate.opsForZSet().removeRange("unionListSet",3,5); 
2.  zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1); 
3.  System.out.print("通过removeRange(K key, long start, long end)方法移除元素的个数:" + removeCount); 
4.  System.out.println(",移除后剩余的元素:" + zSetValue); 

 在此，RedisTemplate.java类相关的集合操作就介绍完了。