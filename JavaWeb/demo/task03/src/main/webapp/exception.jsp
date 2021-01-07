<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/23
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <title>exception内置对象</title>
</head>
<body>
<%
    int i = 10 / 0;
    System.out.println(i);
%>
</body>
</html>
