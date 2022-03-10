原文地址：[https://my.oschina.net/u/3829444/blog/1818376](https://my.oschina.net/u/3829444/blog/1818376)

# Ⅰ

公司做项目,都是使用[Mybatis](https://so.csdn.net/so/search?q=Mybatis&spm=1001.2101.3001.7020), 个人不太喜欢xml的方式,自然也尝试无xml的Mybatis,在之前写的一篇多数据源+Mybatis+无xml配置.

不废话,本篇记录使用JPA遇到的问题笔记.

# Ⅱ

写到Dao层,继承[JpaRepository](https://so.csdn.net/so/search?q=JpaRepository&spm=1001.2101.3001.7020),实现分页时候的问题.

```java
public interface HelloRepository extends JpaRepository<Hello,Integer>
```

单元测试的时候,用到了PageRequest

```java
PageRequest pageRequest=new PageRequest(0,10);
```

这明显是过期了,不能容忍的强迫症犯发,点进去API查看

发现3个构造器都@Deprecated注解,说明过时的调用方式,不推荐使用.

再往下看,可以找到static的方法,是用来建造PageRequest对象的,内部调用的也是原来的构造器.

> 把原来的 new PageRequest 改成 PageRequest.of(0,10);

有3个重载 对应 3个构造器.

官方API说明: since 2.0, use [`of(...)`](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html#of-int-int-org.springframework.data.domain.Sort-) instead.

2.0版本后,使用 of(...) 方法代替 PageRequest(...)构造器

URL :  ↓↓↓

https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html

# Ⅲ

扩展学习一下API的设计思想

分析: PageRequest构造器中的参数最多是4个参数,其中必填的是2个参数(page,rows)

另外2个参数是选填direction(正序倒序),properties(排序字段)

设计这该类的构造器的方法有几种方案:

1.重叠构造器模式

2.JavaBeans模式

3.构建器模式

其他.......

## 重叠构造器模式

构建结构 : 由参数少的构造器向参数多的构造器方向调用, 最终调用参数多的构造器创建对象.

好处: 只要有必要参数,就可以创建对象; 如果还知道了选填的控制参数,就可以选择性的创建可控的对象

坏处: 如果参数多了,就是一大坨,一坨接一坨,闪闪发光

```java
public class LotShit {
    private Double wight;
    private String color;
    private String whoS;
    private boolean sweet;
 
    public LotShit(Double wight) {
        this(wight,"golden");
    }
 
    public LotShit(Double wight, String color) {
        this(wight,color,"大黄");
    }
 
    public LotShit(Double wight, String color,String whoS) {
        this(wight,color,whoS,true);
    }
 
    public LotShit(Double wight, String color, String whoS, boolean sweet) {
        this.wight = wight;
        this.color = color;
        this.whoS = whoS;
        this.sweet = sweet;
    }
}
```

## JavaBeans模式

构建结构 : 遵循特定规则的java类,必须有一个无参的构造函数、属性必须私有化private、私有化的属性必须通过公有类型的方法(get/set)对外暴露

好处 : 创建简单,想要什么自己加什么

坏处 : 构造过程可以被set改变字段值,不能保证其安全

```java
public class LotShit {
    private Double wight;
    private String color;
    private String whoS;
    private boolean sweet;
    
    public LotShit(){}
 
    //getter & setter ...
}
```

## 构建器模式

构建结构 : 一个复杂对象的构建和表示分离，使得同样的构建过程可以创造创建不同的表示

好处 : 可以灵活构造复杂对象实例,适用于多个构造器参数,且参数包含必选和可选参数

坏处 : 写起来复杂了,构建实例成本高了

只有2个参数,还是不要用了,如果你不是闲得慌的话

```java
public class LotShit {
    private Double wight;
    private String color;
    private String whoS;
    private boolean sweet;
 
    private LotShit(Shit shit) {
        this.wight=shit.wight;
        this.color=shit.color;
        this.whoS=shit.whoS;
        this.sweet=shit.sweet;
    }
 
    public static class Shit {
        private Double wight;
        private String color;
        private String whoS;
        private boolean sweet;
 
 
        public LotShit create() {
            return new LotShit(this);
        }
 
        private Shit setWight(Double wight) {
            this.wight = wight;
            return this;
        }
 
        private Shit setColor(String color) {
            this.color = color;
            return this;
        }
 
        private Shit setSweet(boolean sweet) {
            this.sweet = sweet;
            return this;
        }
 
        private Shit setWhoS(String whoS) {
            this.whoS = whoS;
            return this;
        }
    }
}
```

# Ⅳ

说了这么多,似乎PageRequest用的静态方法代替构造器的方式和上面没有什么关系,哈哈哈

这里区别于工厂方法模式中的,它只是一个静态的方法,工厂方法模式通常有产品和工厂两部分

这样使用的分析一下:

1\. 可以自定义方法名字,区别多个构造器是干什么用的,构建的阶段什么的,明显PageRequest.of用的都一样,统一与构造器结构也不错,哈哈哈

2\. 可以充分利用多态,使代码更具有可扩展性,比如返回子类的实例,构造器则不行

3\. 一样具有重叠构造器的优点

4\. 如果该类中没有public或protected的构造器,该类就不能使用静态方法子类化

5.基于第4点,策略模式说到应该多用组合而不是继承,当一个类不能子类化后,组合将是你唯一的选择

PS: 静态工厂方法本质上就是一个静态方法