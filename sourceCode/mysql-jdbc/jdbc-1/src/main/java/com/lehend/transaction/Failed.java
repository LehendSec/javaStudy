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
