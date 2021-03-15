/**
 * Copyright 2009-2015 the original author or authors.
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

/**
 * Specifies if and how MyBatis should automatically map columns to fields/properties.
 *
 * 自动映射行为的枚举
 *
 * @author Eduardo Macarron
 */
public enum AutoMappingBehavior {

    /**
     * Disables auto-mapping.
     *
     * 禁用自动映射的功能
     */
    NONE,

    /**
     * Will only auto-map results with no nested result mappings defined inside.
     *
     * 开启部分映射的功能
     */
    PARTIAL,

    /**
     * Will auto-map result mappings of any complexity (containing nested or otherwise).
     *
     * 开启全部映射的功能
     */
    FULL

}