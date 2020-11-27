package thread_demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadSleepTest extends Thread{

    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag){
            Date d1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(d1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadSleepTest sleepTest = new ThreadSleepTest();
        sleepTest.start();

        System.out.println("开始等待");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //注意线程停止 stop方法已经弃用了 使用interrupt
        sleepTest.setFlag(false);
    }
}
