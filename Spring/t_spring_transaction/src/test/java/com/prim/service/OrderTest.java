package com.prim.service;

import com.prim.pojo.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-service3.xml")
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAddOrder() {
        Order order = new Order("10004", "10002", 2, 1700.0, "刘某", "110", "北京");
        orderService.addOrder(order);
    }
}
