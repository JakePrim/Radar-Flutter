<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/23
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP 页面转发的实现</title>
</head>
<body>
<jsp:forward page="target.jsp">
    <jsp:param name="name" value="zhangfei"/>
</jsp:forward>
</body>
</html>
