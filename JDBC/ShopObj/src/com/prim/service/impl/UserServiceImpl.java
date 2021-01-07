package com.prim.service.impl;

import com.prim.dao.UserDao;
import com.prim.dao.impl.UserDaoImpl;
import com.prim.domain.User;
import com.prim.service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public User login(User user) {
		//将输入的信息传递到dao层,查看数据库中是否存在
		UserDao userDao = new UserDaoImpl();
		return userDao.login(user);
	}
}
