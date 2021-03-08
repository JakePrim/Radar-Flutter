package com.prim.product.controller;

import com.prim.common.pojo.Products;
import com.prim.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springclouddemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 21:45
 * @PackageName: com.prim.product.controller
 * @ClassName: ProductController.java
 **/
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/query/{id}")
    public Products query(@PathVariable Integer id) {
        Products products = productService.queryById(id);
        return products;
    }

}
