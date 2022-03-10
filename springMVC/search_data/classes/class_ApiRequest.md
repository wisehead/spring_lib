#1.class ApiRequest

```java
public class ApiRequest implements Serializable {

    private static final long serialVersionUID = -5782205017337866984L;

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<ApiRequestFilter> filterList;

    private List<EsHighLightSetting> esHighLightSettings;
}
```