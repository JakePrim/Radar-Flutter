/**
 * Singleton类实现
 */
public class Singleton {
    //2. 声明本类类型的引用指向本类的引用
//    private static Singleton sin = new Singleton();
    private static Singleton sin = null;

    //1 私有化构造方法 外部无法new Singleton
    private Singleton(){}

    //3. 提供公有的get方法负责将对象返回出去
    public static Singleton getInstance(){
        if (sin == null){
            sin = new Singleton();
        }
        return sin;
    }
}
