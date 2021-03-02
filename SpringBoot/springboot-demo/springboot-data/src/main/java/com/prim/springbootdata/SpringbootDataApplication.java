package com.prim.springbootdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //实现自动配置
//扫描mapper包 生成对应的动态代理类 注入到IOC容器中
@MapperScan("com.prim.springbootdata.mapper")
public class SpringbootDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDataApplication.class, args);
    }
}
