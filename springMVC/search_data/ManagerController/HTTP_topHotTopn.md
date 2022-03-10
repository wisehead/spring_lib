#1.topHotTopn

```
//nobody use
@PostMapping("/hotSearch/top")
topHotTopn
--JSONObject reqObj = JSON.parseObject(req);                  
--String id = reqObj.getString("id");                         
--String platform = reqObj.getString("platform"); //新增加 add   
--HotSearchTopn hotSearchTopn = this.zhbManagerService.getHotTopn(NumberUtil.parseInt(id));
----this.hotSearchTopnMapper.selectByPrimaryKey(hotSearchTopn);
--this.zhbManagerService.updateTopForHotTopn(platform,orderNo);
----this.hotSearchTopnMapper.update_top(platform, orderNo);
--this.zhbManagerService.updateHotTopn(hotSearchTopn1);
----this.hotSearchTopnMapper.updateByPrimaryKeySelective(hotSearchTopn);
```