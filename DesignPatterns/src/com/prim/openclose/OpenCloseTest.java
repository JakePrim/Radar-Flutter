package com.prim.openclose;

public class OpenCloseTest {
    public static void main(String[] args) {
//        ICourse course = new JavaCourse(1,"java",10000D);
        //对修改关闭 对扩展开放
        ICourse course = new JavaDiscountCourse(1,"java",10000D);
        JavaDiscountCourse discountCourse = (JavaDiscountCourse) course;
        //价格会经常变化 如何做呢？ 建一个打折的类 不影响原来的代码
        System.out.println("id:"+discountCourse.getId()
                +" name:"+discountCourse.getName()
                +" price原价:"+discountCourse.price()
                +" 折后价格:"+ discountCourse.getDiscountPrice());
        //id:1 name:java price:6000.0
    }
}
