package com.sufulu;

import redis.clients.jedis.Jedis;

public class Test {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("172.16.150.132", 6379);
        String ping = jedis.ping();
        System.out.println(ping);//返回 PONG 说明连接成功
    }
}
