<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>实现简单的登录功能</title>
</head>
<body>

<form action="login" method="post">
    用户名: <input type="text" name="username"> <br>
    密码: <input type="password" name="password"> <br>
    <span style="color: red"><%= request.getAttribute("error") == null ? "" : request.getAttribute("error")%>
    </span> <br>
    <input type="submit" value="登录">
</form>
</body>
</html>