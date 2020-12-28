package com.example.task04.factory;

import com.example.task04.dao.UserDao;
import com.example.task04.dao.UserDaoImp;

public class UserDaoFactory {
    public static UserDao getUserDao(){
        return new UserDaoImp();
    }
}
