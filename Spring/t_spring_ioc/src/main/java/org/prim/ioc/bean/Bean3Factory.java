package org.prim.ioc.bean;

/**
 * @author prim
 * 实例工厂
 */
public class Bean3Factory {
    public Bean3Factory() {
        System.out.println("Bean3Factory");
    }

    public Bean3 createBean3() {
        System.out.println("Bean3Factory -> createBean3");
        return new Bean3();
    }
}
