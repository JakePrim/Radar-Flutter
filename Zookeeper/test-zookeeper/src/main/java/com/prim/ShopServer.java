package com.prim;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * 商家服务类
 */
public class ShopServer {
    //Zookeeper的端口和IP集群
    private String connectStr = "172.16.150.130:2181,172.16.150.131:2181,172.16.150.132:2181";

    /**
     * session的时间设置，默认是ms：时间不宜设置太小，因为Zookeeper和加载集群会因为性能等原因而延迟较高
     * 如果时间太少，还没有创建好客户端，会报错
     */
    private int sessionTimout = 60 * 1000;

    private ZooKeeper zkClient;

    /**
     * 连接Zookeeper
     */
    public void connection() throws IOException {
        zkClient = new ZooKeeper(connectStr, sessionTimout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    /**
     * 注册到Zookeeper
     */
    public void register(String shopName) throws KeeperException, InterruptedException {
        //在根节点提前创建meituan 一定要创建临时有序的节点,因为：
        //1. 可以自动编号；2. 断开时节点自动删除 也就意味着商家打样了；3. 创建节点就是营业
        String s = zkClient.create("/meituan/shop", shopName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("[" + shopName + "]开始营业了" + s);
    }

    /**
     * 业务逻辑 - 做生意
     *
     * @param arg
     */
    private void business(String arg) throws IOException {
        System.out.println("【" + arg + "】正在营业中");
        System.in.read();
    }

    public static void main(String[] args) throws Exception {
        // 我要开一个饭店
        ShopServer shopServer = new ShopServer();

        // 连接Zookeeper集群 和 美团取得联系
        shopServer.connection();

        // 将服务节点注册到Zookeeper（入驻美团）
        shopServer.register(args[0]);

        // 业务逻辑处理- 做生意
        shopServer.business(args[0]);
    }


}
