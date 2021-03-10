package com.prim.product.service;

import com.prim.common.pojo.Products;
import com.prim.product.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: springclouddemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 21:44
 * @PackageName: com.prim.product.service
 * @ClassName: ProductServiceImpl.java
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Products queryById(Integer id) {
        Products products = productMapper.selectById(id);
        return products;
    }
}
