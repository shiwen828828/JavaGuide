# MyBatis

> 讲师：波波



​         Java 是第一大编程语言和开发平台。它有助于企业降低成本、缩短开发周期、推动创新以及改善应用服务。如今全球有数百万开发人员运行着超过 51 亿个 Java 虚拟机，Java 仍是企业和开发人员的首选开发平台 



# 课程内容的介绍

1. 基本使用介绍
2. 配置文件详解
3. 映射文件详解
4. 动态SQL语句
5. 延迟加载和缓存处理
6. MyBatis和Spring的整合操作
7. MyBatis的逆向工程





# 一、MyBatis的基本使用

官网地址:https://mybatis.org/mybatis-3/zh/index.html

## 1. 什么是 MyBatis ？

  MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

## 2. Mybatis和hibernate的比较对象

​       关系映射**（英语：**Object Relational Mapping**，简称**ORM**，或**O/RM**，或**O/R mapping）

| Mybatis       | Hibernate                         |
| ------------- | --------------------------------- |
| 半自动ORM框架 | 全自动ORM框架                     |
| 必须写SQL     | 可以不写SQL                       |
| 事务处理      | 事务处理                          |
| 缓存都支持    | 缓存都支持，二级缓存比mybatis更好 |



## 3.MyBatis的基本使用

### 3.1 创建Maven项目添加对应的依赖

​         首先肯定要添加MyBatis的依赖，然后要操作MySQL数据库中的表结构，所以我们需要添加MYSQL的驱动依赖

```xml
<dependencies>
    <!-- MyBatis依赖 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.1</version>
    </dependency>
    <!-- 添加MySQL的依赖 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.11</version>
    </dependency>
</dependencies>
```



### 3.2 创建全局配置文件

​       XML 配置文件中包含了对 MyBatis 系统的核心设置，包括获取数据库连接实例的数据源（DataSource）以及决定事务作用域和控制方式的事务管理器（TransactionManager）。后面会再探讨 XML 配置文件的详细内容，这里先给出一个简单的示例

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/shop?characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
</configuration>
```



### 3.3 定义User对象

​       我们将MyBatis是一个ORM框架，那么会将数据库中的user表中的数据映射为Java中的对象，所以我们需要创建该对象与之对应。

```java
package com.bobo.pojo;

/**
 * 多列操作数据
 *    按住Alt键 鼠标操作
 */
public class User {

  private Integer id;
  private String username;
  private String address;
  private String gender ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User() {
    }

    public User(Integer id, String username, String address, String gender) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}

```



### 3.4 创建映射文件

​         MyBatis要操作数据库，那么我们需要在映射文件中来写SQL语句。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bobo.pojo.User">
    <!-- 添加用户信息 -->
    <insert id="addUser" parameterType="com.bobo.pojo.User" >
        insert into t_user (username,address,gender)values(#{username},#{address},#{gender})
    </insert>
</mapper>
```



### 3.5 将映射文件关联配置文件

​     上面的操作中映射文件和配置文件是没有关联的，我们需要在主配置文件中添加相关的信息

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/shop?serverTimezone=GMT%2B8&amp;useUnicode=true&amp;characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!-- 关联我们自定义的映射文件 -->
        <mapper resource="com/bobo/pojo/UserMapper.xml"/>
    </mappers>
</configuration>
```



### 3.6 测试代码运行

测试代码

```java

    /**
     * MyBatis案例
     */
   @Test
    public void test01() throws Exception{
        // 1.读取核心配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        // 2.获取一个SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 3.获取一个SqlSession对象 基于SqlSessionFactory获取
        SqlSession sqlSession = factory.openSession();
        // 添加数据 ORM框架 面试对象操作
        User user = new User("小明","深圳宝安","1");
        // 4.执行数据库操作 statement= namespace+"."+id
        int count = sqlSession.insert("com.bobo.pojo.User.addUser", user);
        System.out.println("影响的行数..."+count);
        

    }
```



​     在Maven项目中默认的src/main/java目录下面只会打包java编译后的class文件，所以对于的UserMapper.xml文件是不会打包进去的。

![image-20210202211541944](img\image-20210202211541944.png)

出现这种情况我们需要在pom文件中特别指出要处理的资源文件

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```



重启后又出现了新的错误

![image-20210202211848369](img\image-20210202211848369.png)

之前在Spring中的JdbcTemplate中就遇到过，是在jdbc的url中缺少时区的制订

```txt
jdbc:mysql://localhost:3306/shop?serverTimezone=GMT%2B8&amp;useUnicode=true&amp;characterEncoding=utf-8
```



运行测试程序，没有报错

![image-20210202212150329](img\image-20210202212150329.png)



但是数据库中并没有数据

![image-20210202212212756](img\image-20210202212212756.png)

我们需要手动的提交数据

```java
    /**
     * MyBatis案例
     */
   @Test
    public void test01() throws Exception{
        // 1.读取核心配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        // 2.获取一个SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 3.获取一个SqlSession对象 基于SqlSessionFactory获取
        SqlSession sqlSession = factory.openSession();
        // 添加数据 ORM框架 面试对象操作
        User user = new User("小明","深圳宝安","1");
        // 4.执行数据库操作 statement= namespace+"."+id
        int count = sqlSession.insert("com.bobo.pojo.User.addUser", user);
        System.out.println("影响的行数..."+count);
        // 5.MyBatis中默认是不会自动提交DML操作的数据的
       sqlSession.commit();
       // 6.关闭会话
       sqlSession.commit();
    }
```

然后执行数据就进入数据库中了

![image-20210202212351764](img\image-20210202212351764.png)





## 4.修改、删除和查询操作

