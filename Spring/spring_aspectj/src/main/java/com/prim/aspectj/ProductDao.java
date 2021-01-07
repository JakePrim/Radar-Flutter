package com.prim.aspectj;

public class ProductDao {
    public void save() {
        System.out.println("ProductDao.save");
    }

    public String update() {
        System.out.println("ProductDao.update");
        return "hello";
    }

    public int delete() {
        System.out.println("ProductDao.delete");
        return -1;
    }

    public void findOne() {
        System.out.println("ProductDao.findOne");
//        int i = 1/0;
    }

    public void findAll() {
        System.out.println("ProductDao.findAll");
//        int i = 1/0;
    }
}
