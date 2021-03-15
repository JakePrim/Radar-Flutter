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
package org.apache.ibatis.executor;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 结果提取器
 *
 * @author Andrew Gustafson
 */
public class ResultExtractor {

    private final Configuration configuration;
    private final ObjectFactory objectFactory;

    public ResultExtractor(Configuration configuration, ObjectFactory objectFactory) {
        this.configuration = configuration;
        this.objectFactory = objectFactory;
    }

    /**
     * 从 list 中，提取结果
     *
     * @param list list
     * @param targetType 结果类型
     * @return 结果
     */
    public Object extractObjectFromList(List<Object> list, Class<?> targetType) {
        Object value = null;
        // 情况一，targetType 就是 list ，直接返回
        if (targetType != null && targetType.isAssignableFrom(list.getClass())) {
            value = list;
        // 情况二，targetType 是集合，添加到其中
        } else if (targetType != null && objectFactory.isCollection(targetType)) {
            // 创建 Collection 对象
            value = objectFactory.create(targetType);
            // 将结果添加到其中
            MetaObject metaObject = configuration.newMetaObject(value);
            metaObject.addAll(list);
        // 情况三，targetType 是数组
        } else if (targetType != null && targetType.isArray()) {
            // 创建 array 数组
            Class<?> arrayComponentType = targetType.getComponentType();
            Object array = Array.newInstance(arrayComponentType, list.size());
            // 赋值到 array 中
            if (arrayComponentType.isPrimitive()) {
                for (int i = 0; i < list.size(); i++) {
                    Array.set(array, i, list.get(i));
                }
                value = array;
            } else {
                value = list.toArray((Object[]) array);
            }
        // 情况四，普通对象，取首个对象
        } else {
            if (list != null && list.size() > 1) {
                throw new ExecutorException("Statement returned more than one row, where no more than one was expected.");
            } else if (list != null && list.size() == 1) {
                value = list.get(0);
            }
        }
        return value;
    }

}