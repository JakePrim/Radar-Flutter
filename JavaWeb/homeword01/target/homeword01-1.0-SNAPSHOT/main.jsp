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
            <h1>学生信息后台管理</h1>
            <p>学生信息管理</p>
            <span style="color:red;">${requestScope.error}</span>
        </div>
    </div>
</section>
<section class="main">
    <section class="page">
        <div class="container">
            <div id="fatie">
                <a href="addTransit.do">
                    <button>新建</button>
                </a>
            </div>
        </div>
    </section>
    <div class="container">
        <form class="form-horizontal" action="search.do" method="post">
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
                <th>所在班级</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${requestScope.page.items}">
                <tr id="tr1">
                    <td>${student.id}
                    </td>
                    <td>${student.name}
                    </td>
                    <td>${student.sex == 0 ? "女" : "男"}
                    </td>
                    <td>${student.brithday}
                    </td>
                    <td>${student.email}
                    </td>
                    <td>${student.desc}
                    </td>
                    <td>${student.sClass != null ? student.sClass.grade : ""}
                        - ${student.sClass != null ? student.sClass.name : ""}
                    </td>
                    <td>
                        <a href="updateTransit.do?id=${student.id}">修改</a>
                        <a href="delete.do?id=${student.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<nav aria-label="Page navigation example" style="text-align: center">
    <ul class="pagination justify-content-center">
        <li class="page-item ${requestScope.page.currentPage == 1 ? "disabled" : ""}"
            onclick="previous(${requestScope.page.currentPage},${requestScope.page.pageCount})">
            <a class="page-link" href="#" tabindex="-1">Previous</a>
        </li>
        <c:forEach begin="1" end="${requestScope.page.pageCount}" var="p">
            <li class="page-item ${requestScope.page.currentPage==p ? "active" : ""}" onclick="select(${p})">
                <a class="page-link" href="#">${p}</a>
            </li>
        </c:forEach>
        <li class="page-item ${(requestScope.page.currentPage == requestScope.page.pageCount) ? "disabled" : ""}"
            onclick="next(${requestScope.page.currentPage},${requestScope.page.pageCount})">
            <a class="page-link" href="#">Next</a>
        </li>
    </ul>
</nav>
<footer>
    copy@JakePrim
</footer>
<script>
    // for (let x of document.getElementsByClassName("page-item")) {
    //     x.onclick = function () {
    //         window.location = "main.do?page=" + x.innerText;
    //     }
    // }

    function select(page){
        window.location = "main.do?page=" + page
    }

    function previous(page,count) {
        if (page === 1){
            return;
        }
        window.location = "main.do?page=" + (page - 1)
    }

    function next(page,count) {
        if (page === count){
            return;
        }
        console.log("page:" + page)
        window.location = "main.do?page=" + (page + 1)
    }

</script>
</body>
</html>