package course02;

public class JVMStack {
    public int work(int x) throws InterruptedException {
        int z = (x + 5) * 10;//局部变量表
        Thread.sleep(Integer.MAX_VALUE);
        return z;
    }

    public static void main(String[] args) throws InterruptedException {
        JVMStack jvmStack = new JVMStack();
        jvmStack.work(10);//10 放入main栈帧操作数栈
    }
}
