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
package org.apache.ibatis.cache.decorators;

import org.apache.ibatis.cache.Cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Weak Reference cache decorator.
 * Thanks to Dr. Heinz Kabutz for his guidance here.
 *
 * 基于 {@link WeakReference} 的 Cache 实现类
 *
 * @author Clinton Begin
 */
public class WeakCache implements Cache {

    /**
     * 强引用的键的队列
     */
    private final Deque<Object> hardLinksToAvoidGarbageCollection;
    /**
     * {@link #hardLinksToAvoidGarbageCollection} 的大小
     */
    private int numberOfHardLinks;
    /**
     * 被 GC 回收的 WeakEntry 集合，避免被 GC。
     */
    private final ReferenceQueue<Object> queueOfGarbageCollectedEntries;
    /**
     * 委托的 Cache 对象
     */
    private final Cache delegate;

    public WeakCache(Cache delegate) {
        this.delegate = delegate;
        this.numberOfHardLinks = 256;
        this.hardLinksToAvoidGarbageCollection = new LinkedList<>();
        this.queueOfGarbageCollectedEntries = new ReferenceQueue<>();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        // 移除已经被 GC 回收的 WeakEntry
        removeGarbageCollectedItems();
        return delegate.getSize();
    }

    public void setSize(int size) {
        this.numberOfHardLinks = size;
    }

    @Override
    public void putObject(Object key, Object value) {
        // 移除已经被 GC 回收的 WeakEntry
        removeGarbageCollectedItems();
        // 添加到 delegate 中
        delegate.putObject(key, new WeakEntry(key, value, queueOfGarbageCollectedEntries));
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        @SuppressWarnings("unchecked") // assumed delegate cache is totally managed by this cache
        // 获得值的 WeakReference 对象
        WeakReference<Object> weakReference = (WeakReference<Object>) delegate.getObject(key);
        if (weakReference != null) {
            // 获得值
            result = weakReference.get();
            // 为空，从 delegate 中移除 。为空的原因是，意味着已经被 GC 回收
            if (result == null) {
                delegate.removeObject(key);
            // 非空，添加到 hardLinksToAvoidGarbageCollection 中，避免被 GC
            } else {
                // 添加到 hardLinksToAvoidGarbageCollection 的队头
                hardLinksToAvoidGarbageCollection.addFirst(result);
                // 超过上限，移除 hardLinksToAvoidGarbageCollection 的队尾
                if (hardLinksToAvoidGarbageCollection.size() > numberOfHardLinks) {
                    hardLinksToAvoidGarbageCollection.removeLast();
                }
            }
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        // 移除已经被 GC 回收的 WeakEntry
        removeGarbageCollectedItems();
        // 移除出 delegate
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        // 清空 hardLinksToAvoidGarbageCollection
        hardLinksToAvoidGarbageCollection.clear();
        // 移除已经被 GC 回收的 WeakEntry
        removeGarbageCollectedItems();
        // 清空 delegate
        delegate.clear();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    /**
     * 移除已经被 GC 回收的 WeakEntry
     */
    private void removeGarbageCollectedItems() {
        WeakEntry sv;
        while ((sv = (WeakEntry) queueOfGarbageCollectedEntries.poll()) != null) {
            delegate.removeObject(sv.key);
        }
    }

    private static class WeakEntry extends WeakReference<Object> {

        /**
         * 键
         */
        private final Object key;

        private WeakEntry(Object key, Object value, ReferenceQueue<Object> garbageCollectionQueue) {
            super(value, garbageCollectionQueue);
            this.key = key;
        }

    }

}