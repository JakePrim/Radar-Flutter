package com.prim.common.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: homework
 * @Description: 订单表和商品表示多对多的关系
 * 这里是订单和商品的中间表,表示的是每个商品的订单
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 11:54
 * @PackageName: com.prim.common.pojo
 * @ClassName: Order.java
 **/
@Data
@Table(name = "order_item")
public class OrderItem implements Serializable {
    @Id
    private Integer id;

    //订单id
    @Column(name = "order_id")
    private Integer orderId;

    //商品id
    @Column(name = "product_id")
    private Integer productId;

    //商品名称
    @Column(name = "item_name")
    private String itemName;

    //商品价格
    @Column(name = "price")
    private String price;

    //购买的数量
    @Column(name = "buy_counts")
    private String buyCounts;
}
