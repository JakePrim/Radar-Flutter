package com.prim.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prim.common.pojo.OrderStatus;

import java.util.List;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 16:57
 * @PackageName: com.prim.order.mapper
 * @ClassName: OrderStatus.java
 **/
public interface OrderStatusMapper extends BaseMapper<OrderStatus> {
    OrderStatus findById(Integer id);

    /**
     * 根据订单的状态 查询订单的id
     *
     * @param status
     * @return
     */
    List<Integer> findOrderIdByStatus(Integer status);
}
