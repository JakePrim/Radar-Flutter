package com.jakeprim.web.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.jakeprim.domain.User;

/**
 * Application Lifecycle Listener implementation class InitServletContextListener
 *
 */
@WebListener
public class InitServletContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent ev)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent ev)  {
    	System.out.println("项目初始化工作...");
        List<User> users =  new ArrayList<User>();
        //初始化存储用户信息的集合
        ev.getServletContext().setAttribute("users", users);
    }
	
}
