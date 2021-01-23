package com.edu.service.impl;

import com.edu.common.Md5;
import com.edu.common.StateCode;
import com.edu.dao.UserMapper;
import com.edu.pojo.*;
import com.edu.pojo.vo.UserRoleVo;
import com.edu.pojo.vo.UserVo;
import com.edu.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    @Override
    public User findByPhone(User user) throws Exception {
        User findUser = userMapper.findByPhone(user.getPhone());
        System.out.println(findUser.getPassword()+":"+user.getPassword());
        System.out.println("Md5:"+Md5.verify(user.getPassword(), "lagou", findUser.getPassword()));
        if (findUser != null && Md5.verify(user.getPassword(), "lagou", findUser.getPassword())) {
            return findUser;
        }
        return null;
    }

    @Override
    public List<Role> findUserRoleById(Integer userId) {
        List<Role> roles = userMapper.findUserRoleById(userId);
        return roles;
    }

    /**
     * 给用户分配角色
     *
     * @param userRoleVo
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userContextRole(UserRoleVo userRoleVo) {
        //1. 移除之前关联的角色
        userMapper.deleteUserContextRole(userRoleVo.getUserId());
        //2. 关联新的角色
        // 获取角色id 列表
        List<Integer> roleIdList = userRoleVo.getRoleIdList();
        if (roleIdList != null && roleIdList.size() > 0) {
            for (Integer roleId : roleIdList) {
                User_Role_relation relation = new User_Role_relation();
                relation.setUserId(userRoleVo.getUserId());
                relation.setRoleId(roleId);
                Date date = new Date();
                relation.setCreatedTime(date);
                relation.setUpdatedTime(date);
                relation.setCreatedBy("system");
                relation.setUpdatedby("system");
                userMapper.insertUserContextRole(relation);
            }
        }
    }

    @Override
    public ResponseResult getUserPermission(Integer userId) {
        //1. 根据用户id 查询用户所拥有的角色
        List<Role> roles = userMapper.findUserRoleById(userId);
        //2. 获取角色id 保存list集合中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        //3. 获取角色分配的顶级菜单信息
        List<Menu> parentMenus = userMapper.findParentMenuByRoleId(roleIds);
        List<Integer> pids = new ArrayList<>();
        for (Menu parentMenu : parentMenus) {
            pids.add(parentMenu.getId());
        }
        //5. 查询有权限的所有子菜单id
        List<Integer> subIds = userMapper.findContextSubByRoleId(pids, roleIds);
        //6. 查询所在父菜单 分配的子级菜单
        for (Menu parentMenu : parentMenus) {
            List<Menu> subMenus = userMapper.findSubMenuByPid(parentMenu.getId(), subIds);
            parentMenu.setSubMenuList(subMenus);
        }
        //7. 查询所有的资源信息
        List<Resource> resources = userMapper.findUserContextResourceByRoleId(roleIds);

        HashMap<String, Object> map = new HashMap<>();
        map.put("menuList", parentMenus);
        map.put("resourceList", resources);

        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return responseResult;
    }
}
