package genericity_demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GenericityTest {
    public static void main(String[] args) {
        List<String> lit1 = new ArrayList<>();
        lit1.add("one");
//        lit1.add(2); 错误只能添加String类型
        String s = lit1.get(0);
        System.out.println(s);

        //使用通配符 ? 表示任意类型
        List<?> l3 = new LinkedList<>();
        //不支持元素添加操作
//        l3.add(1);

        //? extends Person 表示只能是Person和Person的子类
        List<? extends Person> l4 = new LinkedList<>();
        //不支持元素的添加操作
//        l4.add(new SubPerson());
//        l4.add(new Person());
        Person person = l4.get(0);

        List<? super Person> l5 = new LinkedList<>();
        l5.add(new Person());
        l5.add(new SubPerson());
//        l5.add(new Object()); 错误


    }
}
