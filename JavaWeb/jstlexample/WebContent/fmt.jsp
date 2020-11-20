<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入核心库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 引入格式化标签库 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setAttribute("amt", 1987654.326);
	request.setAttribute("now", new Date());
	request.setAttribute("html", "<a href='index.html'>index</a>");
	request.setAttribute("nothing", null);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>${requestScope.now }</h2>
	<!-- pattern yyyy - 年 MM - 月 dd - 日 HH - 24小时 hh - 12小时 mm - 分钟 ss - 秒数 SSS - 毫秒 -->
	<fmt:formatDate value="${requestScope.now }"
		pattern="yyyy年MM月dd日HH时mm分ss秒SSS毫秒" />

	<h2>${requestScope.amt }</h2>
	¥
	<fmt:formatNumber value="${requestScope.amt }" pattern="0,000.00"></fmt:formatNumber>
	元
	
	<h2>null默认值:<c:out value="${requestScope.nothing }" default="无"></c:out></h2>
	
	<h2>${requestScope.html }</h2>
	
	<h2><c:out value="${requestScope.html }" escapeXml="true"></c:out></h2>
	

</body>
</html>