#1.enum CacheType

```java
public enum CacheType {
    HOT_KEY_WORDS("HOT_KEY_WORDS"),
    HOT_CLICK_WORDS("HOT_CLICK_WORDS"),
    ;

    private final String name;

    CacheType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
```