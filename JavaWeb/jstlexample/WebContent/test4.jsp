<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>公告展示</h1>
<c:forEach items="${requestScope.notices }" varStatus="idx" var="n">
	<h2>${n.id }---${n.name }</h2>
</c:forEach>
倒序输出:
<c:set var="startIndex" value="${fn:length(requestScope.notices)-1 }"></c:set>
<c:forEach items="${requestScope.notices }" varStatus="idx" var="n">
	<h2>${notices[startIndex-idx.index].id }----${notices[startIndex-idx.index].name }</h2>
</c:forEach>
</body>
</html>