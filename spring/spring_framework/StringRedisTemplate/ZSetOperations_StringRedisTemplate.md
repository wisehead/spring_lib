#1.ZSetOperations

```
ZSetOperations 操作解释 拷贝过来的 哈哈哈
有序集合，默认按照score升序排列，存储格式K(1)==V(n)，V(1)=S(1)(K=key,V=value,S=score)

1.add(K,V,S)：添加

2.count(K,Smin,Smax)：键为K的集合，Smin<=score<=Smax的元素个数

3.size(K)：键为K的集合元素个数

4.score(K,obj)：键为K的集合，value为obj的元素分数

5.incrementScore(K,V,delta)：元素分数增加，delta是增量

6.intersectAndStore(K,otherK[s],destK)：K集合与otherK[s]集合，共同的交集元素存到destK（复制），返回元素个数

  unionAndStore(K,otherK[s],destK)：K集合与otherK[s]集合，共同的并集元素存到destK（复制），返回元素个数

7.range(K,start,end)：键为K的集合，索引start<=index<=end的元素子集，正序

  reverseRange(K,start,end)：键为K的集合，索引start<=index<=end的元素子集，倒序

8.rangeByScore(K,Smin,Smax,[offset],[count])：键为K的集合，offerset和count是限制条件，从索引1开始找到count个元素=子集，从子集中找到Smin<=score<=Smax的元素集合，返回Set<V>，正序

  reverseRangeByScore(K,Smin,Smax,[offset],[count])：键为K的集合，offerset和count是限制条件，从索引1开始找到count个元素=子集，从子集中找到Smin<=score<=Smax的元素集合，返回Set<V>，倒序

9.rangeByScoreWithScores(K,Smin,Smax,[offset],[count])：键为K的集合，offerset和count是限制条件，从索引1开始找到count个元素=子集，从子集中找到Smin<=score<=Smax的元素集合，返回泛型接口（包括score和value），正序

  reverseRangeByScoreWithScores(K,Smin,Smax,[offset],[count])：键为K的集合，offerset和count是限制条件，从索引1开始找到count个元素=子集，从子集中找到Smin<=score<=Smax的元素集合，返回泛型接口（包括score和value），倒序

10.rangeWithScores(K,start,end)：键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），正序

  reverseRangeWithScores(K,start,end)：键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），倒序

11.rank(K,obj)：键为K的集合，value为obj的元素索引，正序

 reverseRank(K,obj)：键为K的集合，value为obj的元素索引，倒序

12.remove(K,obj)：删除，键为K的集合，value为obj的元素

13.removeRange(K,start,end)：删除，键为K的集合，索引start<=index<=end的元素子集

14.removeRangeByScore(K,Smin,Smax)：删除，键为K的集合，Smin<=score<=Smax的元素，返回删除个数
```