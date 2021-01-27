package com.prim.service.impl;

import com.prim.mapper.OrderMapper;
import com.prim.mapper.ProductMapper;
import com.prim.model.Order;
import com.prim.model.Product;
import com.prim.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Transactional
    @Override
    public void reduceStock(Integer id) {
        //获取库存 查询商品查询到商品则减少库存
        Product product = productMapper.getProductById(id);
        //商品存在，并且库存大于0
        if (product != null && product.getStock() > 0) {
            int i = productMapper.reduceStock(id);
            if (i == 1) {
                //减库存成功生成订单
                Order order = new Order(UUID.randomUUID().toString(), id, 1001);
                orderMapper.insertOrder(order);
            } else {
                //减库存失败
                throw new RuntimeException("减库存失败");
            }
        } else {
            throw new RuntimeException("商品已经抢光");
        }

    }
}
