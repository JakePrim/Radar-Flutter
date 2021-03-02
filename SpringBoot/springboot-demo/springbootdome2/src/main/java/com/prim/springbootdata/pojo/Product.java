package com.prim.springbootdata.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-01 06:46
 * @PackageName: com.prim.springbootdata.pojo
 * @ClassName: Product.java
 **/
@Component
@PropertySource("classpath:my.properties")//通过该注解加载自定义的配置文件
@ConfigurationProperties(prefix = "product")//配置属性注入
public class Product {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
