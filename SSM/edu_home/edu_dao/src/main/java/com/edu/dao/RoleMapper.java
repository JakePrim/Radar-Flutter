package com.edu.dao;

import com.edu.pojo.*;

import java.util.List;

public interface RoleMapper {
    List<Role> findAllRole(Role role);

    /**
     * 创建角色，注意要把创建角色的用户传入
     *
     * @param role
     */
    void saveRole(Role role);

    /**
     * 更新角色，要把更新角色的用户传入
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 根据roleID 清空中间表的关系
     */
    void deleteRoleContextMenu(Integer roleId);

    /**
     * 为角色分配添加菜单信息
     */
    void roleContextMenu(Role_menu_relation role_menu_relation);

    void deleteRole(Integer roleId);

    void deleteRoleContextUser(Integer roleId);

    /**
     * 查询该角色的拥有的资源分类
     */
    List<ResourceCategory> findResourceCategoryByRoleId(Integer roleId);

    /**
     * 查询该角色下的资源
     *
     * @param roleId
     * @return
     */
    List<Resource> findResourceByRoleId(Integer roleId);

    /**
     * 删除资源和角色的关联关系
     */
    void deleteResourceContextRoleByRoleId(Integer roleId);

    /**
     * 为资源分配角色
     */
    void roleContextResource(RoleResourceRelation roleResourceRelation);
}
