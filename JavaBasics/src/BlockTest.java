public class BlockTest {
    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("构造块");
    }

    public BlockTest() {
        System.out.println("构造方法");
    }

    public static void main(String[] args) {
        BlockTest blockTest = new BlockTest();
        BlockTest blockTest1 = new BlockTest();
    }
}
