<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
Set:
<c:forEach items="${requestScope.set }" var="s" varStatus="idx">
	<h2>${idx.index+1 }---${s }</h2>
</c:forEach>
<br>
Map:
<c:forEach items="${requestScope.map }" var="m" varStatus="idx">
	<h2>${idx.index+1 }---key:${m.key }---value:${m.value }</h2>
</c:forEach>
</body>
</html>