### 4.1 映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bobo.pojo.User">
    <!-- 添加用户 -->
    <insert id="addUser" parameterType="com.bobo.pojo.User" >
        insert into t_user (username,address,gender)values(#{username},#{address},#{gender})
    </insert>
    <!-- 更新用户信息 -->
    <update id="updateUser" parameterType="com.bobo.pojo.User">
        update t_user set username=#{username} ,address=#{address},gender=#{gender} where id =#{id}
    </update>
    <!--  删除用户信息 -->
    <delete id="deleteUser" >
        delete from t_user where id=#{id}
    </delete>
    <!-- 查询所有用户信息 -->
    <select id="queryAll" resultType="com.bobo.pojo.User">
        select * from t_user
    </select>
    <!-- 根据用户编号查询用户信息 -->
    <select id="queryById" parameterType="java.lang.Integer" resultType="com.bobo.pojo.User">
        select * from t_user where id = #{id}
    </select>
</mapper>
```



### 4.2 测试代码

```java
package com.bobo.test;

import com.bobo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.util.List;

public class TestDemo01 {

    /**
     * MyBatis案例
     */
   @Test
    public void test01() throws Exception{
        // 1.读取核心配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        // 2.获取一个SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 3.获取一个SqlSession对象 基于SqlSessionFactory获取
        SqlSession sqlSession = factory.openSession();
        // 添加数据 ORM框架 面试对象操作
        User user = new User("翠花","深圳福田","2");
        // 4.执行数据库操作 statement= namespace+"."+id
        int count = sqlSession.insert("com.bobo.pojo.User.addUser", user);
        System.out.println("影响的行数..."+count);
        // 5.MyBatis中默认是不会自动提交DML操作的数据的
       sqlSession.commit();
       // 6.关闭会话
       sqlSession.commit();
    }

    @Test
    public void test02() throws Exception{
       InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
       SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        User user = new User(19,"翠花","北京海淀","女");
        int i = sqlSession.update("com.bobo.pojo.User.updateUser", user);
        System.out.println("影响的行数:" + i);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteUser() throws Exception{
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        int i = sqlSession.delete("com.bobo.pojo.User.deleteUser", 20);
        System.out.println("影响的行数:" + i);
        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void queryAll() throws Exception{
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        List<User> lists = sqlSession.selectList("com.bobo.pojo.User.queryAll");
        for (User user : lists) {
            System.out.println(user);
        }
        sqlSession.close();

    }

    @Test
    public void queryById() throws Exception{
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        List<User> lists = sqlSession.selectList("com.bobo.pojo.User.queryById",19);
        for (User user : lists) {
            System.out.println(user);
        }
        sqlSession.close();

    }
}

```



## 5.案例优化

​    通过上面的效果演示大家会发现每个案例都有很多的重复代码。完全可以简化操作

![image-20210202222024518](img\image-20210202222024518.png)



单独提供SqlSessionFactory单例方法

```java
package com.bobo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Date;


public class DbUtils {

    private static SqlSessionFactory factory;

    public static SqlSessionFactory getInstance(){
        if(factory == null){
            InputStream in = null;
            try{
                in = Resources.getResourceAsStream("mybatis-cfg.xml");
            }catch (Exception e){
                e.printStackTrace();
            }
            synchronized (DbUtils.class){
                if(factory == null){
                    factory = new SqlSessionFactoryBuilder().build(in);
                }
            }
        }
        return factory;
    }

}

```



声明接口及实现类

```java
package com.bobo.dao;

import com.bobo.pojo.User;

import java.util.List;

public interface IUserDao {

    int  addUser(User user);
    int  updateUser(User user);
    int  deleteUser(Integer id);
    List<User> queryById(Integer id);

}

```

```java
package com.bobo.dao.impl;

import com.bobo.pojo.User;
import com.bobo.dao.IUserDao;
import com.bobo.utils.DbUtils;

import java.util.List;

public class UserDaoImpl implements IUserDao {

    @Override
    public int addUser(User user) {
        return DbUtils.getInstance().openSession().insert("com.bobo.dao.IUserDao.addUser",user);
    }

    @Override
    public int updateUser(User user) {
        return DbUtils.getInstance().openSession().insert("com.bobo.dao.IUserDao.updateUser",user);
    }

    @Override
    public int deleteUser(Integer id) {
        return DbUtils.getInstance().openSession().insert("com.bobo.dao.IUserDao.updateUser",id);
    }

    @Override
    public List<User> queryById(Integer id) {
        return DbUtils.getInstance().openSession().selectList("com.bobo.dao.IUserDao.queryById", id);
    }
}

```

可以发现Dao实现类中的方法是实现是有很多可以相互约定的操作，如果相互都按照约定来执行的话，那么我们完全可以省略掉Dao的实现类，通过代理对象来实现

```java
@Test
public void test03(){
    // 获取一个Dao接口的代理对象
    IUserDao  dao = (IUserDao) Proxy.newProxyInstance(IUserDao.class.getClassLoader()
                                                      , new Class[]{IUserDao.class}
                                                      , new InvocationHandler() {
                                                          @Override
                                                          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                                              System.out.println(IUserDao.class.getName()+"." + method.getName());
                                                              return DbUtils
                                                                  .getInstance()
                                                                  .openSession()
                                                                  .selectList(IUserDao.class.getName()+"." + method.getName(),args[0]);
                                                          }
                                                      });

    // dao.addUser(null);
    //dao.updateUser(null);
    List<User> list = dao.queryById(18);
    for (User user : list) {
        System.out.println(user);
    }

}
```



![image-20210202222256193](img\image-20210202222256193.png)





## 6.基于接口的使用方式

​     通过前面UserDao的设计，可以发现，UserDao中的代码都是模板化代码，都可以通过配置的方式自动来生成，因此在实际开发中我们可以通过Mapper的方式来实现，具体如下：



### 6.1 声明Mapper接口

​         我们只需要创建相关的接口，不用创建对应的实现类

```java
/**
 * Dao的接口
 */
public interface UserMapper {

    int addUser(User user);
}
```



### 6.2 创建映射文件

​     在映射文件中添加SQL操作。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bobo.dao.UserMapper">
    <!-- 添加用户信息 -->
    <insert id="addUser" parameterType="com.bobo.pojo.User" >
        insert into t_user (username,address,gender)values(#{username},#{address},#{gender})
    </insert>
</mapper>
```



### 6.3 全局配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/shop?serverTimezone=GMT%2B8&amp;useUnicode=true&amp;characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>

        <mapper resource="com/bobo/dao/UserMapper.xml"/>
    </mappers>
</configuration>
```



### 6.4 测试

```java
package com.bobo;

import com.bobo.dao.UserMapper;
import com.bobo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class AppStart {
    public static void main(String[] args) throws Exception{
        // 1.加载全局配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        // 2.获取SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 3.根据SqlSessionFactory获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User("张三","广州","男");
        // 通过SqlSession获取Dao接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.addUser(user);

        // 5.提交资源并关闭连接
        sqlSession.commit();
        sqlSession.close();
    }
}

```



![image-20210203140253736](img\image-20210203140253736.png)



### 6.5 基于接口方式补充说明

​         基于接口的使用方式我们是有约定的，如果不按照该约定执行的话那么是会出问题的

​	     使用mapper接口方式必须满足:

| 序号 | 注意点                                                       |
| ---- | ------------------------------------------------------------ |
| 1    | 映射文件的namespace的值必须是接口的全路径名称 比如：com.dpb.dao.UserMapper |
| 2    | 接口中的方法名在映射文件中必须有一个id值与之对应。           |
| 3    | 映射文件的名称必须和接口的名称一致                           |







# 二、全局配置文件

​      MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。 配置文档的顶层结构如下

```txt
configuration 配置
    properties 属性
    settings 设置
    typeAliases 类型别名
    typeHandlers 类型处理器
    objectFactory 对象工厂
    plugins 插件
    environments 环境
        environment 环境变量
            transactionManager 事务管理器
            dataSource 数据源
    databaseIdProvider 数据库厂商标识
    mappers 映射器
```



## 1.properties属性

​     在实际开发中我们可以面临各种各样的系统环境，为了更加灵活的切换，我们可以通过properties引入一个外部属性文件，然后在配置文件中使用即可

![image-20210203142931333](img\image-20210203142931333.png)



```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/shop?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
username=root
password=123456
```

在配置文件中引入及通过EL表达式来使用

![image-20210203143038877](img\image-20210203143038877.png)



如果出现以下错误

![image-20210203143109859](img\image-20210203143109859.png)



那就说明是没有把properties文件打包

![image-20210203143206828](img\image-20210203143206828.png)

然后测试成功

![image-20210203143258653](img\image-20210203143258653.png)



## 2. setting设置属性

​      这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。 下表描述了设置中各项设置的含义、默认值等。

| 设置参数                         | 描述                                                         | 有效值                                           | 默认值                                                |
| -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------ | ----------------------------------------------------- |
| cacheEnabled                     | 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。   | true                                             | false                                                 |
| lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。 | true                                             | false                                                 |
| aggressiveLazyLoading            | 当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载（参考lazyLoadTriggerMethods). | true                                             | false                                                 |
| multipleResultSetsEnabled        | 是否允许单一语句返回多结果集（需要兼容驱动）。               | true                                             | false                                                 |
| useColumnLabel                   | 使用列标签代替列名。不同的驱动在这方面会有不同的表现， 具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。 | true                                             | false                                                 |
| useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要驱动兼容。 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（比如 Derby）。 | true                                             | false                                                 |
| autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                              | PARTIAL                                               |
| autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或者未知属性类型）的行为。 NONE: 不做任何反应WARNING: 输出提醒日志(‘org.apache.ibatis.session.AutoMappingUnknownColumnBehavior’ 的日志等级必须设置为 WARN) FAILING: 映射失败 (抛出 SqlSessionException) | NONE, WARNING, FAILING                           | NONE                                                  |
| defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。 | SIMPLE REUSE BATCH                               | SIMPLE                                                |
| defaultStatementTimeout          | 设置超时时间，它决定驱动等待数据库响应的秒数。               | 任意正整数                                       | Not Set (null)                                        |
| defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个提示值。此参数只可以在查询设置中被覆盖。 | 任意正整数                                       | Not Set (null)                                        |
| safeRowBoundsEnabled             | 允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为false。 | true                                             | false                                                 |
| safeResultHandlerEnabled         | 允许在嵌套语句中使用分页（ResultHandler）。如果允许使用则设置为false。 | true                                             | false                                                 |
| mapUnderscoreToCamelCase         | 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。 | true \| false                                    | False                                                 |
| localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。 | SESSION \| STATEMENT                             | SESSION                                               |
| jdbcTypeForNull                  | 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量. 大多都为: NULL, VARCHAR and OTHER | OTHER                                                 |
| lazyLoadTriggerMethods           | 指定哪个对象的方法触发一次延迟加载。                         | 用逗号分隔的方法列表。                           | equals,clone,hashCode,toString                        |
| defaultScriptingLanguage         | 指定动态 SQL 生成的默认语言。                                | 一个类型别名或完全限定类名。                     | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
| defaultEnumTypeHandler           | 指定 Enum 使用的默认 TypeHandler 。 (从3.4.5开始) 一个类型别名或完全限定类名。 | org.apache.ibatis.type.EnumTypeHandler           |                                                       |
| callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。注意基本类型（int、boolean等）是不能设置成 null 的。 | true \| false                                    | false                                                 |
| returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回null。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集 (i.e. collectioin and association)。（从3.4.2开始） | true \| false                                    | false                                                 |
| logPrefix                        | 指定 MyBatis 增加到日志名称的前缀。                          | 任何字符串                                       | Not set                                               |
| logImpl                          | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J                                            | LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING\    |
| proxyFactory                     | 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。    | CGLIB\                                           | JAVASSIST                                             |
| vfsImpl                          | 指定VFS的实现                                                | 自定义VFS的实现的类全限定名，以逗号分隔。        | Not set                                               |
| useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的工程必须采用Java 8编译，并且加上-parameters选项。（从3.4.1开始） | true \| false                                    | true                                                  |
| configurationFactory             | 指定一个提供Configuration实例的类。 这个被返回的Configuration实例用来加载被反序列化对象的懒加载属性值。 这个类必须包含一个签名方法static Configuration getConfiguration(). (从 3.2.3 版本开始) | 类型别名或者全类名.                              | Not set                                               |

一个完整设置

```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```



## 3.typeAlias

​       类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写。   

mybatis中默认提供的别名

| 别名       | 映射的类型 |
| ---------- | ---------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDecimal |



![image-20210203144701734](img\image-20210203144701734.png)

执行程序

![image-20210203144840945](img\image-20210203144840945.png)



自定义类型的别名

![image-20210203145013749](img\image-20210203145013749.png)



![image-20210203145108293](img\image-20210203145108293.png)



![image-20210203145154930](img\image-20210203145154930.png)

设置了别名后，使用别名是不区分大小写的！！！

![image-20210203145236813](img\image-20210203145236813.png)



​         如果我们的类型非常多，而且也需要给每个类型设置别名，那么这个工作就非常繁琐了，这时我们可以通过批量扫描自动来生成别名，具体如下：

![image-20210203145518177](img\image-20210203145518177.png)



![image-20210203145608209](img\image-20210203145608209.png)



## 4. typeHandlers

​       typeHandlers称为类型处理器，就是实现Java类型和数据库类型之间的转换。除了系统提供的类型转换器之外，我们也可以自定义类型转换器。我们自己实现一个 List <---> varchar之间的类型转换

### 4.1 自定义类型转换器

```java
package com.bobo.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 实现集合和Varchar之间的转换
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ListVarcharTypeHandler extends BaseTypeHandler<List<String>> {

    /**
     * 插入数据的时候要实现的数据转换是
     * List 转换为 字符串
     * String sql = "";
     * PreparedStatement ps = conn.createPreparedStatement(sql);
     * ps.setInt(1,14);
     * ps.setString(2,"aaa");
     * ps.setString(3,list.toString());
     * ps.setString(4,"湖南长沙");
     * 。。。
     * ps.executeUpdate();
     * 。。。
     * @param ps
     * @param i  执行的SQL语句要插入参数的位置
     * @param list 要插入的位置的数据的List类型
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> list, JdbcType jdbcType) throws SQLException {
        System.out.println("setNonNullParameter ..... "); // a;b;c;d;
        // 处理数据 将List转换为特定格式的字符串
        StringBuffer sb = new StringBuffer();
        String msg = null;
        if(list != null){
            for (String p : list) {
                sb.append(p).append(";");
            }
            msg = sb.toString();
            msg = msg.substring(0,msg.length()-1);
        }

        // 给对应位置的占位符赋值
        ps.setString(i,msg);
    }

    /**
     * 获取非空的结果集
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Arrays.asList(rs.getString(columnName).split(";"));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Arrays.asList(rs.getString(columnIndex).split(";"));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Arrays.asList(cs.getString(columnIndex).split(";"));
    }
}

```



### 4.2 映射文件修改

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bobo.dao.UserMapper">

    <insert id="addUser" parameterType="UsER" >
        insert into t_user (username,address,gender,favorites)values(#{username},#{address},#{gender},#{favorites})
    </insert>

    <select id="query" parameterType="int" resultType="UsER">
        select * from t_user where id = #{id}
    </select>
</mapper>
```



### 4.3 修改配置文件

![image-20210203152753218](img\image-20210203152753218.png)



### 4.4 测试

添加数据

![image-20210203152829465](img\image-20210203152829465.png)



查询数据

![image-20210203152848062](img\image-20210203152848062.png)



## 5.mappers属性

​        既然 MyBatis 的行为已经由上述元素配置完了，我们现在就要来定义 SQL 映射语句了。 但首先，我们需要告诉 MyBatis 到哪里去找到这些语句。 在自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 `file:///` 形式的 URL），或类名和包名等。例如：

```
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

这些配置会告诉 MyBatis 去哪里找映射文件，剩下的细节就应该是每个 SQL 映射文件了，也就是接下来我们要讨论的。



# 三、映射文件介绍

​        MyBatis 的真正强大在于它的语句映射，这是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 致力于减少使用成本，让用户能更专注于 SQL 代码。



## 1. log4j

​       程序运行的时候我们需要通过日志来记录程序的运行情况，这些日志可以帮助我们分析程序代码及错误排查等.....

### 1.1 添加相关的依赖

```xml
<!-- log4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>
```



### 1.2 创建log4j.properties文件

​      创建一个日志的属性文件，在该文件中配置日志的相关信息

```properties
log4j.rootCategory=All, stdout , R

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=[QC] %p [%t] %C.%M(%L) | %m%n

log4j.appender.R=org.apache.log4j.DailyRollingFileAppender
log4j.appender.R.File=D:\\logs\\qc.log
log4j.appender.R.layout=org.apache.log4j.PatternLayout
log4j.appender.R.layout.ConversionPattern=%d-[TS] %p %t %c - %m%n
```



### 1.3 使用

```java
package com.bobo;

import com.bobo.dao.UserMapper;
import com.bobo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

public class AppStart1 {

    static Logger logger = Logger.getLogger(AppStart1.class);
    public static void main(String[] args) throws Exception{
        logger.error("log:error");
        logger.warn("log:warn");
        logger.info("log:info");
        logger.debug("log:debug");

        // 1.加载全局配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        // 2.获取SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 3.根据SqlSessionFactory获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 通过SqlSession获取Dao接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.query(26);
        for (User user : list) {
            System.out.println(user + ":" + user.getFavorites());
        }

        // 5.提交资源并关闭连接
        sqlSession.commit();
        sqlSession.close();
    }
}

```





![image-20210203163741860](img\image-20210203163741860.png)



## 2.传入参数

### 2.1 ${}和#{}的区别

**#{}的使用方式**

sql

```xml
<select id="query" parameterType="int" resultType="UsER">
    select * from t_user where id = #{id}
</select>
```

执行后



![image-20210203164458671](img\image-20210203164458671.png)



**${}的使用**

sql

```xml
<select id="query" parameterType="int" resultType="UsER">
    select * from t_user where id = ${id}
</select>
```

执行后抛出了异常信息

![image-20210203164616645](img\image-20210203164616645.png)

${}的使用我们必须在接口的形参中添加`@Param`注解修饰

```java
public interface UserMapper {

    int addUser(User user);

    List<User> query(@Param("id") Integer id);
}
```

再执行就可以了

![image-20210203164749635](img\image-20210203164749635.png)



​        通过日志文件可以看到两种方式执行的SQL语句是有区别的，一种是使用了占位符，一种是直接赋值的。

​        由于MyBatis底层还是Jdbc操作，而Jdbc操作数据库传递数据时有两种方式，一种是Statement，还一种是PreparedStatement方式，使用Statement会有SQL注入的风险，而PreparedStatement通过占位符的方式就可以避免SQL注入的情况。

​       在MyBatis中对应的两种使用方式的体现就是#{}和${}的方式，显然#{}对应的是PreparedStatement，而${}对应的是Statement。显然在MyBatis中我们更加推荐使用#{}



### 2.2 多个参数

定义update标签

```xml
    <update id="updateUser" >
        update t_user set username = #{username} where id = #{id}
    </update>
```



定义的接口

```java
package com.bobo.dao;

import com.bobo.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao的接口
 */
public interface UserMapper {

    int addUser(User user);

    List<User> query(@Param("id") Integer id);

    int updateUser(String userName,Integer id);
}

```



测试 执行

```java
package com.bobo;

import com.bobo.dao.UserMapper;
import com.bobo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Arrays;

public class AppStart3 {
    public static void main(String[] args) throws Exception{
        // 1.加载全局配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        // 2.获取SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 3.根据SqlSessionFactory获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 通过SqlSession获取Dao接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUser("赵六",27);

        // 5.提交资源并关闭连接
        sqlSession.commit();
        sqlSession.close();
    }
}

```





![image-20210203165900327](img\image-20210203165900327.png)



根据错误提示我们可以在映射文件中通过 [arg1,arg0,param1,param2]来获取传递的多个参数

![image-20210203170020363](img\image-20210203170020363.png)





![image-20210203170055846](img\image-20210203170055846.png)



![image-20210203170115191](img\image-20210203170115191.png)



如果我不想使用系统提供的，而是自定义参数名称同样可以通过`@Param`实现

![image-20210203170239350](img\image-20210203170239350.png)



![image-20210203170316912](img\image-20210203170316912.png)



### 2.3 包装对象

​      我们传递的是一个包装类对象，针对这种情况我们应该怎么获取相关的参数

```java
package com.bobo.pojo;

/**
 * 包装类
 */
public class UserWrapper {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

```

如果我们传递的是个包装对象，那么我们可以通过"."存取器来处理相关的情况

![image-20210203172250426](img\image-20210203172250426.png)

![image-20210203172307977](img\image-20210203172307977.png)





## 3.返回数据

### 3.1 resultType

​        通过resultType设置操作的返回结果类型，如果返回的是基本数据类型，那么我们可以直接写Java中的基本数据类型即可。如果返回的是一个对象或者集合，并且对象中的属性和查询的字段名一一对应，那么resultType可以直接写一个对象



### 3.2 resultMap

​      resultMap主要用来解决属性名和字段名不一致以及一对多、一对一查询等问题。字段名不一致时，首先可以通过取别名的方式解决

![image-20210203195033470](img\image-20210203195033470.png)



Java类中的属性name和表结构中的字段userName不同

![image-20210203195107591](img\image-20210203195107591.png)

![image-20210203195156715](img\image-20210203195156715.png)



第一种解决方案就是添加别名

```xml
    <select id="query" parameterType="int" resultType="UsER">
        select
            id
            ,username name
            ,address
            ,gender
            ,favorites
        from t_user where id = ${id}
    </select>
```



![image-20210203195248792](img\image-20210203195248792.png)



第二种解决方式是通过resultMap来指定表结构中的字段和Java类中的属性的对应关系

```xml
<resultMap id="baseMap" type="user">
        <id column="id" property="id"/>
        <result column="username" property="name"/>
        <result column="address" property="address"/>
        <result column="gender" property="gender"/>
        <result column="favorites" property="favorites" />
    </resultMap>

    <select id="queryAll" resultMap="baseMap">
        select * from t_user
    </select>
```

![image-20210203195403813](img\image-20210203195403813.png)



## 4.主键回写

​      一般情况下，主键的两种生成方式

1. 主键自增
2. 自定义主键(一般使用UUID)



![image-20210203200702040](img\image-20210203200702040.png)





### 4.1 主键回填

```xml
<insert id="addUser" parameterType="UsER" useGeneratedKeys="true" keyProperty="id">
    insert into t_user (username,address,gender,favorites)values(#{name},#{address},#{gender},#{favorites})
</insert>
```

useGeneratedKeys:使用生成的主键

keyProperty：将生成的主键的值保存到对象的id属性中

![image-20210203201035667](img\image-20210203201035667.png)



![image-20210203201051939](img\image-20210203201051939.png)



### 4.2 selectKey

​     另外一种方式可以利用MySQL自带的last_insert_id() 函数来获取刚刚插入的id

```xml
    <insert id="addUserWrapper" parameterType="userwrapper">
        <selectKey keyProperty="user.id" resultType="int">
            select LAST_INSERT_ID()
        </selectKey>
        insert into t_user(username,address,gender)values(#{user.name},#{user.address},#{user.gender})
    </insert>
```



![image-20210203201401689](img\image-20210203201401689.png)





# 三、动态SQL语句

​        动态 SQL 是 MyBatis 的强大特性之一。如果你使用过 JDBC 或其它类似的框架，你应该能理解根据不同条件拼接 SQL 语句有多痛苦，例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL，可以彻底摆脱这种痛苦。

​        使用动态 SQL 并非一件易事，但借助可用于任何 SQL 映射语句中的强大的动态 SQL 语言，MyBatis 显著地提升了这一特性的易用性。

​        如果你之前用过 JSTL 或任何基于类 XML 语言的文本处理器，你对动态 SQL 元素可能会感觉似曾相识。在 MyBatis 之前的版本中，需要花时间了解大量的元素。借助功能强大的基于 OGNL 的表达式，MyBatis 3 替换了之前的大部分元素，大大精简了元素种类，现在要学习的元素种类比原来的一半还要少。



## 1. if语句

​      使用动态 SQL 最常见情景是根据条件包含 where 子句的一部分

```xml
<select id="query" parameterType="user" resultType="user">
    select
    *
    from t_user
    where
    1 = 1
    <if test="username != null">
        and username = #{username}
    </if>
    <if test="address != null">
        and address like #{address}
    </if>
    <if test="gender != null">
        and gender = #{gender}
    </if>
</select>
```

Dao的接口文件中

```java
public interface UserMapper {
    List<User> query(User user);
}
```



测试代码

![image-20210205153847083](img\image-20210205153847083.png)



![image-20210205153924037](img\image-20210205153924037.png)



## 2. where 语句

​           我们在写SQL语句的`where`部分的时候为了保证SQL语句的语法正确，我们会加上`1=1`表达式，如果我们不想这么写的话可以使用`<where>`标签来替代

```xml
<select id="query" parameterType="user" resultType="user">
    select
    *
    from t_user
    where
    1 = 1
    <if test="username != null">
        and username = #{username}
    </if>
    <if test="address != null">
        and address like #{address}
    </if>
    <if test="gender != null">
        and gender = #{gender}
    </if>
</select>
```



![image-20210205154358697](img\image-20210205154358697.png)



![image-20210205154520216](img\image-20210205154520216.png)



## 3. choose、when、otherwise

​        有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

```xml
<!--
        查询的条件是 如果传递的有username那就按照username来查询，
        否则判断是否传递的有address
        如果有就根据address来查询否则根据id倒序排序输出
    -->
<select id="queryChoose" parameterType="user" resultType="user" >
    select *
    from t_user
    where 1 = 1
    <choose>
        <when test="username != null">
            and username = #{username}
        </when>
        <when test="address != null">
            and address like #{address}
        </when>
        <otherwise>
            order by id desc
        </otherwise>
    </choose>
</select>
```

测试

![image-20210205160010603](img\image-20210205160010603.png)



![image-20210205160051211](img\image-20210205160051211.png)



![image-20210205160132196](img\image-20210205160132196.png)



## 4. set语句

​       当我们要实现动态更新的时候会因为`,` 问题而造成非常麻烦的后果，这时我们可以通过`<set>`标签来动态管理

```xml
<update id="updateUser" parameterType="user"  >
    update
    t_user
    <set>
        <if test="username != null">
            username = #{username},
        </if>
        <if test="address != null">
            address = #{address},
        </if>
        <if test="gender != null">
            gender = #{gender},
        </if>
    </set>
    where id = #{id}
</update>
```



![image-20210205161238854](img\image-20210205161238854.png)



![image-20210205161317956](img\image-20210205161317956.png)



## 5. trim语句

​      trim标记是一个格式化的标记。可以完成set或者where标记的功能

| 属性            | 说明                 |
| --------------- | -------------------- |
| prefix          | 前缀                 |
| prefixOverrides | 去掉第一个指定内容   |
| suffix          | 后缀                 |
| suffixoverrides | 去掉最后一个指定内容 |



替代where的实现

```xml
    <select id="queryWhere" parameterType="user" resultType="user">
        select
        *
        from t_user
        <trim prefix="where" prefixOverrides="AND |OR ">
            <if test="username != null">
                and username = #{username}
            </if>
            <if test="address != null">
                and address like #{address}
            </if>
            <if test="gender != null">
                and gender = #{gender}
            </if>
        </trim>
    </select>
```

效果

![image-20210205163151561](img\image-20210205163151561.png)



![image-20210205163226744](img\image-20210205163226744.png)



替换`<set>`标签

```xml
    <update id="updateUser" parameterType="user"  >
        update
        t_user
        <trim prefix="set" suffixOverrides=",">
            <if test="username != null">
                username = #{username},
            </if>
            <if test="address != null">
                address = #{address},
            </if>
            <if test="gender != null">
                gender = #{gender},
            </if>
        </trim>
        where id = #{id}
    </update>
```

```xml
    <update id="updateUser" parameterType="user"  >
        update
        t_user
        <trim prefix="set" prefixOverrides=",">
            <if test="username != null">
                ,username = #{username}
            </if>
            <if test="address != null">
                ,address = #{address}
            </if>
            <if test="gender != null">
                ,gender = #{gender}
            </if>
        </trim>
        where id = #{id}
    </update>
```

效果

![image-20210205163919909](img\image-20210205163919909.png)





## 6. foreach

动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）

| 属性       | 说明                                               |
| ---------- | -------------------------------------------------- |
| collection | collection属性的值有三个分别是list、array、map三种 |
| open       | 前缀                                               |
| close      | 后缀                                               |
| separator  | 分隔符，表示迭代时每个元素之间以什么分隔           |
| item       | 表示在迭代过程中每一个元素的别名                   |
| index      | 用一个变量名表示当前循环的索引位置                 |



演示案例

```xml
<select id="queryByIds" resultType="user">
        select *
        from t_user
        <where>
            <if test="ids != null">
                id in
                <foreach collection="ids" open=" ( " close=" ) " item="id" separator=",">
                    #{id}
                </foreach>
            </if>
        </where>
    </select>
```

Mapper接口声明

```java
    List<User> queryByIds(@Param("ids") List<Integer> ids);
```

效果

![image-20210205171125490](img\image-20210205171125490.png)



foreach的第二种使用方式就是在批量插入数据的时候使用

```xml
    <insert id="insertUser" >
        insert into t_user (username,address) values
        <foreach collection="users" separator="," item="user" >
            (#{user.username},#{user.address})
        </foreach>
    </insert>
```

接口中声明

```java
Integer insertUser(@Param("users") List<User> users);
```

测试

![image-20210205171859265](img\image-20210205171859265.png)





## 7. bind语句

`bind` 元素允许你在 OGNL 表达式以外创建一个变量，并将其绑定到当前的上下文

```xml
<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>
```



## 8. SQL代码片段

​     可重复使用的SQL语句代码片段

![image-20210205173729025](img\image-20210205173729025.png)





![image-20210205173805480](img\image-20210205173805480.png)





# 四、 延迟加载和缓存

## 1.关联关系



https://www.processon.com/view/link/601d36dd0791294f82228202



  在关系型数据库中，表与表之间很少是独立与其他表没关系的。所以在实际开发过程中我们会碰到很多复杂的关联关系。在此我们来分析下载mybatis中怎么处理这些关系

### 1.1 1对1关系

  我们有一张员工表(T_EMP)，一张部门表(T_DEPT)。员工表中的一条记录对应于部门表中有且仅有一条记录。这就是一对一的关联关系。

```sql
CREATE TABLE `t_dept` (
  `deptid` int NOT NULL AUTO_INCREMENT,
  `dname` varchar(20) DEFAULT NULL,
  `ddesc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`deptid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_emp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `deptid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
```



查询每个员工的信息及对应的部门信息

```java
package com.bobo.pojo;

public class Emp {

    private Integer id;

    private String name;

    private Integer age;

    // 1对1关联关系的体现  一个员工对象最多有一个部门对象
    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```



部门对象

```java
package com.bobo.pojo;

public class Dept {

    private Integer deptid;

    private String dname;

    private String ddesc;

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDdesc() {
        return ddesc;
    }

    public void setDdesc(String ddesc) {
        this.ddesc = ddesc;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptid=" + deptid +
                ", dname='" + dname + '\'' +
                ", ddesc='" + ddesc + '\'' +
                '}';
    }
}

```



映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bobo.dao.EmpMapper">

    <resultMap id="baseMap1" type="emp">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="age" property="age"/>
        <!-- 配置1对1的关联关系 -->
        <association property="dept" javaType="Dept">
            <!--  从表的属性和字段的映射关系-->
            <id column="deptid" property="deptid"/>
            <result column="dname" property="dname"/>
        </association>
    </resultMap>

    <select id="queryEmp" resultMap="baseMap1">
         SELECT
          t1.id,
          t1.`name`,
          t1.`age`,
          t2.`deptid`,
          t2.`dname`
        FROM
          t_emp t1
          LEFT JOIN t_dept t2
            ON t1.`deptid` = t2.`deptid`
    </select>
</mapper>
```



接口声明

```java
package com.bobo.dao;

import com.bobo.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {
     List<Emp> queryEmp();
}

```



测试

![image-20210205203549037](img\image-20210205203549037.png)



### 1.2 1对多关系

​       部门和员工的信息其实就是一个1对多的关联关系

![image-20210205204610207](img\image-20210205204610207.png) 

```sql
# 查询部门信息 然后关联查询出部门中包含的相关的员工信息
SELECT
    t1.`deptid`
    ,t1.`dname`
    ,t1.`ddesc`
    ,t2.`id`
    ,t2.`name`
    ,t2.age
FROM t_dept t1
LEFT JOIN t_emp t2
ON t1.`deptid` = t2.`deptid`

```

pojo对象

![image-20210205205219897](img\image-20210205205219897.png)

映射文件配置

```xml
    <resultMap id="baseMap2" type="dept">
        <id column="deptid" property="deptid"/>
        <result column="dname" property="dname"/>
        <collection property="emps" ofType="emp">
            <id column="id" property="id"/>
            <result column="name" property="name"/>
            <result column="age" property="age"/>
        </collection>
    </resultMap>

    <select id="queryDept" resultMap="baseMap2">
        SELECT
            t1.`deptid`
            ,t1.`dname`
            ,t1.`ddesc`
            ,t2.`id`
            ,t2.`name`
            ,t2.age
        FROM t_dept t1
        LEFT JOIN t_emp t2
        ON t1.`deptid` = t2.`deptid`
    </select>
```





测试

![image-20210205205150012](img\image-20210205205150012.png)





### 1.3 多对多关系

​         双向的一对多就是多对多关系



## 2 延迟加载

  延迟查询是一对一和一对多查询的延续。
  在默认的一对一和一对多中，一条SQL就能够查询到所有数据，但是，有的数据有时候一时半会用不上，例如查询员工，捎带获取员工的部门数据，但是部门数据使用的频率很低，这种时候可以使用延迟查询，首先获取到所有的员工数据，然后在需要的时候再去获取部门数据。**当需要使用数据的时候才去加载**既是延迟加载

### 2.1开启延迟加载

​       全局配置文件中配置

![image-20210218150831536](img\image-20210218150831536.png)





![image-20210218150856851](img\image-20210218150856851.png)



### 2.2 1对1延迟加载

​      要开启延迟加载那么我们的SQL语句查找操作也得分成多次操作

```xml

<resultMap id="baseMap1" type="emp">
    <id column="id" property="id"/>
    <result column="name" property="name"/>
    <result column="age" property="age"/>
    <association property="dept" javaType="Dept"
                 column="deptid" select="queryDeptById">
        <id column="deptid" property="deptid"/>
        <result column="dname" property="dname"/>
    </association>
</resultMap>

<select id="queryDeptById" parameterType="int" resultType="dept">
    SELECT * FROM t_dept where deptid = #{deptid}
</select>

<select id="queryEmp" resultMap="baseMap1">
    SELECT
    *
    FROM
    t_emp t1
</select>
```



![image-20210218151057009](img\image-20210218151057009.png)



延迟加载的效果

只使用主表数据，没有使用从表数据的操作，会开启延迟加载

![image-20210218151204580](img\image-20210218151204580.png)



当使用了从表的数据那么就会操作从的数据

![image-20210218151306558](img\image-20210218151306558.png)



### 2.3 1对多的延迟加载

​     同样的也得将SQL语句拆分为多个SQL执行

```xml
    <resultMap id="baseMap2" type="dept">
        <id column="deptid" property="deptid"/>
        <result column="dname" property="dname"/>
        <collection property="emps" ofType="emp" column="deptid" select="queryEmpByDid">
            <id column="id" property="id"/>
            <result column="name" property="name"/>
            <result column="age" property="age"/>
        </collection>
    </resultMap>

    <select id="queryDept" resultMap="baseMap2">
        SELECT
            *
        FROM t_dept t1

    </select>
    <select id="queryEmpByDid" parameterType="int" resultType="emp" >
        SELECT * FROM t_emp where deptid = #{deptid}
    </select>
```



![image-20210218151728653](img\image-20210218151728653.png)



![image-20210218151819123](img\image-20210218151819123.png)



## 3. 缓存

缓存简介：
  缓存(Cache )是计算机领域非常通用的概念。它介于应用程序和永久性数据存储源(如硬盘上的文件或者数据库)之间，其作用是降低应用程序直接读写永久性数据存储源的频率，从而提高应用的运行性能。缓存中的数据是数据存储源中数据的拷贝，应用程序在运行时直接读写缓存中的数据，只在某些特定时刻按照缓存中的数据来同步更新数据存储源。
缓存的物理介质通常是内存，而永久性数据存储源的物理介质通常是硬盘或磁盘，应用程序读写内在的速度显然比读写硬盘的速度快，如果缓存中存放的数据量非常大，也会用硬盘作为缓存的物理介质。缓存的实现不仅需要作为物理介质的硬件，同时还需要用于管理缓存的并发访问和过期等策略的软件。因此，缓存是通过软件和硬件共同实现的
**作用：降低访问数据源【数据库】频率**

### 3.1缓存分类

| 类别     | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| 一级缓存 | **事务范围**：缓存只能被当前事务访问。缓存的生命周期 依赖于事务的生命周期当事务结束时，缓存也就结束生命周期。 在此范围下，缓存的介质是内存。 |
| 二级缓存 | **进程范围**：缓存被进程内的所有事务共享。这些事务有 可能是并发访问缓存，因此必须对缓存采取必要的事务隔离机制。 缓存的生命周期依赖于进程的生命周期，进程结束时， 缓存也就结束了生命周期。进程范围的缓存可能会存放大量的数据， 所以存放的介质可以是内存或硬盘。 |
| 三级缓存 | **集群范围**：在集群环境中，缓存被一个机器或者多个机器的进程共享。 缓存中的数据被复制到集群环境中的每个进程节点， 进程间通过远程通信来保证缓存中的数据的一致性， 缓存中的数据通常采用对象的松散数据形式 |

MyBatis支持1级缓存和2级缓存，在实际开发中，实际上很少使用到MyBatis自带的缓存，大部分情况下，缓存都是使用EHCache，MemoryCache、Redis等等来实现缓存。



### 3.2 一级缓存

​      MyBatis中的一级缓存是基于SqlSession的

```java
    @Test
    public void fun1() throws Exception{
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 事务开启
        SqlSession sqlSession = factory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        // 第一次查询员工信息 一级缓存中是没有数据的，所以会直接查询数据库中的数据
        // 然后会将数据保存到一级缓存中
        List<Emp> emps = mapper.queryEmp();
        for (Emp emp : emps) {
            System.out.println(emp+" : " + emp.getDept());
        }
        System.out.println("----------------------");
        // 第二次查询 因为一级缓存中已经存在了要查询的数据，所以就直接返回了，没有数据库查询操作
        emps = mapper.queryEmp();
        for (Emp emp : emps) {
            System.out.println(emp+" : " + emp.getDept());
        }
        // 事务关闭 会清空一级缓存的数据
        sqlSession.close();
        // 重新开启事务
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(EmpMapper.class);
        // 因为是新开的事务，一级缓存中的数据是空的，所以会直接查询数据库中的数据
        emps = mapper.queryEmp();
        for (Emp emp : emps) {
            System.out.println(emp+" : " + emp.getDept());
        }
        sqlSession.close();

    }

```



![image-20210218154936341](img\image-20210218154936341.png)



### 3.3 二级缓存

​      二级缓存是基于SqlSessionFactory的，一级缓存的作用域仅仅只是SqlSession，范围比较窄，应用不多，二级缓存是进程级别的，用的就比较多了。二级缓存我们一般使用Redis或者Ehcache来实现，

    ```xml
<dependency>
	<groupId>net.sf.ehcache</groupId>
	<artifactId>ehcache</artifactId>
	<version>1.5.0</version>
</dependency>

<dependency>
	<groupId>org.mybatis.caches</groupId>
	<artifactId>mybatis-ehcache</artifactId>
	<version>1.1.0</version>
</dependency>

    ```



开启二级缓存，我们只需要在需要开启的映射文件中添加cache标签即可

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache" />
```



```java
    @Test
    public void fun1() throws Exception{
        InputStream in = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 事务开启
        SqlSession sqlSession = factory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        // 第一次查询员工信息 一级二级缓存中是没有数据的，所以会直接查询数据库中的数据
        // 然后会将数据保存到一级缓存和二级缓存中
        List<Emp> emps = mapper.queryEmp();
        for (Emp emp : emps) {
            System.out.println(emp+" : " + emp.getDept());
        }
        System.out.println("----------------------");
        // 第二次查询 因为一级缓存中已经存在了要查询的数据，所以就直接返回了，没有数据库查询操作
        emps = mapper.queryEmp();
        for (Emp emp : emps) {
            System.out.println(emp+" : " + emp.getDept());
        }
        // 事务关闭 会清空一级缓存的数据 但不会清空二级缓存的数据
        sqlSession.close();
        // 重新开启事务
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(EmpMapper.class);
        // 因为是新开的事务，一级缓存中的数据是空的，但是二级缓存中有相关的数据 直接返回
        emps = mapper.queryEmp();
        for (Emp emp : emps) {
            System.out.println(emp+" : " + emp.getDept());
        }
        sqlSession.close();

    }
```





![image-20210218155743445](img\image-20210218155743445.png)



# 五、MyBatis的实际应用

## 1.MyBatis和Spring的整合操作

创建一个普通的Maven项目即可

### 1.1 添加相关的依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.bobo</groupId>
    <artifactId>MyBatisDemo11Spring</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- Spring核心依赖 -->

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
        <!--
            MyBatis 相关依赖
                MyBatis 自身依赖
                MySQL驱动
                日志
        -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
        </dependency>
        <!-- Spring整合MyBatis的插件依赖 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.4</version>
        </dependency>
        <!-- 单元测试的依赖 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!-- 数据库连接池 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>*.xml</include>
                    <include>*.properties</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>

</project>
```



### 1.2 创建相关的配置文件

MyBatis的全局配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

</configuration>
```



Spring的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 配置扫描 -->
    <context:component-scan base-package="com.bobo.service.*" />
    <!-- 关联数据库属性文件 -->
    <context:property-placeholder location="db.properties" />

    <!-- 配置数据源  数据库连接池 -->
    <bean class="com.mchange.v2.c3p0.ComboPooledDataSource" id="dataSource">
        <property name="driverClass" value="${driver}" />
        <property name="jdbcUrl" value="${url}"/>
        <property name="user" value="${username}"/>
        <property name="password" value="${password}"/>
    </bean>

    <!-- 整合MyBatis -->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sessionFactoryBean">
        <!-- 关联数据源 -->
        <property name="dataSource" ref="dataSource" />
        <!-- 关联配置文件 -->
        <property name="configLocation" value="mybatis-cfg.xml" />
        <!-- 添加别名 -->
        <property name="typeAliasesPackage" value="com.bobo.pojo" />
    </bean>
    <!-- 配置接口的扫描路径 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer" >
        <property name="basePackage" value="com.bobo.mapper" />
    </bean>
</beans>
```



db.properties文件

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/shop?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
username=root
password=123456

#driver=com.mysql.cj.jdbc.Driver
#url=jdbc:mysql://192.168.100.122:3306/shop?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
#username=root
#password=123456
```



log4j.properties文件

```properties
log4j.rootCategory=All, stdout , R

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=[QC] %p [%t] %C.%M(%L) | %m%n

log4j.appender.R=org.apache.log4j.DailyRollingFileAppender
log4j.appender.R.File=D:\\logs\\qc.log
log4j.appender.R.layout=org.apache.log4j.PatternLayout
log4j.appender.R.layout.ConversionPattern=%d-[TS] %p %t %c - %m%n
```



### 1.3 业务测试

JavaBean对象

```java
package com.bobo.pojo;

public class User {

    private Integer id;

    private String username;

    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

```



Mapper接口和映射文件

```java
package com.bobo.mapper;

import com.bobo.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> query();
}

```



```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bobo.mapper.UserMapper">

    <select id="query" resultType="user">
        select * from t_user
    </select>
</mapper>
```



service

```java
package com.bobo.service;

import com.bobo.pojo.User;

import java.util.List;

public interface IUserService {
    List<User> query();
}

```



```java
package com.bobo.service.impl;

import com.bobo.mapper.UserMapper;
import com.bobo.pojo.User;
import com.bobo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> query() {
        return mapper.query();
    }
}

```





测试

```java
    @Test
    public void test(){
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserService bean = ac.getBean(IUserService.class);
        List<User> list = bean.query();
        for (User user : list) {
            System.out.println(user);
        }
    }
```



![image-20210218164520116](img\image-20210218164520116.png)



整体的项目结构

![image-20210218164546086](img\image-20210218164546086.png)



课后的作业：实现SpringMVC+Spring+MyBatis的整合。





## 2.逆向工程

### 2.1 什么是逆向工程

  简单点说，就是通过数据库中的单表，自动生成Java代码。Mybatis官方提供了逆向工程，可以针对单表自动生成Mybatis代码（mapper.java\mapper.xml\po类）企业中，逆向工程是个很常用的工具,比我们手动创建映射文件的配置信息方便很多.

官网 http://www.mybatis.org/generator



### 2.2 逆向工程实现

添加mybatis-generator-maven-plugin插件

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.2</version>
                <configuration>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>
        </plugins>
    </build>
```





添加自动生成的配置文件

​    注意配置文件的名称必须是generatorConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!-- 数据库的驱动包路径 -->
    <classPathEntry location="C:\Users\dpb\.m2\repository\mysql\mysql-connector-java\8.0.11\mysql-connector-java-8.0.11.jar" />

    <context id="DB2Tables" targetRuntime="MyBatis3">
        <!-- 去掉生成文件中的注释 -->
        <commentGenerator>
            <property name="suppressAllComments" value="true" />
        </commentGenerator>
        <!-- 数据库链接URL、用户名、密码 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/shop?serverTimezone=GMT%2B8"
                        userId="root"
                        password="123456">
        </jdbcConnection>
        <!-- <jdbcConnection driverClass="oracle.jdbc.driver.OracleDriver"
              connectionURL="jdbc:oracle:thin:@localhost:1521:XE"
              userId="car"
              password="car">
      </jdbcConnection>  -->

        <javaTypeResolver >
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>
        <!-- 生成模型的包名和位置 -->
        <javaModelGenerator targetPackage="com.bobo.pojo" targetProject="./src/main/java">
            <!-- 是否在当前路径下新加一层schema,eg：fase路径com.oop.eksp.user.model， true:com.oop.eksp.user.model.[schemaName] -->
            <property name="enableSubPackages" value="false" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- 生成的映射文件包名和位置 -->
        <sqlMapGenerator targetPackage="com.bobo.mapper"  targetProject="./src/main/java">
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>
        <!-- 生成DAO的包名和位置 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.bobo.mapper"  targetProject="./src/main/java">
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>

        <table  tableName="t_user" domainObjectName="User" />
        <table  tableName="t_emp" domainObjectName="Emp" />

    </context>
</generatorConfiguration>


```



自动生成代码



![image-20210218171111062](img\image-20210218171111062.png)



![image-20210218171150213](img\image-20210218171150213.png)







### 2.3 接口方法说明

UserBeanMapper接口中的方法说明

| 方法                                                         | 功能说明                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| int countByExample(UserExample example)                      | 按条件计数                                                   |
| int deleteByPrimaryKey(Integer id)                           | 按主键删除                                                   |
| int deleteByExample(UserExample example)                     | 按条件查询                                                   |
| Integer insert(User record)                                  | 插入数据（返回值为ID）                                       |
| User selectByPrimaryKey(Integer id)                          | 按主键查询                                                   |
| List selectByExample(UserExample example)                    | 按条件查询                                                   |
| List selectByExampleWithBLOGs(UserExample example)           | 按条件查询（包括BLOB字段）。只有当数据表中的字段类型有为二进制的才会产生。 |
| int updateByPrimaryKey(User record)                          | 按主键更新                                                   |
| int updateByPrimaryKeySelective(User record)                 | 按主键更新值不为null的字段                                   |
| int updateByExample(User record, UserExample example)        | 按条件更新                                                   |
| int updateByExampleSelective(User record, UserExample example) | 按条件更新值不为null的字段                                   |



关键代码

```java
package com.bobo.service.impl;

import com.bobo.mapper.EmpMapper;
import com.bobo.pojo.Emp;
import com.bobo.pojo.EmpExample;
import com.bobo.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements IEmpService {

    @Autowired
    private EmpMapper mapper;

    @Override
    public Emp queryEmpById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Emp> query(Emp emp) {
        EmpExample empExample = new EmpExample();
        // 绑定查询的条件
        EmpExample.Criteria criteria = empExample.createCriteria();
        if(!"".equals(emp.getName()) && emp.getName() != null){
            criteria.andNameLike("%"+emp.getName() +"%");
        }

        if(emp.getId() != null && emp.getId() > 0){
            criteria.andIdEqualTo(emp.getId());
        }
        if(emp.getAge()!= null &&emp.getAge() > 0){
            criteria.andAgeEqualTo(emp.getAge());
        }
        return mapper.selectByExample(empExample);
    }

    @Override
    public Integer insert(Emp emp) {
        return mapper.insertSelective(emp);
    }

    @Override
    public Integer updateById(Emp emp) {
        return mapper.updateByPrimaryKeySelective(emp);
    }

    @Override
    public Integer deleteById(Emp emp) {
        return mapper.deleteByPrimaryKey(emp.getId());
    }
}

```



