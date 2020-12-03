package new_feature;

import com.sun.tools.javac.util.List;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceTest {
    public static void main(String[] args) {
        //1. 匿名内部类的语法格式:父类/接口类型 引用变量名 = new 父类/接口类型(){方法重写;}
        Runnable runnable = () -> System.out.println("我是既没有参数有没有返回值的方法");
        runnable.run();

        Consumer consumer = o -> System.out.println(o + "有参但没有返回值的方法");
        consumer.accept("尤其提示:");

        Supplier supplier = () -> "无参有返回值";
        System.out.println(supplier.get());

        Function function = o -> o;
        System.out.println(function.apply("有参有返回值的方法"));

        Comparator comparable = (o1, o2) -> 0;
        System.out.println(comparable.compare(10, 20));//0

        Predicate predicate = o -> false;
        System.out.println(predicate.test(10));//false

        List.of(12,3,2,4);
    }
}
