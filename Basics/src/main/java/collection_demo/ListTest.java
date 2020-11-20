package collection_demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        //当new默认构造方法并没有申请数组的内存空间
        List lt1 = new ArrayList();
        //当调用add方法时，元素会给数组申请长度为10的一维数组，扩容原理是：原始长度的1.5倍
        lt1.add("one");

        List lt2 = new LinkedList();
        lt2.add("two");
        lt2.add("three");
        System.out.println("lt2:" + lt2);
    }
}
