package com.prim.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springclouddemo
 * @Description: 启动类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 22:10
 * @PackageName: com.prim.page
 * @ClassName: Application.java
 **/
@SpringBootApplication
//@EnableEurekaClient server client 服务发现
@EnableDiscoveryClient
//启用熔断服务
//@EnableCircuitBreaker
// 开启Feign客户端功能 feign 支持熔断所以可以注释掉hystrix熔断即可
@EnableFeignClients
public class PageApplication9101 {
    public static void main(String[] args) {
        SpringApplication.run(PageApplication9101.class, args);
    }
}
