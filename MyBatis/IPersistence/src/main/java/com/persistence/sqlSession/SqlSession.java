package com.persistence.sqlSession;

import java.util.List;

/**
 * @program: PBatisTest
 * @Description: Sql会话对象
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 18:47
 * @PackageName: com.persistence.sqlSession
 * @ClassName: SqlSession.java
 **/
public interface SqlSession {
    /**
     * 根据条件查询单个
     * @param statementid = namespace.id sql 语句的唯一标识
     * @param params 参数
     */
    <E> E selectOne(String statementid, Object... params);

    /**
     * 查询所有
     *
     * @param statementid = namespace.id sql 语句的唯一标识
     * @param params 参数
     */
    <E> List<E> selectList(String statementid, Object... params);
}
