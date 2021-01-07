<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/28
  Time: 8:10
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSTL函数标签的使用</title>
</head>
<body>
<%
    request.setAttribute("var","Hello World!");
%>
原始字符串为:${var} <br>
判断该字符串是否包含指定字符串的结果为: ${fn:contains(var,"Hello")}
转换为大写结果: ${fn:toUpperCase(var)}
</body>
</html>
