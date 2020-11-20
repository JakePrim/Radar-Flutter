package com.prim.service.impl;

import com.prim.dao.OrderDao;
import com.prim.dao.ProductDao;
import com.prim.pojo.Order;
import com.prim.pojo.Product;
import com.prim.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

/**
 * @author prim
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;


    public void addOrder(final Order order) {
        //设置业务规则
        order.setCreateTime(new Date());
        order.setStatus("待付款");
        //插入订单表
        orderDao.insert(order);
        Product product = productDao.findOne(order.getPid());
        //修改库存数
        product.setStock(product.getStock() - order.getNumber());
        productDao.update(product);
    }
}
