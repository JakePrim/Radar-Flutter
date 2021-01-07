<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 引入jstl表达式 -->    
  <div class="header">
   <div class="logo f1">
    <img src="image/logo.png">
  </div>
    <div class="menu f1">
        <div class="menu-title"><a href="#">内容分类</a></div>
            <ul>
                <li><a href="#">现实主义</a></li>
                <li><a href="#">抽象主义</a></li>
            </ul>
        </div>
  <div class="auth fr">
    <ul>
    	<c:if test="${empty user }">
    		<li><a href="${pageContext.request.contextPath }/login.jsp">登录</a></li>
        	<li><a href="${pageContext.request.contextPath }/register.jsp">注册</a></li>
    	</c:if>
    	<c:if test="${not empty user }">
    		<li><img class="profile_item" alt="" src="${user.path }"> </li>
    	</c:if>
     	
   </ul>
 </div>
</div>