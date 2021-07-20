package com.lehend.statement;

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
