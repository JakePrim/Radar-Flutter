package com.prim.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory = null;
    static {
        try {
            //加载xml配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            //得到SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }

    /**
     * 设置SQL是否自动提交事务,一般对于数据库的写操作：插入 更新 删除 都需要手动提交事务来保证数据的完整性
     * @param isAutoCommit
     * @return
     */
    public static SqlSession openSession(boolean isAutoCommit){
        //默认情况下自动提交事务
        //false 关闭自动提交 需要手动提交事务
        return sqlSessionFactory.openSession(isAutoCommit);
    }

    public static void closeSession(SqlSession sqlSession){
        if (sqlSession != null){
            sqlSession.close();
        }
    }
}
