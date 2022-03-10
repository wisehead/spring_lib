
# ElasticsearchRestTemplate使用 - 简书

在新版的SpringBoot项目中，在这个包下，推荐使用的是ElasticsearchRestTemplate这个类(ElasticsearchTemplate不推荐使用了)，和之前的用法有些不同。

### pom文件如下（boot版本2.4.5）

```xml
     <!--boot版本2.4.5-->
      <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
```

## ElasticsearchRestTemplate使用如下

### 模糊查询的index

比如我们需要匹配多个index，我们可以这样定义,（这样我们查询就可以查到index开头的所有索引）

```kotlin
@Document(indexName = "index*")
public class IndexModel {
}
```

### 查询name为tom的,样例如下

```java
NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "tom")
                .build();
SearchHits<IndexModel> searchHits =  template.search(
                nativeSearchQuery, IndexModel.class);
```

### 查询info下面like为swim的,样例如下

```java
NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("info.like", "swim")
                .build();
SearchHits<IndexModel> searchHits =  template.search(
                nativeSearchQuery, IndexModel.class);
```

### 多条件and查询，样例如下

```cpp
NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("info.like", "swim")
                .build();
 NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery().must(
                        QueryBuilders.matchPhraseQuery("info.like", "swim"))
                        .must(QueryBuilders.matchPhraseQuery("info.name", "tom"))
                .build();
```