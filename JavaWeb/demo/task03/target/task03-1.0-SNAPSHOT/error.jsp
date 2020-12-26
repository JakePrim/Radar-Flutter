<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/23
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>错误页面</h1>
<%
    if (exception != null) {
        out.println("异常错误信息:" + exception.getMessage());
    }
%>
</body>
</html>
