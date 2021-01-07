<%@ page import="com.example.task04.model.User" %><%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/24
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页面</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
%>
<h1>登录成功 欢迎<%= user.getUsername()%>使用</h1>

</body>
</html>
