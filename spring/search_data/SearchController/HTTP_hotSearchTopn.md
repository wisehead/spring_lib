#1.hotSearchTopn

```
hotSearchTopn
--List<HotSearchTopn> hotSearchTopns = zhbSearchService.queryHotSearch("miniApps",Integer.valueOf(topn));
----List<HotSearchTopn> hotSearchTopns = this.hotSearchTopnMapper.getHotTopn(platform,topn);
------select * from hot_search_topn where is_on = 0 and platform = #{platform} order by order_no asc limit #{topn}

```


#2.hotSearchTopnMapper

```
public interface HotSearchTopnMapper extends Mapper<HotSearchTopn>

```

#3.main/resources/mapper/HotSearchTopnMapper.xml

```
    <select id="getHotTopn" resultMap="BaseResultMap" >
        select * from hot_search_topn where is_on = 0 and platform = #{platform} order by order_no asc limit #{topn}
    </select>
    <select id="getTopn" resultMap="BaseResultMap" >
         select * from hot_search_topn where
        <if test="platform != null">
            platform = #{platform}
        </if> and
         (name like '%${word}%' or description like '%${word}%') and
         is_on = 0 order by order_no asc
    </select>
```