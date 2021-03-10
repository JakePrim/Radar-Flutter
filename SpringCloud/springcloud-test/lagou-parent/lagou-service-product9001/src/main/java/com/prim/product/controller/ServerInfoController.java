package com.prim.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springclouddemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-08 21:58
 * @PackageName: com.prim.product.controller
 * @ClassName: ServerInfoController.java
 **/
@RestController
@RequestMapping("/server")
public class ServerInfoController {

    //获取yml文件的配置
    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }

}
