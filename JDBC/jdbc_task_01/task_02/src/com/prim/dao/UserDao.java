package com.prim.dao;

import com.prim.entity.User;
import com.prim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


/**
 * 数据访问层
 * 用户表操作
 */
public class UserDao {
    //注册用户的方法，接受一个user对象
    public int register(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getUsername(),
                user.getPassword(), user.getTelephone(), user.getBirthday(), user.getSex()};
        int update = qr.update(sql, params);
        return update;
    }

    //用户登录
    public User login(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from user where username=? and password=?";
        User user = qr.query(sql, new BeanHandler<>(User.class), username, password);
        return user;
    }
}
