package com.jakeprim.mapper;

import com.jakeprim.domain.Orders;
import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.List;

public interface OrderMapper {
    /**
     * 一对一关联查询：查询所有订单，查询每个订单所属的用户信息
     */
    List<Orders> findOrdersWithUser();

    /**
     * 一对一嵌套查询
     *
     * @return
     */
    List<Orders> findOrdersWithUser2();

    List<Orders> findById(Integer uid);
}
