package thread_demo;

public class SubThreadRunTest {
    public static void main(String[] args) {
        SubThreadRun subThreadRun = new SubThreadRun();
//        subThreadRun.run(); //调用run方法，本质上就是普通成员方法的调用
        //run没有启动线程，而start方法会启动线程，Java虚拟机会自动调用该线程类的run方法
        //start启动的线程和main方法的线程是两个线程
        subThreadRun.start();
    }
}
