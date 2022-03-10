#1.deleteHotTopn

```
 @GetMapping("/hotSearch/delete")
 
deleteHotTopn
--this.zhbManagerService.deleteHotTopn(id);
----HotSearchTopn hotSearchTopn = new HotSearchTopn();       
----hotSearchTopn.setId(NumberUtil.parseInt(id));            
----this.hotSearchTopnMapper.delete(hotSearchTopn);   


```