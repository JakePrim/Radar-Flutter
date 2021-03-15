/**
 * Copyright 2009-2017 the original author or authors.
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
package org.apache.ibatis.executor.keygen;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;

import java.sql.Statement;
import java.util.List;

/**
 * 基于从数据库查询主键的 KeyGenerator 实现类，适用于 Oracle、PostgreSQL 。
 *
 * @author Clinton Begin
 * @author Jeff Butler
 */
public class SelectKeyGenerator implements KeyGenerator {

    public static final String SELECT_KEY_SUFFIX = "!selectKey";

    /**
     * 是否在 before 阶段执行
     *
     * true ：before
     * after ：after
     */
    private final boolean executeBefore;
    /**
     * MappedStatement 对象
     */
    private final MappedStatement keyStatement;

    public SelectKeyGenerator(MappedStatement keyStatement, boolean executeBefore) {
        this.executeBefore = executeBefore;
        this.keyStatement = keyStatement;
    }

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        if (executeBefore) {
            processGeneratedKeys(executor, ms, parameter);
        }
    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        if (!executeBefore) {
            processGeneratedKeys(executor, ms, parameter);
        }
    }

    private void processGeneratedKeys(Executor executor, MappedStatement ms, Object parameter) {
        try {
            // 有查询主键的 SQL 语句，即 keyStatement 对象非空
            if (parameter != null && keyStatement != null && keyStatement.getKeyProperties() != null) {
                String[] keyProperties = keyStatement.getKeyProperties();
                final Configuration configuration = ms.getConfiguration();
                final MetaObject metaParam = configuration.newMetaObject(parameter);
                // Do not close keyExecutor.
                // The transaction will be closed by parent executor.
                // 创建执行器，类型为 SimpleExecutor
                Executor keyExecutor = configuration.newExecutor(executor.getTransaction(), ExecutorType.SIMPLE);
                // 执行查询主键的操作
                List<Object> values = keyExecutor.query(keyStatement, parameter, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
                // 查不到结果，抛出 ExecutorException 异常
                if (values.size() == 0) {
                    throw new ExecutorException("SelectKey returned no data.");
                // 查询的结果过多，抛出 ExecutorException 异常
                } else if (values.size() > 1) {
                    throw new ExecutorException("SelectKey returned more than one value.");
                } else {
                    // 创建 MetaObject 对象，访问查询主键的结果
                    MetaObject metaResult = configuration.newMetaObject(values.get(0));
                    // 单个主键
                    if (keyProperties.length == 1) {
                        // 设置属性到 metaParam 中，相当于设置到 parameter 中
                        if (metaResult.hasGetter(keyProperties[0])) {
                            setValue(metaParam, keyProperties[0], metaResult.getValue(keyProperties[0]));
                        } else {
                            // no getter for the property - maybe just a single value object
                            // so try that
                            setValue(metaParam, keyProperties[0], values.get(0));
                        }
                    // 多个主键
                    } else {
                        // 遍历，进行赋值
                        handleMultipleProperties(keyProperties, metaParam, metaResult);
                    }
                }
            }
        } catch (ExecutorException e) {
            throw e;
        } catch (Exception e) {
            throw new ExecutorException("Error selecting key or setting result to parameter object. Cause: " + e, e);
        }
    }

    private void handleMultipleProperties(String[] keyProperties,
                                          MetaObject metaParam, MetaObject metaResult) {
        String[] keyColumns = keyStatement.getKeyColumns();
        // 遍历，进行赋值
        if (keyColumns == null || keyColumns.length == 0) {
            // no key columns specified, just use the property names
            for (String keyProperty : keyProperties) {
                setValue(metaParam, keyProperty, metaResult.getValue(keyProperty));
            }
        } else {
            if (keyColumns.length != keyProperties.length) {
                throw new ExecutorException("If SelectKey has key columns, the number must match the number of key properties.");
            }
            for (int i = 0; i < keyProperties.length; i++) {
                setValue(metaParam, keyProperties[i], metaResult.getValue(keyColumns[i]));
            }
        }
    }

    private void setValue(MetaObject metaParam, String property, Object value) {
        if (metaParam.hasSetter(property)) {
            metaParam.setValue(property, value);
        } else {
            throw new ExecutorException("No setter found for the keyProperty '" + property + "' in " + metaParam.getOriginalObject().getClass().getName() + ".");
        }
    }

}
