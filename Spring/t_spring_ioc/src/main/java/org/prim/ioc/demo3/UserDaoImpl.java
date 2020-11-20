package org.prim.ioc.demo3;

import org.prim.ioc.UserService;

public class UserDaoImpl implements UserDao {
    @Override
    public void findAll() {
        System.out.println("findAll");
    }

    @Override
    public void save() {
        System.out.println("save");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }
}
