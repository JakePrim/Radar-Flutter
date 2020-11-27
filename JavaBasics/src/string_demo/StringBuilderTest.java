package string_demo;

public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        System.out.println(sb);
        System.out.println("容量:" + sb.capacity());//0
        System.out.println("长度:" + sb.length());//0
        System.out.println("-----------------------");
        //使用参数指定容量来构造对象
        StringBuilder sb2 = new StringBuilder(20);
        System.out.println(sb2);
        System.out.println("容量:" + sb2.capacity());//20
        System.out.println("长度:" + sb2.length());//0
        System.out.println("-----------------------");
        //根据参数指定的字符串内容
        StringBuilder sb3 = new StringBuilder("hello");
        System.out.println(sb3);
        System.out.println("容量:" + sb3.capacity());//16+5 = 21 初始容量
        System.out.println("长度:" + sb3.length());//5

        StringBuilder sb4 = sb3.insert(0, "abcd");
        sb4.insert(4,"1234");
        System.out.println("sb4="+sb4);
        sb4.insert(sb4.length(),"ABCD");
        System.out.println("sb4="+sb4);
        sb4.append("world");
        System.out.println("sb4="+sb4);
        //查看容量的变化 自动扩容机制. 当字符串的长度超过了字符串对象的初始容量时，该字符串对象会自动扩容
        //默认扩容算法是：
        System.out.println("sb3容量:" + sb3.capacity());//44
        System.out.println("sb3长度:" + sb3.length());//22
    }
}
