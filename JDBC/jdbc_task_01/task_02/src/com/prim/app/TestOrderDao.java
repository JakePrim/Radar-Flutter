package com.prim.app;

import com.prim.dao.OrdersDao;
import com.prim.entity.Orders;
import com.prim.entity.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestOrderDao {
    OrdersDao ordersDao = new OrdersDao();

    @Test
    public void testFindUid() throws SQLException {
        List<Orders> allOrdersUid = ordersDao.findAllOrdersUid("001");
        for (Orders orders : allOrdersUid) {
            System.out.println(orders);
        }
    }

    @Test
    public void testFindAllProductOid() throws SQLException {
        List<Product> productList = ordersDao.findAllProductOid("order001");
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}
