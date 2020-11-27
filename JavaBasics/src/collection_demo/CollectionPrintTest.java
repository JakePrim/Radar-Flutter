package collection_demo;

import annotation_demo.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionPrintTest {
    public static void main(String[] args) {
        Collection c1 = new ArrayList();
        c1.add("one");
        c1.add(2);
        c1.add(new Person("张飞"));
        System.out.println(c1);

        Iterator iterator = c1.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        while (iterator.hasNext()) {
            Object next = iterator.next();
            stringBuilder.append(next).append(",").append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2).append("]");
        System.out.println(stringBuilder.toString());

        while (iterator.hasNext()) {
            Object next = iterator.next();
            if ("one".equals(next)) {
                iterator.remove();//迭代器的删除是没有问题的
                //不能使用下列的方式使用集合的remove 会导致并发异常 在迭代过程中不能对集合进行操作
//                c1.remove(next);
            }
        }
        for (Object o : c1) {
            System.out.println(o);
//            c1.remove(o);//同样迭代过程中 集合不能进行操作
        }
    }
}
