<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2021/2/1
  Time: 9:26 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传图片</title>
</head>
<body>
<%--上传图片，文件与文字相比较起来输入内容较大必须使用post提交--%>
<%--上传文件和普通文本有区别，action接收参数也会区别对待，所以声明带文件提交的表单为"多部件表单"--%>
<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="fname">
    <br>
    <button>提交</button>
</form>
</body>
</html>
