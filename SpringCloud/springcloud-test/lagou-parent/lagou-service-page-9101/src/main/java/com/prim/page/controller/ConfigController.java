package com.prim.page.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloud-test
 * @Description: 获取配置
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-10 15:29
 * @PackageName: com.prim.page.controller
 * @ClassName: ConfigController.java
 **/
//@RestController
//@RequestMapping("/config")
//@RefreshScope //手动刷新
public class ConfigController {
//    @Value("${mysql.user}")
//    private String mysqlUser;
//
//    @Value("${person.name}")
//    private String personName;
//
//    @RequestMapping("/remote")
//    public String findRemoteConfig() {
//        return mysqlUser + "  " + personName;
//    }

    @Value("${lagou.message}")
    private String message;

    @Value("${pagea}")
    private String pagea;

    @Value("${pageb}")
    private String pageb;

    @GetMapping("/message")
    public String findConfig() {
        return message + "  " + pagea + "  " + pageb;
    }

}
