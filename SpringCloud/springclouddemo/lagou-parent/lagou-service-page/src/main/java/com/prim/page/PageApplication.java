package com.prim.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
//@EnableEurekaClient
@EnableDiscoveryClient
public class PageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class, args);
    }

    //向容器中注入 RestTemplate 封装了HTTPclient
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
