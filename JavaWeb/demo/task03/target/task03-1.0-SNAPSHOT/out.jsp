<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/23
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>out内置对象的使用</title>
</head>
<body>
<%
//    out.println("<h1>out内置对象</h1>");
    String serverName = request.getServerName();
    System.out.println("获取服务器的名称："+serverName);
    System.out.println("获取端口号:"+request.getServerPort());
    //通过内置对象设置属性信息，也就是存储数据
    request.setAttribute("name","guanyu");
%>
<jsp:forward page="requestTarget.jsp"></jsp:forward>
</body>
</html>
