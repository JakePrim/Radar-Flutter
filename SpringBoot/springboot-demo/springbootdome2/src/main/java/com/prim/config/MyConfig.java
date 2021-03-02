package com.prim.config;

import com.prim.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-01 06:56
 * @PackageName: com.prim.config
 * @ClassName: MyConfig.java
 **/
@Configuration //标识当前类是一个配置类，springboot会扫描该类，将所有标识@Bean注解的返回值注入到容器中
public class MyConfig {

    @Bean //注入的名称就是方法的名称，注入的类型就是返回值的类型
//    @Bean(name = "myservice2") //注入的名称就是方法的名称，注入的类型就是返回值的类型
    public MyService myService() {
        return new MyService();
    }

    @Bean("service_")
    public MyService myService2(){
        return new MyService();
    }
}
