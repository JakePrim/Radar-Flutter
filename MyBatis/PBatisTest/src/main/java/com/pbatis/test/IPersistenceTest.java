package com.pbatis.test;

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
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        //查询所有数据
        List<User> users = iUserDao.findAll();
        for (User user1 : users) {
            System.out.println(user1);
        }
        //根据条件查询
        User user = new User();
        user.setId(3);
        user.setUsername("jake");
        User user1 = iUserDao.findById(user);
        System.out.println(user1);
//        User findUser = sqlSession.selectOne("com.pbatis.dao.IUserDao.findById", user);
//        System.out.println(findUser);
//        List<User> list = sqlSession.selectList("com.pbatis.dao.IUserDao.findAll");
//        for (User user1 : list) {
//            System.out.println(user1);
//        }


//        User user = new User();
//        user.setUsername("tom");
//        user.setId(12);
//        user.setPassword("12312");
//        user.setVersion(1);
//        user.setBirthday("2021-1-1");
//        user.setIs_delete(0);
        //插入操作
//        int save = iUserDao.delete(user);
//        System.out.println(save);
    }
}
