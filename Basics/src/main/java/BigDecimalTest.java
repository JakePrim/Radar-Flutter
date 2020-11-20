import javax.crypto.spec.PSource;
import java.math.BigDecimal;

public class BigDecimalTest {
    public static void main(String[] args) {
        //1. 构造BigDecimal类型的两个对象
        BigDecimal bd1 = new BigDecimal("5.2");
        BigDecimal bd2 = new BigDecimal("1.3");
        //2. 实现加减乘除运算
        System.out.println("加法运算:" + bd1.add(bd2));//6.5
        System.out.println("减法运算:" + bd1.subtract(bd2));//3.9
        System.out.println("乘法运算:" + bd1.multiply(bd2));//6.76
        System.out.println("除法运算:" + bd1.divide(bd2));//4

        System.out.println(0.1 + 0.2);//0.30000000000000004
        //3. 实现精确运算
        BigDecimal bd3 = new BigDecimal("0.1");
        BigDecimal bd4 = new BigDecimal("0.2");
        System.out.println(bd3.add(bd4));//0.3

        //4. 注意事项
        BigDecimal bd5 = new BigDecimal("2");
        BigDecimal bd6 = new BigDecimal("0.3");
        //BigDecimal.ROUND_HALF_UP 四舍五入  得到7
        System.out.println("除法运算:"+bd5.divide(bd6,BigDecimal.ROUND_HALF_UP));// 除不尽 java.lang.ArithmeticException
    }
}

