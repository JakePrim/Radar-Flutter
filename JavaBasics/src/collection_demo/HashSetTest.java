package collection_demo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class HashSetTest {
    public static void main(String[] args) {
        Set<String> s1 = new HashSet<>();
//        Set<String> s1 = new LinkedHashSet<>();//[tow, three, one] 按添加顺序打印
        System.out.println("s1=" + s1);
        s1.add("tow");
        System.out.println(s1);//[tow]
        s1.add("three");
        s1.add("one");
        //HashSet不是按顺序添加的
        System.out.println(s1);//[one, tow, three]
        s1.add("one");//Set不允许有重复的数据
        System.out.println(s1);//[one, tow, three]
    }
}
