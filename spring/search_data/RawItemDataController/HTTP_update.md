#1.update

```

update
--rawItemDataService.update(map);
--String platform = map.getPlatform();                                                                                 
--//update es                                                                                                          
--if (PlatformEnums.WEB.getName().equals(platform)) {                                                                  
      //save es                                                                                                        
      ZhbWebSearchIndexEntity zhbSearchIndexEntity = rawItemDataService.convertWebIndexEntity(map, DataType.ARTICLE);  
      zhbWebSearchIndexRepository.save(zhbSearchIndexEntity);                                                          
--} else {                                                                                                             
      //save es                                                                                                        
      ZhbSearchIndexEntity zhbSearchIndexEntity = rawItemDataService.convertAppIndexEntity(map, DataType.ARTICLE);     
      zhbSearchIndexRepository.save(zhbSearchIndexEntity);                                                             
--}                                                                                                                    

```