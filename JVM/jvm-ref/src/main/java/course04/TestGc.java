package course04;

import java.util.LinkedList;
import java.util.List;

/**
 * VM参数：
 * -XX:+PrintGCDetails -XX:+UseSerialGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDetails -XX:+UseConcMakeSweepGC
 * -XX:+PrintGCDetails -XX:+UseG1GC
 */
public class TestGc {
    /*不停往list中填充数据*/
    //就使用不断的填充 堆 -- 触发GC
    public static class FillListThread extends Thread {
        List<Object> list = new LinkedList<>();

        @Override
        public void run() {
            try {
                while (true) {
                    if (list.size() * 512 / 1024 / 1024 >= 990) {
                        list.clear();
                        System.out.println("list is clear");
                    }
                    byte[] bl;
                    for (int i = 0; i < 100; i++) {
                        bl = new byte[512];
                        list.add(bl);
                    }
                    Thread.sleep(1);
                }

            } catch (Exception e) {
            }
        }
    }

    /*每100ms定时打印*/
    public static class TimerThread extends Thread {
        public final static long startTime = System.currentTimeMillis();

        @Override
        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(100); //0.1s
                }

            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        //填充对象线程和打印线程同时启动
//        FillListThread myThread = new FillListThread(); //造成GC，造成STW
//        TimerThread timerThread = new TimerThread(); //时间打印线程
//        myThread.start();
//        timerThread.start();

        String name1 = "张";
        String name2 = "三";

        String str = name1 + name2;
        String str2 = new String("张三").intern();
        String str3 = new String("张三").intern();
        System.out.println(str == str2);//false
        System.out.println(str2 == str3);//true
        System.out.println(str == str3);//false

        String s1 = "张三";
        String s2 = new String("张") + new String("三");
        String s3 = s2.intern();

        System.out.println(s1 == s2);//false
        System.out.println(s2 == s3);//false
        System.out.println(s1 == s3);//true
    }
}
