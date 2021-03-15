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
package org.apache.ibatis.reflection.wrapper;

import org.apache.ibatis.reflection.MetaObject;

/**
 * {@link ObjectWrapper} 工厂接口
 *
 * @author Clinton Begin
 */
public interface ObjectWrapperFactory {

    /**
     * 是否包装了指定对象
     *
     * @param object 指定对象
     * @return 是否
     */
    boolean hasWrapperFor(Object object);

    /**
     * 获得指定对象的 ObjectWrapper 对象
     *
     * @param metaObject MetaObject 对象
     * @param object 指定对象
     * @return ObjectWrapper 对象
     */
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}