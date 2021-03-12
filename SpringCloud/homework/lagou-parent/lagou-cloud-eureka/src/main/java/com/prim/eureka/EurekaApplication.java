package com.prim.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: homework
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 16:06
 * @PackageName: com.prim.eureka
 * @ClassName: EurekaApplication.java
 **/
@SpringBootApplication
// 启用eureka服务中心 服务端
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
