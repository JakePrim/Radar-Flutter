package com.prim;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestZk {
    //Zookeeper的端口和IP集群
    private String connectStr = "172.16.150.130:2181,172.16.150.131:2181,172.16.150.132:2181";

    /**
     * session的时间设置，默认是ms：时间不宜设置太小，因为Zookeeper和加载集群会因为性能等原因而延迟较高
     * 如果时间太少，还没有创建好客户端，会报错
     */
    private int sessionTimout = 60 * 1000;

    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        //创建Zookeeper客户端,操作Zookeeper
        zkClient = new ZooKeeper(connectStr, sessionTimout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈，进行业务代码处理。");
                System.out.println(watchedEvent.getType());
            }
        });
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        /**
         * 参数1：创建节点的路径
         * 参数2：节点数据
         * 参数3：节点权限
         *  ACL对象：一个id和permission对 表示在哪些/哪个范围的id(Who)在通过了怎样的鉴权(How)之后，允许进行哪些操作(What)
         *  permission(what)一个int表示的位码，每一位代表一个对应操作的允许状态
         *  OPEN_ACL_UNSAFE: 创建开放节点，允许任意操作 - 使用最多
         *  READ_ACL_UNSAFE: 创建只读节点
         *  CREATOR_ALL_ACL: 创建者才有全部权限
         * 参数4：节点的类型：持久型、临时型节点
         */
        String str = zkClient.create("/prim", "lao".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(str);
    }

    /**
     * 获取节点
     */
    @Test
    public void getNode() throws KeeperException, InterruptedException {
        byte[] data = zkClient.getData("/prim", false, new Stat());
        String str = new String(data);
        System.out.println("get=" + str);
    }

    @Test
    public void updateNode() throws KeeperException, InterruptedException {
        //version是版本 通过ls -s /prim可以查看更新的版本
        Stat stat = zkClient.setData("/prim", "laoB".getBytes(), 1);
    }

    @Test
    public void deleteNode() throws KeeperException, InterruptedException {
        //由于我们进行了两次更新操作，所以数据版本号为2了
        zkClient.delete("/prim", 2);
    }

    /**
     * 获取子节点
     */
    @Test
    public void subNode() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/china", false);
        System.out.println(children);
    }

    /**
     * 监听根节点,下面的变化
     */
    @Test
    public void watchRootNode() throws KeeperException, InterruptedException, IOException {
        List<String> children = zkClient.getChildren("/", true);
        System.out.println(children);
        System.in.read();
    }

    @Test
    public void existsNode() throws KeeperException, InterruptedException {
        Stat exists = zkClient.exists("/prim", false);
        if (exists == null){
            System.out.println("节点不存在");
        }else {
            System.out.println("节点存在");
        }
    }
}
