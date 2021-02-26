package com.pbatis.test;

import com.pbatis.pojo.User;
import com.persistence.io.Resources;
import com.persistence.sqlSession.SqlSession;
import com.persistence.sqlSession.SqlSessionFactory;
import com.persistence.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-25 19:01
 * @PackageName: com.pbatis.test
 * @ClassName: IPersistenceTest.java
 **/
public class IPersistenceTest {
    public static void main(String[] args) throws DocumentException, PropertyVetoException {
        InputStream inputStream = Resources.getResourceAsSteam("sqlMapConfig.xml");
        System.out.println("inputstream:" + inputStream);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId("1");
        user.setUsername("lucy");
        User findUser = sqlSession.selectOne("user.findById", user);
        System.out.println(findUser);
    }
}
