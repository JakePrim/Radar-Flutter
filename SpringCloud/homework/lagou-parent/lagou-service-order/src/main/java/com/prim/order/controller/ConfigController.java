package com.prim.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-12 07:37
 * @PackageName: com.prim.order.controller
 * @ClassName: ConfigController.java
 **/
@RestController
@RequestMapping("/config")
@RefreshScope //手动刷新
public class ConfigController {
    @Value("${slogan}")
    private String slogan;

    @RequestMapping("/remote")
    public String findRemoteConfig() {
        return slogan;
    }
}
