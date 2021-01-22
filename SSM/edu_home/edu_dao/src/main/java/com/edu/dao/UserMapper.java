package com.edu.dao;

import com.edu.pojo.User;
import com.edu.pojo.vo.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserMapper {
    List<User> findAllUserByPage(UserVo userVo);

    /**
     * 更新用户的状态
     * @param user
     */
    void updateUserStatus(User user);
}
