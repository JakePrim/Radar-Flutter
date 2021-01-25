package com.edu.service;

import com.edu.pojo.Menu;
import com.edu.pojo.vo.PageVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MenuService {
    List<Menu> findSubMenuListByPid(Integer pid);

    PageInfo<Menu> findAllMenu(PageVO pageVO);

    Menu findMenuById(Integer id);

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Integer id);
}
