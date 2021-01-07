<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <% String value = (String)request.getAttribute("key"); %>
  <h1 style="color: blue;"><%=value %></h1>
</body>
</html>