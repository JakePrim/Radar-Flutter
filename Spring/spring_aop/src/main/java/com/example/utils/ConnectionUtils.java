package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 从数据源中获取一个连接，并且获取到的连接与线程绑定
 */
@Component //生成该类的实例 存到IOC容器中
public class ConnectionUtils {
    @Autowired
    private DataSource dataSource;

    //线程内部的存储类，可以在指定的线程内来存储数据 key:ThreadLocal value:任意类型的值
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 获取当前线程绑定的连接，如果获取到的连接为空，那么从数据源中获取连接并且放到ThreadLocal中 绑定到当前线程中
     *
     * @return
     */
    public Connection getThreadConnection() {
        //1. 线程ThreadLocal上获取连接
        Connection connection = threadLocal.get();
        //2. 如果连接为空 则从数据源获取连接 存储到当前线程中
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 解除当前线程的连接绑定
     */
    public void removeThreadLocal(){
        threadLocal.remove();
    }
}
