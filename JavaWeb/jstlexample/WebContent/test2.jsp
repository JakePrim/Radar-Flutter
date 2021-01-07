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
	String day = "abc";
    request.setAttribute("day", day);
%>
<c:choose>
	<c:when test="${requestScope.day == 'MONDAY' }">
		<h1 style="color: blue;">星期一</h1>
	</c:when>
	<c:when test="${requestScope.day == 'TUESDAY' }">
		<h1 style="color: blue;">星期二</h1>
	</c:when>
	<c:when test="${requestScope.day == 'WEDNESDAY' }">
		<h1 style="color: blue;">星期三</h1>
	</c:when>
	<c:when test="${requestScope.day == 'THURSDAY' }">
		<h1 style="color: blue;">星期四</h1>
	</c:when>
	<c:when test="${requestScope.day == 'FRIDAY' }">
		<h1 style="color: blue;">星期五</h1>
	</c:when>
	<c:when test="${requestScope.day == 'SATURDAY' }">
		<h1 style="color: blue;">星期六</h1>
	</c:when>
	<c:when test="${requestScope.day == 'SUNDAY' }">
		<h1 style="color: blue;">星期日</h1>
	</c:when>
	<c:otherwise>
		<h1 style="color: red;">内容不对呦!</h1>
	</c:otherwise>
</c:choose>

</body>
</html>