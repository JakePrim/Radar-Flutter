<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>分数:${requestScope.score }</h1>
	<c:if test="${requestScope.score >= 60 }">
		<h1 style="color:green;">通过测试</h1>
	</c:if>
	<c:if test="${requestScope.score < 60 }">
		<h1 style="color:red;">未通过测试</h1>
	</c:if>
	
	<!-- choose when otherwise -->
	${requestScope.grade }
	<c:choose>
		<c:when test="${grade == 'A' }">
			<h2>优秀</h2>
		</c:when>
		<c:when test="${grade == 'B' }">
			<h2>不错呦</h2>
		</c:when>
		<c:when test="${grade == 'C' }">
			<h2>水平一般</h2>
		</c:when>
		<c:when test="${grade == 'D' }">
			<h2>需要努力</h2>
		</c:when>
		<c:otherwise>
			<h2>加油吧!</h2>
		</c:otherwise>
	</c:choose>
</body>
</html>