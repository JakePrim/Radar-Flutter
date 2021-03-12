package com.prim.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: homework
 * @Description: goods服务启动类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:11
 * @PackageName: com.prim.goods
 * @ClassName: GoodsApplication.java
 **/
@SpringBootApplication
@MapperScan("com.prim.goods.mapper")
@EnableDiscoveryClient //开启服务发现
public class GoodsApplication9201 {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication9201.class, args);
    }
}
