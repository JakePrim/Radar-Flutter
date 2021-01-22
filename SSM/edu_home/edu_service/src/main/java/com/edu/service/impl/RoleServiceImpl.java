package com.edu.service.impl;

import com.edu.dao.MenuMapper;
import com.edu.dao.RoleMapper;
import com.edu.pojo.Role;
import com.edu.pojo.Role_menu_relation;
import com.edu.pojo.vo.RoleMenuVo;
import com.edu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        List<Role> roles = roleMapper.findAllRole(role);
        return roles;
    }

    @Override
    public void saveRole(Role role) {
        //TODO 获取操作当前的 用户名
        role.setCreatedBy("test");
        role.setUpdatedBy("test");
        Date date = new Date();
        role.setCreatedTime(date);
        role.setUpdatedTime(date);

        roleMapper.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {
        //TODO 获取操作当前的 用户名
        role.setUpdatedBy("test");
        role.setUpdatedTime(new Date());
        roleMapper.updateRole(role);
    }

    @Override
    public List<Integer> findMenuIdByRoleId(Integer roleId) {
        List<Integer> menuIds = menuMapper.findMenuIdByRoleId(roleId);
        return menuIds;
    }

    //事务管理 防止删除后  添加出现异常导致没有添加成功，而且关联关系也删除了
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        //1. 先删除关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());
        //2. 添加关联关系
        List<Integer> menuIdList = roleMenuVo.getMenuIdList();
        Date date = new Date();
        for (Integer menuId : menuIdList) {
            Role_menu_relation relation = new Role_menu_relation();
            relation.setRoleId(roleMenuVo.getRoleId());
            relation.setMenuId(menuId);
            relation.setCreatedTime(date);
            relation.setUpdatedTime(date);
            relation.setCreatedBy("system");
            relation.setUpdatedBy("system");
            roleMapper.roleContextMenu(relation);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteRole(Integer roleId) {
        //删除角色和菜单的关联关系
        roleMapper.deleteRoleContextMenu(roleId);
        //删除角色和用户的关联关系
        roleMapper.deleteRoleContextUser(roleId);
        //从角色表删除角色
        roleMapper.deleteRole(roleId);
    }
}
