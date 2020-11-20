package com.prim.service.impl1;

import com.prim.dao.OrderDao;
import com.prim.dao.ProductDao;
import com.prim.pojo.Order;
import com.prim.pojo.Product;
import com.prim.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

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

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    public void addOrder(Order order) {
        //设置业务规则
        order.setCreateTime(new Date());
        order.setStatus("待付款");
        //开启一个事务
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            //插入订单表
            orderDao.insert(order);
            Product product = productDao.findOne(order.getPid());
            //修改库存数
            product.setStock(product.getStock() - order.getNumber());
            productDao.update(product);
            //提交事务
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            e.printStackTrace();
        }
    }
}
