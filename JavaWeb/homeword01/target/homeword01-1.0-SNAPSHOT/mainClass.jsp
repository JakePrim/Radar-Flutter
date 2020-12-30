<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.homework.homeword01.pojo.Student" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>班级信息后台管理</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">

</head>

<body>
<header>
    <div class="container">
        <nav>
            <a href="main.do">学生信息后台管理</a>
        </nav>
        <nav>
            <a href="classmain.do">班级信息后台管理</a>
        </nav>
    </div>
</header>
<section class="banner">
    <div class="container">
        <div>
            <h1>班级信息后台管理</h1>
            <p>班级信息管理</p>
            <span style="color:red;">${requestScope.error}</span>
        </div>
    </div>
</section>
<section class="main">
    <div class="container">
        <form class="form-horizontal" action="searchclass.do" method="post">
            <div style="float: right;">
                    <%--可根据编号、年级、班级名称、班主任查询--%>
                    <input type="radio" name="classType" id="cid" value="cid" checked>
                    <label for="cid">班级编号</label> <br>
                    <input type="radio" name="classType" id="name" value="name">
                    <label for="name">班级名称</label> <br>
                    <input type="radio" name="classType" id="grade" value="grade">
                    <label for="grade">年级</label> <br>
                        <input type="radio" name="classType" id="teacher" value="teacher">
                        <label for="teacher">班主任</label>
            </div>
            <div class="form-group" style="float: right;">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp;&nbsp;
                </div>
            </div>
            <div class="form-group" style="float: right;width: 300px;">
                <div class="col-sm-8">
                    <input name="searchContent" class="form-control" id="searchContent"
                           placeholder="输入要查询的信息" style="width: 250px">
                </div>
            </div>
        </form>
    </div>
    <div class="container">

        <table class="table table-striped">
            <thead>
            <tr>
                <th>编号</th>
                <th>班级名称</th>
                <th>年级</th>
                <th>班主任名称</th>
                <th>班级口号</th>
                <th>班级人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="classes" items="${requestScope.classes}">
                <tr id="tr1">
                    <td>${classes.id}
                    </td>
                    <td>${classes.name}
                    </td>
                    <td>${classes.grade}
                    </td>
                    <td>${classes.teacher}
                    </td>
                    <td>${classes.slogan}
                    </td>
                    <td>${classes.num}人
                    </td>
                    <td>
                        <a href="updateclassTransit.do?id=${classes.id}">修改</a>
                        <a href="deleteclass.do?id=${classes.id}">删除</a>
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
            <a href="addClass.jsp">
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