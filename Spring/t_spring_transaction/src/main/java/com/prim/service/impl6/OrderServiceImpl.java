package com.prim.service.impl6;

import com.prim.dao.OrderDao;
import com.prim.dao.ProductDao;
import com.prim.pojo.Order;
import com.prim.pojo.Product;
import com.prim.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * rollbackFor 异常回滚
     * rollbackFor用于指定能够触发事务回滚的异常类型，可以指定多个，用逗号分隔。
     * rollbackFor默认值为UncheckedException，包括了RuntimeException和Error.
     * 当我们直接使用@Transactional不指定rollbackFor时，Exception及其子类都不会触发回滚
     *
     * @param order
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
