<%@ page import="com.jakeprim.task05.Person" %><%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/26
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL表达式 Bean对象</title>
</head>
<body>
<%--jsp的方式--%>
<%
    Person person = new Person("张飞", 28);
    pageContext.setAttribute("person", person);
    request.setAttribute("prop", "age");
%>
姓名：<%=person.getName()%> <br>
年龄：<%=person.getAge()%> <br>

<%--EL表达式的方式--%>
姓名：${person.name} <br>
年龄： ${person.age} <br>

${person["name"]}
<%--动态取值--%>
动态取值:${person[prop]}
</body>
</html>
