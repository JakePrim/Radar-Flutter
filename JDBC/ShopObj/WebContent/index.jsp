<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/common.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/js1.js"></script>
</head>
<body>
	<div class="header">
		<div class="logo">
			<img src="${pageContext.request.contextPath }/image/logo.png">
		</div>
		<div class="menu"   onclick="show_menu()" onmouseleave="show_menu1()">
			 <div class="menu_title" ><a href="###">内容分类</a></div>
			 <ul id="title">
			 	<c:forEach items="${categories }" var="c">
			 		<li>${c.cname}</li>
			 	</c:forEach>
			 </ul>
		</div>
		<div class="auth">
			<ul>
				<li><a href="#">登录</a></li>
				<li><a href="#">注册</a></li>
			</ul>
		</div>
	</div>
	<div class="content">
	  <div class="banner">
  		<img src="image/welcome.png" class="banner-img">
	  </div>
	  <div class="img-content">
		<ul>
		<c:forEach items="${pageBean.lists }" var="p">
			<li>
				<img src="${pageContext.request.contextPath }${p.path }" class="img-li">
				<div class="info">
					<h3>${p.pname }</h3>
					<p>
						${p.desc }
					</p>
					<div class="img-btn">
						<div class="price">￥${p.price }</div>
							<a href="#" class="cart">
						       <div class="btn">
							      <img src="image/cart.svg">
						       </div>
						    </a>
					</div>
				</div>
			</li>
		</c:forEach>
			
		</ul>
	  </div>
	  <div class="page-nav">
		<ul>
			<c:if test="${pageBean.page != 1 }">
				<li><a href="${pageContext.request.contextPath }/index?page=1">首页</a></li>
				<li><a href="${pageContext.request.contextPath }/index?page=${pageBean.page-1 }">上一页</a></li>
			</c:if>
			<c:forEach var="i" begin="1" end="${pageBean.totalPage }">
				<c:if test="${i==pageBean.page }">
					<li><span class="first-page">${i }</span></li>
				</c:if>
				<c:if test="${i!=pageBean.page }">
					<li><span><a href="${pageContext.request.contextPath }/index?page=${i}">${i }</a></span></li>
				</c:if>
			</c:forEach>
			<c:if test="${pageBean.page != pageBean.totalPage }">
				<li><a href="${pageContext.request.contextPath }/index?page=${pageBean.page+1 }">下一页</a></li>
				<li><a href="${pageContext.request.contextPath }/index?page=${pageBean.totalPage }">尾页</a></li>
			</c:if>
		</ul>
	  </div>
	</div>
	<div class="footer"></div>
</body>
</html>