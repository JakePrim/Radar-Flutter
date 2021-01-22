package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.Menu;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.Role;
import com.edu.pojo.vo.RoleMenuVo;
import com.edu.service.MenuService;
import com.edu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/findAllRoles")
    public ResponseResult findAllRoles(@RequestBody Role role) {
        List<Role> roles = roleService.findAllRole(role);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), roles);
        return responseResult;
    }

    @PostMapping("/saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role) {
        if (role.getId() != null) {
            roleService.updateRole(role);
        } else {
            roleService.saveRole(role);
        }
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), "");
        return responseResult;
    }

    @GetMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        List<Menu> menus = menuService.findSubMenuListByPid(-1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList", menus);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return responseResult;
    }

    @GetMapping("/findMenuIdByRoleId")
    public ResponseResult findMenuIdByRoleId(Integer roleId) {
        List<Integer> menusIds = roleService.findMenuIdByRoleId(roleId);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), menusIds);
        return responseResult;
    }

    @PostMapping("/roleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo) {
        roleService.roleContextMenu(roleMenuVo);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), "");
        return responseResult;
    }

    @PostMapping("/deleteRole")
    public ResponseResult deleteRole(Integer roleId){
        roleService.deleteRole(roleId);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), "删除角色成功");
        return responseResult;
    }
}
