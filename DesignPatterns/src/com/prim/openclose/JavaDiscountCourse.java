package com.prim.openclose;

/**
 * 打折类 继承JavaCourse
 */
public class JavaDiscountCourse extends JavaCourse {
    public JavaDiscountCourse(Integer id, String name, Double price) {
        super(id, name, price);
    }

//    @Override
//    public Double price() {
//        //但是这样操作会 丢失了原价
//        return super.price() * 0.6;
//    }

    /**
     * 打折后的价格
     * @return
     */
    public Double getDiscountPrice(){
        return super.price() * 0.6;
    }


}
