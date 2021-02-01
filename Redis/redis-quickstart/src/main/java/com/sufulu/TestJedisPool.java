package com.sufulu;

import redis.clients.jedis.Jedis;

public class TestJedisPool {
    public static void main(String[] args) {
        Jedis jedis = JedisPoolUtils.getJedis();
        Jedis jedis1 = JedisPoolUtils.getJedis();
        System.out.println(jedis == jedis1);
//        jedis.set("k1", "v1");
        Long k1 = jedis.ttl("k1");
        System.out.println(k1);
    }
}
