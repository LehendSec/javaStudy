##  **10**、**JDBC**

###  **10.1**、数据库驱动

 这里的驱动的概念和平时听到的那种驱动的概念是一样的，比如平时购买的声卡，网卡直接插到计算机
 上面是不能用的，必须要安装相应的驱动程序之后才能够使用声卡和网卡，同样道理，我们安装好数据
 库之后，我们的应用程序也是不能直接使用数据库的，必须要通过相应的数据库驱动程序，通过驱动程
 序去和数据库打交道，如下所示：

![](./image/image868.png){width="3.491205161854768in" height="2.6157983377077865in"}

###  **10.2**、**JDBC**介绍

 SUN公司为了简化、统一对数据库的操作，定义了一套Java操作数据库的规范（接口），称之为JDBC。 这套接口由数据库厂商去实现，这样，开发人员只需要学习jdbc接口，并通过jdbc加载具体的驱动，就 可以操作数据库。

 如下图所示：

![](./image/image872.png){width="3.2619313210848646in" height="3.4703619860017496in"}

 JDBC全称为：Java Data Base Connectivity（java数据库连接），它主要由接口组成。
 组成JDBC的２个包：java.sql、javax.sql
 开发JDBC应用需要以上2个包的支持外，还需要导入相应JDBC的数据库实现(即数据库驱动)。

![image-20210713213210771](./image/image-20210713213210771.png)

###  **10.3**、编写**JDBC**程序

搭建实验环境

创建测试数据库

```sql
CREATE DATABASE jdbcStudy CHARACTER SET utf8 COLLATE utf8_general_ci;
USE jdbcStudy;
CREATE TABLE users(
id INT PRIMARY KEY,
NAME VARCHAR(40),
PASSWORD VARCHAR(40),
email VARCHAR(60),
birthday DATE
);
INSERT INTO users(id,NAME,PASSWORD,email,birthday)
VALUES(1,'zhansan','123456','zs@sina.com','1980-12-04'),
(2,'lisi','123456','lisi@sina.com','1981-12-04'),
(3,'wangwu','123456','wangwu@sina.com','1979-12-04');
```

![image-20210713214017061](./image/image-20210713214017061.png) 

![image-20210713214444919](./image/image-20210713214444919.png)

新建一个Java工程，并导入数据驱动，

![](./image/image884.png){width="7.499998906386701in" height="10.928696412948382in"}

也可以使用pom.xml导入

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

 编写程序从user表中读取数据，并打印在命令行窗口中。

```java
package com.lehend;

import java.sql.*;

public class Jdbc_1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        设置数据库链接的url，用户名、密码
        String url = "jdbc:mysql://localhost:3306/jdbcStudy?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "root";
//        加载数据库驱动
        Class.forName("com.mysql.jdbc.Driver");
//        获取与数据库的链接对象
        Connection connection = DriverManager.getConnection(url, username, password);
//       创建数据库操作对象
        Statement statement = connection.createStatement();
//        获取sql语句
        String sql = "select id,name,password,email,birthday from users";

        //向数据库发sql,并获取代表结果集的resultset
        ResultSet resultSet = statement.executeQuery(sql);

//        取出结果集
        while (resultSet.next()) {
            System.out.println("id=" + resultSet.getObject("id"));
            System.out.println("name=" + resultSet.getObject("name"));
            System.out.println("password=" + resultSet.getObject("password"));
            System.out.println("email=" + resultSet.getObject("email"));
            System.out.println("birthday=" + resultSet.getObject("birthday"));
            System.out.println("-----------------------------");
        }
//        关闭数据库链接
        //6.关闭链接，释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
```

#### 步骤总结:

1. 加载驱动
2. 连接数据库DriverManager
3. 获得执行sql的对象Statement
4. 获得返回的结果集
5. 释放连接

###  **10.4**、对象说明

####  DriverManager类讲解

 Jdbc程序中的DriverManager用于加载驱动，并创建与数据库的链接，这个API的常用方法：

