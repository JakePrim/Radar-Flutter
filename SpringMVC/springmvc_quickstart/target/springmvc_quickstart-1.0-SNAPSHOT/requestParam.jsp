<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2021/1/18
  Time: 6:07 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--pageContext.request.contextPath 获取当前的项目路径--%>
<a href="${pageContext.request.contextPath}/user/simpleParam?id=1&username='张三'">基本类型参数</a>

<a href="${pageContext.request.contextPath}/user/requestParam?pageNo=2">分页查询</a>


<%--对象类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/objectParam" method="post">
    编号：<input type="text" name="id"> <br>
    用户名：<input type="text" name="username"> <br>
    <input type="submit" value="对象传参">
</form>

<%--数组类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/arrayParam">
    <input type="checkbox" name="ids" value="1">1 <br>
    <input type="checkbox" name="ids" value="2">2 <br>
    <input type="checkbox" name="ids" value="3">3 <br>
    <input type="checkbox" name="ids" value="4">4 <br>
    <input type="checkbox" name="ids" value="5">5 <br>
    <input type="submit" value="复选框-数组传参">
</form>

<%--复杂类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/queryParam">
    关键字：<input type="text" name="keyword"> <br>
    User对象：
    <input type="text" name="user.id" placeholder="编号"> <br>
    <input type="text" name="user.username" placeholder="用户名"> <br>
    <%--  list集合  --%>
    List集合：
    <input type="text" name="userList[0].id" placeholder="集合[0]-编号"> <br>
    <input type="text" name="userList[0].username" placeholder="集合[0]-用户名"> <br>
    <input type="text" name="userList[1].id" placeholder="集合[1]-编号"> <br>
    <input type="text" name="userList[1].username" placeholder="集合[1]-用户名"> <br>
    Map集合：
    <input type="text" name="userMap['user1'].id" placeholder="Map-编号1"> <br>
    <input type="text" name="userMap['user1'].username" placeholder="Map-用户名1"> <br>
    <input type="submit" value="复杂类型-集合传参">
</form>

<%--自定义类型转换器 错误的产生 2012/2/2--%>
<form action="${pageContext.request.contextPath}/user/converterParam">
    生日： <input type="text" name="birthday">
    <input type="submit" value="自定义类型转换器">
</form>
</body>
</html>
