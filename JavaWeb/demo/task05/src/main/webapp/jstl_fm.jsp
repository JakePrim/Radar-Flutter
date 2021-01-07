<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/28
  Time: 8:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Date date = new Date();
    request.setAttribute("date",date);
%>
时间：${date}
<br>
<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
</body>
</html>
