package com.prim.app;

import com.prim.dao.UserDao;
import com.prim.entity.User;
import com.prim.utils.DateUtils;
import com.prim.utils.UUIDUtils;
import org.junit.Test;

import java.sql.SQLException;

public class TestUserDao {
    //测试用户操作
    UserDao userDao = new UserDao();

    @Test
    public void testRegister() throws SQLException {
        //1. 创建user
        User user = new User();
        user.setUid(UUIDUtils.getUUID());
        user.setUsername("武松");
        user.setPassword("123456");
        user.setTelephone("13512341234");
        user.setSex("男");
        user.setBirthday(DateUtils.getDateFormart());
        int register = userDao.register(user);
        System.out.println("影响行数:" + register);
    }

    @Test
    public void testLogin() throws SQLException {
        User user = userDao.login("武松", "123456");
        if (user != null) {
            System.out.println("欢迎您:" + user.getUsername());
        } else {
            System.out.println("用户名或密码错误");
        }
    }


}
