package com.lehend.statement;

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
