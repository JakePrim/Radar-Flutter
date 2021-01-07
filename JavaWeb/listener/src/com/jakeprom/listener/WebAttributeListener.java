package com.jakeprom.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class WebAttributeListener
 *
 */
//@WebListener
public class WebAttributeListener implements ServletContextAttributeListener, HttpSessionAttributeListener, ServletRequestAttributeListener {

    /**
     * Default constructor. 
     */
    public WebAttributeListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
         System.out.println("ServletContext 新增属性:"+arg0.getName()+":"+arg0.getValue());
         
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("ServletContext 移除属性:"+arg0.getName()+":"+arg0.getValue());
    }
    
    /**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("ServletContext 更新属性:"+arg0.getName()+":"+arg0.getValue());
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent arg0)  { 
         // TODO Auto-generated method stub]
    	System.out.println("HttpServletRequest 移除属性:"+arg0.getName()+":"+arg0.getValue());
    }

	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent arg0)  { 
    	System.out.println("HttpServletRequest 新增属性:"+arg0.getName()+":"+arg0.getValue());
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("HttpServletRequest 更新属性:"+arg0.getName()+":"+arg0.getValue());
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
    	System.out.println("HttpSession 新增属性:"+arg0.getName()+":"+arg0.getValue());
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("HttpSession 移除属性:"+arg0.getName()+":"+arg0.getValue());
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("HttpSession 更新属性:"+arg0.getName()+":"+arg0.getValue());
    }

	
	
}
