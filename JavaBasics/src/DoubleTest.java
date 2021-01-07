public class DoubleTest {
    public static void main(String[] args) {
        Double db1 = Double.valueOf(3.14);//装箱
        double d1 = db1.doubleValue();//拆箱

        //Java5 自动装箱和拆箱 double没有自动装箱池
        Double db2 = 3.14;
        double d2 = db2;


        double d3 = Double.parseDouble("13.14");
        System.out.println("d3=" + d3);

        //判断调用对象的数值是否是非数字
        System.out.println("db2对象的判断结果是:"+db2.isNaN());//false

        Double db3 = Double.valueOf(0/0.0);
        System.out.println("db2对象的判断结果是:"+db3.isNaN());//true

    }
}
