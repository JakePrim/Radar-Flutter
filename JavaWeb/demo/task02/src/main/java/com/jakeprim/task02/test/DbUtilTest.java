package com.jakeprim.task02.test;

import com.jakeprim.task02.utils.DbUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtilTest {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DbUtils.getConnection();
            System.out.println("连接数据库成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("连接数据库失败");
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
