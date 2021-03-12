package com.prim.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 16:26
 * @PackageName: com.prim.eureka
 * @ClassName: EurekaApplication9301.java
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication9301 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication9301.class, args);
    }
}
