package com.jakeprom.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class WebListener implements ServletContextListener,HttpSessionListener,ServletRequestListener{

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		System.out.println("HttpServletRequest 已销毁");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest servletRequest = (HttpServletRequest)sre.getServletRequest();
		System.out.println("HttpServletRequest 已创建 URI:"+servletRequest.getRequestURI());
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContext 已销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("ServletContext 已初始化 ");
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Session 被创建,SessionId:"+se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("session 已销毁");
	}

}
