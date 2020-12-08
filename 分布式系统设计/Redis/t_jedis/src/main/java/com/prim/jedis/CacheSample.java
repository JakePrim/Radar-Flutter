package com.prim.jedis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author prim
 */
public class CacheSample {
    public CacheSample() {
        //将数据转换成json
        Jedis jedis = new Jedis("192.168.0.106", 6379);
        try {
            List<Goods> goodsList = new ArrayList<Goods>();
            //redis 会对中文进行utf-8 编码
            goodsList.add(new Goods(8818, "apple", "四六级考试的", 130.5f));
            goodsList.add(new Goods(8819, "mac", "善良的机会", 30.5f));
            goodsList.add(new Goods(8820, "android", "是打开链接", 31.5f));
            jedis.select(3);
            for (Goods goods : goodsList) {
                String json = JSON.toJSONString(goods);
                jedis.set("goods:" + goods.getGoodsId(), json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void main(String[] args) {
        CacheSample cacheSample = new CacheSample();
        System.out.println("请输入要查询的商品编号 = ");
        String goodsId = new Scanner(System.in).next();
        Jedis jedis = new Jedis("192.168.0.106", 6379);
        try {
            jedis.select(3);
            if (jedis.exists("goods:" + goodsId)) {
                String json = jedis.get("goods:" + goodsId);
                System.out.println("json = " + json);
                Goods goods = JSON.parseObject(json, Goods.class);
                System.out.println("name = " + goods.getGoodsName());
            } else {
                System.out.println("输入的商品编号不存在请重新输入");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
