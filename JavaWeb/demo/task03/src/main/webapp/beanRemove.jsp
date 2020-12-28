<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/24
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现JavaBean组件数据的删除</title>
</head>
<body>
<%
    //从session内置对象中删除名字为student的属性
    session.removeAttribute("student");
%>
</body>
</html>
