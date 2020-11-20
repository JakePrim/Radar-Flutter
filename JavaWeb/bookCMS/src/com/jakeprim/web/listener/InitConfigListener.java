package com.jakeprim.web.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.jakeprim.domain.User;

/**
 * Application Lifecycle Listener implementation class InitConfigListener
 *
 */
@WebListener
public class InitConfigListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitConfigListener() {
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
         List<User> userList = new ArrayList<User>();
         //初始化后台管理用户
         userList.add(new User("root", "root"));
         userList.add(new User("aaa", "111"));
         ev.getServletContext().setAttribute("userList", userList);
    }
	
}
