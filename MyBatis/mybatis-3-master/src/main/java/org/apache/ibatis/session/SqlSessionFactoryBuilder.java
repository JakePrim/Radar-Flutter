/**
 * Copyright 2009-2016 the original author or authors.
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
package org.apache.ibatis.session;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * Builds {@link SqlSession} instances.
 *
 * {@link SqlSessionFactory} 构造器
 *
 * @author Clinton Begin
 */
@SuppressWarnings("Duplicates")
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        return build(reader, null, null);
    }

    public SqlSessionFactory build(Reader reader, String environment) {
        return build(reader, environment, null);
    }

    public SqlSessionFactory build(Reader reader, Properties properties) {
        return build(reader, null, properties);
    }

    /**
     * 构造 SqlSessionFactory 对象
     *
     * @param reader Reader 对象
     * @param environment 环境
     * @param properties Properties 变量
     * @return SqlSessionFactory 对象
     */
    @SuppressWarnings("Duplicates")
    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        try {
            // 创建 XMLConfigBuilder 对象
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
            // 执行 XML 解析
            // 创建 DefaultSqlSessionFactory 对象
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    // 1.我们最初调用的build
    public SqlSessionFactory build(InputStream inputStream) {
        //调用了重载方法
        return build(inputStream, null, null);
    }

    public SqlSessionFactory build(InputStream inputStream, String environment) {
        return build(inputStream, environment, null);
    }

    public SqlSessionFactory build(InputStream inputStream, Properties properties) {
        return build(inputStream, null, properties);
    }

    // 2.调用的重载方法
    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
        try {
            // 创建 XMLConfigBuilder, XMLConfigBuilder是专门解析mybatis的配置文件的类
            XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
            // 执行 XML 解析
            // 创建 DefaultSqlSessionFactory 对象
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                inputStream.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    /**
     * 创建 DefaultSqlSessionFactory 对象
     *
     * @param config Configuration 对象
     * @return DefaultSqlSessionFactory 对象
     */
    public SqlSessionFactory build(Configuration config) {
        // 构建者设计模式
        return new DefaultSqlSessionFactory(config);
    }

}