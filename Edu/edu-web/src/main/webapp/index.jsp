<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2021/2/5
  Time: 2:47 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/login" method="get">
    <p><input type="text" name="phone" placeholder="手机号"></p>
    <p><input type="text" name="password" placeholder="密码"></p>
    <input type="submit" value="登录">
</form>
</body>
</html>
