#1.class ZhbSearchIndexEntity

```java
public class ZhbSearchIndexEntity implements BaseEntity {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String dataId;

    @Field(type = FieldType.Keyword)
    private String sourceUrl;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    private Date createTime;

    @Field(type = FieldType.Keyword)
    private DataType dataType;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    private Date publishedTime;

    @Field(type = FieldType.Text)
    private String summary;

    @Field(type = FieldType.Text)
    private String ext;

    @Field(type = FieldType.Text)
    private String classifyName;

    @Field(type = FieldType.Text)
    private String classifyDesc;

    @Field(type = FieldType.Text)
    private String description;
/*
    @Field(type = FieldType.Text)
    private String detail;*/

    @Field(type = FieldType.Keyword)
    private String publishTime;

    @Field(type = FieldType.Keyword)
    private String nCode;

    @Field(type = FieldType.Text)
    private String photo;

    @Field(type = FieldType.Text)
    private String redirectUrl;
}    
```