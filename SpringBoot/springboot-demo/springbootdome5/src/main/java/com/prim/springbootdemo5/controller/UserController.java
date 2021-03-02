package com.prim.springbootdemo5.controller;

import com.prim.springbootdemo5.pojo.TUser;
import com.prim.springbootdemo5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-02 07:56
 * @PackageName: com.prim.springbootdemo5.controller
 * @ClassName: UserController.java
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    //restful风格进行访问
    @Autowired
    private UserService userService;

    @GetMapping("/query")
    public List<TUser> queryAll() {
        return userService.queryAll();
    }

    @GetMapping("/query/{id}")
    public TUser queryById(@PathVariable Integer id) {
        return userService.selectByPrimaryKey(id);
    }
}
