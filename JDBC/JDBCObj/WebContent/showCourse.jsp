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
	<h1>添加成功</h1>
	<table>
		<tr>
			<td>课程名称</td><td>所属方向</td><td>课程描述</td><td>创建时间</td>
		</tr>
		<c:forEach items="${requestScope.courses }" var="course" varStatus="idx">
			<tr>
				<td>${course.name }</td>
				<td>${course.category }</td>
				<td>${course.desp }</td>
				<td>${course.createtime }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>