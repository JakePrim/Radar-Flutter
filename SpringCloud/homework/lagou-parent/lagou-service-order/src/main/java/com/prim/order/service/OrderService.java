package com.prim.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.common.pojo.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 18:59
 * @PackageName: com.prim.order.service
 * @ClassName: OrderService.java
 **/
public interface OrderService {
    Order findById(Integer id);

    IPage<Order> findPageOrderStatus(Page<Order> page, Integer status);

    IPage<Order> selectCreateTimePageVo(Page<Order> page, String startTime, String endTime);
}
