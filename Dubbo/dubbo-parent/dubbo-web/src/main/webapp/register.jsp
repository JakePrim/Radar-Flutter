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
    <title>用户注册</title>
</head>
<body>

<p><a href="findUser.jsp">查找用户</a></p>
<p><a href="delete.jsp">删除用户</a></p>
<p><a href="update.jsp">更新用户</a></p>

<form>
    <p>username: <input name="username"></p>
    <p>password: <input name="password"></p>
    <p>phone: <input name="phone"></p>
    <p><input type="button" id="btn" value="register"></p>
</form>
<script src="js/jquery-3.3.1.min.js"></script>
<script>
    $("#btn").on("click", function () {
        // $.ajax({
        //     type: 'POST',
        //     url: 'register',
        //     data: $("form").serialize(),
        //     contentType: 'application/json;charset=UTF-8',
        //     success: function (result) {
        //         console.log(result)
        //     }
        // })
        $.post("register", $("form").serialize(), function (result) {
            alert(result)
        }, "json");
    });
</script>
</body>
</html>
