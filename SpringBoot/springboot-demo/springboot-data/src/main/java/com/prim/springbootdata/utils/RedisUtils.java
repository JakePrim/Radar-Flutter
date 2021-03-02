package com.prim.springbootdata.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-01 22:31
 * @PackageName: com.prim.springbootdata.utils
 * @ClassName: RedisUtils.java
 **/
@Component // 注入到IOC容器中
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 读取缓存
     *
     * @return
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     */
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            //写入Redis 1天后缓存失效
            redisTemplate.opsForValue().set(key, value, 1, TimeUnit.DAYS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
