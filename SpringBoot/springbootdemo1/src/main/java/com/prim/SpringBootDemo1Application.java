package com.prim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: springboot-demo
 * @Description: springBoot的启动类，通常放在二级包中比如：com.prim.SpringBootDemo1Application
 * springboot项目会自动的包扫描，会扫描启动类所在的包及其子包下的所有内容
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-27 23:11
 * @PackageName: com.prim
 * @ClassName: SpringBootDemo1Application.java
 **/
//标识当前类为springBoot项目的启动类
@SpringBootApplication
public class SpringBootDemo1Application {
    public static void main(String[] args) {
        /**
         * 第一个参数：springBoot的启动类的class
         * 第二个参数：main方法的参数
         * 样板代码
         */
        SpringApplication.run(SpringBootDemo1Application.class, args);
    }
}
