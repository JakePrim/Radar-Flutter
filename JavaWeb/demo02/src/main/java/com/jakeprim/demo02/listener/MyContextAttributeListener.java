package com.jakeprim.demo02.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

public class MyContextAttributeListener implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("ServletContext增加属性:"+event.getName());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("ServletContext修改属性:"+event.getName());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("ServletContext删除属性:"+event.getName());
    }
}
