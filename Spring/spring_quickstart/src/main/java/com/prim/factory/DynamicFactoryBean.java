package com.prim.factory;

import com.prim.dao.UserDao;
import com.prim.dao.impl.UserDaoImpl;

public class DynamicFactoryBean {
    public UserDao createUserDao() {
        return new UserDaoImpl();
    }
}
