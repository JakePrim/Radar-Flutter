package thread_demo;

public class ThreadJoinTest extends Thread{

    @Override
    public void run() {
        //模拟倒数10个数效果
        System.out.println("倒计时开始:");
        for (int i = 10; i > 0; i--) {
            System.out.println(""+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("新年快乐");
    }

    public static void main(String[] args) {
        ThreadJoinTest threadJoinTest = new ThreadJoinTest();
        threadJoinTest.start();

        //主线程开始等待
        System.out.println("主线程开始等待....");
        try {
            //主线程等待子线程终止
//            threadJoinTest.join();
            threadJoinTest.join(5000);//最多等待5s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("子线程运行结束");
    }
}
