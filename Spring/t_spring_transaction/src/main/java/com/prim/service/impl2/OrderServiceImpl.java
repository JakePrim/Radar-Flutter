package com.prim.service.impl2;

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

    //不需要在自己开启事务和提交事务 只需要明确回滚即可
    @Autowired
    private TransactionTemplate transactionTemplate;


    public void addOrder(final Order order) {
        //设置业务规则
        order.setCreateTime(new Date());
        order.setStatus("待付款");
        //自动帮助提交
        transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
                //开启一个事务
                try {
                    //插入订单表
                    orderDao.insert(order);
                    Product product = productDao.findOne(order.getPid());
                    //修改库存数
                    product.setStock(product.getStock() - order.getNumber());
                    productDao.update(product);
                    //提交事务
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();//设置回滚
                }
                return null;
            }
        });
    }
}
