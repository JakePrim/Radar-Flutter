<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/27
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查找用户</title>
</head>
<body>
<p><a href="register.jsp">用户注册</a></p>
<p><a href="delete.jsp">删除用户</a></p>
<p><a href="update.jsp">更新用户</a></p>
<form>
    <p>username: <input id="username" name="username"></p>
    <p><input type="button" id="btn" value="find"></p>
</form>
<script src="js/jquery-3.3.1.min.js"></script>
<script>
    $("#btn").on("click", function () {
        $.get("findByName?name=" + $("#username").val(), function (result) {
            alert(JSON.stringify(result))
        });
    });
</script>
</body>
</html>
