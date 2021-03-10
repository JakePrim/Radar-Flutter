package com.prim.product.service;

import com.prim.common.pojo.Products;

/**
 * @program: springclouddemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 21:43
 * @PackageName: com.prim.product.service
 * @ClassName: ProductService.java
 **/
public interface ProductService {
    /**
     * 通过商品id查询商品信息
     *
     * @param id
     * @return
     */
    Products queryById(Integer id);
}
