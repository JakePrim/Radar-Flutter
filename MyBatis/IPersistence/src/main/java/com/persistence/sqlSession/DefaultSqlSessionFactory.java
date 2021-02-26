package com.persistence.sqlSession;

import com.persistence.pojo.Configuration;

/**
 * @program: PBatisTest
 * @Description: 默认的SqlSessionFactory实现类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 18:44
 * @PackageName: com.persistence.sqlSession
 * @ClassName: DefaultSqlSessionFactory.java
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
