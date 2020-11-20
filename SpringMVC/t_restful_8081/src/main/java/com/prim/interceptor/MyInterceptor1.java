package com.prim.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author prim
 * 实现拦截器类
 */
public class MyInterceptor1 implements HandlerInterceptor {

    /**
     * preHandle - 前置执行处理
     *
     * @param request
     * @param response
     * @param handler
     * @return true 继续执行； false 立即结束 返回响应
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL() + "准备执行-1");
        return true;
    }

    /**
     * postHandle - 目标资源已被Spring MVC框架处理
     * 控制器的目标方法执行了return之后，马上执行postHandle
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(request.getRequestURL() + "-目标处理成功-1");
    }

    /**
     * afterCompletion - 响应文本已经产生
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(request.getRequestURL() + "-响应内容已产生-1");
    }
}
