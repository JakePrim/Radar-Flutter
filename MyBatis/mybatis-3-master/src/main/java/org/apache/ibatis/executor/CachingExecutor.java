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
package org.apache.ibatis.executor;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cache.TransactionalCacheManager;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * 支持二级缓存的 Executor 的实现类
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 */
public class CachingExecutor implements Executor {

    /**
     * 被委托的 Executor 对象
     */
    private final Executor delegate;
    /**
     * TransactionalCacheManager 对象
     */
    private final TransactionalCacheManager tcm = new TransactionalCacheManager();

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
        // 设置 delegate 被当前执行器所包装
        delegate.setExecutorWrapper(this);
    }

    @Override
    public Transaction getTransaction() {
        // 执行 delegate 对应的方法
        return delegate.getTransaction();
    }

    @Override
    public void close(boolean forceRollback) {
        try {
            //issues #499, #524 and #573
            // 如果强制回滚，则回滚 TransactionalCacheManager
            if (forceRollback) {
                tcm.rollback();
                // 如果强制提交，则提交 TransactionalCacheManager
            } else {
                tcm.commit();
            }
        } finally {
            // 执行 delegate 对应的方法
            delegate.close(forceRollback);
        }
    }

    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    @Override
    public int update(MappedStatement ms, Object parameterObject) throws SQLException {
        // 如果需要清空缓存，则进行清空
        flushCacheIfRequired(ms);
        // 执行 delegate 对应的方法
        return delegate.update(ms, parameterObject);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        // 获得 BoundSql 对象
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        // 创建 CacheKey 对象
        CacheKey key = createCacheKey(ms, parameterObject, rowBounds, boundSql);
        // 查询
        return query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        // 如果需要清空缓存，则进行清空
        flushCacheIfRequired(ms);
        // 执行 delegate 对应的方法
        return delegate.queryCursor(ms, parameter, rowBounds);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
            throws SQLException {

        // 从 MappedStatement 中获取 Cache，注意这里的 Cache 是从MappedStatement中获取的
        // 也就是我们上面解析Mapper中<cache/>标签中创建的，它保存在Configration中
        // 我们在初始化解析xml时分析过每一个MappedStatement都有一个Cache对象，就是这里
        Cache cache = ms.getCache();

        // 如果配置文件中没有配置 <cache>，则 cache 为空
        if (cache != null) {
            //如果需要刷新缓存的话就刷新：flushCache="true"
            flushCacheIfRequired(ms);
            if (ms.isUseCache() && resultHandler == null) {
                // 暂时忽略，存储过程相关
                ensureNoOutParams(ms, boundSql);
                @SuppressWarnings("unchecked")
                // 从二级缓存中，获取结果，这里为什么使用的tcm获取缓存呢？
                List<E> list = (List<E>) tcm.getObject(cache, key);
                if (list == null) {
                    // 如果没有值，则执行查询，这个查询实际也是先走一级缓存查询，一级缓存也没有的话，则进行DB查询
                    list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
                    // 缓存查询结果 并不是真正的向二级缓存存储数据
                    tcm.putObject(cache, key, list); // issue #578 and #116
                }
                // 如果存在，则直接返回结果
                return list;
            }
        }
        // 不使用缓存，则从数据库中查询(会查一级缓存)
        return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        // 执行 delegate 对应的方法
        return delegate.flushStatements();
    }

    @Override
    public void commit(boolean required) throws SQLException {
        // 执行 delegate 对应的方法
        delegate.commit(required);
        // 提交 TransactionalCacheManager
        tcm.commit();
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        try {
            // 执行 delegate 对应的方法
            delegate.rollback(required);
        } finally {
            if (required) {
                // 回滚 TransactionalCacheManager
                tcm.rollback();
            }
        }
    }

    private void ensureNoOutParams(MappedStatement ms, BoundSql boundSql) {
        if (ms.getStatementType() == StatementType.CALLABLE) {
            for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                if (parameterMapping.getMode() != ParameterMode.IN) {
                    throw new ExecutorException("Caching stored procedures with OUT params is not supported.  Please configure useCache=false in " + ms.getId() + " statement.");
                }
            }
        }
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        // 执行 delegate 对应的方法
        return delegate.createCacheKey(ms, parameterObject, rowBounds, boundSql);
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        // 执行 delegate 对应的方法
        return delegate.isCached(ms, key);
    }

    @Override
    public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {
        // 执行 delegate 对应的方法
        delegate.deferLoad(ms, resultObject, property, key, targetType);
    }

    @Override
    public void clearLocalCache() {
        // 执行 delegate 对应的方法
        delegate.clearLocalCache();
    }

    /**
     * 如果需要清空缓存，则进行清空
     *
     * @param ms MappedStatement 对象
     */
    private void flushCacheIfRequired(MappedStatement ms) {
        Cache cache = ms.getCache();
        if (cache != null && ms.isFlushCacheRequired()) { // 是否需要清空缓存
            //清空二级缓存
            tcm.clear(cache);
        }
    }

    @Override
    public void setExecutorWrapper(Executor executor) {
        throw new UnsupportedOperationException("This method should not be called");
    }

}