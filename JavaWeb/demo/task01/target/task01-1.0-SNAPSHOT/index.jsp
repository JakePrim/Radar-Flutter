<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="test4">测试get请求</a>
<form action="test4" method="post">
  <input type="submit" value="提交">
</form>
</body>
</html>