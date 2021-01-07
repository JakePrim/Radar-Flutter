package design_patterns;

public abstract class AbstractCalculator {
    public abstract int calculate(int a, int b);

    public int splitExpression(String exp, String op) {
        String[] sArr = exp.split(op);
        return calculate(Integer.parseInt(sArr[0]), Integer.parseInt(sArr[1]));
    }
}
