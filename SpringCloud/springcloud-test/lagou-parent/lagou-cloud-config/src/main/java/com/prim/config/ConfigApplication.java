package com.prim.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @program: springcloud-test
 * @Description: 配置中心的启动类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-10 15:01
 * @PackageName: com.prim.config
 * @ClassName: ConfigApplication.java
 **/
@SpringBootApplication
@EnableDiscoveryClient //发现服务
@EnableConfigServer //启用配置中心
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
