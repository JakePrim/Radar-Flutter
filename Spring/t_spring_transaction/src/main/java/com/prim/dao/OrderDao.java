package com.prim.dao;

import com.prim.pojo.Order;

import java.util.List;

/**
 * @author prim
 */
public interface OrderDao {
    void insert(Order order);

    void update(Order order);

    void delete(String id);

    Order findOne(String id);

    List<Order> findAll();
}
