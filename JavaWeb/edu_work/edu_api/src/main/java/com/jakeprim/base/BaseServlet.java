package com.jakeprim.base;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class BaseServlet extends HttpServlet {
    /**
     * doGet 方法作为一个调度器使用，根据请求的功能不同调用对应的方法
     * 必须传递一个参数:methodName = 功能名
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //json 和 多部件上传 无法通过getParameter获取参数
//        String methodName = request.getParameter("methodName");
        String methodName = null;

        //1. 获取post请求的Content-Type类型
        String contentType = request.getHeader("Content-Type");

        //2. 判断传递的数据是不是json格式
        if ("application/json;charset=utf-8".equalsIgnoreCase(contentType)) {
            String json = getPostJSON(request);
            //转换json格式的字符串转换为map
            Map<String, Object> map = JSON.parseObject(json, Map.class);
            //从map集合中 获取methodName
            methodName = (String) map.get("methodName");

            //将获取到的数据 保存到request域对象中 在具体的servlet中获取
            request.setAttribute("params", map);
        } else {
            methodName = request.getParameter("methodName");
        }

        if (methodName != null) {
            try {
                //通过返回优化方法的调用
                Class c = this.getClass();
                //根据传入的方法名获取对应的方法对象
                Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                //执行方法
                method.invoke(this, request, response);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.out.println("请求的功能不存在");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * POST 格式为application/json 使用该方法进行读取
     */
    public String getPostJSON(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = null;
        try {
            //1. 从request中获取缓冲输入流对象 读取json数据
            reader = request.getReader();
            //2. 创建StringBuffer保存读取的数据
            sb = new StringBuilder();
            //3. 循环读取
            String line = null;
            while ((line = reader.readLine()) != null) {
                //将每次读取的数据追加到 StringBuffer中
                sb.append(line);
            }
            //4. 返回结果
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
