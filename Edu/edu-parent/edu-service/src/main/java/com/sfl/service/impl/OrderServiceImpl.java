package com.sfl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfl.mapper.OrderDao;
import com.sfl.pojo.UserCourseOrder;
import com.sfl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: Edu
 * @Description: 订单服务实现
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 09:51
 * @PackageName: com.sfl.service.impl
 * @ClassName: OrderServiceImpl.java
 **/
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDao orderDao;

    @Override
    public int saveOrder(UserCourseOrder userCourseOrder) {
        int row = orderDao.saveOrder(userCourseOrder);
        return row;
    }

    @Override
    public Integer updateOrder(String orderNo, Integer status) {
        Integer row = orderDao.updateOrder(orderNo, status);
        return row;
    }

    @Override
    public Integer deleteOrder(String orderNo) {
        Integer row = orderDao.deleteOrder(orderNo);
        return row;
    }

    @Override
    public List<UserCourseOrder> findOrderAll(String userId) {
        List<UserCourseOrder> orders = orderDao.findOrderAll(userId);
        return orders;
    }
}
