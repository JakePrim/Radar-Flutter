package new_feature;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListPersonTest {
    public static void main(String[] args) {
        //准备list集合并放入Person类型的对象后打印
        List<Person> list = new LinkedList<>();
        list.add(new Person("张飞", 30));
        list.add(new Person("xiaoqiao", 17));
        list.add(new Person("zhouyu", 20));
        list.add(new Person("张飞", 30));
        list.add(new Person("guanyu", 35));
        list.add(new Person("liubei", 40));
        for (Person person : list) {
            System.out.println(person);
        }
        System.out.println("--------------------------------------");
        //将list集合中所有成年人过滤出来并放入另外一个集合中打印
        List<Person> list1 = new LinkedList<>();
        for (Person person : list) {
            if (person.getAge() >= 18) {
                list1.add(person);
            }
        }
        System.out.println(list1);

        System.out.println("--------------------------------------");
        //使用Stream接口实现上述功能 可读性更高
        list.stream().filter(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() >= 18;
            }
        }).forEach(new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println(person);
            }
        });
        System.out.println("--------------------------------------");
        // 使用lamdba表达式对上述代码优化
        list.stream().filter(person -> person.getAge() >= 18).forEach(System.out::println);

        System.out.println("--------------------------------------");
        // 实现对集合中元素通过流跳过2个元素后再取3个元素
        Stream<Person> limit = list.stream().skip(2).limit(3);
        limit.forEach(System.out::println);
        System.out.println("--------------------------------------");

        // 实现集合中所有元素中的年龄获取并打印
        list.stream().map(Person::getAge).forEach(System.out::println);

        System.out.println("--------------------------------------");
        // 实现集合中的排序 注意Person类要实现Comparable接口
        list.stream().sorted().forEach(System.out::println);

        System.out.println("--------------------------------------");
        // 判断集合中是否没有元素的年龄是大于45岁
        boolean noneMatch = list.stream().noneMatch(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() > 45;
            }
        });
        System.out.println(noneMatch ? "没有元素大于45" : "有元素大于45");

        System.out.println("--------------------------------------");
        // 按照指定的比较器规则获取集合所有元素中的最大值
        Optional<Person> max = list.stream().max(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        System.out.println("年龄排序后的最大值是:" + max);

        System.out.println("--------------------------------------");
        // 实现将集合中所有元素的年龄映射出来并进行累加
        Optional<Integer> reduce = list.stream().map(Person::getAge).reduce(Integer::sum);
        System.out.println("年龄的累加:" + reduce);
        System.out.println("--------------------------------------");
        // 实现集合中所有姓名映射出来并收集到集合中
        list.stream().map(Person::getName).collect(Collectors.toList()).forEach(System.out::println);
    }
}
