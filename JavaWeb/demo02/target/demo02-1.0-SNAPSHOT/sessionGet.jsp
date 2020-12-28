<%@ page import="com.jakeprim.demo02.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/12/28
  Time: 4:01 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取session中的数据</title>
</head>
<body>
<%
    Student student = (Student) session.getAttribute("student");
    System.out.println("活化操作获取session中的数据:"+student);
%>
</body>
</html>
