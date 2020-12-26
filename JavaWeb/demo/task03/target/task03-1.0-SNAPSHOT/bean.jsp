<%@ page import="com.jakeprim.task03.Student" %><%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/24
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JavaBean组件的使用</title>
</head>
<body>
<%--创建一个Student类型的对象由student引用变量 有效范围当前页面--%>
<jsp:useBean id="student" scope="page" class="com.jakeprim.task03.Student"/>
<%--表示将student对象中名字为id的属性值设置为1002--%>
<jsp:setProperty name="student" property="id" value="1002"/>
<jsp:setProperty name="student" property="name" value="guanyu"/>
<%
    //    Student student = new Student();
//    student.setId(1001);
//    student.setName("zhangfei");
%>
<%--<%= student.getId()%>--%>
<%--<%= student.getName()%>--%>
<jsp:getProperty name="student" property="name"/>
<jsp:getProperty name="student" property="id"/>
</body>
</html>
