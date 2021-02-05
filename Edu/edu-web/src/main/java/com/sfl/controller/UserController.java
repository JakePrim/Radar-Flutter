package com.sfl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.User;
import com.sfl.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: edu-web
 * @Description: 用户相关接口
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 14:44
 * @PackageName: com.sfl.controller
 * @ClassName: UserController.java
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference //远程引用
    private UserService userService;

    /**
     * 用户登录/注册接口：如果手机号没有注册自动注册。
     *
     * @param phone
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResultDTO<User> login(String phone, String password) {
        ResultDTO<User> resultDTO = userService.login(phone, password);
        return resultDTO;
    }
}
