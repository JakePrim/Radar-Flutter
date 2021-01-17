package com.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example") // IOC 注解扫描
@EnableAspectJAutoProxy //aop 自动代理
public class SpringConfig {
}
