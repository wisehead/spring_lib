#1.class IntentResponse

```java
public class IntentResponse implements Serializable {
    /**
     * Errno : 0
     * Msg :
     * Data : [{"Id":1,"From":0,"To":18,"Flags":0,"Context":"癌症吃什么药","RegexLinev":{"Expr":"癌症.*药","Data":"{\"type\":\"product\", \"id\":\"2041TY\"}"}}]
     */

    private int Errno;
    private String Msg;
    private List<DataBean> Data;
}

```
