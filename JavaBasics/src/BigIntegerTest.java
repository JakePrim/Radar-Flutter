import java.math.BigInteger;

public class BigIntegerTest {
    public static void main(String[] args) {
        BigInteger bi1 = new BigInteger("20");
        BigInteger bi2 = new BigInteger("8");
        System.out.println("+:" + bi1.add(bi2));//28
        System.out.println("-:" + bi1.subtract(bi2));//12
        System.out.println("*:" + bi1.multiply(bi2));//160
        System.out.println("/:" + bi1.divide(bi2));//2
        System.out.println("%:" + bi1.remainder(bi2));//4
        //一次性得到商和余数
        BigInteger[] bigIntegers = bi1.divideAndRemainder(bi2);
        for (int i = 0; i < bigIntegers.length; i++) {
            System.out.println("i:" + bigIntegers[i]);//2 4
        }
    }
}
