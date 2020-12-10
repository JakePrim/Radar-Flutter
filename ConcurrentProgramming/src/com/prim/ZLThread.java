package com.prim;

/**
 * 模拟线程的启动
 * // 模拟创建线程的实现 ZLThread native start1 -> start1 直接调用系统的方法 -> OS pthread_create()
 */
public class ZLThread {
    static {
        System.loadLibrary("EnjoyThreadNative");
    }

    public native void start1();

    public static void main(String[] args) {
        ZLThread zlThread = new ZLThread();
        zlThread.start1();
    }
}
