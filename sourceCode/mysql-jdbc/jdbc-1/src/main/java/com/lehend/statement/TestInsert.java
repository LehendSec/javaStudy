package com.lehend.statement;

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
