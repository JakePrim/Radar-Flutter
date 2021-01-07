<%@ page import="com.jakeprim.demo02.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/12/28
  Time: 3:48 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>session中钝化和活化操作</title>
</head>
<body>
<%
    Student student = new Student("zhangfei");
    //将数据放入到session中
    session.setAttribute("student", student);
%>
</body>
</html>
