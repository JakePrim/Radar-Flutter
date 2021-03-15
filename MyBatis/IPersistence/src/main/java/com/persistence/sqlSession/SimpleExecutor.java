package com.persistence.sqlSession;

import com.persistence.config.BoundSql;
import com.persistence.pojo.Configuration;
import com.persistence.pojo.MappedStatement;
import com.persistence.utils.GenericTokenParser;
import com.persistence.utils.ParameterMapping;
import com.persistence.utils.ParameterMappingTokenHandler;
import com.persistence.utils.TokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: PBatisTest
 * @Description: 执行器接口的具体实现类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-08 16:33
 * @PackageName: com.persistence.sqlSession
 * @ClassName: SimpleExecutor.java
 **/
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 执行jdbc代码
        // 1. 注册驱动，获取连接 直接从configuration获取DataSource拿到连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取SQL语句
        // select * from user where id = #{id} and username=#{username}
        // jdbc 识别的占位符只能是？号，为什么使用#{}呢？ 可以根据里面的参数名称 来找到传递参数实体类型的属性的值
        String sql = mappedStatement.getSql();
        //转换SQL语句 select * from user where id = ？ and username=？
        //还需要对#{}的值进行解析存储，找到实体对象对应属性的值进行添加
        BoundSql boundSql = getBoundSql(sql);

        // 3. 获取预处理对象 preparedStatement,传递解析过后的sql
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4. 设置参数
        //参数的全类名
        String parameterType = mappedStatement.getParameterType();
        //获取参数的Class
        Class<?> parameterClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射根据参数名称 获取到实体对象中的属性值
            Field field = parameterClass.getDeclaredField(content);
            //暴力访问
            field.setAccessible(true);
            Object o = field.get(params[0]);
            //设置参数值
            preparedStatement.setObject(i + 1, o);
        }

        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        // 6. 封装返回的结果集
        String resultType = mappedStatement.getResultType();
        //获取返回结果的具体的class
        Class<?> resultClass = getClassType(resultType);
        //创建集合
        ArrayList<Object> results = new ArrayList<>();
        while (resultSet.next()) {
            //创建结果类的对象实例
            Object instance = resultClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //列 循环
            for (int i = 1; i < metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object object = resultSet.getObject(columnName);
//                System.out.println(columnName + ":" + object);
                //使用反射 内省，根据数据库表和实体的对应关系完成封装
                //PropertyDescriptor 提供了内省技术的实现
                //会对resultClass中的columnName的属性进行读写的方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClass);
                //写入方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                //将具体的值写入到对象中
                writeMethod.invoke(instance, object);
            }
            results.add(instance);
        }

        return (List<E>) results;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object params) {
        try {
            // 执行jdbc代码
            // 1. 注册驱动，获取连接 直接从configuration获取DataSource拿到连接
            Connection connection = configuration.getDataSource().getConnection();
            // 2. 获取SQL语句
            // select * from user where id = #{id} and username=#{username}
            // jdbc 识别的占位符只能是？号，为什么使用#{}呢？ 可以根据里面的参数名称 来找到传递参数实体类型的属性的值
            String sql = mappedStatement.getSql();
            //转换SQL语句 select * from user where id = ？ and username=？
            //还需要对#{}的值进行解析存储，找到实体对象对应属性的值进行添加
            BoundSql boundSql = getBoundSql(sql);
            // 3. 获取预处理对象 preparedStatement,传递解析过后的sql
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
            //参数的全类名
            String parameterType = mappedStatement.getParameterType();
            //获取参数的Class
            if (parameterType != null) {
                Class<?> parameterClass = getClassType(parameterType);
                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
                for (int i = 0; i < parameterMappingList.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappingList.get(i);
                    String content = parameterMapping.getContent();
                    //反射根据参数名称 获取到实体对象中的属性值
                    Field field = parameterClass.getDeclaredField(content);
                    //暴力访问
                    field.setAccessible(true);
                    Object o = field.get(params);
                    //设置参数值
                    preparedStatement.setObject(i + 1, o);
                }
            }
            //执行SQL
            int row = preparedStatement.executeUpdate();
            return row;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    /**
     * 来完成对#{}的解析工作：
     * 1. 将#{}使用?进行代替
     * 2. 解析出#{}里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类: 配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析过后的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{} 解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
