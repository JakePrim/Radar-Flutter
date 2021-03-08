package com.pbatis.dao.impl;

import com.pbatis.dao.IUserDao;
import com.pbatis.pojo.User;
import com.persistence.io.Resources;
import com.persistence.sqlSession.SqlSession;
import com.persistence.sqlSession.SqlSessionFactory;
import com.persistence.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: PBatisTest
 * @Description: 解决思路：不要dao层的实现了，使用动态代理模式来生成dao层接口的代理实现类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-08 19:03
 * @PackageName: com.pbatis.dao.impl
 * @ClassName: UserDaoImpl.java
 **/
public class UserDaoImpl implements IUserDao {
    @Override
    public List<User> findAll() throws PropertyVetoException, DocumentException {
        // 存在代码重复的模板：
        InputStream inputStream = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // statementId 存在硬编码的问题：
        List<User> list = sqlSession.selectList("user.findAll");
        for (User user1 : list) {
            System.out.println(user1);
        }
        return list;
    }

    @Override
    public User findById(User user) throws PropertyVetoException, DocumentException {
        // 存在代码重复的模板：
        InputStream inputStream = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // statementId 存在硬编码的问题：
        User findUser = sqlSession.selectOne("user.findById", user);
        System.out.println(findUser);
        return findUser;
    }
}
