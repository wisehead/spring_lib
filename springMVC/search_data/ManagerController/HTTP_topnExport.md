#1.topnExport

```
topnExport
--Map<String, Object> mps = this.zhbManagerService.topn(platform, option, keyword, start, end, "search", NumberUtil.parseInt(pageNo), NumberUtil.parseInt(pageSize));    
--Map<String, Object> mps1 = this.zhbManagerService.topn(platform,option, keyword, start, end, "product", NumberUtil.parseInt(pageNo), NumberUtil.parseInt(pageSize));   
--Map<String, Object> mps2 = this.zhbManagerService.topn(platform,option, keyword, start, end, "service", NumberUtil.parseInt(pageNo), NumberUtil.parseInt(pageSize));   
--filePath = this.zhbManagerService.exportData(mps, mps1, mps2, "all");                                                                                                  
----JSONArray jsonArray = JSON.parseArray(data.get("list").toString());                                                 
----List<JSONObject> list = jsonArray.stream().map(x -> JSON.parseObject(x.toString())).collect(Collectors.toList());   
                                                                                                                         
----JSONArray jsonArray1 = JSON.parseArray(products.get("list").toString());                                            
----List<JSONObject> list1 = jsonArray1.stream().map(x -> JSON.parseObject(x.toString())).collect(Collectors.toList());                                                                                                                          
----JSONArray jsonArray2 = JSON.parseArray(services.get("list").toString());                                            
----List<JSONObject> list2 = jsonArray2.stream().map(x -> JSON.parseObject(x.toString())).collect(Collectors.toList()); 
----dataPath = excelPath + "/data_" + DateUtil.currentSeconds() + ".xlsx";                                              
----FileUtil.writeExcel(list, list1, list2, 3, dataPath);                                                               
--JSONObject jsonObject = new JSONObject();         
--File f = new File(filePath);                      
--jsonObject.put("fileUrl", http + f.getName());    
--jsonObject.put("type", type);                     
--return WrapMapper.ok().result(jsonObject);        
--

```