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
package org.apache.ibatis.cursor.defaults;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is the default implementation of a MyBatis Cursor.
 * This implementation is not thread safe.
 *
 * 默认 {@link Cursor} 实现类
 *
 * @author Guillaume Darmont / guillaume@dropinocean.com
 */
public class DefaultCursor<T> implements Cursor<T> {

    // ResultSetHandler stuff
    private final DefaultResultSetHandler resultSetHandler;
    private final ResultMap resultMap;
    private final ResultSetWrapper rsw;
    private final RowBounds rowBounds;
    /**
     * ObjectWrapperResultHandler 对象
     */
    private final ObjectWrapperResultHandler<T> objectWrapperResultHandler = new ObjectWrapperResultHandler<>();

    /**
     * CursorIterator 对象，游标迭代器。
     */
    private final CursorIterator cursorIterator = new CursorIterator();
    /**
     * 是否开始迭代
     *
     * {@link #iterator()}
     */
    private boolean iteratorRetrieved;
    /**
     * 游标状态
     */
    private CursorStatus status = CursorStatus.CREATED;
    /**
     * 已完成映射的行数
     */
    private int indexWithRowBound = -1;

    private enum CursorStatus {

        /**
         * A freshly created cursor, database ResultSet consuming has not started
         */
        CREATED,
        /**
         * A cursor currently in use, database ResultSet consuming has started
         */
        OPEN,
        /**
         * A closed cursor, not fully consumed
         *
         * 已关闭，并未完全消费
         */
        CLOSED,
        /**
         * A fully consumed cursor, a consumed cursor is always closed
         *
         * 已关闭，并且完全消费
         */
        CONSUMED
    }

    public DefaultCursor(DefaultResultSetHandler resultSetHandler, ResultMap resultMap, ResultSetWrapper rsw, RowBounds rowBounds) {
        this.resultSetHandler = resultSetHandler;
        this.resultMap = resultMap;
        this.rsw = rsw;
        this.rowBounds = rowBounds;
    }

    @Override
    public boolean isOpen() {
        return status == CursorStatus.OPEN;
    }

    @Override
    public boolean isConsumed() {
        return status == CursorStatus.CONSUMED;
    }

    @Override
    public int getCurrentIndex() {
        return rowBounds.getOffset() + cursorIterator.iteratorIndex;
    }

    @Override
    public Iterator<T> iterator() {
        // 如果已经获取，则抛出 IllegalStateException 异常
        if (iteratorRetrieved) {
            throw new IllegalStateException("Cannot open more than one iterator on a Cursor");
        }
        if (isClosed()) {
            throw new IllegalStateException("A Cursor is already closed.");
        }
        // 标记已经获取
        iteratorRetrieved = true;
        return cursorIterator;
    }

    @Override
    public void close() {
        if (isClosed()) {
            return;
        }

        // 关闭 ResultSet
        ResultSet rs = rsw.getResultSet();
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore
        } finally {
            // 设置状态为 CursorStatus.CLOSED
            status = CursorStatus.CLOSED;
        }
    }

    // 遍历下一条记录
    protected T fetchNextUsingRowBound() {
        // 遍历下一条记录
        T result = fetchNextObjectFromDatabase();
        // 循环跳过 rowBounds 的索引
        while (result != null && indexWithRowBound < rowBounds.getOffset()) {
            result = fetchNextObjectFromDatabase();
        }
        // 返回记录
        return result;
    }

    // 遍历下一条记录
    protected T fetchNextObjectFromDatabase() {
        // 如果已经关闭，返回 null
        if (isClosed()) {
            return null;
        }

        try {
            // 设置状态为 CursorStatus.OPEN
            status = CursorStatus.OPEN;
            // 遍历下一条记录
            if (!rsw.getResultSet().isClosed()) {
                resultSetHandler.handleRowValues(rsw, resultMap, objectWrapperResultHandler, RowBounds.DEFAULT, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 复制给 next
        T next = objectWrapperResultHandler.result;
        // 增加 indexWithRowBound
        if (next != null) {
            indexWithRowBound++;
        }
        // No more object or limit reached
        // 没有更多记录，或者到达 rowBounds 的限制索引位置，则关闭游标，并设置状态为 CursorStatus.CONSUMED
        if (next == null || getReadItemsCount() == rowBounds.getOffset() + rowBounds.getLimit()) {
            close();
            status = CursorStatus.CONSUMED;
        }
        // 置空 objectWrapperResultHandler.result 属性
        objectWrapperResultHandler.result = null;

        return next;
    }

    private boolean isClosed() {
        return status == CursorStatus.CLOSED || status == CursorStatus.CONSUMED;
    }

    private int getReadItemsCount() {
        return indexWithRowBound + 1;
    }

    private static class ObjectWrapperResultHandler<T> implements ResultHandler<T> {

        /**
         * 结果对象
         */
        private T result;

        @Override
        public void handleResult(ResultContext<? extends T> context) {
            // 设置结果对象
            this.result = context.getResultObject();
            // 暂停
            context.stop();
        }

    }

    /**
     * 游标的迭代器实现类
     */
    private class CursorIterator implements Iterator<T> {

        /**
         * Holder for the next object to be returned
         *
         * 结果对象，提供给 {@link #next()} 返回
         */
        T object;

        /**
         * Index of objects returned using next(), and as such, visible to users.
         * 索引位置
         */
        int iteratorIndex = -1;

        @Override
        public boolean hasNext() {
            // 如果 object 为空，则遍历下一条记录
            if (object == null) {
                object = fetchNextUsingRowBound();
            }
            // 判断 object 是否非空
            return object != null;
        }

        @Override
        public T next() {
            // Fill next with object fetched from hasNext()
            T next = object;

            // 如果 next 为空，则遍历下一条记录
            if (next == null) {
                next = fetchNextUsingRowBound();
            }

            // 如果 next 非空，说明有记录，则进行返回
            if (next != null) {
                // 置空 object 对象
                object = null;
                // 增加 iteratorIndex
                iteratorIndex++;
                // 返回 next
                return next;
            }

            // 如果 next 为空，说明没有记录，抛出 NoSuchElementException 异常
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from Cursor");
        }

    }

}