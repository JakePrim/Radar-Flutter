package com.prim.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: springclouddemo
 * @Description: 启动类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 21:36
 * @PackageName: com.prim
 * @ClassName: Application.java
 **/
@SpringBootApplication
// 将当前项目作为Eureka Client注册到Eureka server ，只能在Eureka环境中使用
//@EnableEurekaClient
// 也是将当前项目标识为注册中心的客户端，向注册中心进行注册，可以在所有的服务注册中心环境下使用
@EnableDiscoveryClient
//扫描mapper接口 生成动态代理类
@MapperScan("com.prim.product.mapper")
public class ProductApplication9001 {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication9001.class, args);
    }
}
