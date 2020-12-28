<%@ page import="com.homework.homeword01.pojo.Student" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改学生信息</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/add.css">
</head>
<body>
<%
    //获取学生信息
    Student student = (Student) request.getAttribute("student");
%>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="main">
                学生信息管理
            </a>
        </div>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1>Hello, 欢迎修改学生信息!</h1>
        <span style="color: red"><%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></span>
    </div>
    <div class="page-header">
        <h3><small>修改</small></h3>
    </div>
    <form class="form-horizontal" action="update" method="post">
        <div class="form-group">
            <label for="num" class="col-sm-2 control-label">学号 ：</label>
            <div class="col-sm-8">
                <input name="num" class="form-control" id="num" value="<%=student.getId()%>" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">姓名 ：</label>
            <div class="col-sm-8">
                <input name="name" class="form-control" id="name" value="<%=student.getName()%>">
            </div>
        </div>
        <div class="form-group">
            <label for="sex" class="col-sm-2 control-label">性别 ：</label>
            <select id="sex" name="sex" class="col-sm-2 form-control"
                    style="width: auto;margin-left: 15px">
                <option value="0" <%= student.getSex() == 0 ? "selected" : ""%>>女生</option>
                <option value="1" <%= student.getSex() == 1 ? "selected" : ""%>>男生</option>
            </select>
        </div>

        <div class="form-group">
            <label for="date" class="col-sm-2 control-label">出生日期 ：</label>
            <div class="col-sm-8">
                <input name="date" class="form-control" id="date" type="date" value="<%=student.getBrithday()%>">
            </div>
        </div>

        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">邮箱 ：</label>
            <input type="email" id="email" name="email" style="padding-left: 15px" value="<%=student.getEmail()%>">
        </div>

        <div class="form-group">
            <label for="desc" class="col-sm-2 control-label">备注 ：</label>
            <div class="col-sm-8">
                <input name="desc" class="form-control" id="desc" value="<%=student.getDesc()%>">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">保存</button>&nbsp;&nbsp;&nbsp;
            </div>
        </div>
    </form>
</div>
<footer class="text-center">
    copy@jakeprim
</footer>
</body>
</html>
