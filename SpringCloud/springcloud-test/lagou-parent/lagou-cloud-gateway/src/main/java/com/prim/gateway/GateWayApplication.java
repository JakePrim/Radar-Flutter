package com.prim.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: springcloud-test
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-10 10:35
 * @PackageName: com.prim.gateway
 * @ClassName: GateWayApplication.java
 **/
@SpringBootApplication
// 开启服务注册中心
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }
}
