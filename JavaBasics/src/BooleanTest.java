public class BooleanTest {
    public static void main(String[] args) {
        //1. 在Java5之前采用方法进行装箱和拆箱
        //从boolean类型到Boolean类型装换
        Boolean b1 = Boolean.valueOf(true);//装箱
        //从Boolean类型转换为boolean类型
        boolean b = b1.booleanValue();//拆箱
        //2. java5开始支持自动装箱和拆箱
        Boolean bo2 = false;
        boolean b2 = bo2;

        System.out.println("b2=" + b2);

        //3. 实现从String类型到boolean类型的转换
        //该方法的执行原理只要参数数值不为true/TRUE,结果就是false
        boolean b3 = Boolean.parseBoolean("true");//true 、TRUE
        System.out.println("b3=" + b3);//true
    }
}
