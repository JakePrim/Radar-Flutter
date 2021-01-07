package org.prim.ioc.impl;

import org.prim.ioc.UserService;

/**
 * @author prim
 */
public class UserServiceImpl implements UserService {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sayHelloWorld() {
        System.out.println("helloSpring:" + name);
    }
}
