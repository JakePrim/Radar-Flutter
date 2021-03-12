package com.prim.common.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: homework
 * @Description: 订单Javabean
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 13:59
 * @PackageName: com.prim.common.pojo
 * @ClassName: Order.java
 **/
@Data
@Table(name = "order")
public class Order implements Serializable {
    @Id
    private Integer id;

    //支付类型 10：微信 11：支付宝 12:银行卡
    @Column(name = "pay_type")
    private String payType;

    //是否删除订单
    @Column(name = "is_delete")
    private Integer isDelete;

    //总金额
    @Column(name = "total_amount")
    private String totalAmount;

    //实际支付金额
    @Column(name = "real_amount")
    private String realAmount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    //订单可能由多个order item
    private List<OrderItem> orderItems;

    //订单的状态
    private OrderStatus orderStatus;
}
