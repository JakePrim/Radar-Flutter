package com.jakeprim.service.impl;

import java.util.List;

import com.jakeprim.domain.User;
import com.jakeprim.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public int login(List<User> listUser, User user) {
		for (User item : listUser) {
			if (item.getPassword().equals(user.getPassword()) && item.getUsername().equals(user.getUsername())) {
				return 1;
			}
		}
		return 0;
	}

}
