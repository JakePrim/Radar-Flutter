package org.prim.ioc.bean;

/**
 * @author prim
 * 静态工厂
 */
public class Bean2Factory {
    public Bean2Factory() {
        System.out.println("Bean2Factory");
    }

    public static Bean2 createBean2() {
        System.out.println("Bean2Factory -> createBean2");
        return new Bean2();
    }
}
