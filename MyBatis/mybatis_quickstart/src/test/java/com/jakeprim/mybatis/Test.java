package com.jakeprim.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jakeprim.domain.Orders;
import com.jakeprim.domain.User;
import com.jakeprim.mapper.OrderMapper;
import com.jakeprim.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
//        user.setUsername("jake");
//        user.setBirthday(new Date());
//        user.setSex("男");
//        user.setPassword("123456");
//        user.setAddress("石家庄市");
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
//        user.setUsername("露西");
//        user.setBirthday(new Date());
//        user.setSex("女");
//        user.setPassword("123456");
//        user.setAddress("石家庄市");
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

//    UserDao userDao = new UserDaoImpl();

    @org.junit.Test
    public void testDao() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findUserById(1);
        System.out.println(user);
        sqlSession.close();
    }


    @org.junit.Test
    public void testResultMapDao() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAllResultMap();
        for (User user : users) {

            System.out.println(user);
        }
        sqlSession.close();
    }

    @org.junit.Test
    public void testResultMapDao1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user1 = new User();
        user1.setId(7);
//        user1.setUsernameabc("jake");
        List<User> users = userMapper.findUserByIdAndUserName3(user1);
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }


    @org.junit.Test
    public void testResultMapDao2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findUserByUserName1("%jake%");
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    /**
     * 添加用户 返回主键  方式一
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
//        user.setUsernameabc("prim");
//        user.setBirthdayabc(new Date());
//        user.setSexabc("女");
//        user.setAddressabc("石家庄市");
        System.out.println("1--->" + user);
        userMapper.saveUser1(user);
        System.out.println("2--->" + user);//9
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 动态SQL - if
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user1 = new User();
        //
        user1.setId(9);
        user1.setUsername("prim");
        List<User> users = userMapper.findUserByIdAndUserNameIf(user1);
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    /**
     * 动态SQL - set
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test4() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user1 = new User();
        user1.setId(9);
        user1.setUsername("prim最帅");
        user1.setAddress("北京市拉钩网");
        userMapper.updateSet(user1);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 动态SQL - foreach
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test5() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(9);
        ids.add(10);
        Integer[] is = {1, 9, 11};
        List<User> list = userMapper.findByArray(is);
        for (User user : list) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    /**
     * PageHelper 的使用
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test6() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //设置分页参数
        //参数1：当前页
        //参数2：每页显示的条数
        PageHelper.startPage(1, 2);

        //注意：在映射配置文件 写的SQL语句 后面不要有分号 ;
        //得到分页后的 数据
        List<User> users = userMapper.findAllResultMap();

        for (User user : users) {
            System.out.println(user);
        }
        // 获取分页的相关的其他参数
        PageInfo<User> userPageInfo = new PageInfo<User>(users);
        System.out.println("总条数:" + userPageInfo.getTotal());
        System.out.println("总页数:" + userPageInfo.getPages());
        System.out.println("是否是第一页：" + userPageInfo.isIsFirstPage());
        sqlSession.close();
    }

    /**
     * 多表一对一关联查询
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test7() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<Orders> orders = orderMapper.findOrdersWithUser2();
        for (Orders order : orders) {
            System.out.println(order);
        }
    }

    /**
     * 多表：一对多关联查询
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test8() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAllWithOrders();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 多表：多对多关联查询
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test9() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAllWithRoles();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @org.junit.Test
    public void test10() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAllWithOrders2();
        for (User user : users) {
            System.out.println(user);
            //要用到订单信息了 发起查询
            System.out.println(user.getOrdersList());//调用了toString方法
//            user.getOrdersList(); //注意单独调用 并不会进行查询
        }
    }

    /**
     * 多对多嵌套查询测试
     *
     * @throws IOException
     */
    @org.junit.Test
    public void test11() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAllWithRoles2();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getRoleList());
        }
    }

    /**
     * 验证mybatis中的一级缓存
     */
    @org.junit.Test
    public void testOneCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findById(1);
        System.out.println(user);
//        sqlSession.clearCache();//手动清空缓存
        User user1 = mapper.findById(1);
        System.out.println(user1);
        sqlSession.close();
    }

    /**
     * 验证mybatis中的二级缓存
     */
    @org.junit.Test
    public void testTwoCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession1 = sessionFactory.openSession();
        //sqlsession
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        User user1 = mapper1.findById(1);
        System.out.println(user1);
        //只有执行sqlSession.commit() 或者sqlSession.close
        //清空一级缓存，同时一级缓存中内容才会刷新到二级缓存 这个很关键
        sqlSession1.close();

        //sqlsession
        SqlSession sqlSession2 = sessionFactory.openSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = mapper2.findById(1);
        System.out.println(user2);
        sqlSession2.close();
    }
}
