package com.jakeprim.demo02.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class MyRequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("请求初始化 创建请求");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("请求销毁");
    }
}
