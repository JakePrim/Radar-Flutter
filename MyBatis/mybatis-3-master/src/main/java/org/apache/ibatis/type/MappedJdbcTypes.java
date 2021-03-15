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
package org.apache.ibatis.type;

import java.lang.annotation.*;

/**
 * 匹配的 JDBC Type 类型的注解
 *
 * @author Eduardo Macarron
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 注册到类
public @interface MappedJdbcTypes {

    /**
     * @return 匹配的 JDBC Type 类型的注解
     */
    JdbcType[] value();

    /**
     * @return 是否包含 {@link java.sql.JDBCType#NULL}
     */
    boolean includeNullJdbcType() default false;

}