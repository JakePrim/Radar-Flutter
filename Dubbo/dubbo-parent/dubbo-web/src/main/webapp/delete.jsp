<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/27
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除</title>
</head>
<body>
<p><a href="register.jsp">用户注册</a></p>
<p><a href="findUser.jsp">查找用户</a></p>
<p><a href="update.jsp">更新用户</a></p>
<form action="${pageContext.request.contextPath}/delete" method="get">
    <p>uid: <input id="uid" name="id"></p>
    <p><input type="submit" id="btn" value="delete"></p>
</form>
</body>
</html>
