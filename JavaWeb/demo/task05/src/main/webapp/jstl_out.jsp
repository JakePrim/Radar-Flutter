<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/28
  Time: 7:42
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:out value="hello world"></c:out>
<%--set标签--%>
<c:set var="name" value="zhangfei" scope="page"></c:set>
<c:out value="${name}"></c:out>

<%--设置对象属性--%>
<jsp:useBean id="person" class="com.jakeprim.task05.Person" scope="page"></jsp:useBean>
<c:set property="name" value="guanyu" target="${person}"></c:set>
<c:set property="age" value="35" target="${person}"></c:set>
<c:out value="${person.name}"></c:out>
<c:out value="${person.age}"></c:out>

<c:set var="name1" value="liubei" scope="page"></c:set>
<c:remove var="name1" scope="page"></c:remove>

<%--条件判断标签--%>
<c:set var="age" value="20" scope="page"></c:set>
<c:out value="${age}"></c:out>
<c:if test="${age} >= 18">
    <c:out value="成年了"></c:out>
</c:if>
<%
    String[] sarr = {"11","22","33","44","55"};
    request.setAttribute("str",sarr);
%>
<c:forEach var="ts" items="${str}">
    <c:out value="${ts}"></c:out>
</c:forEach>
<br>
<%--跳跃性循环--%>
<c:forEach var="ts" items="${str}" step="2">
    <c:out value="${ts}"></c:out>
</c:forEach>
<br>
<%--指定起始和结束位置--%>
<c:forEach var="ts" items="${str}" begin="1" end="3">
    <c:out value="${ts}"></c:out>
</c:forEach>
</body>
</html>
