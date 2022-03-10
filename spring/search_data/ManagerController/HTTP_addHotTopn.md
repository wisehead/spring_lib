#1.addHotTopn

```
@PostMapping("/hotSearch/add")

addHotTopn
--JSONObject reqObj = JSON.parseObject(req);
--// String sql = "update hot_search_topn h set h.order_no = h.order_no+1 where h.is_on = 0";
--this.zhbManagerService.updateAddForHotTopn(platform);
----this.hotSearchTopnMapper.update_add(platform);
------update hot_search_topn h set h.order_no = h.order_no+1 where h.is_on = 0 and h.platform = #{platform}
--this.zhbManagerService.addHotTopn(hotSearchTopn);
----this.hotSearchTopnMapper.insert(hotSearchTopn);
```