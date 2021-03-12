package com.prim.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prim.common.pojo.OrderItem;

import java.util.List;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 16:56
 * @PackageName: com.prim.order.mapper
 * @ClassName: OrderItemMapper.java
 **/
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    List<OrderItem> findByOrderId(Integer id);
}
