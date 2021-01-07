package object_demo;

public class StudentTest {
    public static void main(String[] args) {
        Student s1 = new Student(1, "小明");
        Student s2 = new Student(1, "夏利");
//        Student s2 = s1;//这时候s2和s1指向的是同一个内容地址 equals：true

        boolean equals = s1.equals(s2);//false 因为两个对象的地址不一样
        System.out.println(equals);//重写equals后，只要学号相同返回 true

        System.out.println(s1==s2);//false

        //先调用Object继承来的方法
        System.out.println(s1.hashCode() + "|" + s2.hashCode());//2018699554|1311053135 不相同
        //重写之后
        //32|32 相同
    }
}
