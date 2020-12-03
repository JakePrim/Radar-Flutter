package design_patterns;

public class Plus extends AbstractCalculator{
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
