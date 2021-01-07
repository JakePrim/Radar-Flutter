<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 引入jstl表达式 -->       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/add" method="post">
	<p>课程添加</p>
	课程名:<input type="text" name="name"><br>
	所属方向:<input type="text" name="category"><br>
	课程描述:<input type="text" name="desp"><br>
	<input type="submit" value="添加">
</form>
</body>
</html>