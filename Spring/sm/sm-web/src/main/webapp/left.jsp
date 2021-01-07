<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/3/18
  Time: 10:57 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <title>左边导航</title>
    <link rel="stylesheet" type="text/css" href="../css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="../css/common.css"/>
    <script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
    <!--框架高度设置-->
    <script type="text/javascript">
        $(function () {
            $('.sidenav li').click(function () {
                $(this).siblings('li').removeClass('now');
                $(this).addClass('now');
            });

            $('.erji li').click(function () {
                $(this).siblings('li').removeClass('now_li');
                $(this).addClass('now_li');
            });

            var main_h = $(window).height();
            $('.sidenav').css('height', main_h + 'px');
        })
    </script>
    <!--框架高度设置-->
</head>

<body>
<div id="left_ctn">
    <ul class="sidenav">
        <li class="now">
            <div class="nav_m">
                <span><a>人员管理</a></span>
                <i>&nbsp;</i>
            </div>
            <ul class="erji">
                <li>
                    <span><a href="${pageContext.request.contextPath}/staff/list.do" target="main">员工管理</a></span>
                </li>
                <li>
                    <span><a href="${pageContext.request.contextPath}/department/list.do" target="main">部门管理</a></span>
                </li>
            </ul>
        </li>
        <li>
            <div class="nav_m">
                <span><a>日志信息</a></span>
                <i>&nbsp;</i>
            </div>
            <ul class="erji">
                <li>
                    <span><a href="${pageContext.request.contextPath}/log/operationLog.do" target="main">操作日志</a></span>
                </li>
                <li>
                    <span><a href="${pageContext.request.contextPath}/log/loginLog.do" target="main">登录日志</a></span>
                </li>
                <li>
                    <span><a href="${pageContext.request.contextPath}/log/systemLog.do" target="main">系统日志</a></span>
                </li>
            </ul>
        </li>
        <li>
            <div class="nav_m">
                <span><a>个人中心</a></span>
                <i>&nbsp;</i>
            </div>
            <ul class="erji">
                <li>
                    <span><a href="${pageContext.request.contextPath}/info.do" target="main">个人信息</a></span>
                </li>
                <li>
                    <span><a href="${pageContext.request.contextPath}/toChangePassword.do" target="main">修改密码</a></span>
                </li>
            </ul>
        </li>
        <li>
            <div class="nav_m">
                <span><a href="${pageContext.request.contextPath}/logout.do" target="_top">退出系统</a></span>
            </div>
        </li>
    </ul>
</div>
</body>
</html>

