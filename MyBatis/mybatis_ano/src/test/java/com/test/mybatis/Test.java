package com.test.mybatis;

import com.example.domain.Orders;
import com.example.domain.User;
import com.example.mapper.OrderMapper;
import com.example.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class Test {

    private SqlSession sqlSession;

    private SqlSessionFactory sessionFactory;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sessionFactory.openSession();
    }

    @After
    public void after() {
        sqlSession.commit();
        sqlSession.close();
    }

    @org.junit.Test
    public void testSelect() throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @org.junit.Test
    public void testInsert() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("join");
        user.setSex("男");
        user.setAddress("加州");
        user.setBirthday(new Date());
        mapper.save(user);
    }

    @org.junit.Test
    public void testUpdate() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(11);
        user.setUsername("join-1");
        user.setSex("女");
        mapper.update(user);
    }

    @org.junit.Test
    public void testDelete() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.delete(11);
    }


    @org.junit.Test
    public void testFindAllWithUser() {
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Orders> orders = mapper.findAllWithUser();
        for (Orders order : orders) {
            System.out.println(order);
        }
    }

    @org.junit.Test
    public void findAllWithOrders() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAllWithOrders();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getOrdersList());
        }
    }
}
