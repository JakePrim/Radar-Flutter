package com.prim;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customers {
    public static void main(String[] args) throws Exception {
        //1. 获得Zookeeper的链接 用户打开美团APP
        Customers customers = new Customers();
        customers.connection();
        //2. 获取meituan下的所有子节点列表 - 获取商家列表
        customers.shopList();

        //3. 业务处理 - 对比商家下单点餐
        customers.business();
    }

    private void business() throws IOException {
        System.out.println("用户正在浏览商家");
        System.in.read();
    }

    private void shopList() throws KeeperException, InterruptedException {
        //对父节点进行监听
        List<String> shops = zkClient.getChildren("/meituan", true);
        //声明存储服务器信息的集合
        List<String> shopList = new ArrayList<>();
        for (String shop : shops) {
            //获取每一个节点的数据
            byte[] data = zkClient.getData("/meituan/" + shop, false, new Stat());
            if (data != null)
                shopList.add(new String(data));
        }
        System.out.println("目前正在营业的商店列表：" + shopList);
    }

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
                //重新再次获取商家列表
                try {
                    shopList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
