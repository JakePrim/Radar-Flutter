package com.persistence.sqlSession;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 17:01
 * @PackageName: com.persistence.sqlSession
 * @ClassName: SqlSessionFactory.java
 **/
public interface SqlSessionFactory {
    SqlSession openSession();
}
