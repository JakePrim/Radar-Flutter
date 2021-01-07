package com.jakeprom.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class RequestTotalServlet
 */
@WebServlet("/rt")
public class RequestTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestTotalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//输出当前的统计结果
		ServletContext context = request.getServletContext();
		List<String> timeList = (List)context.getAttribute("timeList");
		List<Integer> valueList = (List)context.getAttribute("valueList");
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("timeList", timeList);
		resultMap.put("valueList", valueList);
		//将统计的信息转换称json
		String json = JSON.toJSONString(resultMap);
		//响应返回json
		response.getWriter().println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
