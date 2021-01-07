<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/26
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL表达式从内置对象中获取数据</title>
</head>
<body>
<%
//    pageContext.setAttribute("name", "pageContext对象中的属性值");
    request.setAttribute("name2", "request对象中的属性值");
    session.setAttribute("name3", "session对象中的属性值");
    application.setAttribute("name4", "application对象中的属性值");

    request.setAttribute("name", "request对象中的属性值");
    session.setAttribute("name", "session对象中的属性值");
    application.setAttribute("name", "application对象中的属性值");
%>
<%--jsp原始数据打印--%>
<%="name的值:" + pageContext.getAttribute("name")%> <br>
<%="name2的值:" + request.getAttribute("name2")%> <br>
<%="name3的值:" + session.getAttribute("name3")%> <br>
<%="name4的值:" + application.getAttribute("name4")%> <br>

<%--使用EL表达式来获取--%>
name:${name} <br>
name2:${name2} <br>
name3: ${name3} <br>
name4: ${name4} <br>
<%-- EL 获取值的顺序pageContext -> request -> session ->application --%>
name:${name} <br>
</body>
</html>
