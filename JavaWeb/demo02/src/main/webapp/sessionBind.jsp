<%@ page import="com.jakeprim.demo02.model.Person" %><%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/12/28
  Time: 3:16 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session中对象的绑定和解除</title>
</head>
<body>
<%
    Person person = new Person("zhangfei", 30);
    session.setAttribute("person", person);
    session.removeAttribute("person");
%>
</body>
</html>
