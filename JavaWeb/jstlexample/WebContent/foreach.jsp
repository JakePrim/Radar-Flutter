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
<!-- 类似 for (Company c : list) {
			
		} 
		c 等价于 var = "c"
-->
<c:forEach items="${requestScope.companies }" var="c" varStatus="idx">
	<h2>${idx.index+1 } : ${c.name } - ${c.url }</h2>
	
</c:forEach>
</body>
</html>