package thread_demo;

public class SubThreadTest {
    public static void main(String[] args) {
        SubThread1 subThread1 = new SubThread1();
        SubThread2 subThread2 = new SubThread2();

        subThread1.start();
        subThread2.start();

        System.out.println("主线程开始等待...");

        try {
            subThread1.join();
            subThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程等待结束");
    }
}
