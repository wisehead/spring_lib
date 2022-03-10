# [StringRedisTemplate操作redis数据](https://www.cnblogs.com/slowcity/p/9002660.html)

### **StringRedisTemplate与RedisTemplate区别点**

*   两者的关系是StringRedisTemplate继承RedisTemplate。
    
*   两者的数据是不共通的；也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，RedisTemplate只能管理RedisTemplate中的数据。
    
*   其实他们两者之间的区别主要在于他们使用的序列化类:

　　　　RedisTemplate使用的是JdkSerializationRedisSerializer    存入数据会将数据先序列化成字节数组然后在存入Redis数据库。 

　　 　  StringRedisTemplate使用的是StringRedisSerializer

*   使用时注意事项：

　　　当你的redis数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用StringRedisTemplate即可。

　　　但是如果你的数据是复杂的对象类型，而取出的时候又不想做任何的数据转换，直接从Redis里面取出一个对象，那么使用RedisTemplate是更好的选择。

*   RedisTemplate使用时常见问题：

　　　　redisTemplate 中存取数据都是字节数组。当redis中存入的数据是可读形式而非字节数组时，使用redisTemplate取值的时候会无法获取导出数据，获得的值为null。可以使用 StringRedisTemplate 试试。

### **RedisTemplate中定义了5种数据结构操作**

```plain
redisTemplate.opsForValue();　　//操作字符串
redisTemplate.opsForHash();　　 //操作hash
redisTemplate.opsForList();　　 //操作list
redisTemplate.opsForSet();　　  //操作set
redisTemplate.opsForZSet();　 　//操作有序set
```

### **StringRedisTemplate常用操作**

```plain
stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间  

stringRedisTemplate.boundValueOps("test").increment(-1);//val做-1操作

stringRedisTemplate.opsForValue().get("test")//根据key获取缓存中的val

stringRedisTemplate.boundValueOps("test").increment(1);//val +1

stringRedisTemplate.getExpire("test")//根据key获取过期时间

stringRedisTemplate.getExpire("test",TimeUnit.SECONDS)//根据key获取过期时间并换算成指定单位 

stringRedisTemplate.delete("test");//根据key删除缓存

stringRedisTemplate.hasKey("546545");//检查key是否存在，返回boolean值 

stringRedisTemplate.opsForSet().add("red_123", "1","2","3");//向指定key中存放set集合

stringRedisTemplate.expire("red_123",1000 , TimeUnit.MILLISECONDS);//设置过期时间

stringRedisTemplate.opsForSet().isMember("red_123", "1")//根据key查看集合中是否存在指定数据

stringRedisTemplate.opsForSet().members("red_123");//根据key获取set集合
```

###  **StringRedisTemplate的使用** 

 springboot中使用注解@Autowired 即可

```plain
@Autowired 
public StringRedisTemplate stringRedisTemplate;	
```

#### **使用样例：**

```plain
@RestController
@RequestMapping("/user")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    private UserService userService;
    
    @Autowired 
    public StringRedisTemplate stringRedisTemplate;    
    
    @RequestMapping("/num")
    public String countNum() {
        String userNum = stringRedisTemplate.opsForValue().get("userNum");
        if(StringUtils.isNull(userNum)){
            stringRedisTemplate.opsForValue().set("userNum", userService.countNum().toString());
        }
        return  userNum;
    }
}
```

posted @ 2018-05-07 15:08  [斑马森林](https://www.cnblogs.com/slowcity/)  阅读(137676)  评论(0)  [编辑](https://i.cnblogs.com/EditPosts.aspx?postid=9002660)  [收藏](javascript:)  [举报](javascript:)