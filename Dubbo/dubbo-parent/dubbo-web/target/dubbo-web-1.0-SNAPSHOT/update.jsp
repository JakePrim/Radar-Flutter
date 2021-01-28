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
    <title>用户更新</title>
</head>
<body>
<p><a href="register.jsp">用户注册</a></p>
<p><a href="findUser.jsp">查找用户</a></p>
<p><a href="delete.jsp">删除用户</a></p>

<p>请先输入要更新用户的id :<input type="text" id="find_uid"></p>
<p><input type="button" value="查询用户" id="btn_find"></p>

<form>
    <p><input type="text" name="uid" id="uid" hidden></p>
    <p>username: <input id="username" name="username"></p>
    <p>password: <input id="password" name="password"></p>
    <p>phone: <input id="phone" name="phone"></p>
    <p><input type="button" id="btn" value="update"></p>
</form>
<script src="js/jquery-3.3.1.min.js"></script>
<script>
    $("#btn_find").on("click", function () {
        $.get("findById?id=" + $("#find_uid").val(), function (result) {
            console.log(result.uid)
            // 回显用户信息
            $("#uid").val(result.uid);
            $("#username").val(result.username);
            $("#password").val(result.password);
            $("#phone").val(result.phone);
        });
    })
    $("#btn").on("click", function () {
        $.post("update", $("form").serialize(), function (result) {
            alert(result)
        }, "json");
    });
</script>
</body>
</html>
