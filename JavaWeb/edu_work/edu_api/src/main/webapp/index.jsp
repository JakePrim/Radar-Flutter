<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2021/1/7
  Time: 10:29 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload"> <br>
    <input type="text" name="username"> <br>
    <input type="text" name="password"> <br>
    <input type="submit" value="提交">
</form>
</body>
</html>
