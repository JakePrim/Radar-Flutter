package com.edu.service;

import com.edu.pojo.Role;
import com.edu.pojo.vo.RoleMenuVo;

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
}
