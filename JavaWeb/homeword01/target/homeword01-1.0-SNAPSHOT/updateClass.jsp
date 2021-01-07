<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改班级</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/add.css">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="classmain.do">
                班级信息管理
            </a>
        </div>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1>Hello, 欢迎来修改班级信息!</h1>
        <span style="color:red;">${requestScope.error}</span>
    </div>
    <div class="page-header">
        <h3><small>修改</small></h3>
    </div>
    <form class="form-horizontal" action="updateclass.do" method="post">
        <div class="form-group">
            <label for="cid" class="col-sm-2 control-label">班级编号 ：</label>
            <div class="col-sm-8">
                <input name="cid" class="form-control" id="cid" value="${requestScope.sClass.id}" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">班级名称 ：</label>
            <div class="col-sm-8">
                <input name="name" class="form-control" id="name" value="${requestScope.sClass.name}">
            </div>
        </div>
        <div class="form-group">
            <label for="grade" class="col-sm-2 control-label">年级 ：</label>
            <div class="col-sm-8">
                <input name="grade" class="form-control" id="grade" value="${requestScope.sClass.grade}">
            </div>
        </div>

        <div class="form-group">
            <label for="teacher" class="col-sm-2 control-label">班主任 ：</label>
            <div class="col-sm-8">
                <input name="teacher" class="form-control" id="teacher" type="text" value="${requestScope.sClass.teacher}">
            </div>
        </div>

        <div class="form-group">
            <label for="slogan" class="col-sm-2 control-label">班级口号 ：</label>
            <textarea id="slogan" name="slogan" style="padding-left: 15px"> ${requestScope.sClass.slogan} </textarea>
        </div>

        <%--注意：班级人数应该随着 该班级学生的增加或减少 而改变人数 --%>
        <div class="form-group">
            <label for="num" class="col-sm-2 control-label">班级人数 ：</label>
            <div class="col-sm-8">
                <input name="num" class="form-control" id="num" value="${requestScope.sClass.num}" readonly>
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
