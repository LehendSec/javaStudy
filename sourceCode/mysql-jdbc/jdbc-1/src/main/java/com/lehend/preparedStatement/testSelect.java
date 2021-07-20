package com.lehend.preparedStatement;

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
