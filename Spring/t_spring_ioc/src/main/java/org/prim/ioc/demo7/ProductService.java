package org.prim.ioc.demo7;

import javax.annotation.Resource;

public class ProductService {

    @Resource(name = "categoryDao")
    private CategoryDao categoryDao;

    @Resource(name = "productDao")
    private ProductDao productDao;

    public void save() {
        System.out.println("ProductService -> save");
        productDao.save();
        categoryDao.save();
    }
}
