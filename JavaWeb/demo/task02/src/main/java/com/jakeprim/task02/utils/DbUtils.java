package com.jakeprim.task02.utils;

import java.sql.*;

public class DbUtils {
    private static String jdbcName; //驱动信息
    private static String dbUrl; //连接数据库信息
    private static String dbUserName;//用户名
    private static String dbPassword;//密码

    //进行静态成员的初始化操作
    static {
        jdbcName = "com.mysql.jdbc.Driver";//驱动包
        //characterEncoding 如果不设置，向数据库存入中文会显示??
        dbUrl = "jdbc:mysql://localhost:3306/db_web?characterEncoding=utf8";
        dbUserName = "root";
        dbPassword = "123456";
        try {
            Class.forName(jdbcName);//可以省略
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        return connection;
    }

    public static void close(Connection connection) throws SQLException {
        if (null != connection) {
            connection.close();
        }
    }

    public static void close(Connection connection, Statement statement) throws SQLException {
        if (null != statement) {
            statement.close();
        }
        if (null != connection) {
            connection.close();
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (null != statement) {
            statement.close();
        }
        if (null != connection) {
            connection.close();
        }
    }

}
