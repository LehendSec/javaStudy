package com.lehend;

import java.sql.*;

public class JdbcStatement {
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
