<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h1>学生姓名:${sessionScope.student.name }</h1>
 <h1>学生手机:${sessionScope.student.moblie }</h1>
 <h1>讲师:${param.teacher }</h1>
 <h1>教师评级:${sessionScope.grade }</h1>
 <h1>Student:${sessionScope.student }</h1>
 ${3+5 }
 ${5>3 }
 
</body>
</html>