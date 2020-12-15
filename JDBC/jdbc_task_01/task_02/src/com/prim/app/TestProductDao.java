package com.prim.app;

import com.prim.dao.ProductDao;
import com.prim.entity.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestProductDao {
    ProductDao productDao = new ProductDao();

    @Test
    public void testFindProductById() throws SQLException {
        Product product = productDao.findById("1");
        System.out.println(product.getPname() + ":" + product.getPrice() + ":" + product.getCategory());
    }

    @Test
    public void testGetCount() throws SQLException {
        int count = productDao.getCount("3");
        System.out.println("分类id为3的商品个数:" + count);
    }

    @Test
    public void testFindProductCid() throws SQLException {
        List<Product> products = productDao.findAllProductByCid("2");
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
