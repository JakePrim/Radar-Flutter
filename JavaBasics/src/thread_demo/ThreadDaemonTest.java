package thread_demo;

public class ThreadDaemonTest extends Thread {
    @Override
    public void run() {
        System.out.println(isDaemon() ? "该线程是守护线程" : "该线程不是守护线程");
        //当子线程不是守护线程时，虽然主线程先结束，但是子线程依然会继续执行，直到执行完毕
        //当子线程是守护线程时，当主线程结束后，则子线程随之结束
        for (int i = 0; i < 50; i++) {
            System.out.println("子线程中:" + i);
        }
    }

    public static void main(String[] args) {
        ThreadDaemonTest threadDaemonTest = new ThreadDaemonTest();
        //必须线程启动之前设置 子线程为守护线程
        threadDaemonTest.setDaemon(true);
        threadDaemonTest.start();
        for (int i = 0; i < 20; i++) {
            System.out.println("主线程中:" + i);
        }
    }
}
