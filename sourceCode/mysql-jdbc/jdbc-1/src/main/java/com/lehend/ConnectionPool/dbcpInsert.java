package com.lehend.ConnectionPool;


import com.lehend.utils.JdbcUtils;
import com.lehend.utils.JdbcUtils_DBCP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


public class dbcpInsert {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils_DBCP.getConnection();
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
