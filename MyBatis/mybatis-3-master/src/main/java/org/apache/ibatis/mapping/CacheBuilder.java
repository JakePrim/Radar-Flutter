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
package org.apache.ibatis.mapping;

import org.apache.ibatis.builder.InitializingObject;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.cache.decorators.*;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * {@link Cache} 构造器
 *
 * @author Clinton Begin
 */
public class CacheBuilder {

    /**
     * 编号。
     *
     * 目前看下来，是命名空间
     */
    private final String id;
    /**
     * 负责存储的 Cache 实现类
     */
    private Class<? extends Cache> implementation;
    /**
     * Cache 装饰类集合
     *
     * 例如，负责过期的 Cache 实现类
     */
    private final List<Class<? extends Cache>> decorators;
    /**
     * 缓存容器大小
     */
    private Integer size;
    /**
     * 清空缓存的频率。0 代表不清空
     */
    private Long clearInterval;
    /**
     * 是否序列化
     */
    private boolean readWrite;
    /**
     * Properties 对象
     */
    private Properties properties;
    /**
     * 是否阻塞
     */
    private boolean blocking;

    public CacheBuilder(String id) {
        this.id = id;
        this.decorators = new ArrayList<>();
    }

    public CacheBuilder implementation(Class<? extends Cache> implementation) {
        this.implementation = implementation;
        return this;
    }

    public CacheBuilder addDecorator(Class<? extends Cache> decorator) {
        if (decorator != null) {
            this.decorators.add(decorator);
        }
        return this;
    }

    public CacheBuilder size(Integer size) {
        this.size = size;
        return this;
    }

    public CacheBuilder clearInterval(Long clearInterval) {
        this.clearInterval = clearInterval;
        return this;
    }

    public CacheBuilder readWrite(boolean readWrite) {
        this.readWrite = readWrite;
        return this;
    }

    public CacheBuilder blocking(boolean blocking) {
        this.blocking = blocking;
        return this;
    }

    public CacheBuilder properties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public Cache build() {
        // 设置默认实现类
        setDefaultImplementations();
        // 创建基础 Cache 对象
        Cache cache = newBaseCacheInstance(implementation, id);
        // 设置属性
        setCacheProperties(cache);
        // issue #352, do not apply decorators to custom caches
        // 如果是 PerpetualCache 类，则进行包装
        if (PerpetualCache.class.equals(cache.getClass())) {
            // 遍历 decorators ，进行包装
            for (Class<? extends Cache> decorator : decorators) {
                // 包装 Cache 对象
                cache = newCacheDecoratorInstance(decorator, cache);
                // 设置属性
                setCacheProperties(cache);
            }
            // 执行标准化的 Cache 包装
            cache = setStandardDecorators(cache);
        // 如果是自定义的 Cache 类，则包装成 LoggingCache 对象，因为要统计。
        } else if (!LoggingCache.class.isAssignableFrom(cache.getClass())) {
            cache = new LoggingCache(cache);
        }
        return cache;
    }

    /**
     * 设置默认实现类
     */
    private void setDefaultImplementations() {
        if (implementation == null) {
            implementation = PerpetualCache.class;
            if (decorators.isEmpty()) {
                decorators.add(LruCache.class);
            }
        }
    }

    private Cache setStandardDecorators(Cache cache) {
        try {
            // 如果有 size 方法，则进行设置
            MetaObject metaCache = SystemMetaObject.forObject(cache);
            if (size != null && metaCache.hasSetter("size")) {
                metaCache.setValue("size", size);
            }
            // 包装成 ScheduledCache 对象
            if (clearInterval != null) {
                cache = new ScheduledCache(cache);
                ((ScheduledCache) cache).setClearInterval(clearInterval);
            }
            // 包装成 SerializedCache 对象
            if (readWrite) {
                cache = new SerializedCache(cache);
            }
            // 包装成 LoggingCache 对象
            cache = new LoggingCache(cache);
            // 包装成 SynchronizedCache 对象
            cache = new SynchronizedCache(cache);
            // 包装成 BlockingCache 对象
            if (blocking) {
                cache = new BlockingCache(cache);
            }
            return cache;
        } catch (Exception e) {
            throw new CacheException("Error building standard cache decorators.  Cause: " + e, e);
        }
    }

