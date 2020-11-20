package collection_demo;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionTest {
    public static void main(String[] args) {
        //add
        Collection c1 = new ArrayList();
        boolean one = c1.add("one");
        boolean add = c1.add(Integer.valueOf(2));
        c1.add(4);
        System.out.println("c1:" + c1);//[one, 2,4]
        //addAll
        Collection c2 = new ArrayList();
        c2.addAll(c1);//把c1 中的元素一个一个放到c2中
        System.out.println("c2=" + c2);//c2=[one, 2, 4]
        c2.add(c1);//把c1整体对象放到c2中
        System.out.println("c2=" + c2);//[one, 2, 4, [one, 2, 4]]

        //判断集合中是否包含参数指定的单个元素 Objects.equals(o,e)
        boolean one1 = c2.contains("one");
        boolean one2 = c2.contains("two");
        System.out.println("one1:" + one1 + " one2:" + one2);//one1:true one2:false

        //判读当前集合中是否包含参数指定集合的所有元素
        Collection c3 = new ArrayList();
        c3.add(2);
        System.out.println(c3);

        boolean containsAll = c1.containsAll(c3);
        System.out.println(containsAll);//true
        //判断集合c1中是否拥有集合c3整体为单位的元素
        boolean contains = c1.contains(c3);
        System.out.println("contains:" + contains);//false
        c3.add("two");
        //只有集合c3所有元素都在集合c1中出现才会返回true，否则返回false
        boolean containsAll1 = c1.containsAll(c3);
        System.out.println(containsAll1);//false


        //计算两个集合的交集 并保留到当前集合中
        System.out.println("c2:" + c2);//[one, 2, 4, [one, 2, 4]]
        System.out.println("c3:" + c3);//[2, two]
        //让集合自己和自己取交集 没有发生改变返回false
        boolean b = c2.retainAll(c2);
        System.out.println(b);//false
        //计算集合c2和集合c3的交集并保留到集合c2中 取代集合c2原有的数值
        b = c2.retainAll(c3);
        System.out.println(b);//true
        System.out.println(c2);//[2]
        System.out.println(c3);//[2, two]

        //集合中单个元素和所有元素的删除操作
        System.out.println(c1);//[one, 2, 4]
        boolean b1 = c1.remove("one");
        System.out.println(b1);//true
        c1.remove(1);//没有当前元素 返回false

        c1.remove(c3);//删除整体对象c3
        c1.removeAll(c3);//本质上就是一个一个元素进行删除
    }
}
