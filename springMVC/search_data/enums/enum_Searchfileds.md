#1.enum Searchfileds

```java
public enum Searchfileds {
    CONTENT("content"),
    TITLE("title"),
    PUBLISHED_TIME("publishedTime"),
    SUMMARY("summary"),
    DATA_ID("dataId"),
    ID("id"),
    DATA_TYPE("dataType"),
    ;

    private final String name;

    Searchfileds(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

```