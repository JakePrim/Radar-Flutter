/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.autoconstructor;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.util.*;

import static org.junit.Assert.assertNotNull;

public class AutoConstructorTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setUp() throws Exception {
        // create a SqlSessionFactory
        // 创建 SqlSessionFactory 对象，基于 mybatis-config.xml 配置文件。
        try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/autoconstructor/mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }

        // populate in-memory database
        // 初始化数据到内存数据库，基于 CreateDB.sql SQL 文件。
        BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
                "org/apache/ibatis/autoconstructor/CreateDB.sql");
    }

    @Test
    public void fullyPopulatedSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubject(1);
            assertNotNull(subject);
            final Object subject2 = mapper.getSubject(1);
            assertNotNull(subject2);
        }
    }

    @Test
    public void fullyPopulatedSubject2() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubject2(1);
            assertNotNull(subject);
        }
    }

    @Test
    public void fullyPopulatedSubject3() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubject3(1);
            assertNotNull(subject);
        }
    }

    @Test
    public void fullyPopulatedSubject4() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubject4(null);
            assertNotNull(subject);
        }
    }

    @Test
    public void testGetSubjects() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubjectList(Arrays.asList(1,2 ));
            assertNotNull(subject);
        }
    }

    @Test(expected = PersistenceException.class)
//  @Test
    public void primitiveSubjects() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            mapper.getSubjects();
        }
    }

    @Test
    public void annotatedSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            verifySubjects(mapper.getAnnotatedSubjects());
        }
    }

    @Test(expected = PersistenceException.class)
//    @Test
    public void badSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            mapper.getBadSubjects();
        }
    }

    @Test
    public void extensiveSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            verifySubjects(mapper.getExtensiveSubject());
        }
    }

    private void verifySubjects(final List<?> subjects) {
        assertNotNull(subjects);
        Assertions.assertThat(subjects.size()).isEqualTo(3);
    }

    @Test
    public void testCache() {
        sqlSessionFactory.getConfiguration().setCacheEnabled(true); // 开启缓存功能
        SqlSession sqlSession01 = sqlSessionFactory.openSession();
        AutoConstructorMapper mapper01 = sqlSession01.getMapper(AutoConstructorMapper.class);
        final Object subject01 = mapper01.getSubject(1);

        SqlSession sqlSession02 = sqlSessionFactory.openSession();
        AutoConstructorMapper mapper02 = sqlSession02.getMapper(AutoConstructorMapper.class);
        final Object subject02 = mapper02.getSubject(1);
    }

    @Test
    public void testResultMap() {
        sqlSessionFactory.getConfiguration().setCacheEnabled(true); // 开启缓存功能
        SqlSession sqlSession01 = sqlSessionFactory.openSession();
        AutoConstructorMapper mapper01 = sqlSession01.getMapper(AutoConstructorMapper.class);
        Map<String, Object> params  = new HashMap<>();
        params.put("id", 1);
        mapper01.testResultMap(new HashMap<>(params));
    }

    @Test
    public void testGetSubject5() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
        mapper.getSubject5("a", 10);
    }

    @Test
    public void testBatch() {
        // 创建要插入的用户的名字的数组
        List<String> names = new ArrayList<>();
        names.add("占小狼");
        names.add("朱小厮");
        names.add("徐妈");
        names.add("飞哥");

        // 获得执行器类型为 Batch 的 SqlSession 对象，并且 autoCommit = false ，禁止事务自动提交
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            // 获得 Mapper 对象
            UserMapper mapper = session.getMapper(UserMapper.class);
            // 循环插入
            for (String name : names) {
                mapper.insertUser(name);
            }
            // 提交批量操作
            session.commit();
        }
    }

}
