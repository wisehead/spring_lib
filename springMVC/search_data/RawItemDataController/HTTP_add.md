#1.add

```
@RequestMapping("/raw/item/data")
@PostMapping("/add")

add
--rawItemDataService.save(map);
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