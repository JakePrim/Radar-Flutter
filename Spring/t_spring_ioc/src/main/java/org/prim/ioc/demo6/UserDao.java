package org.prim.ioc.demo6;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao {
    public void save(){
        System.out.println("DAO保存用户....");
    }
}
