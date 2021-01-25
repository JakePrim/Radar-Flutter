package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.Role;
import com.edu.pojo.User;
import com.edu.pojo.vo.UserRoleVo;
import com.edu.pojo.vo.UserVo;
import com.edu.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo) {
        PageInfo<User> userPageInfo = userService.findAllUserByPage(userVo);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), userPageInfo);
        return responseResult;
    }

    @GetMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(Integer id, String status) {
        userService.updateUserStatus(id, status);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), status);
        return responseResult;
    }

    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) {
        try {
            User findUser = userService.findByPhone(user);
            if (findUser != null) {
                //登录成功
                //存储session中 access_token
                HttpSession session = request.getSession();
                String access_token = UUID.randomUUID().toString();
                session.setAttribute("access_token", access_token);
                session.setAttribute("user_id", findUser.getId());
                HashMap<String, Object> map = new HashMap<>();
                map.put("access_token", access_token);
                map.put("user_id", findUser.getId());
                //查询出来的user也存到
                findUser.setPassword("");
                map.put("user", findUser);

                ResponseResult responseResult = new ResponseResult(true, "1", "登录成功", map);
                return responseResult;
            } else {
                //登录失败
                ResponseResult responseResult = new ResponseResult(true, StateCode.LOGIN_ERROR.getCode(), StateCode.LOGIN_ERROR.getMsg(), null);
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult responseResult = new ResponseResult(true, StateCode.LOGIN_ERROR.getCode(), StateCode.LOGIN_ERROR.getMsg(), "");
            return responseResult;
        }
    }

    @GetMapping("/findUserRoleById")
    public ResponseResult findUserRoleById(Integer userId) {
        List<Role> roles = userService.findUserRoleById(userId);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), roles);
        return responseResult;
    }

    @PostMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserRoleVo userRoleVo) {
        userService.userContextRole(userRoleVo);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return responseResult;
    }

    @GetMapping("getUserPermission")
    public ResponseResult getUserPermission(HttpServletRequest request) {
        //获取请求头 设置的access_token信息，对比token获取session中存储的userId
        String req_access_token = request.getHeader("Authorization");
        HttpSession session = request.getSession();
        String access_token = (String) session.getAttribute("access_token");
        if (access_token.equals(req_access_token)) {//校验正确
            Integer user_id = (Integer) session.getAttribute("user_id");
            ResponseResult result = userService.getUserPermission(user_id);
            return result;
        } else {//校验错误
            //响应错误
            ResponseResult responseResult = new ResponseResult(false, "400", "token过期", null);
            return responseResult;
        }
    }
}
