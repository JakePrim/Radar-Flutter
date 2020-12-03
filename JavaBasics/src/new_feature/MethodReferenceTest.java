package new_feature;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceTest {
    public static void main(String[] args) {
        //使用匿名内部类的方式通过函数结构Function中的方法实现
        Function<String, Integer> function = new Function<String, Integer>() {

            @Override
            public Integer apply(String o) {
                return Integer.parseInt(o);
            }
        };
        //静态方法引用
        Function<String, Integer> function2 = Integer::parseInt;
        System.out.println(function2.apply("12345"));
        //非静态方法引用：一个参数对象作为调用对象来调用方法时，可以使用上述方式
        Comparator<Integer> comparator = Integer::compareTo;
        System.out.println(comparator.compare(10, 20));//-1
        //构造器引用
        Supplier<Person> supplier = Person::new;
        //等价于如下代码：
        Supplier<Person> supplier1 = new Supplier<Person>() {
            @Override
            public Person get() {
                return new Person();
            }
        };
        //有参方式创建
        BiFunction<String, Integer, Person> biFunction = new BiFunction<String, Integer, Person>() {
            @Override
            public Person apply(String s, Integer integer) {
                return new Person(s, integer);
            }
        };
        System.out.println(biFunction.apply("123", 20));
        //直接的写法
        BiFunction<String, Integer, Person> biFunction1 = Person::new;

        //数组的引用
        Function<Integer, Person[]> function1 = new Function<Integer, Person[]>() {
            @Override
            public Person[] apply(Integer integer) {
                return new Person[integer];
            }
        };
        //创建三个数组
        function1.apply(3);
        //简写方式
        Function<Integer, Person[]> function3 = Person[]::new;
        function3.apply(5);
    }
}
