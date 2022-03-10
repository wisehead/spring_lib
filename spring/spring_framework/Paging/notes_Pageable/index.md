`PageRequest extends AbstractPageRequest 而 AbstractPageRequset implements Pageable`

# first:

> 写一个接受分页参数的PageRequest类（工具类）（这样就不用自己去写get、set等方法）,当然也可以不写，直接使用对应的参数去接收（Controller层）

```dart
    public static PageRequest getGridPageParams() {
        int pageNumber = 0;
        int pageSize = 20;
        HttpServletRequest request = ServiceUtil.getRequest();
        String P_pageNumber = request.getParameter("P_pageNumber");
        String P_pagesize = request.getParameter("P_pagesize");
        String order = request.getParameter("P_orders");
        String stringDirection = request.getParameter("sord");
        PageRequest pageRequest = null;
        if (null != P_pageNumber) {
            pageNumber = Integer.parseInt(P_pageNumber) - 1;
        }
        if (null != P_pagesize) {
            pageSize = Integer.parseInt(P_pagesize);
        }
        if (order != null && stringDirection != null) {
            Direction direction = Direction.fromString(stringDirection);
            Sort sort = new Sort(direction, order);
            pageRequest = new PageRequest(pageNumber, pageSize, sort);
            return pageRequest;
        }
        pageRequest = new PageRequest(pageNumber, pageSize);
        return pageRequest;
    }   
```

# second：

> 将分页参数传给真正的spring进行管理

```java
    @GetMapping("/list")
    @ResponseBody
    public CommonAjaxResult getDappKindList(DappKind dappKind) {
        Pageable pageable = PageUtil.getGridPageParams();//获得封装的分页data
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page result = dappKindService.findByPage(dappKind, page);//调service层
        PageVo pageVo = PageUtil.parseFromPage(result);//方法如下(更友好的显示给前端)
        if(null != pageVo){
            return CommonAjaxResult.success(pageVo);
        }
        return CommonAjaxResult.error("获取列表失败,请稍后再试");
    }
    public static PageVo parseFromPage(Page page) {
        PageVo pageVo = new PageVo();
        pageVo.setData(page.getContent());
        pageVo.setPageNumber(page.getNumber() + 1);
        pageVo.setPageSize(page.getSize());
        pageVo.setTotal(page.getTotalElements());
        pageVo.setTotalPages(page.getTotalPages());
        return pageVo;
    }
```

# third：

> 在service层中进行查询条件的处理

```kotlin
   public Page findByPage(DappKind dappKind, Pageable pageable) {
       Specification<DappKind> specification = getWhereClause(dappKind);
       return dappKindRepository.findAll(specification, pageable);//调jpa中方法
   }

   /**
    * 查询条件的处理
    */
   private Specification<DappKind> getWhereClause(final DappKind dappKind) {
       return new Specification<DappKind>() {
           @Override
           public Predicate toPredicate(Root<DappKind> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               List<Predicate> list = new ArrayList<>();
               if (StringUtils.isNotBlank(dappKind.getId())) {
                   list.add(cb.equal(root.get("id").as(String.class), dappKind.getId()));
               }
               if (StringUtils.isNotBlank(dappKind.getName())) {
                   list.add(cb.like(root.get("name").as(String.class),  "%" + dappKind.getName() + "%"));
               }
               list.add(cb.equal(root.get("status").as(String.class), DappKind.dappKindStatus.NORMAL));
               Predicate[] p = new Predicate[list.size()];
               return cb.and(list.toArray(p));
           }
       };
   }
```

##### Simple Version (需要自己去写SQL(SQL中不需要关注分页参数)Mybatis帮助你实现了分页)

```cpp
public AjaxResult pageList(Distribute model, Pageable pageable) {
        Page<Distribute> pageObject = distributeService.pageList(param_map, pageable);
}
```