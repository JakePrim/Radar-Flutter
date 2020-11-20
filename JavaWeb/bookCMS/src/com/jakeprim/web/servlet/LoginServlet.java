package com.jakeprim.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.Constants;
import com.jakeprim.domain.User;
import com.jakeprim.service.UserService;
import com.jakeprim.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String verifyCode = request.getParameter("verifyCode");
		String code = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		System.out.println(verifyCode+":"+code);
		if (code == null || !code.equalsIgnoreCase(verifyCode)) {
			request.setAttribute("msg", "验证码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User(username, password);
		List<User> userList = (List<User>) request.getServletContext().getAttribute("userList");
		UserService userService = new UserServiceImpl();
		int index = userService.login(userList, user);
		if (index == 0) {
			request.setAttribute("msg", "用户名或密码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			request.getSession().setAttribute("loginUser", user);
			response.sendRedirect(request.getContextPath()+"/categoryList.jsp");
		}
	}

}
