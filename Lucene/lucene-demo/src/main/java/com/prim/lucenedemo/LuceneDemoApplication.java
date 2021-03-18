package com.prim.lucenedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.prim.lucenedemo.mapper")
public class LuceneDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuceneDemoApplication.class, args);
    }

}
