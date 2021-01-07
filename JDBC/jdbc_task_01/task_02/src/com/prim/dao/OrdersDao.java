package com.prim.dao;

import com.prim.entity.OrderItem;
import com.prim.entity.Orders;
import com.prim.entity.Product;
import com.prim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao {
    //1. 获取UID为001用户的订单的所有信息
    public List<Orders> findAllOrdersUid(String uid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from orders where uid=?";
        List<Orders> query = qr.query(sql, new BeanListHandler<>(Orders.class), uid);
        return query;
    }

    //2. 获取订单编号为order001订单中的所有商品信息
    public List<Product> findAllProductOid(String oid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT pid FROM orderitem WHERE oid=?";
        //获取订单项的数据
        List<OrderItem> orderItemList = qr.query(sql, new BeanListHandler<>(OrderItem.class), oid);
        List<Product> productList = new ArrayList<>();
        ProductDao productDao = new ProductDao();
        //查询商品的详细信息 添加到列表中
        for (OrderItem item : orderItemList) {
            Product product = productDao.findById(item.getPid());
            if (product != null) {
                productList.add(product);
            }
        }
        return productList;
    }
}
