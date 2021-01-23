package com.edu.service;

import com.edu.pojo.ResponseResult;
import com.edu.pojo.Role;
import com.edu.pojo.User;
import com.edu.pojo.vo.UserRoleVo;
import com.edu.pojo.vo.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    PageInfo<User> findAllUserByPage(UserVo userVo);

    void updateUserStatus(Integer id, String status);

    User findByPhone(User user) throws Exception;

    List<Role> findUserRoleById(Integer userId);

    void userContextRole(UserRoleVo userRoleVo);

    /**
     * 获取权限 进行菜单动态展示
     */
    ResponseResult getUserPermission(Integer userId);
}
