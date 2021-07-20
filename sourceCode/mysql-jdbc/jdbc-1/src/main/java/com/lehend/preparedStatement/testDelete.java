package com.lehend.preparedStatement;

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
