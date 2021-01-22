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
}
