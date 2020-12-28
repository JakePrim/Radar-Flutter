<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/12/28
  Time: 2:58 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session.setAttribute("name", "zhangfei");
    session.setAttribute("name", "guanyu");
    session.removeAttribute("name");
%>
</body>
</html>
