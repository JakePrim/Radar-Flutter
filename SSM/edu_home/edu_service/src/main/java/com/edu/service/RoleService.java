package com.edu.service;

import com.edu.pojo.ResourceCategory;
import com.edu.pojo.Role;
import com.edu.pojo.vo.RoleMenuVo;
import com.edu.pojo.vo.RoleResourceVo;

import java.util.List;

public interface RoleService {
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

    List<Integer> findMenuIdByRoleId(Integer roleId);

    void roleContextMenu(RoleMenuVo roleMenuVo);

    void deleteRole(Integer roleId);

    /**
     * 查询该角色的拥有的资源信息
     *
     * @param roleId
     * @return
     */
    List<ResourceCategory> findResourceListByRoleId(Integer roleId);

    /**
     * 为角色分配资源
     */
    void roleContextResource(RoleResourceVo roleResourceVo);
}
