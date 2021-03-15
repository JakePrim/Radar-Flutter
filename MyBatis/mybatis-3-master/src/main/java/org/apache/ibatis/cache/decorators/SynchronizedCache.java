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
package org.apache.ibatis.cache.decorators;

import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 同步的 Cache 实现类
 *
 * @author Clinton Begin
 */
public class SynchronizedCache implements Cache {

    /**
     * 委托的 Cache 对象
     */
    private final Cache delegate;

    public SynchronizedCache(Cache delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override // 同步
    public synchronized int getSize() {
        return delegate.getSize();
    }

    @Override // 同步
    public synchronized void putObject(Object key, Object object) {
        delegate.putObject(key, object);
    }

    @Override // 同步
    public synchronized Object getObject(Object key) {
        return delegate.getObject(key);
    }

    @Override // 同步
    public synchronized Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    @Override // 同步
    public synchronized void clear() {
        delegate.clear();
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

}