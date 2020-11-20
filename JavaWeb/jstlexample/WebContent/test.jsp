<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
	int x = 10;
    request.setAttribute("x", x);
%>
<c:if test="${requestScope.x > 0 && requestScope.x <= 10 }">
	<h1 style="color: green;">1-10之间的整数</h1>
</c:if>
<c:if test="${requestScope.x > 10 && requestScope.x <= 20 }">
	<h1 style="color: red;">11-20之间的整数</h1>
</c:if>

</body>
</html>