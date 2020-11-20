package com.prim.dao;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderTest {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/t_jdbc_template?useUnicode=true&characterEncoding=utf-8";
    private String name = "root";
    private String password = "root";

    /**
     * 模拟添加订单
     */
    @Test
    public void addOrder() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(driver);//加载驱动
            connection = DriverManager.getConnection(url, name, password);
            connection.setAutoCommit(false);//手动提交事务
            statement = connection.createStatement();
            statement.execute("insert into `order` values('10002','10001',2,2499,now(),null,null,'王某','110','北京王府井','待发货')");
            statement.execute("update product set stock = stock-2 where id='10001'");
            connection.commit();
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
