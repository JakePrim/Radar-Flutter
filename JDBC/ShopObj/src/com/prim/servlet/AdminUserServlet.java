package com.prim.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.prim.domain.User;
import com.prim.service.UserService;
import com.prim.service.impl.UserServiceImpl;

/**
 * 后台管理系统用户登录功能
 */
@WebServlet("/user")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIN = "login";
	
	private static final String LOGOUT = "logout";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (LOGIN.equals(method)) {
			//处理登录逻辑
			_login(request,response);
		}else if (LOGOUT.equals(method)) {
			_logout(request,response);
		}
	}

	/**
	 * 后台管理系统登出的逻辑
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void _logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("退出登录");
		request.getSession().invalidate();//清除Session
		//重定向到登录页
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	/**
	 * 后台管理系统登录逻辑
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void _login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username:"+username+" password:"+password);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		//通过调用service来处理登录的逻辑
		UserService userService = new UserServiceImpl();
		User loginUser = userService.login(user);
		if (loginUser != null) {//登录成功
			System.out.println("登录成功");
			request.getSession().setAttribute("user",loginUser);
			response.sendRedirect(request.getContextPath()+"/admin/category?method=findAll");
		}else {//登录失败 跳回登录页
			System.out.println("登录失败");
			request.setAttribute("errorMsg", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
