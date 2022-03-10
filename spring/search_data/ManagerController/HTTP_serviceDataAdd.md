#1.serviceDataAdd

```
@PostMapping("/dataManage/add")
serviceDataAdd
--JSONObject reqObj = JSON.parseObject(req);
--RawItemData rawItemData = new RawItemData();
--rawItemDataService.save(rawItemData);
--zhbSearchIndexRepository.save(convertZhbIndex(rawItemData));
```