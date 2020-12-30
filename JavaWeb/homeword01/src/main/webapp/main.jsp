<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.homework.homeword01.pojo.Student" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生信息后台管理</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">

</head>

<body>
<header>
    <div class="container">
        <nav>
            <a href="main">学生信息后台管理</a>
        </nav>
    </div>
</header>
<section class="banner">
    <div class="container">
        <div>
            <h1>学生信息后台管理</h1>
            <p>学生信息管理</p>
            <span style="color:red;">${requestScope.error}</span>
        </div>
    </div>
</section>
<section class="main">
    <div class="container">
        <form class="form-horizontal" action="search" method="post">
            <div class="form-group" style="float: right;">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp;&nbsp;
                </div>
            </div>
            <div class="form-group" style="float: right;width: 300px;">
                <div class="col-sm-8">
                    <input name="searchContent" class="form-control" id="searchContent"
                           placeholder="输入要查询的学号" style="width: 250px">
                </div>
            </div>
        </form>
    </div>
    <div class="container">

        <table class="table table-striped">
            <thead>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>出生日期</th>
                <th>邮箱</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${requestScope.students}">
                <tr id="tr1">
                    <td>${student.getId()}
                    </td>
                    <td>${student.getName()}
                    </td>
                    <td>${student.getSex() == 0 ? "女" : "男"}
                    </td>
                    <td>${student.getBrithday()}
                    </td>
                    <td>${student.getEmail()}
                    </td>
                    <td>${student.getDesc()}
                    </td>
                    <td>
                        <a href="updateTransit?id=${student.getId()}">修改</a>
                        <a href="delete?id=${student.getId()}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<section class="page">
    <div class="container">
        <div id="fatie">
            <a href="addStudent.jsp">
                <button>新建</button>
            </a>
        </div>
    </div>
</section>
<footer>
    copy@JakePrim
</footer>
</body>
</html>