package com.prim.controller;

import com.prim.service.OrderService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private OrderService orderService;

    //Zookeeper集群
    private String connectStr = "172.16.150.130:2181,172.16.150.131:2181,172.16.150.132:2181";


    /**
     * 减库存操作
     *
     * @param id
     * @return
     */
    @GetMapping("/reduce")
    public Object reduce(Integer id) throws Exception {
        //重试策略，1000ms 重试1次 最多试3次
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        //创建curator 工具对象
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectStr, retry);
        client.start();
        //根据客户端工具对象 创建锁"内部互斥锁" "/product_" + id 每个商品创建一个节点，例如商品id为1 创建的节点就是/product_1
        InterProcessMutex lock = new InterProcessMutex(client, "/product_" + id);
        try {
            lock.acquire();
            //加锁
            orderService.reduceStock(id);
        } finally {
            //释放锁
            lock.release();
        }
        return "ok";
    }
}
