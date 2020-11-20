package com.prim.dao;

import com.prim.pojo.Order;
import com.prim.pojo.Product;

import java.util.List;

/**
 * @author prim
 */
public interface ProductDao {
    void insert(Product order);

    void update(Product order);

    void delete(String id);

    Product findOne(String id);

    List<Product> findAll();
}