    private void setCacheProperties(Cache cache) {
        if (properties != null) {
            // 初始化 Cache 对象的属性
            MetaObject metaCache = SystemMetaObject.forObject(cache);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String name = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (metaCache.hasSetter(name)) {
                    Class<?> type = metaCache.getSetterType(name);
                    if (String.class == type) {
                        metaCache.setValue(name, value);
                    } else if (int.class == type
                            || Integer.class == type) {
                        metaCache.setValue(name, Integer.valueOf(value));
                    } else if (long.class == type
                            || Long.class == type) {
                        metaCache.setValue(name, Long.valueOf(value));
                    } else if (short.class == type
                            || Short.class == type) {
                        metaCache.setValue(name, Short.valueOf(value));
                    } else if (byte.class == type
                            || Byte.class == type) {
                        metaCache.setValue(name, Byte.valueOf(value));
                    } else if (float.class == type
                            || Float.class == type) {
                        metaCache.setValue(name, Float.valueOf(value));
                    } else if (boolean.class == type
                            || Boolean.class == type) {
                        metaCache.setValue(name, Boolean.valueOf(value));
                    } else if (double.class == type
                            || Double.class == type) {
                        metaCache.setValue(name, Double.valueOf(value));
                    } else {
                        throw new CacheException("Unsupported property type for cache: '" + name + "' of type " + type);
                    }
                }
            }
        }
        // 如果实现了 InitializingObject 接口，执行进一步初始化逻辑
        if (InitializingObject.class.isAssignableFrom(cache.getClass())) {
            try {
                ((InitializingObject) cache).initialize();
            } catch (Exception e) {
                throw new CacheException("Failed cache initialization for '" +
                        cache.getId() + "' on '" + cache.getClass().getName() + "'", e);
            }
        }
    }

    /**
     * 创建基础 Cache 对象
     *
     * @param cacheClass Cache 类
     * @param id 编号
     * @return Cache 对象
     */
    private Cache newBaseCacheInstance(Class<? extends Cache> cacheClass, String id) {
        // 获得 Cache 类的构造方法
        Constructor<? extends Cache> cacheConstructor = getBaseCacheConstructor(cacheClass);
        try {
            // 创建 Cache 对象
            return cacheConstructor.newInstance(id);
        } catch (Exception e) {
            throw new CacheException("Could not instantiate cache implementation (" + cacheClass + "). Cause: " + e, e);
        }
    }

    /**
     * 获得 Cache 类的构造方法
     *
     * @param cacheClass Cache 类
     * @return 构造方法
     */
    private Constructor<? extends Cache> getBaseCacheConstructor(Class<? extends Cache> cacheClass) {
        try {
            return cacheClass.getConstructor(String.class);
        } catch (Exception e) {
            throw new CacheException("Invalid base cache implementation (" + cacheClass + ").  " +
                    "Base cache implementations must have a constructor that takes a String id as a parameter.  Cause: " + e, e);
        }
    }

    /**
     * 包装指定 Cache 对象
     *
     * @param cacheClass 包装的 Cache 类
     * @param base 被包装的 Cache 对象
     * @return 包装后的 Cache 对象
     */
    private Cache newCacheDecoratorInstance(Class<? extends Cache> cacheClass, Cache base) {
        // 获得方法参数为 Cache 的构造方法
        Constructor<? extends Cache> cacheConstructor = getCacheDecoratorConstructor(cacheClass);
        try {
            // 创建 Cache 对象
            return cacheConstructor.newInstance(base);
        } catch (Exception e) {
            throw new CacheException("Could not instantiate cache decorator (" + cacheClass + "). Cause: " + e, e);
        }
    }

    /**
     * 获得方法参数为 Cache 的构造方法
     *
     * @param cacheClass 指定类
     * @return 构造方法
     */
    private Constructor<? extends Cache> getCacheDecoratorConstructor(Class<? extends Cache> cacheClass) {
        try {
            return cacheClass.getConstructor(Cache.class);
        } catch (Exception e) {
            throw new CacheException("Invalid cache decorator (" + cacheClass + ").  " +
                    "Cache decorators must have a constructor that takes a Cache instance as a parameter.  Cause: " + e, e);
        }
    }

}