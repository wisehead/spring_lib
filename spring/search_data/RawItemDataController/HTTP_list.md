#1.list

```
@GetMapping("/list")

list
--PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));                                                                          
--Example example = new Example(RawItemData.class);                                                                                                     
--Example.Criteria criteria = example.createCriteria();                                                                                                 

--if (StrUtil.isNotBlank(dataType)) {                                                                                                                   
----criteria.andEqualTo("dataType", dataType);                                                                                                        
--}                                                                                                                                                     

--if (!"all".equals(platform)) {                                                                                                                        
----criteria.andEqualTo("platform", platform);                                                                                                        
--}                                                                                                                                                     

--if (StrUtil.isNotBlank(keyword)) {                                                                                                                    
----String likeCondition = String.format("%s%s%s", "%", keyword, "%");                                                                                
----criteria.andLike("name", likeCondition);                                                                                                          
--} 
                                                                                                                                                    
--List<RawItemData> list = rawItemDataService.findByExample(example);                                                                                   
--PageInfo<RawItemData> pageInfo = new PageInfo<>(list);                                                                                                
--//ApiResponse<RawItemData> apiResponse = new ApiResponse<>(pageInfo.getPages(), pageInfo.getPageSize(), pageInfo.getList(), pageInfo.getTotal());     
--return WrapMapper.ok().result(pageInfo);                                                                                                              

```