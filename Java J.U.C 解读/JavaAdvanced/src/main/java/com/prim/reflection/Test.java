package com.prim.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        testBook();
    }

    private static void testGoods(){
        try {
            Class<?> aClass = Class.forName("com.prim.reflection.Goods");
            Constructor<?> constructor = aClass.getConstructor();
            Goods goods = (Goods) constructor.newInstance();
            goods.display();

            //调用有参的构造方法
            Constructor<?> constructor1 = aClass.getConstructor(String.class, String.class, String.class, Float.class);
            Goods goods1 = (Goods) constructor1.newInstance("1001", "iphone", "5G 手机", 20000.0f);
            System.out.println("args = " + goods1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testBook(){
        try {
            Class clz = Class.forName("com.prim.reflection.Book");
            Object instance = clz.newInstance();
            Field nameField = clz.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(instance,"Java编程思想");
            System.out.println(nameField.get(instance));

            Field priceField = clz.getField("price");
            priceField.set(instance,84.5f);
            System.out.println(priceField.get(instance));


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
