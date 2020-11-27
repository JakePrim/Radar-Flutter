package thread_demo;

/**
 * 线程通信测试
 */
public class ThreadCommunicateTest implements Runnable {
    private int cnt = 1;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                //每当有一个线程进来后，先叫醒另一个线程，由于synchronized锁门了另一个线程不会给你强资源
                notify();
                if (cnt <= 100) {
                    System.out.println("线程" + Thread.currentThread().getName() + " 中:cnt=" + cnt);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cnt++;
                    //调用wait
                    try {
                        wait();//当前线程进入阻塞状态，自动释放synchronized锁，必须在synchronized锁中调用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {

                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadCommunicateTest tct = new ThreadCommunicateTest();
        Thread t1 = new Thread(tct);
        t1.start();
        Thread t2 = new Thread(tct);
        t2.start();
    }
}
