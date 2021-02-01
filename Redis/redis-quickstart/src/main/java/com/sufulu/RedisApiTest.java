package com.sufulu;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisApiTest {

    private void testString() {
        Jedis jedis = new Jedis("172.16.150.132", 6379);
        String ping = jedis.ping();
        System.out.println(ping);//返回 PONG 说明连接成功
        //存入三条数据
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");

        //查询全部
        Set<String> keys = jedis.keys("*");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String k = iterator.next();
            //jedis.get(k)获取值
//            System.out.println(k + "->" + jedis.get(k));
        }
        //判断某个键是否存在
        Boolean k2Exists = jedis.exists("k2");
        System.out.println("k2Exists:" + k2Exists);//true
        //查看k1的过期时间
        System.out.println(jedis.ttl("k1"));//-1

        //设置多个键
        jedis.mset("k4", "v4", "k5", "v5");

        //String类型
        System.out.println("-------------------------------------------");

        //获取多个值
        System.out.println(jedis.mget("k1", "k2", "k3", "k4", "k5"));

        System.out.println("----------------------------------------");
    }

    private void testList() {
        Jedis jedis = new Jedis("172.16.150.132", 6379);

        //list
        jedis.lpush("list01", "l1", "l2", "l3", "l4", "l5");

        List<String> list01 = jedis.lrange("list01", 0, -1);
        for (String s : list01) {
            System.out.println(s);
        }

        System.out.println("----------------------------------");
    }

    private void testSet() {
        Jedis jedis = new Jedis("172.16.150.132", 6379);
        // set
        jedis.sadd("order", "001");
        jedis.sadd("order", "002");
        jedis.sadd("order", "003");

        Set<String> order = jedis.smembers("order");
        Iterator<String> iterator1 = order.iterator();
        while (iterator1.hasNext()) {
            String next = iterator1.next();
            System.out.println(next);
        }

        //删除
        jedis.srem("order", "002");
        System.out.println(jedis.smembers("order").size());
    }

    private void testHash() {
        Jedis jedis = new Jedis("172.16.150.132", 6379);
        jedis.hset("hash01", "username", "james");
        System.out.println(jedis.hget("hash01", "username"));

        HashMap<String, String> map = new HashMap<>();
        map.put("gender", "boy");
        map.put("address", "beijing");
        map.put("phone", "123131");
        jedis.hset("person", map);

        //获取多个属性的值
        List<String> list = jedis.hmget("person", "phone", "address");
        for (String s : list) {
            System.out.println(s);
        }
    }

    private void testZset() {
        Jedis jedis = new Jedis("172.16.150.132", 6379);

        jedis.zadd("zset01", 60d, "zs1");
        jedis.zadd("zset01", 70d, "zs2");
        jedis.zadd("zset01", 80d, "zs3");
        jedis.zadd("zset01", 90d, "zs4");
        jedis.zadd("zset01", 100d, "zs5");

        Set<String> zset01 = jedis.zrange("zset01", 0, -1);
        Iterator<String> iterator = zset01.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
    }

    public static void main(String[] args) {
        new RedisApiTest().testZset();
    }
}
