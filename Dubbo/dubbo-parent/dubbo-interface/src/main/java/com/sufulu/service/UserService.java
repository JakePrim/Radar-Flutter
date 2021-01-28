package com.sufulu.service;

import com.sufulu.entity.User;

import java.util.List;

public interface UserService {
    int register(User user);

    int delete(Integer id);

    int update(User user);

    List<User> findByName(String username);

    User findById(Integer id);
}
