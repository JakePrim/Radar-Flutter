package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.Menu;
import com.edu.pojo.ResponseResult;
import com.edu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        List<Menu> menuList = menuService.findAllMenu();
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), menuList);
        return responseResult;
    }

    /**
     * 编辑菜单 需要回显的数据
     *
     * @param id 当前菜单的id
     * @return
     */
    @GetMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        //根据id的值判断当前是更新还是添加操作
        if (id == -1) {//添加
            //如果为-1 表示不需要查询 当前菜单的上一级菜单
            map.put("menuInfo", null);
            map.put("parentMenuList", menuList);
            ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
            return responseResult;
        } else {//更新
            //回显当前的menu的信息
            Menu menuInfo = menuService.findMenuById(id);
            map.put("menuInfo", menuInfo);
            map.put("parentMenuList", menuList);
            ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
            return responseResult;
        }
    }

    @PostMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu) {
        if (menu.getId() != null) {
            menuService.updateMenu(menu);
        } else {
            menuService.saveMenu(menu);
        }
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), "");
        return responseResult;
    }
}
