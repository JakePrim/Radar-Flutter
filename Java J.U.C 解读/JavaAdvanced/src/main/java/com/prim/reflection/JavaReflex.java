package com.prim.reflection;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * java 反射基础
 */
public class JavaReflex {
    public static void main(String[] args) {
        try {
//            testConstructor();
//            testField();
            testMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testMethod() throws Exception {
        Class<?> aClass = Class.forName("com.prim.reflection.Person");
        Object instance = aClass.newInstance();
        //获取公有方法
        Method eatMethod = aClass.getMethod("eat");
        eatMethod.invoke(instance);
        //获取私有方法
        Method runMethod = aClass.getDeclaredMethod("run");
        runMethod.setAccessible(true);
        runMethod.invoke(instance);
        //给方法传递参数
        Method runsMethod = aClass.getDeclaredMethod("runs", String.class);
        runsMethod.setAccessible(true);
        runsMethod.invoke(instance,"JakePrim");
    }

    private static void testClass() throws Exception {
        // 反射第一步先获得Class对象
        //通过以下三种方式获得某个class文件对应的Class对象
        //第一种方法
        Class<Person> personClass = Person.class;//字节码被加载到内存后得到到class对象
        //第二种方法
        Person person = new Person();
        Class<? extends Person> aClass = person.getClass();
        //第三种方法
        Class<?> aClass1 = Class.forName("com.prim.reflection.Person");
    }

    private static void testConstructor() throws Exception {
        Class<?> aClass1 = Class.forName("com.prim.reflection.Person");
        // Constructor 类到使用
        // 得到某个类的所有构造方法
        Constructor<?>[] constructors = aClass1.getConstructors();


        // 获得有参数的构造方法 传递参数类型
        Constructor<?> constructor = aClass1.getConstructor(String.class, String.class);
        //将具体的参数传入 调用构造方法
        Person person1 = (Person) constructor.newInstance("abx", "男");
        System.out.println(person1);

        // 获得无参数的构造方法
        Constructor<?> constructor1 = aClass1.getConstructor();
        Person person3 = (Person) constructor1.newInstance();
        person3.eat();

        // 通过Class类的newInstance()来调用类的默认构造方法
        Person person2 = (Person) aClass1.newInstance();
        System.out.println(person2);

        //获得私有构造方法
        Constructor<?> declaredConstructor = aClass1.getDeclaredConstructor(String.class);
        declaredConstructor.setAccessible(true);//如果是私有的方法一定要调用setAccessible 设置为true 设置可访问的权限
        Person person4 = (Person) declaredConstructor.newInstance("JakePrim");
        System.out.println("args = " + person4);
    }

    private static void testField() throws Exception {
        //获得公有的属性
        Class<?> aClass = Class.forName("com.prim.reflection.Person");
        Object o = aClass.newInstance();//获得对象
        Field nameField = aClass.getField("name");
        nameField.set(o, "test");//等价于 P p = new P(); p.name="test";
        Object name = nameField.get(o);
        System.out.println("name:" + name);

        //获得私有属性
        Field sexField = aClass.getDeclaredField("sex");
        //注意私有属性必须设置可访问权限
        sexField.setAccessible(true);
        sexField.set(o, "男");
        Object sex = sexField.get(o);
        System.out.println("sex:" + sex);
    }
}
