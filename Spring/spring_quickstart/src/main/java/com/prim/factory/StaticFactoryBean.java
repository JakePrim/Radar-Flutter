package com.prim.factory;

import com.prim.dao.UserDao;
import com.prim.dao.impl.UserDaoImpl;

public class StaticFactoryBean {
    public static UserDao createUserDao() {
        //假设：这个对象在jar包中的一个对象
        return new UserDaoImpl();
    }
}
