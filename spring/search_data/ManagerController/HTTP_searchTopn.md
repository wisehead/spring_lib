#1.searchTopn

```
searchTopn
--int pno = NumberUtil.parseInt(pageNum);
--int psize = NumberUtil.parseInt(pageSize);
--PageHelper.startPage(pno, psize); // 设定当前页码，以及当前页显示的条数
--List<HotSearchTopn> list = this.zhbManagerService.getHotTopn(platform, keyword);
----hotSearchTopnMapper.getTopn(platform,keyword)
------<select id="getTopn" resultMap="BaseResultMap" >                   
           select * from hot_search_topn where                           
          <if test="platform != null">                                   
              platform = #{platform}                                     
          </if> and                                                      
           (name like '%${word}%' or description like '%${word}%') and   
           is_on = 0 order by order_no asc                               
------</select>                                                                                                              
--PageInfo<HotSearchTopn> pageInfo = new PageInfo<HotSearchTopn>(list);
--return WrapMapper.ok().result(pageInfo).message("success");
```