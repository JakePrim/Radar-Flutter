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
     *
     * @param statementid = namespace.id sql 语句的唯一标识
     * @param params      参数
     */
    <E> E selectOne(String statementid, Object... params);

    /**
     * 查询所有
     *
     * @param statementid = namespace.id sql 语句的唯一标识
     * @param params      参数
     */
    <E> List<E> selectList(String statementid, Object... params);

    /**
     * 添加
     */
    int insert(String statement, Object params);

    /**
     * 修改
     */
    int update(String statement, Object params);


    /**
     * 删除
     */
    int delete(String statement, Object params);

    /**
     * 为dao接口生成代理实现类
     *
     * @param daoClass dao接口类
     */
    <T> T getMapper(Class<?> daoClass);
}
