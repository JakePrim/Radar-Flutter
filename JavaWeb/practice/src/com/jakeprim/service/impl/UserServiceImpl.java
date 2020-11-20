package com.jakeprim.service.impl;

import java.util.List;

import com.jakeprim.domain.User;
import com.jakeprim.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void regist(List<User> users, User user) {
		users.add(user);
	}

	@Override
	public User login(List<User> users, User user) {
		for (User item : users) {
			if (item.getUsername().equals(user.getUsername()) && item.getPassword().equals(user.getPassword())) {
				return item;
			}
		}
		return null;
	}

}
