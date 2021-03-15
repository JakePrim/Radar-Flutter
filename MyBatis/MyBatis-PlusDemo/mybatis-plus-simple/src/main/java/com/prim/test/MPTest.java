package com.prim.test;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.prim.mapper.UserMapper;
import com.prim.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: MyBatis-PlusDemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 11:32
 * @PackageName: com.prim.test
 * @ClassName: MPTest.java
 **/
public class MPTest {
    @Test
    public void mybatisTest() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new MybatisSqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
