package com.sufulu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sufulu.entity.User;
import com.sufulu.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Reference
    private UserService userService;

    @PostMapping("/register")
    public String register(User user) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        user.setCreatetime(format);
        int register = userService.register(user);
        if (register > 0) {
            return "注册成功";
        } else {
            return "注册失败";
        }
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        int delete = userService.delete(id);
        if (delete > 0) {
            return "delete success";
        } else {
            return "delete error";
        }
    }

    @PostMapping("/update")
    public String update(User user) {
        int delete = userService.update(user);
        if (delete > 0) {
            return "更新成功";
        } else {
            return "更新失败";
        }
    }

    @GetMapping("/findByName")
    public List<User> findByName(String name) {
        List<User> users = userService.findByName(name);
        return users;
    }

    @GetMapping("/findById")
    public User findById(Integer id) {
        User user = userService.findById(id);
        return user;
    }
}
