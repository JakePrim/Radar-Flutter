<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="login">
    <div class="header">
        <h1>
            <a href="login">登录 学生管理系统</a>
        </h1>
        <button></button>
    </div>
    <span style="color: red"> ${requestScope.error} </span>
    <form action="login" method="post">
        <div class="name">
            用户名:<input type="text" id="name" name="username">
            <p></p>
        </div>
        <div class="pwd">
            密码：<input type="password" id="pwd" name="password">
            <p></p>
        </div>
        <div class="btn-red">
            <input type="submit" value="登录" id="login-btn">
        </div>
    </form>
</div>
</body>
</html>