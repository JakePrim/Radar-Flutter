package com.persistence.sqlSession;

import com.persistence.pojo.Configuration;
import com.persistence.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: PBatisTest
 * @Description: 执行器，去真正的执行jdbc的代码
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-08 16:32
 * @PackageName: com.persistence.sqlSession
 * @ClassName: Executor.java
 **/
public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, Exception;

    int update(Configuration configuration, MappedStatement mappedStatement, Object params);
}
