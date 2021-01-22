package com.edu.service.impl;

import com.edu.dao.UserMapper;
import com.edu.pojo.User;
import com.edu.pojo.vo.UserVo;
import com.edu.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> findAllUserByPage(UserVo userVo) {
        //开启分页
        if (userVo != null) {
            PageHelper.startPage(userVo.getCurrentPage(), userVo.getPageSize());
        }
        List<User> users = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public void updateUserStatus(Integer id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());

        userMapper.updateUserStatus(user);
    }
}
