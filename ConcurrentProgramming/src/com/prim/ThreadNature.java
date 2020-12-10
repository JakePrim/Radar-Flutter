package com.prim;

/**
 * Java线程的本质
 * 锁
 */
public class ThreadNature {
    public static void main(String[] args) {
        /**
         * 1. 线程  线程本质 --- Thread --- JVM --- OS Linux
         * 线程的本质：
         * 线程如何和jvm 交互？ jvm如何调用系统层面的？
         *
         * 模拟一个Java线程
         * run方法
         */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("java thread");
            }
        });
        //调用start方法 --> run方法
        //private native void start0();
        //进入就绪状态 为什么不会立即启动，因为通过OS的线程，CPU启动 创建线程，创建线程完毕之后通过JNI 调用Java的run方法
        thread.start();
        //调用的过程：
        //Thread.start() --> JVM --> 调用 start0 方法 --> xxx.c --> pthread_create() 创建线程 --> JNI --> run()
        //start0方法 --> 最终会调用 pthread_create()方法「glibc库提供的 linux的源码」去创建一个线程

        //总结
        // javaObject start() --> native : Thread.c :start0 --> JVM 实例化一个C++对象 JavaThread-->
        // OS pthread_create() 创建出来一个线程

    }
}
