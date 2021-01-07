package reflect_demo;

/**
 * 获取类信息
 */
public class ClassTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //1. 使用数据类型.class方式，可以获取对应类型的Class对象
        Class c = String.class;
        System.out.println("c1=" + c);//java.lang.String
        c = int.class;
        System.out.println("c1" + c);//int
        c = void.class;
        System.out.println("c1=" + c);//void
        //基本数据类型不能调用getClass(),只有引用对象才可以调用方法
        //2. 使用对象.getClass()方式获取对应的class对象
        String str = new String("hello");
        Class<? extends String> aClass = str.getClass();
        System.out.println("aClass=" + aClass);//java.lang.String

        //3. 使用包装类.TYPE的方式来获取对应基本数据类型的Class对象
        System.out.println("----------------------------------------");
        c = Integer.TYPE;//基本数据类型的对象
        System.out.println("c=" + c);//int
        c = Integer.class;
        System.out.println("c=" + c);//java.lang.Integer

        //4. 调用Class类中的forName方法来获取对应的Class对象，只能获取引用类型不能获取基本类型
        System.out.println("----------------------------------------");
        c = Class.forName("java.lang.String");//需要传递完整的包名.类名
//        c = Class.forName("String");//❌ 错误的
        System.out.println("c=" + c);//java.lang.String

        c = Class.forName("java.util.Date");
        System.out.println("c=" + c);//java.util.Date

//        c = Class.forName("int");//❌ 不能获取基本数据类型的Class对象
//        System.out.println("c="+c);

        //5. 使用类加载器的方式来获取Class对象
        ClassLoader classLoader = ClassTest.class.getClassLoader();
        System.out.println("classLoader:" + classLoader);//null
        c = classLoader.loadClass("java.lang.Integer");//NullPointerException
        System.out.println("c=" + c);

        // newInstance创建该Class对象所表示类的实例

    }
}
