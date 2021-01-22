package com.edu.service;

import com.edu.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findSubMenuListByPid(Integer pid);

    List<Menu> findAllMenu();

    Menu findMenuById(Integer id);

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);
}
