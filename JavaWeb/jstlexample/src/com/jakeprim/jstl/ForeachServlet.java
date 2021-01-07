package com.jakeprim.jstl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;

/**
 * Servlet implementation class ForeachServlet
 */
@WebServlet("/foreach")
public class ForeachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForeachServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Set<String> set = new HashSet<String>();
		set.add("January");
		set.add("February");
		set.add("March");
		set.add("April");
		set.add("May");
		request.setAttribute("set", set);
		
		HashMap<String, String> map = new HashMap<>();
		map.put("May", "1-1");
		map.put("April", "1-2");
		map.put("March", "1-3");
		request.setAttribute("map", map);
		
		request.getRequestDispatcher("/test3.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
