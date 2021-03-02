package com.prim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-27 23:10
 * @PackageName: com.prim.controller
 * @ClassName: HelloController.java
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/boot")
    public String hello() {
        return "hello spring boot";
    }
}
