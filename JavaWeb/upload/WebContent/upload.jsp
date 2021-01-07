<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>文件上传</h1>
	<form action="${pageContext.request.contextPath }/UploadServlet" method="post" enctype="multipart/form-data">
		<input type="text" name="name">
		<br>
		<input type="file" value="浏览" name="file"> 
		<br>
		<input type="submit" value="提交">
	</form>
</body>
</html>