package com.sfl.mapper;

import com.sfl.pojo.UserCourseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: Edu
 * @Description: 订单dao
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 08:50
 * @PackageName: com.sfl.mapper
 * @ClassName: OrderDao.java
 **/
public interface OrderDao {
    int saveOrder(UserCourseOrder userCourseOrder);

    Integer updateOrder(@Param("orderNo") String orderNo, @Param("status") Integer status);

    /**
     * 删除订单
     *
     * @param orderNo
     * @return
     */
    Integer deleteOrder(String orderNo);

    /**
     * 查询订单
     */
    List<UserCourseOrder> findOrderAll(String userId);
}
