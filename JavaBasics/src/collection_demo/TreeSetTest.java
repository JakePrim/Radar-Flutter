package collection_demo;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest {
    public static void main(String[] args) {
        Set<String> ts = new TreeSet<>();
        ts.add("one");
        ts.add("two");
        ts.add("three");
        //没有按照添加顺序 而是安装ASCII 采用红黑树实现 元素有大小次序
        System.out.println(ts);//[one, three, two]
        ts.add("one");
        System.out.println(ts);//[one, three, two]

        System.out.println("---------------------------------------");
//        Set<Student> s2 = new TreeSet<>();
        //使用比较器
        Set<Student> s2 = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                //o1新增加的对象  o2是已有的对象
                int compare = o1.getName().compareTo(o2.getName());
                //姓名相同比较年龄
                if (0 == compare) {
                    return o1.getAge() - o2.getAge();
                }
                return compare;
            }
        });
        s2.add(new Student("关羽", 35));
        s2.add(new Student("刘备", 40));
        s2.add(new Student("张飞", 30));
        System.out.println(s2);//collection_demo.Student cannot be cast to java.lang.Comparable
    }
}
