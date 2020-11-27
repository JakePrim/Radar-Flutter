package thread_demo;

public class ThreadIdNameTest extends Thread{
    public ThreadIdNameTest(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("编号:"+getId()+" 名称:"+getName());
    }

    public static void main(String[] args) {
        ThreadIdNameTest tint = new ThreadIdNameTest("线程1");
        tint.start();
        System.out.println("当前线程:"+Thread.currentThread().getId());
    }
}
