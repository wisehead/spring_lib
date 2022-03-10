#1.class ApiResponse

```java
public class ApiResponse<E> implements Serializable {

    private static final long serialVersionUID = 2490364023692403771L;
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /* 回写请求时的分页设置 */
    private int page;
    private int pageSize;

    /* 满足条件的记录总数 */
    private long total = 0;

    private Collection<E> pagedData;
}
```