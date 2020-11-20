<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="">
		验证码:<input type="text" name="code"> <img alt="1" src="${pageContext.request.contextPath }/getckeckcode">
		<br>
		<input type="submit" value="提交">
	</form>
</body>
</html>