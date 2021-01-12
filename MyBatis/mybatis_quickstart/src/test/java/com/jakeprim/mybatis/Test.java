package com.jakeprim.mybatis;

import com.jakeprim.dao.UserDao;
import com.jakeprim.dao.impl.UserDaoImpl;
import com.jakeprim.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class Test {
    @org.junit.Test
    public void mybatisTest() throws IOException {
        //1. 加载核心配置文件 借助类加载器加载 SqlMapConfig.xml
        InputStream stream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2. 获取SqlSessionFactory工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        //3. 获取SqlSession会话对象 操作数据库
        SqlSession session = factory.openSession();
        //4. 执行SQL 参数：statementId 就是namespace.id 组成的 找到要执行的SQL语句
        List<User> list = session.selectList("UserMapper.findAll");
        //5. 遍历结果
        for (User user : list) {
            System.out.println(user);
        }
        //6. 关闭资源
        session.close();
    }

    /**
     * 测试insert操作
     */
    @org.junit.Test
    public void testInsert() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession session = sessionFactory.openSession(true);
        User user = new User();
        user.setUsername("jake");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setPassword("123456");
        user.setAddress("石家庄市");
        int insert = session.insert("UserMapper.save", user);
        System.out.println("影响行数:" + insert);
        //必须要手动 提交事务
//        session.commit();
        session.close();
    }

    /**
     * 测试更新用户
     *
     * @throws IOException
     */
    @org.junit.Test
    public void testUpdate() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession session = sessionFactory.openSession();
        User user = new User();
        user.setId(5);
        user.setUsername("露西");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setPassword("123456");
        user.setAddress("石家庄市");
        int update = session.update("UserMapper.updateUser", user);
        System.out.println("影响行数:" + update);
        //手动提交事务
        session.commit();
        session.close();
    }

    /**
     * 测试删除用户
     */
    @org.junit.Test
    public void testDelete() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession session = sessionFactory.openSession();
        int delete = session.delete("UserMapper.deleteUser", 6);
        System.out.println("影响行数:" + delete);
        //手动提交事务
        session.commit();
        session.close();
    }

    UserDao userDao = new UserDaoImpl();

    @org.junit.Test
    public void testDao() throws IOException {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
