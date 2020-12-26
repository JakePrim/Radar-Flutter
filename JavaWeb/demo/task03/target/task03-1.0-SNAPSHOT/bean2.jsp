<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/24
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JavaBean组件接收参数</title>
</head>
<body>
<jsp:useBean id="student" class="com.jakeprim.task03.Student" scope="session"/>
<jsp:setProperty name="student" property="id" param="id1"/>
<jsp:setProperty name="student" property="name" param="name1"/>
</body>
</html>