```java
DriverManager.registerDriver(new Driver())
DriverManager.getConnection(url, user, password)
```

 注意：**在实际开发中并不推荐采用registerDriver方法注册驱动**。原因有二：

  1. 查看Driver的源代码可以看到，如果采用此种方式，会导致驱动程序注册两次，也就是在内存中会有两个Driver对象。

   2. 程序依赖mysql的api，脱离mysql的jar包，程序将无法编译，将来程序切换底层数据库将会非常麻烦。

推荐方式：**Class.forName("com.mysql.jdbc.Driver");**

 采用此种方式不会导致驱动对象在内存中重复出现，并且采用此种方式，程序仅仅只需要一个字符串， 不需要依赖具体的驱动，使程序的灵活性更高。

####  数据库URL讲解

 URL用于标识数据库的位置，通过URL地址告诉JDBC程序连接哪个数据库，URL的写法为：

![](./image/image906.png){width="5.544242125984252in" height="1.1359437882764654in"}

 常用数据库URL地址的写法：

- Oracle写法：jdbc:oracle:thin:@localhost:1521:sid
- SqlServer写法：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sid
- MySql写法：jdbc:mysql://localhost:3306/sid

```
Oracle写法：jdbc:oracle:thin:@localhost:1521:sid
SqlServer写法：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sid
MySql写法：jdbc:mysql://localhost:3306/sid
```

 如果连接的是本地的Mysql数据库，并且连接使用的端口是3306，那么的url地址可以简写为

 <font color="green">jdbc:mysql:///数据库</font>

####  Connection类讲解

 Jdbc程序中的Connection，它用于代表数据库的链接，Collection是数据库编程中最重要的一个对象， 客户端与数据库所有交互都是通过connection对象完成的，这个对象的常用方法：

- createStatement()：创建向数据库发送sql的statement对象。
- prepareStatement(sql) ：创建向数据库发送预编译sql的PrepareSatement对象
- setAutoCommit(boolean autoCommit)：设置事务是否自动提交。
- commit() ：在链接上提交事务。
- rollback() ：在此链接上回滚事务。

#### Statement类讲解

Jdbc程序中的Statement对象用于向数据库发送SQL语句， Statement对象常用方法：

- executeQuery(String sql) ：用于向数据发送查询语句。
- executeUpdate(String sql)：用于向数据库发送insert、update或delete语句
- execute(String sql)：用于向数据库发送任意sql语句
- addBatch(String sql) ：把多条sql语句放到一个批处理中。
- executeBatch()：向数据库发送一批sql语句执行。


####  ResultSet类讲解

 Jdbc程序中的ResultSet用于代表Sql语句的执行结果。Resultset封装执行结果时，采用的类似于表格的 方式。ResultSet 对象维护了一个指向表格数据行的游标，初始的时候，游标在第一行之前，调用 ResultSet.next() 方法，可以使游标指向具体的数据行，进行调用方法获取该行的数据。 

ResultSet既然用于封装执行结果的，所以该对象提供的都是用于获取数据的get方法：

- 获取任意类型的数据

  - getObject(int index)
  - getObject(string columnName)
- 获取指定类型的数据，例如：
  - getString(int index)
  - getString(String columnName)

ResultSet还提供了对结果集进行滚动的方法：

- next()：移动到下一行
- Previous()：移动到前一行
- absolute(int row)：移动到指定行
- beforeFirst()：移动resultSet的最前面。
- afterLast() ：移动到resultSet的最后面。

####  释放资源

​	Jdbc程序运行完后，切记要释放程序在运行过程中，创建的那些与数据库进行交互的对象，这些对象通 常是ResultSet, Statement和Connection对象，特别是Connection对象，它是非常稀有的资源，用完后 必须马上释放，如果Connection不能及时、正确的关闭，极易导致系统宕机。Connection的使用原则 是尽量晚创建，尽量早的释放。

为确保资源释放代码能运行，资源释放代码也一定要放在finally语句中。

###  **10.5**、**statement**对象(不安全)

