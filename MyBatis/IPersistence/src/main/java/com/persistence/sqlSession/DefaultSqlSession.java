package com.persistence.sqlSession;

import com.persistence.pojo.Configuration;

import java.util.List;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 18:48
 * @PackageName: com.persistence.sqlSession
 * @ClassName: DefaultSqlSession.java
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> E selectOne(String statementid, Object... params) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) {
        return null;
    }
}
