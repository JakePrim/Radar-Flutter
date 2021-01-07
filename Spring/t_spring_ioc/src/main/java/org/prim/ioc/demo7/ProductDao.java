package org.prim.ioc.demo7;

import org.springframework.stereotype.Repository;

public class ProductDao {
    public void save(){
        System.out.println("ProductDao.save");
    }
}
