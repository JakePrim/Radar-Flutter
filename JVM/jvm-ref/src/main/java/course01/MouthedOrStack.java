package course01;

/**
 * 虚拟机栈
 */
public class MouthedOrStack {
    public static void main(String[] args) {
        A();
    }

    private static void A() {
        B();
    }

    private static void B() {
        C();
    }

    private static void C() {

    }
}
