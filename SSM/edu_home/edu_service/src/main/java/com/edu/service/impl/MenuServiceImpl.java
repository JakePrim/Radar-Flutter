package com.edu.service.impl;

import com.edu.dao.MenuMapper;
import com.edu.pojo.Menu;
import com.edu.pojo.vo.PageVO;
import com.edu.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findSubMenuListByPid(Integer pid) {
        List<Menu> menus = menuMapper.findSubMenuListByPid(pid);
        return menus;
    }

    @Override
    public PageInfo<Menu> findAllMenu(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Menu> menuList = menuMapper.findAllMenu();
        PageInfo<Menu> pageInfo = new PageInfo<>(menuList);
        return pageInfo;
    }

    @Override
    public Menu findMenuById(Integer id) {
        Menu menu = menuMapper.findMenuById(id);
        return menu;
    }

    @Override
    public void saveMenu(Menu menu) {
        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);
        menu.setCreatedBy("system");
        menu.setUpdatedBy("system");
        menuMapper.saveMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menu.setUpdatedTime(new Date());
        menu.setUpdatedBy("system");
        menuMapper.updateMenu(menu);
    }

    @Transactional
    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteMenuContextRoleByMenuId(id);
        menuMapper.deleteMenuById(id);
        menuMapper.deleteMenuByParentId(id);
    }
}
