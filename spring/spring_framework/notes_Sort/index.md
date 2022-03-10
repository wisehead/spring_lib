
# (22条消息) Spring Boot中JPA实现Sort排序的三种方式_木小鱼的笔记-CSDN博客_jpa 排序

引言： 在Spring Boot应用中，基于数据某个字段进行排序是一个非常常用的需求，这里将给出Sort的三种常用用法，基于分页的应用，大家可以各取所需，择机使用。

## 环境说明

Spring 4.2 Spring Boot 1.5.11 Java 8

## 前置说明

ECardEntity.java的定义：

```
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jd.ai.fasion.util.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="t_ebusiness_card")
@Data
@EqualsAndHashCode(callSuper=true)
public class ECardEntity extends BaseEntity { 
    private static final long serialVersionUID = 6580526495176090890L;

    @Column
    private String name;

    @Column(name="zip_url")
    private String zipUrl;

    @Column(name="thumb_url")
    private String thumbUrl;

    @Column(name="seq_num")
    private int seqNum;
}
```

这里的seqNum是排序字段，基于升序来排序。  
Repository的定义：

```
@Repository
public interface EBusinessCardRepository extends JpaRepository<ECardEntity, Long> {
   ///方法的定义
}
```

## 方法1： 基于特殊参数的排序

建立分页对象:

```
  Pageable pageable = new PageRequest(pageNum, size);
```

在Repository中定义相应的方法：

```
  Page<ECardEntity> findByOrderBySeqNumAsc(Pageable pageable);
```

这里使用默认的字段拼接形成的方法名，从而自动解析形成对应的方法。

## 方法2： 基于自定义的@Query进行排序

Pageable的对象定义与方法1中相同。  
在Repository中定义相应的JPL语句：

```
@Query("select e from ECardEntity e ORDER BY e.seqNum ASC")
Page<ECardEntity> findInOrders(Pageable pageable);
```

## 方法3： 基于Pageable中的Sort字段

Pageable对象的声明：

```
Sort sort = new Sort(Direction.ASC, "seqNum");
Pageable pageable = new PageRequest(pageNum, size, sort);
```

这里将Sort字段作为构造方法的入口参数，创建了Pageable对象。  
在Repository无需声明任何新的方法，直接使用[JpaRepository](https://so.csdn.net/so/search?q=JpaRepository&spm=1001.2101.3001.7020)中继承而来的findAll(Pageable pageable）方法即可。  
在Service中调用具体Repository中的方法如下：

```
  Page<ECardEntity> eCardEntities = this.eCardRepo.findAll(pageable);
```

## 总结

这几种方法都是非常简单易用的，这里对于这个排序的简单需求来说，方法是最为简单的，无需在Repository进行任何的方法声明直接使用即可。