package com.persistence.sqlSession;

import com.persistence.pojo.Configuration;
import com.persistence.pojo.MappedStatement;
import com.persistence.pojo.MappedType;

import java.lang.reflect.*;
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
    public int insert(String statement, Object params) {
        return update(statement, params);
    }

    @Override
    public int update(String statement, Object params) {
        try {
            //在Executor中完成插入操作
            SimpleExecutor simpleExecutor = new SimpleExecutor();
            //获取再configuration中存储的Mapper映射信息
            MappedStatement mappedStatement = configuration.getMapper().get(statement);
            //执行更新操作
            int row = simpleExecutor.update(configuration, mappedStatement, params);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(String statement, Object params) {
        return update(statement, params);
    }

    @Override
    public <T> T getMapper(Class<?> daoClass) {
        //使用JDK动态代理来为DAO接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{daoClass}, new InvocationHandler() {
            /**
             *
             * @param proxy 当前代理对象的引用
             * @param method 当前被调用方法的引用
             * @param args 方法传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //根据不同的情况来调用selectList和selectOne
                //1. 准备参数1：statementid = namespace + id 但是这里面无法获取到这些所以需要定义一个指定的规则：namespace.id = 接口全限定名.方法名
                String id = method.getName();
                String namespace = method.getDeclaringClass().getName();
                String statementId = namespace + "." + id;
                MappedStatement mappedStatement = configuration.getMapper().get(statementId);
                if (mappedStatement.getMappedType() == MappedType.SELECT) {
                    //2. 准备参数2 Object... 判断返回值的类型如果是集合调用selectList
                    //获取方法返回值类型
                    Type genericReturnType = method.getGenericReturnType();
                    //判断是否进行了泛型类型参数化 如果有泛型那么是list集合
                    if (genericReturnType instanceof ParameterizedType) {
                        List<Object> objects = selectList(statementId, args);
                        return objects;
                    }
                    return selectOne(statementId, args);
                } else if (mappedStatement.getMappedType() == MappedType.INSERT) {
                    int row = insert(statementId, args[0]);
                    return row;
                } else if (mappedStatement.getMappedType() == MappedType.UPDATE) {
                    int row = update(statementId, args[0]);
                    return row;
                } else if (mappedStatement.getMappedType() == MappedType.DELETE) {
                    int row = delete(statementId, args[0]);
                    return row;
                }
                return null;
            }
        });
        return (T) proxyInstance;
    }
}
