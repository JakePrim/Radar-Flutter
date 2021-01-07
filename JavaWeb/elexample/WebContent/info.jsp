<%@page import="com.jakeprim.el.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <%
    Student student = (Student)request.getAttribute("student");
    String grade = (String)request.getAttribute("grade");
 %>
 <h1>学生姓名:<%=student.getName()%> </h1>
 <h1>学生手机:<%=student.getMoblie() %> </h1>
 <h1>教师评级:<%=grade %> </h1>
</body>
</html>