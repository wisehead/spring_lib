#1.dataManage

```
@PostMapping("/dataManage/delete")

dataManage
--JSONObject reqObj = JSON.parseObject(req);                                                        
--String fileId = reqObj.getString("fileId");                                                       
--rawItemDataService.deleteById(NumberUtil.parseLong(fileId));    
----mapper.deleteByPrimaryKey(id);                                  
--//es delete                                                                                       
--ZhbSearchIndexEntity zhbSearchIndexEntity = zhbSearchIndexRepository.findById(fileId).get();      
--zhbSearchIndexRepository.delete(zhbSearchIndexEntity);                                            

```