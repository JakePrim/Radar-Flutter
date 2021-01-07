package design_patterns;

public class AbstractCalculatorTest {
    public static void main(String[] args) {
        AbstractCalculator plus = new Plus();
        int i = plus.splitExpression("1+1", "\\+");
        System.out.println(i);
    }
}
