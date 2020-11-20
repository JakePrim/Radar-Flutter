package course01;

public class OperandStack {
    public int test() {
        int a = 0;
        int b = 1;
        int z = (a + b) * 10;
        return z;
    }

    public static void main(String[] args) {
        OperandStack operandStack = new OperandStack();
        operandStack.test();
    }
}
