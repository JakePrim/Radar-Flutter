package com.prim;

import com.prim.controller.HelloController;
import com.prim.springbootdata.pojo.Person;
import com.prim.springbootdata.pojo.Product;
import com.prim.service.MyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)//运行器,Spring的测试环境
@RunWith(SpringRunner.class)//运行器,SpringBoot的测试环境
@SpringBootTest
//标记当前类为springboot的测试类，加载项目的ApplicationContext上下文环境
class Springbootdome2ApplicationTests {

    @Autowired
    private HelloController helloController;

    @Autowired
    private Person person;

//    @Autowired
//    private Student student;

    @Test
    void contextLoads() {
//        System.out.println(student);
    }

    @Autowired
    private Product product;

    @Test
    void contextLoads1() {
        System.out.println(product);
    }


    @Autowired
    private MyService myService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testConfig() {
        System.out.println(applicationContext.containsBean("service_"));//true
    }

}
