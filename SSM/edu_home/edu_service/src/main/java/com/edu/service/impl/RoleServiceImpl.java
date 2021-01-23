package com.edu.service.impl;

import com.edu.dao.MenuMapper;
import com.edu.dao.RoleMapper;
import com.edu.pojo.*;
import com.edu.pojo.vo.RoleMenuVo;
import com.edu.pojo.vo.RoleResourceVo;
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

    /**
     * 查询该角色的拥有的资源信息
     *
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceCategory> findResourceListByRoleId(Integer roleId) {
        //1. 查询该角色拥有的资源分类信息
        List<ResourceCategory> resourceCategoryList = roleMapper.findResourceCategoryByRoleId(roleId);
        //2. 查询该角色拥有的资源信息
        List<Resource> resourceList = roleMapper.findResourceByRoleId(roleId);
        //封装资源数据到分类下
        for (Resource resource : resourceList) {
            for (ResourceCategory resourceCategory : resourceCategoryList) {
                if (resource.getCategoryId().equals(resourceCategory.getId())) {
                    resourceCategory.getResourceList().add(resource);
                    break;
                }
            }
        }

        return resourceCategoryList;
    }

    /**
     * 为角色分配资源,注意进行事务管理
     *
     * @param roleResourceVo
     */
    @Transactional
    @Override
    public void roleContextResource(RoleResourceVo roleResourceVo) {
        //1. 删除角色和资源之前的关联关系
        roleMapper.deleteResourceContextRoleByRoleId(roleResourceVo.getRoleId());
        //2. 为角色分配资源
        List<Integer> resourceIds = roleResourceVo.getResourceIdList();
        if (resourceIds != null && resourceIds.size() > 0) {
            for (Integer resourceId : resourceIds) {
                RoleResourceRelation relation = new RoleResourceRelation();
                relation.setResourceId(resourceId);
                relation.setRoleId(roleResourceVo.getRoleId());
                Date date = new Date();
                relation.setCreatedTime(date);
                relation.setUpdatedTime(date);
                relation.setCreatedBy("system");
                relation.setUpdatedBy("system");
                roleMapper.roleContextResource(relation);
            }
        }
    }
}
