package com.prim.service.impl;

import com.prim.dao.UserDao;
import com.prim.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImpl implements UserService {

    //注入UserDao对象
    private UserDao userDao;

//    public UserServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }


    //创建setter方法 注入UserDao对象
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        //根据之前将的IOC容器中获取 dao的实例类
        //将下面的关系，其实可以由Spring框架来维护 持久层和业务层的依赖关系 而不需要我们手动去维护
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.save();
    }
}
