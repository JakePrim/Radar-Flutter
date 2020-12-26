package com.example.task04.service;

import com.example.task04.dao.UserDao;
import com.example.task04.factory.UserDaoFactory;
import com.example.task04.model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = UserDaoFactory.getUserDao();
    }

    /**
     * 实现登录业务逻辑处理
     *
     * @param user
     * @return
     */
    public User userLoginService(User user) {
        return userDao.userLogin(user);
    }
}
