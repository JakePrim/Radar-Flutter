package com.example.ioc.service.impl;

import com.example.ioc.dao.IUserDao;
import com.example.ioc.service.IUserService;
import com.example.ioc.utils.BeanFactory;

public class UserServiceImpl implements IUserService {

//    IUserDao userDao = new UserDaoImpl();

    @Override
    public void save() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        userDao.save();
//        IUserDao userDao = (IUserDao) Class.forName("com.example.ioc.dao.impl.UserDaoImpl").newInstance();
        IUserDao userDao = (IUserDao) BeanFactory.getBean("userDao");
        userDao.save();
    }
}
