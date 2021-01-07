package com.prim.jedis;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 */
public class JedisTester {
    public static void main(String[] args) {
        //连接远程服务器 ip 端口号
        Jedis jedis = new Jedis("192.168.0.106", 6379);
        try {
            //jedis.auth("");//如果在redis.conf 中设置了密码 需要先输入密码
            //选择二号数据库
            jedis.select(2);
            jedis.set("sn", "7781-9938");
            String sn = jedis.get("sn");
            System.out.println("sn = " + sn);
            jedis.mset(new String[]{"title", "婴幼儿奶粉", "num", "20"});
            List<String> mget = jedis.mget(new String[]{"title", "num", "sn"});
            System.out.println("mget = " + mget);
            Long num = jedis.incr("num");
            System.out.println("num = " + num);
            jedis.hset("student:3312", "name", "zhangsan");
            String name = jedis.hget("student:3312", "name");
            System.out.println("name = " + name);
            //传入一个map
            Map<String, String> map = new HashMap<String, String>(10);
            map.put("name", "lilan");
            map.put("age", "18");
            map.put("id", "3313");
            jedis.hmset("student:3313", map);
            Map<String, String> map1 = jedis.hgetAll("student:3313");
            System.out.println("args = " + map1);

            //list
            jedis.del("letter");
            jedis.rpush("letter",new String[]{"d","e","f"});
            jedis.lpush("letter",new String[]{"c","b","a"});
            List<String> letter = jedis.lrange("letter", 0, -1);
            System.out.println("letter = " + letter);

            jedis.lpop("letter");
            jedis.rpop("letter");
            List<String> letter1 = jedis.lrange("letter", 0, -1);
            System.out.println("letter1 = " + letter1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //使用完毕后一定要关闭
            jedis.close();
        }
    }
}
