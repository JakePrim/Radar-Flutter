package com.prim.global;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * DAO 工厂
 * @author prim
 */
public class DaoFactory {
    private static DaoFactory daoFactory = null;

    private SqlSessionFactory sqlSessionFactory = null;

    private DaoFactory() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        try {
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public <T> T getDao(Class<T> tClass) {
        return openSession().getMapper(tClass);
    }

    public <T> T getDao(Class<T> tClass, boolean autoCommit) {
        return openSession(autoCommit).getMapper(tClass);
    }

    public SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    public SqlSession openSession(boolean isCommit) {
        return sqlSessionFactory.openSession(isCommit);
    }

    public static void closeSession(SqlSession sqlSession){
        if (sqlSession != null){
            sqlSession.close();
        }
    }
}
