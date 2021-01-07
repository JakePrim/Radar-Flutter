package com.jakeprom.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

public class StaticDataListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 初始化静态数据
		//模拟查询数据库数据
		List<Channel> channels = new ArrayList<Channel>();
		channels.add(new Channel("免费课程", "http://www.baidu.com"));
		channels.add(new Channel("实战课程", "http://www.baidu.com"));
		channels.add(new Channel("就业课程", "http://www.baidu.com"));
		//直接提取数据
		sce.getServletContext().setAttribute("channels", channels);
	}

}
