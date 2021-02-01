package com.sufulu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis工具类
 */
public class JedisPoolUtils {
    private JedisPoolUtils() {
    }

    private volatile static JedisPool jedisPool = null;

    private volatile static Jedis jedis = null;

    //返回一个连接池
    private static JedisPool getInstance() {
        //双层检测锁
        if (jedisPool == null) {
            synchronized (JedisPoolUtils.class) {
                if (jedisPool == null) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    jedisPoolConfig.setMaxTotal(1000);//最大连接数量
                    jedisPoolConfig.setMaxIdle(30);//最大等待数量
                    jedisPoolConfig.setMaxWaitMillis(60 * 1000);//最大等待时间
                    jedisPoolConfig.setTestOnBorrow(true);//后台运行
                    jedisPool = new JedisPool(jedisPoolConfig, "172.16.150.132", 6379);
                }
            }
        }
        return jedisPool;
    }

    //返回jedis
    public static Jedis getJedis() {
        if (jedis == null) {
            jedis = getInstance().getResource();
        }
        return jedis;
    }
}
