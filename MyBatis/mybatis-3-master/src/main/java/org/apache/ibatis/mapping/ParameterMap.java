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
package org.apache.ibatis.mapping;

import org.apache.ibatis.session.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * 参数集合
 *
 * @author Clinton Begin
 */
public class ParameterMap {

    /**
     * 编号
     */
    private String id;
    /**
     * 类型
     *
     * 适用于 paramType="" 标签属性
     */
    private Class<?> type;
    /**
     * ParameterMapping 集合
     *
     * 适用于 paramMap="" 标签属性
     */
    private List<ParameterMapping> parameterMappings;

    private ParameterMap() {
    }

    public static class Builder {

        private ParameterMap parameterMap = new ParameterMap();

        public Builder(Configuration configuration, String id, Class<?> type, List<ParameterMapping> parameterMappings) {
            parameterMap.id = id;
            parameterMap.type = type;
            parameterMap.parameterMappings = parameterMappings;
        }

        public Class<?> type() {
            return parameterMap.type;
        }

        public ParameterMap build() {
            //lock down collections
            parameterMap.parameterMappings = Collections.unmodifiableList(parameterMap.parameterMappings);
            return parameterMap;
        }

    }

    public String getId() {
        return id;
    }

    public Class<?> getType() {
        return type;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

}
