package reflect_demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Field类主要用于描述获取到的单个成员变量信息
 * 可以获取私有的成员变量
 */
public class FieldTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //1. 获取Class对象
        Class<?> c = Class.forName("reflect_demo.Person");
        //2. 根据class对象获取有参的构造方法
        Constructor<?> constructor = c.getConstructor(String.class, int.class);
        //3. 根据有参构造方法获取引用对象
        Object instance = constructor.newInstance("张飞", 30);
        //4. 根据对象获取对应的成员变量信息
        Field nameField = c.getDeclaredField("name");
        //设置为true 表示取消Java语言访问检查
        nameField.setAccessible(true);//如果获取的是私有变量/修改数值，必须要加上该方法设置为true
        //5. 使用Person类型的对象来获取成员变量的数值并打印
        Object value = nameField.get(instance);
        System.out.println("获取到的成员变量值:" + value);//张飞
        //6. 修改成员变量的数值，在打印
        nameField.set(instance, "关羽");
        System.out.println("获取到修改的成员变量值:" + nameField.get(instance));//关羽

        //7. 获取成员变量的信息
        System.out.println("-------------------");
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("获取到的访问修饰符:" + field.getModifiers());
            System.out.println("获取到的数据类型:" + field.getType());
            System.out.println("获取到的名称是:" + field.getName());
            System.out.println("-------------------");
        }
    }
}
