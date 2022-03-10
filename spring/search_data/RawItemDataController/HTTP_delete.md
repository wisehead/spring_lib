#1.delete

```
@DeleteMapping("/{id}")
delete
--RawItemData rawItemData = rawItemDataService.findById(id);
--rawItemDataService.deleteById(id);
--//delete es                                                                                                           
--if (PlatformEnums.WEB.getName().equals(rawItemData.getPlatform())) {                                                  
      ZhbWebSearchIndexEntity zhbSearchIndexEntity = zhbWebSearchIndexRepository.findById(String.valueOf(id)).get();    
      zhbWebSearchIndexRepository.delete(zhbSearchIndexEntity);                                                         
--} else {                                                                                                              
      ZhbSearchIndexEntity zhbSearchIndexEntity = zhbSearchIndexRepository.findById(String.valueOf(id)).get();          
      zhbSearchIndexRepository.delete(zhbSearchIndexEntity);                                                            
--}                                                                                                                     

```