package com.sfl.service;

import com.sfl.pojo.UserCourseOrder;

import java.util.List;

/**
 * @program: edu-web
 * @Description: 订单服务
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 09:54
 * @PackageName: com.sfl.service
 * @ClassName: OrderService.java
 **/
public interface OrderService {
    /**
     * 添加订单
     *
     * @param userCourseOrder
     * @return
     */
    int saveOrder(UserCourseOrder userCourseOrder);

    Integer updateOrder(String orderNo, Integer status);

    Integer deleteOrder(String orderNo);

    List<UserCourseOrder> findOrderAll(String userId);
}
