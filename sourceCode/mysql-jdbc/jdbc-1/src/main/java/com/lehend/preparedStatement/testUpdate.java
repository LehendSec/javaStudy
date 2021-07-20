package com.lehend.preparedStatement;

import com.lehend.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
