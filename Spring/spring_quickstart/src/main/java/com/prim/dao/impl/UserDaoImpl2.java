package com.prim.dao.impl;

import com.prim.dao.UserDao;

public class UserDaoImpl2 implements UserDao {
    @Override
    public void save() {
        System.out.println("保存成功2");
    }
}
