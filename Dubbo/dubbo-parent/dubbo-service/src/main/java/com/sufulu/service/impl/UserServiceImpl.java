package com.sufulu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sufulu.entity.User;
import com.sufulu.mapper.UserMapper;
import com.sufulu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service //暴露服务
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public int register(User user) {
        return userMapper.register(user);
    }

    public int delete(Integer id) {
        return userMapper.delete(id);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    public List<User> findByName(String username) {
        return userMapper.findByName(username);
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
