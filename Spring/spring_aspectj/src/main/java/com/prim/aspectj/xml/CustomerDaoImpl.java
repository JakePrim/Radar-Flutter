package com.prim.aspectj.xml;

/**
 * @author prim
 */
public class CustomerDaoImpl implements CustomerDao {
    @Override
    public void save() {
        System.out.println("CustomerDaoImpl.save 保存客户");
    }

    @Override
    public String update() {
        System.out.println("CustomerDaoImpl.update 修改客户");
        return "update success";
    }

    @Override
    public void delete() {
        System.out.println("CustomerDaoImpl.delete 删除客户");
    }

    @Override
    public void findOne() {
        System.out.println("CustomerDaoImpl.findOne 查询一个客户");
    }

    @Override
    public void findAll() {
        System.out.println("CustomerDaoImpl.findAll 查询多个客户");
//        int i = 1 / 0;
    }
}
