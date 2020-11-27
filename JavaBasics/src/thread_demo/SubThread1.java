package thread_demo;

public class SubThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i+=2) {
            System.out.println("子线程:" + getName() + " 中：" + i);
        }
    }
}
