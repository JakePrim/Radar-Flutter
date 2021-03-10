package com.prim.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: springclouddemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-04 07:55
 * @PackageName: com.prim.eureka
 * @ClassName: EurekaApplication.java
 **/
@SpringBootApplication
//标识当前项目为Server
@EnableEurekaServer
public class EurekaApplication9201 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication9201.class, args);
    }
}
