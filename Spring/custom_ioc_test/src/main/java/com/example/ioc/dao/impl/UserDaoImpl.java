package com.example.ioc.dao.impl;

import com.example.ioc.dao.IUserDao;

public class UserDaoImpl implements IUserDao {
    @Override
    public void save() {
        System.out.println("保存成功");
    }
}
