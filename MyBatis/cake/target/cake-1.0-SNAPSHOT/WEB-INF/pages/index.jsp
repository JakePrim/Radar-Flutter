<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <!-- Custom Theme files -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content=""/>
    <script type="application/x-javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        } </script>
    <!-- //Custom Theme files -->
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
    <link href="css/style.css" type="text/css" rel="stylesheet" media="all">
    <!-- js -->
    <script src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
    <!-- //js -->
    <!-- cart -->
    <script src="js/simpleCart.min.js"></script>
    <!-- cart -->
</head>
<body>
<!--header-->
<div class="header">
    <div class="container">
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <h1 class="navbar-brand"><a href="${pageContext.request.contextPath}/index.do">IMOOC</a></h1>
            </div>
            <!--navbar-header-->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="${pageContext.request.contextPath}/index.do" class="active">首页</a></li>
                    <c:forEach items="${root.children}" var="c1">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">${c1.title}<b
                                    class="caret"></b></a>
                            <ul class="dropdown-menu multi-column columns-4">
                                <div class="row">
                                    <c:forEach items="${c1.children}" var="c2">
                                        <div class="col-sm-3">
                                            <h4>${c2.title}</h4>
                                            <ul class="multi-column-dropdown">
                                                <c:forEach items="${c2.children}" var="c3">
                                                    <li><a class="list"
                                                           href="${pageContext.request.contextPath}/list.do?cid=11101">${c3.title}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </c:forEach>
                                </div>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </nav>
        <div class="header-info">
            <div class="header-right search-box">
                <a href="#"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
                <div class="search">
                    <form class="navbar-form">
                        <input type="text" class="form-control">
                        <button type="submit" class="btn btn-default" aria-label="Left Align">
                            搜
                        </button>
                    </form>
                </div>
            </div>
            <div class="header-right login">
                <a href="#"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></a>
                <div id="loginBox">
                    <form id="loginForm">
                        <fieldset id="body">
                            <fieldset>
                                <label for="email">Email</label>
                                <input type="text" name="email" id="email">
                            </fieldset>
                            <fieldset>
                                <label for="password">Password</label>
                                <input type="password" name="password" id="password">
                            </fieldset>
                            <input type="submit" id="login" value="登录">
                            <label for="checkbox"><input type="checkbox" id="checkbox"> <i>记住</i></label>
                        </fieldset>
                        <p>新用户 ? <a class="sign" href="#">注册</a><span><a href="#">忘记密码?</a></span></p>
                    </form>
                </div>
            </div>
            <div class="header-right cart">
                <a href="#"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span></a>
                <div class="cart-box">
                    <h4><a href="#">
                        <span class="simpleCart_total"> $0.00 </span> (<span id="simpleCart_quantity"
                                                                             class="simpleCart_quantity"> 0 </span>)
                    </a></h4>
                    <p><a href="#" class="simpleCart_empty">空</a></p>
                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!--//header-->

<!--banner-->
<div class="banner">
    <div class="container">
        <h2 class="hdng">IMOOC <span>蛋糕</span></h2>
        <p>特别的日子，特别的你</p>
        <a href="${pageContext.request.contextPath}/detail.do?id=${cake.cake.id}">SHOP NOW</a>
        <div class="banner-text">
            <img src="${cake.cake.imagePath}" alt=""/>
        </div>
    </div>
</div>
<!--//banner-->

<!--gallery-->
<div class="gallery">
    <div class="container">
        <div class="gallery-grids">
            <c:forEach items="${list}" var="item" varStatus="sta">
            <c:if test="${sta.index == 0}"><div class="col-md-8 gallery-grid glry-one"></c:if>
            <c:if test="${sta.index == 1}"><div class="col-md-4 gallery-grid glry-two"></c:if>
                <c:if test="${sta.index > 1}"><div class="col-md-3 gallery-grid"></c:if>
                    <a href="#"><img src="${item.cake.imagePath}" class="img-responsive" alt=""/>
                        <div class="gallery-info">
                            <p><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> view</p>
                            <a class="shop" href="${pageContext.request.contextPath}/detail.do?id=${item.cake.id}">SHOP
                                NOW</a>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                    <div class="galy-info">
                        <p>${item.cake.title}</p>
                        <div class="galry">
                            <div class="prices">
                                <h5 class="item_price">$${item.cake.price}</h5>
                            </div>
                            <div class="detail_xing">
                                <c:forEach begin="1" end="5" var="sta">
                                    <c:if test="${sta <= item.cake.sweetness}">
                                        <span class="glyphicon glyphicon-star"></span>
                                    </c:if>
                                    <c:if test="${sta > item.cake.sweetness}">
                                        <span class="glyphicon glyphicon-star-empty"></span>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                </c:forEach>
        </div>
    </div>
</div>
            <!--//gallery-->

            <!--subscribe-->
            <div class="subscribe">
                <div class="container">
                    <h3>Newsletter</h3>
                    <form>
                        <input type="text" class="text" value="Email" onFocus="this.value = '';"
                               onBlur="if (this.value == '') {this.value = 'Email';}">
                        <input type="submit" value="Subscribe">
                    </form>
                </div>
            </div>
            <!--//subscribe-->
            <!--footer-->
            <div class="footer">
                <div class="container">
                    <div class="footer-grids">
                        <div class="col-md-2 footer-grid">
                            <h4>company</h4>
                            <ul>
                                <li><a href="#">Products</a></li>
                                <li><a href="#">Work Here</a></li>
                                <li><a href="#">Team</a></li>
                                <li><a href="#">Happenings</a></li>
                                <li><a href="#">Dealer Locator</a></li>
                            </ul>
                        </div>
                        <div class="col-md-2 footer-grid">
                            <h4>service</h4>
                            <ul>
                                <li><a href="#">Support</a></li>
                                <li><a href="#">FAQ</a></li>
                                <li><a href="#">Warranty</a></li>
                                <li><a href="#">Contact Us</a></li>
                                <li><a href="admin/login.html" target="_blank">Admin Login</a></li>
                            </ul>
                        </div>
                        <div class="col-md-3 footer-grid">
                            <h4>order & returns</h4>
                            <ul>
                                <li><a href="#">Order Status</a></li>
                                <li><a href="#">Shipping Policy</a></li>
                                <li><a href="#">Return Policy</a></li>
                                <li><a href="#">Digital Gift Card</a></li>
                            </ul>
                        </div>
                        <div class="col-md-2 footer-grid">
                            <h4>legal</h4>
                            <ul>
                                <li><a href="#">Privacy</a></li>
                                <li><a href="#">Terms and Conditions</a></li>
                                <li><a href="#">Social Responsibility</a></li>
                            </ul>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <!--//footer-->
            <div class="footer-bottom">
                <div class="container">
                    <p>Copyright © 2017 imooc.com All Rights Reserved | 京ICP备 13046642号-2</p>
                </div>
            </div>
</body>
</html>
