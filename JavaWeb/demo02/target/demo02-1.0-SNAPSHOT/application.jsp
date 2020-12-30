<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/12/28
  Time: 2:37 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请求中属性状态的变化</title>
</head>
<body>
<%
    application.setAttribute("name","zhangfei");
    application.setAttribute("name","guanyu");
    application.removeAttribute("name");
%>
</body>
</html>
