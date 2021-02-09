package com.sfl.service;

import com.sfl.pojo.UserCourseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: Edu
 * @Description: 订单服务
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 09:50
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

    /**
     * 更新订单状态
     *
     * @param orderNo
     * @param status
     * @return
     */
    Integer updateOrder(String orderNo, Integer status);

    /**
     * 删除订单
     *
     * @param orderNo
     * @return
     */
    Integer deleteOrder(String orderNo);

    List<UserCourseOrder> findOrderAll(String userId);
}
