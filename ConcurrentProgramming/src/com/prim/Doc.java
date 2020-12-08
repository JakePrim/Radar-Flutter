package com.prim;

public class Doc {
    public static void main(String[] args) {
        /**
         * 1. 线程  线程本质 --- Thread --- JVM --- OS Linux
         * 线程的本质：
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
        thread.start();
        //Thread.start() --> JVM --> start0 --> xxx.c start0方法 --> 最终会调用pthread_create()方法「glibc提供」
        //创建一个线程
    }
}
