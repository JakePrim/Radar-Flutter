package com.prim.mapper;

import com.prim.model.Product;

public interface ProductMapper {
    //查询商品 查询库存
    Product getProductById(Integer id);

    //更新库存
    int reduceStock(Integer id);
}
