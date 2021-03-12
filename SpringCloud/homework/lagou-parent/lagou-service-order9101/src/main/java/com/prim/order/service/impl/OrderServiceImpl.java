package com.prim.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.common.pojo.Goods;
import com.prim.common.pojo.Order;
import com.prim.common.pojo.OrderStatus;
import com.prim.order.mapper.OrderMapper;
import com.prim.order.mapper.OrderStatusMapper;
import com.prim.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 19:23
 * @PackageName: com.prim.order.service.impl
 * @ClassName: OrderServiceImpl.java
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order findById(Integer id) {
        return orderMapper.findById(id);
    }

    @Override
    public IPage<Order> findPageOrderStatus(Page<Order> page, Integer status) {
        return orderMapper.selectPageVo(page, status);
    }

    @Override
    public IPage<Order> selectCreateTimePageVo(Page<Order> page, String startTime, String endTime) {
        return orderMapper.selectCreateTimePageVo(page, startTime, endTime);
    }
}
