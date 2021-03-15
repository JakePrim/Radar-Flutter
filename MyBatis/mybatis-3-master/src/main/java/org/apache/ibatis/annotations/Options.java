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
package org.apache.ibatis.annotations;

import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;

import java.lang.annotation.*;

/**
 * 操作可选项
 *
 * @author Clinton Begin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Options {

    /**
     * The options for the {@link Options#flushCache()}.
     * The default is {@link FlushCachePolicy#DEFAULT}
     */
    enum FlushCachePolicy {
        /** <code>false</code> for select statement; <code>true</code> for insert/update/delete statement. */
        DEFAULT,
        /** Flushes cache regardless of the statement type. */
        TRUE,
        /** Does not flush cache regardless of the statement type. */
        FALSE
    }

    /**
     * @return 是否使用缓存
     */
    boolean useCache() default true;

    /**
     * @return 刷新缓存的策略
     */
    FlushCachePolicy flushCache() default FlushCachePolicy.DEFAULT;

    /**
     * @return 结果类型
     */
    ResultSetType resultSetType() default ResultSetType.DEFAULT;

    /**
     * @return 语句类型
     */
    StatementType statementType() default StatementType.PREPARED;

    /**
     * @return 加载数量
     */
    int fetchSize() default -1;

    /**
     * @return 超时时间
     */
    int timeout() default -1;

    /**
     * @return 是否生成主键
     */
    boolean useGeneratedKeys() default false;

    /**
     * @return 主键在 Java 类中的属性
     */
    String keyProperty() default "";

    /**
     * @return 主键在数据库中的字段
     */
    String keyColumn() default "";

    /**
     * @return 结果集
     */
    String resultSets() default "";

}