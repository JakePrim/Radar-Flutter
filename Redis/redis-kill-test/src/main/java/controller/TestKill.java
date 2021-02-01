package controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class TestKill {

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate springRedisTemplate;



    @RequestMapping("kill")
    @ResponseBody
    public synchronized String kill() {
        //定义商品id，这里写死即可
        String productKey = "HUAWEI-P40";
        //通过redisson获得锁
        RLock lock = redisson.getLock(productKey);//底层源码就是集成了setnx setex等操作

        //上锁 过期时间为30秒
        lock.lock(30, TimeUnit.SECONDS);
        try {
            //1. 从Redis获取 手机库存数量
            int phoneCount = Integer.parseInt(springRedisTemplate.opsForValue().get("phone"));
            //2. 判断手机的数量是否够秒杀的
            if (phoneCount > 0) {
                phoneCount--;
                //修改Redis的库存数据
                springRedisTemplate.opsForValue().set("phone", String.valueOf(phoneCount));
                System.out.println("库存减一，剩余：" + phoneCount);
            } else {
                System.out.println("库存不足");
                return "count not";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "over!";
    }

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        //使用单个redis服务器 传递Redis服务器的IP和端口号，以及选择几号数据库
        config.useSingleServer().setAddress("redis://172.16.150.132:6379").setDatabase(0);
        //使用Redis集群 主从复制
//        config.useClusterServers().setScanInterval(2000).addNodeAddress("redis://172.16.150.132:6379",
//                "redis://172.16.150.131:6379", "redis://172.16.150.130:6379");
        return (Redisson) Redisson.create(config);
    }
}
