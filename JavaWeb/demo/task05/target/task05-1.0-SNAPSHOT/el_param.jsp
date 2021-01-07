<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/26
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL 获取请求参数</title>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
%>
<%--jsp原始方式获取请求参数值--%>
<%= request.getParameter("name")%> <br>
<%= Arrays.toString(request.getParameterValues("hobby"))%> <br>

<%--EL表达式的方式获取请求参数值--%>
姓名是:${param.name} <br>
爱好是:${paramValues.hobby[0]}
</body>
</html>
