package com.persistence.sqlSession;

import com.persistence.config.XmlConfigBuilder;
import com.persistence.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @program: PBatisTest
 * @Description: 生产SqlSessionFactory工厂，解析配置文件
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 16:59
 * @PackageName: com.persistence.sqlSession
 * @ClassName: SqlSessionFactoryBuilder.java
 **/
public class SqlSessionFactoryBuilder {
    /**
     * SqlSessionFactory
     *
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException {
        //1. dom4j 解析配置文件，将解析出来的内存封装到Configuration
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        //2. 创建SqlSessionFactory : 生产sqlSession会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
