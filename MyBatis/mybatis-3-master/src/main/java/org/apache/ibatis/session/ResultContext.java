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
 * 结果上下文接口
 *
 * @author Clinton Begin
 */
public interface ResultContext<T> {

    /**
     * @return 当前结果对象
     */
    T getResultObject();

    /**
     * @return 总的结果对象的数量
     */
    int getResultCount();

    /**
     * @return 是否暂停
     */
    boolean isStopped();

    /**
     * 暂停
     */
    void stop();

}