<font color="red">Jdbc中的statement对象用于向数据库发送SQL语句，想完成对数据库的增删改查，只需要通过这个对象向数据库发送增删改查语句即可。</font>
Statement对象的executeUpdate方法，用于向数据库发送增、删、改的sql语句,executeUpdate执行完后，将会返回一个整数（即增删改语句导致了数据库几行数据发生了变化）。
Statement.executeQuery方法用于向数据库发送查询语句，executeQuery方法返回代表查询结果的ResultSet对象。

####  CRUD操作-create

 使用executeUpdate(String sql)方法完成数据添加操作，示例操作：

```java
Statement st = conn.createStatement();
String sql = "insert into user(….) values(…..) ";
int num = st.executeUpdate(sql);
if(num>0){
	System.out.println("插入成功！！！");
}
```

#### CRUD操作-delete

 使用executeUpdate(String sql)方法完成数据删除操作，示例操作：

```java
Statement st = conn.createStatement();
String sql = "delete from user where id=1";
int num = st.executeUpdate(sql);
if(num>0){
	System.out.println(“删除成功！！！");
}
```

#### CRUD操作-update

 使用executeUpdate(String sql)方法完成数据修改操作，示例操作：

```java
Statement st = conn.createStatement();
String sql = "update user set name='' where name=''";
int num = st.executeUpdate(sql);
if(num>0){
System.out.println(“修改成功！！！");
}
```

#### CRUD操作-read

 使用executeQuery(String sql)方法完成数据查询操作，示例操作：

```java
Statement st = conn.createStatement();
String sql = "select * from user where id=1";
ResultSet rs = st.executeUpdate(sql);
while(rs.next()){
//根据获取列的数据类型，分别调用rs的相应方法映射到java对象中
}
```

