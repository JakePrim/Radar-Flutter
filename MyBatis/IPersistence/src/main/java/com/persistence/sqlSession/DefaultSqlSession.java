package com.persistence.sqlSession;

import com.persistence.pojo.Configuration;
import com.persistence.pojo.MappedStatement;

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
        //调用selectList方法
        List<Object> objects = selectList(statementid, params);
        //size等于1表示只有一条记录
        if (objects.size() == 1) {
            return (E) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) {
        //去完成Executor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //获取再configuration中存储的Mapper映射信息
        MappedStatement mappedStatement = configuration.getMapper().get(statementid);
        List<Object> list = null;
        try {
            list = simpleExecutor.query(configuration, mappedStatement, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<E>) list;
    }

    @Override
    public <T> T getMapper(Class<?> daoClass) {
        return null;
    }
}
