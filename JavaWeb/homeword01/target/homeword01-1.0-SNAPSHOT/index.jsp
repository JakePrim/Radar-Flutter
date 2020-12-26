<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%-- 判断是否登录 登录信息保存在session中30min --%>
<%
    if (session.getAttribute("login") != null) {
        //登录成功 进入首页
        response.sendRedirect("main.jsp");
    } else {
        //重定向到登录页面
        response.sendRedirect("login.jsp");
    }
%>
</body>
</html>