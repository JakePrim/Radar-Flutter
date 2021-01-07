package reflect_demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过Constructor 创建对象
 */
public class ConstructorTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> c = Class.forName("reflect_demo.Person");
//        c.newInstance(); 老版本的API创建对象的方式

        //新版的API推荐使用 创建对象的方式

        //1. 使用无参方式构造对象
        //获取Class对象类中的无参构造方法
        Constructor<?> constructor = c.getConstructor();
        //使用获取到的无参构造方法来构造对应类型的对象
        System.out.println("获取到的无参构造对象是:" + constructor.newInstance());//Person{name='null', age=0}

        //2. 使用有参的方式构造对象
        //获取Class对象类中的有参构造方法，也就是Person的有参构造方法. 参数传递有参构造方法的参数类型
        Constructor<?> constructor1 = c.getConstructor(String.class, int.class);
        //构建对象，参数传递有参构造方法的参数值即可
        Object instance = constructor1.newInstance("张飞", 30);
        System.out.println("获取到的有参构造对象是:" + instance);//Person{name='张飞', age=30}

        //3. 获取目标类的所有公共构造方法并打印
        Constructor<?>[] constructors = c.getConstructors();
        for (Constructor<?> ct : constructors) {
            System.out.println("构造方法的访问修饰符是:" + ct.getModifiers());
            System.out.println("构造方法的方法名是:" + ct.getName());
            Class<?>[] parameterTypes = ct.getParameterTypes();
            System.out.println("构造方法的所有参数类型是:");
            for (Class<?> type : parameterTypes) {
                System.out.println("type=" + type);
            }
            System.out.println("--------------------------");
        }

    }
}
