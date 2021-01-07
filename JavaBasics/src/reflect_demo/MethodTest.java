package reflect_demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射Method
 * initComment1482
 * setBaseLine:initComment1403:
 */
public class MethodTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取相关的对象类
        Class<?> c = Class.forName("reflect_demo.Person");
        //获取对象
        Constructor<?> constructor = c.getConstructor(String.class, int.class);
        Object instance = constructor.newInstance("prim", 28);
        //获取方法
        Method setName = c.getMethod("setName", String.class);
        //调用方法
        Object su = setName.invoke(instance, "su");
        System.out.println(su);//null 表示没有返回值
        System.out.println(instance);//{name='su', age=28}

        Method getName = c.getMethod("getName");
        Object name = getName.invoke(instance);
        System.out.println("name:" + name);//su
    }
}
