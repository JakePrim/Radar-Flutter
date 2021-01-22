package com.edu.service;

import com.edu.pojo.User;
import com.edu.pojo.vo.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    PageInfo<User> findAllUserByPage(UserVo userVo);

    void updateUserStatus(Integer id, String status);
}
