package com.prim.controller;

import com.prim.springbootdata.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-28 17:07
 * @PackageName: com.prim.controller
 * @ClassName: HelloController.java
 **/
@RestController
@RequestMapping("/boot")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World Spring Boot！！！123";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Person person;

    @GetMapping("/person")
    public String person() {
        return person.toString();
    }

    @GetMapping("/jdbc")
    public String jdbc() {
        return jdbcTemplate.toString();
    }
}
