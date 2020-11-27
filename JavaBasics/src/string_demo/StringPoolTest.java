package string_demo;

public class StringPoolTest {
    public static void main(String[] args) {
        //验证一下常量池的存在
        //到目前为止，只有String这个特殊类除了new的方式外还可以直接字符串赋值(包装类除外)
        String str1 = "abc";
        String str2 = "abc";
        System.out.println(str1 == str2);//比较地址 true就说明是同一个对象 从常量池中取出来使用

        String str = "hello";//创建1个对象 存放在常量池中
        String str3 = new String("hello");//两个对象：参数对象在常量池中 和 在堆区中

        String s = "hello";//常量池
        String s2 = "hello";//常量池
        String s3 = new String("hello");//堆区
        String s4 = new String("hello");//堆区
        System.out.println(s == s2);//true
        System.out.println(s.equals(s2));//true
        System.out.println(s3 == s4);//false
        System.out.println(s3.equals(s4));//true
        System.out.println(s2 == s4);//false
        System.out.println(s2.equals(s4));//true

        String s5 = "abcd";
        String s6 = "ab"+"cd";//常量优化机制
        System.out.println(s5==s6);//true 是同一个

        String s7 = "ab";
        String s8 = s7 + "cd";//
        System.out.println(s5==s8);//false
    }
}
