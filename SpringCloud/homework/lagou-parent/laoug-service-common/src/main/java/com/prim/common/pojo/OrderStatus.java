package com.prim.common.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: homework
 * @Description: 订单状态javabean
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:03
 * @PackageName: com.prim.common.pojo
 * @ClassName: OrderStatus.java
 **/
@Data
@Table(name = "order_status")
public class OrderStatus implements Serializable {
    @Id
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    //订单状态 0 未支付、1 已支付、2 已失效、-1 已删除
    private Integer status;

    //订单创建时间
    private Date createTime;

    //订单支付时间
    private Date payTime;

    //订单关闭时间
    private Date closeTime;
}
