public class IntegerTest {
    public static void main(String[] args) {
        System.out.println("最大值：" + Integer.MAX_VALUE);
        System.out.println("最小值:" + Integer.MIN_VALUE);
        System.out.println("二进制的位数:" + Integer.SIZE);
        System.out.println("所占字节的个数：" + Integer.BYTES);
        System.out.println("对应int类型的Class实例是:" + Integer.TYPE);

        //从int类型转换为Integer类型
        Integer integer = Integer.valueOf(123);//装箱
        //将字符串转换为Integer类型
        Integer valueOf = Integer.valueOf("456");
        System.out.println(valueOf);
        //从Integer类型到int类型的转换
        int i = valueOf.intValue();//拆箱


        //从Java5开始增加了自动装箱和自动拆箱的机制
        Integer it5 = 100;//直接通过赋值运算符实现自动装箱
        int ib = it5;//直接通过赋值运算符实现自动拆箱6

//        Integer it6 = 128;
//        Integer it7 = 128;
//        Integer it8 = new Integer(128);
//        Integer it9 = new Integer(128);
//
//        System.out.println(it6 == it7);//比较地址 false
//        System.out.println(it6.equals(it7));//比较内容 true
//        System.out.println(it8 == it9);//false
//        System.out.println(it8.equals(it9));//true

        Integer it6 = 127;
        Integer it7 = 127;
        Integer it8 = new Integer(127);
        Integer it9 = new Integer(127);

        System.out.println(it6 == it7);//比较地址 true
        System.out.println(it6.equals(it7));//比较内容 true
        System.out.println(it8 == it9);//false
        System.out.println(it8.equals(it9));//true

        int ic = Integer.parseInt("123");
        System.out.println("字符串转换为整数的结果:" + ic);


    }
}
