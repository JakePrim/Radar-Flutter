package com.jakeprom.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class RequestTotalListener implements ServletContextListener,ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest servletRequest = (HttpServletRequest)sre.getServletRequest();
		String uri = servletRequest.getRequestURI();
		//排除servlet
		if (uri.endsWith("/rt")) {
			return;
		}
		// 获取访问量
		// 10:02 10:04 10:05
		List<String> timeList = (List) sre.getServletContext().getAttribute("timeList");
		// 5 9 10
		List<Integer> valueList = (List) sre.getServletContext().getAttribute("valueList");
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String time=format.format(date);
		if (timeList.indexOf(time) == -1) {
			timeList.add(time);//list中不存在当前的时间则存储当前的时间 
			valueList.add(1);//当前的时间访问量 1 
			sre.getServletContext().setAttribute("timeList", timeList);
			sre.getServletContext().setAttribute("valueList", valueList);
		}else {//时间点已经存在 找到对应的value +1
			int index = timeList.indexOf(time);
			Integer value = valueList.get(index);
			if (value != null) {
				value = value + 1;
				valueList.set(index, value);
			}else {
				valueList.set(index, 1);
			}
			sre.getServletContext().setAttribute("valueList", valueList);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//存储时间点
		List timeList = new ArrayList();
		//存储流量值
		List valueList = new ArrayList();
		
		sce.getServletContext().setAttribute("timeList", timeList);
		sce.getServletContext().setAttribute("valueList", valueList);
		
	}

}