####  使用**jdbc**对数据库增删改查（提取工具类）


 2、在src目录下创建一个db.properties文件，如下图所示：

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/jdbcStudy?
useUnicode=true&characterEncoding=utf8&useSSL=true
username=root
password=root
```

 3、在lehend下新建一个 utils 包，新建一个类 <font color="green">JdbcUtils</font>

```java
package com.lehend.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        try {
//            Properties properties = new Properties();
////            properties.load(JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties"));
//            properties.load(JdbcUtils.class.getClassLoader().getResourceAsStream("/db.properties"));
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
//            获取数据库连接驱动 以及数据库链接的其他配置信息
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
//          加载数据库驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    获取数据库连接对象
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // 释放资源，要释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement
    public static void release(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
```

 使用statement对象完成对数据库的CRUD操作

#####  1、插入一条数据

```java
package com.lehend;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsert {


    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //    获取数据库连接
            connection = JdbcUtils.getConnection();
//            获取负责执行sql命令的Statement对象
            statement = connection.createStatement();
//            要执行的sql语句
            String sql = "insert into users(id,name,password,email,birthday) " + "values(4,'lehend','123','1231@qq.com','2020-01-01')";
            //执行插入操作，executeUpdate方法返回成功的条数
            int row = statement.executeUpdate(sql);
            if(row > 0){
                System.out.println("插入成功！！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
//            sql操作完成之后释放资源
            JdbcUtils.release(connection,statement,resultSet);
        }
    }

}
```

![image-20210716110718583](./image/image-20210716110718583.png)

#####  2、删除一条数据

```java
package com.lehend;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDelete {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String sql = "delete from users where id =4";
            int row = statement.executeUpdate(sql);
            if(row > 0){
                System.out.println("删除成功！！");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
//            释放资源
            JdbcUtils.release(connection,statement,resultSet);
        }


    }

}
```

![image-20210716110647959](./image/image-20210716110647959.png)

##### 3、更新一条数据

```java
package com.lehend;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUpdate {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String sql = "update users set name='lehend',email='1231341@qq.com' where id=3";
            int row = statement.executeUpdate(sql);
            if(row > 0){
                System.out.println("更新成功！！");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
    }
}
```

![image-20210716111332585](./image/image-20210716111332585.png)

##### 4、查询数据

```java
package com.lehend;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSelect {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String sql = "select * from users";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
```

![image-20210716111839203](./image/image-20210716111839203.png)

#### SQL 注入问题

<font color="red">通过巧妙的技巧来拼接字符串，造成SQL短路，从而获取数据库数据</font>

```java
package com.lehend;

import com.lehend.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sqli extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
//            String sql = "lehend' AND updatexml(1, CONCAT('^',(SELECT DATABASE()),'~'),3) OR '1=1";
            String sql = "select * from users where name='" + username + "' and password = '" + password + "' ";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                System.out.println(resultSet.getString("name")+"："+resultSet.getString("password"));
                System.out.println("==============");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

![image-20210716143653968](./image/image-20210716143653968.png)

![image-20210716143644085](./image/image-20210716143644085.png)

### **10.6**、**PreparedStatement**对象

PreperedStatement是Statement的子类，它的实例对象可以通过调用Connection.preparedStatement()方法获得，相对于Statement对象而言：PreperedStatement可以避 免SQL注入的问题。

Statement会使数据库频繁编译SQL，可能造成数据库缓冲区溢出。

PreparedStatement可对SQL进行预编译，从而提高数据库的执行效率。并且PreperedStatement对于 sql中的参数，允许使用占位符的形式进行替换，简化sql语句的编写。

 使用PreparedStatement对象完成对数据库的CRUD操作

####  1、插入数据

```java
package com.preparedStatement;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class testInsert {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "insert into users(id,name,password,email,birthday) values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            //为SQL语句中的参数赋值，注意，索引是从1开始的
            preparedStatement.setInt(1, 5);//id是int类型的
            preparedStatement.setString(2, "pio");//name是varchar(字符串类型)
            preparedStatement.setString(3, "123");//password是varchar(字符串类型)
            preparedStatement.setString(4, "asaf124@qq.com");//email是varchar(字符串类型)
            preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));//birthday是date类型
//执行插入操作，executeUpdate方法返回成功的条数
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("插入成功！！");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, preparedStatement, resultSet);
        }

    }
}
```

####  2、删除一条数据

```java
package com.preparedStatement;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class testDelete {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "delete from users where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 5);
            int row = preparedStatement.executeUpdate();
            if(row > 0){
                System.out.println("删除成功！！");
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection, preparedStatement, resultSet);
        }
    }
}
```

####  3、更新一条数据

```java
package com.preparedStatement;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testUpdate {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sql = "update users set name = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"jackob");
            preparedStatement.setInt(2,2);
            int row = preparedStatement.executeUpdate();
            if(row > 0){
                System.out.println("更新成功！！");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
    }
}
```

####  4、查询一条数据

```java
package com.preparedStatement;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class testSelect {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "select * from users where id > ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("username is "+resultSet.getString("name"));
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
    }
}
```

#### 5、避免SQL 注入

 <font color="red">**原理：执行的时候参数会用引号包起来，并把参数中的引号作为转义字符，从而避免了参数也作为条件 的一部分**</font>

```java
package com.lehend.preparedStatement;

import com.lehend.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class preparedStatementSqli extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "select * from users where name='" + username + "' and password = '" + password + "' ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

           resultSet = preparedStatement.executeQuery();
           while(resultSet.next()){
               System.out.println(resultSet.getString("name")+"："+resultSet.getString("password"));
               System.out.println("==============");
           }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

poc

```
username=lehend' AND updatexml(1, CONCAT('^',(SELECT DATABASE()),'~'),3) OR '1=1
&password=123
```

![image-20210719211400868](./image/image-20210719211400868.png)

![image-20210719211436606](./image/image-20210719211436606.png)



###  **10.7 idea 连接MySQL**

![image-20210719211836000](./image/image-20210719211836000.png)

如果配置账号和密码还是报错，提示驱动不存在，当前页面左下角有一个download 用于下载驱动，下载完成再次测试连接就没问题了。也可以在连接的url中指定数据库

![image-20210719212831052](./image/image-20210719212831052.png) 

创建一个MySQL命令行窗口

![image-20210719212537035](./image/image-20210719212537035.png)

![image-20210719212912192](./image/image-20210719212912192.png)

双击表名称查看表的内容

![image-20210719213227826](./image/image-20210719213227826.png)

双击字段内容后可以进行变更，然后点击提交完成变更

![image-20210719213419217](./image/image-20210719213419217.png)

### **10.8**、事务

 概念

 **事务指逻辑上的一组操作，组成这组操作的各个单元，要不全部成功，要不全部不成功。**

> ACID 原则

####  原子性**(Atomic)**

 整个事务中的所有操作，要么全部完成，要么全部不完成，不可能停滞在中间某个环节。事务在执

 行过程中发生错误，会被回滚（ROLLBACK）到事务开始前的状态，就像这个事务从来没有执行过一样。

####  一致性**(Consist)**

 一个事务可以封装状态改变（除非它是一个只读的）。事务必须始终保持系统处于一致的状态，不
 管在任何给定的时间并发事务有多少。也就是说：如果事务是并发多个，系统也必须如同串行事务
 一样操作。其主要特征是保护性和不变性(Preserving an Invariant)，以转账案例为例，假设有五

 个账户，每个账户余额是100元，那么五个账户总额是500元，如果在这个5个账户之间同时发生多
 个转账，无论并发多少个，比如在A与B账户之间转账5元，在C与D账户之间转账10元，在B与E之
 间转账15元，五个账户总额也应该还是500元，这就是保护性和不变性。

####  隔离性**(Isolated)**

 隔离状态执行事务，使它们好像是系统在给定时间内执行的唯一操作。如果有两个事务，运行在相

 同的时间内，执行相同的功能，事务的隔离性将确保每一事务在系统中认为只有该事务在使用系
 统。这种属性有时称为串行化，为了防止事务操作间的混淆，必须串行化或序列化请求，使得在同
 一时间仅有一个请求用于同一数据。

####  持久性**(Durable)**

 在事务完成以后，该事务对数据库所作的更改便持久的保存在数据库之中，并不会被回滚。

> 隔离性问题

 1、脏读：脏读指一个事务读取了另外一个事务未提交的数据。
 2、不可重复读：不可重复读指在一个事务内读取表中的某一行数据，多次读取结果不同。
 3、虚读(幻读) : 虚读(幻读)是指在一个事务内读取到了别的事务插入的数据，导致前后读取不一致。

 代码测试

```sql
/*创建账户表*/
CREATE TABLE account(
id INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(40),
money FLOAT
);
/*插入测试数据*/
insert into account(name,money) values('A',1000);
insert into account(name,money) values('B',1000);
insert into account(name,money) values('C',1000);
```

![image-20210719213805375](./image/image-20210719213805375.png)

![image-20210719213821231](./image/image-20210719213821231.png) 



 当Jdbc程序向数据库获得一个Connection对象时，默认情况下这个Connection对象会自动向数据库提交 在它上面发送的SQL语句。若想关闭这种默认提交方式，让多条SQL在一个事务中执行，可使用下列的
 JDBC控制事务语句

- Connection.setAutoCommit(false);//开启事务(star transaction)
- Connection.rollback();//回滚事务(rollback)
- Connection.commit();//提交事务(commit)

> 程序编写

 1、模拟转账成功时的业务场景

这里catch块没有设置，默认自动回滚

```java
package com.lehend.transaction;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Failed {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
//            关闭mysql 自动提交，相当于开启事务
            connection.setAutoCommit(false);//通知数据库开启事务(start transaction)
            String sql1 = "update account set money=money-100 where name='A'";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            ////用这句代码模拟执行完SQL1之后程序出现了异常而导致后面的SQL无法正常执行，事务也无法正常提交，此时数据库会自动执行回滚操作
            int num = 1 / 0;
            String sql2 = "update account set money=money+100 where name='B'";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            connection.commit();//上面的两条SQL执行Update语句成功之后就通知数据库提交事务(commit)

            System.out.println("执行成功！！！");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
```

2、模拟转账过程中出现异常导致有一部分SQL执行失败后让数据库自动回滚事务

这里catch块有设置，手动回滚

```java
package com.lehend.transaction;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Failed {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
//            关闭mysql 自动提交，相当于开启事务
            connection.setAutoCommit(false);//通知数据库开启事务(start transaction)
            String sql1 = "update account set money=money-100 where name='A'";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            ////用这句代码模拟执行完SQL1之后程序出现了异常而导致后面的SQL无法正常执行，事务也无法正常提交，此时数据库会自动执行回滚操作
            int num = 1 / 0;
            String sql2 = "update account set money=money+100 where name='B'";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            connection.commit();//上面的两条SQL执行Update语句成功之后就通知数据库提交事务(commit)

            System.out.println("执行成功！！！");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
```

![image-20210719215402570](./image/image-20210719215402570.png)

![image-20210719215639653](./image/image-20210719215639653.png)

3、模拟转账过程中出现异常导致有一部分SQL执行失败时手动通知数据库回滚事务

```java
package com.lehend.transaction;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class failed2 {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
//            关闭mysql 自动提交，相当于开启事务
            connection.setAutoCommit(false);//通知数据库开启事务(start transaction)
            String sql1 = "update account set money=money-100 where name='A'";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            ////用这句代码模拟执行完SQL1之后程序出现了异常而导致后面的SQL无法正常执行，事务也无法正常提交，此时数据库会自动执行回滚操作
            int num = 1 / 0;
            String sql2 = "update account set money=money+100 where name='B'";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            connection.commit();//上面的两条SQL执行Update语句成功之后就通知数据库提交事务(commit)

            System.out.println("执行成功！！！");

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }

    }
}
```

### ![image-20210719215402570](./image/image-20210719215402570.png) 



### **10.9 **、数据库连接池

 用户每次请求都需要向数据库获得链接，而数据库创建连接通常需要消耗相对较大的资源，创建时间也
 较长。假设网站一天10万访问量，数据库服务器就需要创建10万次连接，极大的浪费数据库的资源，并 且极易造成数据库服务器内存溢出、拓机。

> 数据库连接池的基本概念

 数据库连接是一种关键的有限的昂贵的资源,这一点在多用户的网页应用程序中体现的尤为突出.对数据库 连接的管理能显著影响到整个应用程序的伸缩性和健壮性,影响到程序的性能指标.数据库连接池正式针对 这个问题提出来的.**数据库连接池负责分配,管理和释放数据库连接,它允许应用程序重复使用一个现有的数据库连接,而不是重新建立一个。**
 数据库连接池在初始化时将创建一定数量的数据库连接放到连接池中, 这些数据库连接的数量是由最小数 据库连接数来设定的.无论这些数据库连接是否被使用,连接池都将一直保证至少拥有这么多的连接数量. 连接池的最大数据库连接数量限定了这个连接池能占有的最大连接数,当应用程序向连接池请求的连接数 超过最大连接数量时,这些请求将被加入到等待队列中.

 数据库连接池的最小连接数和最大连接数的设置要考虑到以下几个因素:

1. 最小连接数:是连接池一直保持的数据库连接,所以如果应用程序对数据库连接的使用量不大,将会有大量的数据库连接资源被浪费.
2. 最大连接数:是连接池能申请的最大连接数,如果数据库连接请求超过次数,后面的数据库连接请求将被加入到等待队列中,这会影响以后的数据库操作
3. 如果最小连接数与最大连接数相差很大:那么最先连接请求将会获利,之后超过最小连接数量的连接
4. 请求等价于建立一个新的数据库连接.不过,这些大于最小连接数的数据库连接在使用完不会马上被释放,他将被放到连接池中等待重复使用或是空间超时后被释放.

 ![image-20210719220154923](./image/image-20210719220154923.png)

<font color="red">编写连接池需实现**java.sql.DataSource**接口。</font>

> 开源数据库连接池

 现在很多WEB服务器(Weblogic, WebSphere, Tomcat)都提供了DataSoruce的实现，即连接池的实现。 **通常我们把DataSource的实现，按其英文含义称之为数据源，数据源中都包含了数据库连接池的实现。**

 也有一些开源组织提供了数据源的独立实现：

- DBCP 数据库连接池
- C3P0 数据库连接池

在使用了数据库连接池之后，在项目的实际开发中就不需要编写连接数据库的代码了，直接从数据源获得数据库的连接。

#### DBCP数据源

 DBCP 是 Apache 软件基金组织下的开源连接池实现，要使用DBCP数据源，需要应用程序应在系统中增 加如下两个 jar 文件：

 commons-dbcp-1.4.jar：连接池的实现
 commons-pool-1.6.jar：连接池实现的依赖库

maven依赖

```xml
<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>

<dependency>
    <groupId>commons-pool</groupId>
    <artifactId>commons-pool</artifactId>
    <version>1.6</version>
</dependency>
```



 Tomcat 的连接池正是采用该连接池来实现的。该数据库连接池既可以与应用服务器整合使用，也可由 应用程序独立使用。

 测试：

#####  1、导入相关jar包

```XML
<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>

<dependency>
    <groupId>commons-pool</groupId>
    <artifactId>commons-pool</artifactId>
    <version>1.6</version>
</dependency>
```

#####  2、在类目录下加入dbcp的配置文件：dbcpconfig.properties

```properties
#连接设置
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/jdbcStudy?
useUnicode=true&characterEncoding=utf8&useSSL=true
username=root
password=root
#<!-- 初始化连接 -->
initialSize=10
#最大连接数量
maxActive=50
#<!-- 最大空闲连接 -->
maxIdle=20
#<!-- 最小空闲连接 -->
minIdle=5
#<!-- 超时等待时间以毫秒为单位 6000毫秒/1000等于60秒 -->
maxWait=60000
#JDBC驱动建立连接时附带的连接属性属性的格式必须为这样：[属性名=property;]
#注意："user" 与 "password" 两个属性会被明确地传递，因此这里不需要包含他们。
connectionProperties=useUnicode=true;characterEncoding=UTF8
#指定由连接池所创建的连接的自动提交（auto-commit）状态。
defaultAutoCommit=true
#driver default 指定由连接池所创建的连接的只读（read-only）状态。
#如果没有设置该值，则“setReadOnly”方法将不被调用。（某些驱动并不支持只读模式，如：
Informix）
defaultReadOnly=
#driver default 指定由连接池所创建的连接的事务级别（TransactionIsolation）。
#可用值为下列之一：（详情可见javadoc。）NONE,READ_UNCOMMITTED, READ_COMMITTED,
REPEATABLE_READ, SERIALIZABLE
defaultTransactionIsolation=READ_UNCOMMITTED
```

#####  3、编写工具类 JdbcUtils_DBCP.java

```java
package com.lehend.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

//数据库连接工具类
public class JdbcUtils_DBCP {
    /**
     * 在java中，编写数据库连接池需实现java.sql.DataSource接口，每一种数据库连接池都
     * 是DataSource接口的实现
     * DBCP连接池就是java.sql.DataSource接口的一个具体实现
     */
    private static DataSource ds = null;
    //在静态代码块中创建数据库连接池
    static {
        try {
            //加载dbcpconfig.properties配置文件
            InputStream in = JdbcUtils_DBCP.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties prop = new Properties();
            prop.load(in);
            //创建数据源
            ds = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    //从数据源中获取数据库连接
    public static Connection getConnection() throws SQLException {
//从数据源中获取数据库连接
        return ds.getConnection();
    }

    // 释放资源
    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
//关闭存储查询结果的ResultSet对象
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (st != null) {
            try {
//关闭负责执行SQL命令的Statement对象
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
//将Connection连接对象还给数据库连接池
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

![image-20210719223859618](./image/image-20210719223859618.png)

 

#### C3P0

C3P0是一个开源的JDBC连接池，它实现了数据源和JNDI绑定，支持JDBC3规范和JDBC2的标准扩展。目 前使用它的开源项目有Hibernate，Spring等。C3P0数据源在项目开发中使用得比较多。

**c3p0与dbcp区别**

- dbcp没有自动回收空闲连接的功能 
- c3p0有自动回收空闲连接功能

测试 

##### 1、导入相关jar包 

c3p0-0.9.5.5.jar

mchange-commons-java-0.2.19.jar

##### 2、在类目录下加入C3P0的配置文件：c3p0-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
    <!--
    C3P0的缺省(默认)配置，
    如果在代码中“ComboPooledDataSource ds = new ComboPooledDataSource();”这样写
    就表示使用的是C3P0的缺省(默认)配置信息来创建数据源
    -->
    <default-config>
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/jdbcStudy</property>
        <property name="user">root</property>
        <property name="password">root</property>
        <property name="acquireIncrement">5</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">5</property>
        <property name="maxPoolSize">20</property>
    </default-config>

    <!--C3P0的命名配置，如果在代码中“ComboPooledDataSource ds = new ComboPooledDataSource("MySQL");”这样写就表示使用的是name是MySQL的配置信息来创建数据源-->
    <named-config name="MySQL">
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/jdbcStudy</property>
        <property name="user">root</property>
        <property name="password">root</property>
        <property name="acquireIncrement">5</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">5</property>
        <property name="maxPoolSize">20</property>
    </named-config>
</c3p0-config>
```

maven依赖

```xml
<!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.5.5</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.mchange/mchange-commons-java -->
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>mchange-commons-java</artifactId>
    <version>0.2.19</version>
</dependency>
```

##### 3、创建工具类 JdbcUtils_C3P0.java

```java
package com.lehend.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//数据库连接工具类
public class JdbcUtils_C3P0 {
    private static ComboPooledDataSource ds = null;

    //在静态代码块中创建数据库连接池
    static {
        try {
//通过代码创建C3P0数据库连接池
/*ds = new ComboPooledDataSource();
ds.setDriverClass("com.mysql.jdbc.Driver");
ds.setJdbcUrl("jdbc:mysql://localhost:3306/jdbcstudy");
ds.setUser("root");
ds.setPassword("123456");
ds.setInitialPoolSize(10);
ds.setMinPoolSize(5);
ds.setMaxPoolSize(20);*/
//通过读取C3P0的xml配置文件创建数据源，C3P0的xml配置文件c3p0-config.xml必须放在src目录下
//ds = new ComboPooledDataSource();//使用C3P0的默认配置来创建数据源
            ds = new ComboPooledDataSource("MySQL");//使用C3P0的命名配置来创建数据源
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    //从数据源中获取数据库连接
    public static Connection getConnection() throws SQLException {
//从数据源中获取数据库连接
        return ds.getConnection();
    }

    //释放资源
    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
//关闭存储查询结果的ResultSet对象
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (st != null) {
            try {
//关闭负责执行SQL命令的Statement对象
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
//将Connection连接对象还给数据库连接池
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

测试

```java
package com.lehend.ConnectionPool;


import com.lehend.utils.JdbcUtils;
import com.lehend.utils.JdbcUtils_C3P0;
import com.lehend.utils.JdbcUtils_DBCP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


public class c3p0Insert {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils_C3P0.getConnection();
            String sql = "insert into users(id,name,password,email,birthday) values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            //为SQL语句中的参数赋值，注意，索引是从1开始的
            preparedStatement.setInt(1, 7);//id是int类型的
            preparedStatement.setString(2, "pio");//name是varchar(字符串类型)
            preparedStatement.setString(3, "123");//password是varchar(字符串类型)
            preparedStatement.setString(4, "asaf124@qq.com");//email是varchar(字符串类型)
            preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));//birthday是date类型
//执行插入操作，executeUpdate方法返回成功的条数
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("插入成功！！");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, preparedStatement, resultSet);
        }

    }
}
```

![image-20210719230150217](./image/image-20210719230150217.png)