package com.sufulu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 测试Redis事务
 */
public class TestTransaction {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("172.16.150.132", 6379);
        int yue = Integer.parseInt(jedis.get("yue"));
        int zhichu = 10;

        //监控余额
        jedis.watch("yue");
        //模拟网络延迟
        Thread.sleep(5000);

        if (yue < zhichu) {
            jedis.unwatch();//解除监控
            System.out.println("余额不足");
        } else {
            //开启事务
            Transaction transaction = jedis.multi();
            transaction.decrBy("yue", zhichu);//余额减少10元
            transaction.incrBy("zhichu", zhichu);//累计消费增加
            //执行事务
            transaction.exec();
            System.out.println("余额：" + jedis.get("yue"));
            System.out.println("累计支出:" + jedis.get("zhichu"));
        }
    }
}
