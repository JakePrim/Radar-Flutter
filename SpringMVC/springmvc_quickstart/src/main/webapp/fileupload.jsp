<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2021/1/19
  Time: 10:58 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/fileuploads" method="post" enctype="multipart/form-data">
    名称： <input type="text" name="username"> <br>
    文件1：
    <input type="file" name="filePic" value="上传文件"> <br>
    文件1：
    <input type="file" name="filePic" value="上传文件"> <br>
    <input type="submit" value="单文件上传">
</form>
</body>
</html>
