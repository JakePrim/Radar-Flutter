package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.User;
import com.edu.pojo.vo.UserVo;
import com.edu.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(Integer id, String status) {
        userService.updateUserStatus(id, status);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), status);
        return responseResult;
    }
}
