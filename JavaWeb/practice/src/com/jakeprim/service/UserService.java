package com.jakeprim.service;

import java.util.List;

import com.jakeprim.domain.User;

public interface UserService {
	void regist(List<User> users,User user);
	
	User login(List<User> users,User user);
}
