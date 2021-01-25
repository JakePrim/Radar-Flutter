package com.edu.dao;

import com.edu.pojo.Menu;

import java.util.List;

public interface MenuMapper {
    /**
     * 查询所有父子菜单信息
     */
    List<Menu> findSubMenuListByPid(Integer pid);

    List<Integer> findMenuIdByRoleId(Integer roleId);

    /**
     * 查询所有菜单列表
     */
    List<Menu> findAllMenu();

    Menu findMenuById(Integer id);

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    /**
     * 如果删除的一级菜单 那么一级菜单下的子菜单也要进行删除
     * 删除菜单，需要删除角色和菜单的中间表关联信息
     * 然后在删除菜单
     */
    void deleteMenuContextRoleByMenuId(Integer id);

    void deleteMenuById(Integer id);

    void deleteMenuByParentId(Integer pid);
}
