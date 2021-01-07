<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/24
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JavaBean组件中对象属性值的打印</title>
</head>
<body>
<%--从session中拿出来 赋值给student对象--%>
<jsp:useBean id="student" scope="session" class="com.jakeprim.task03.Student"/>
<%--获取名字为student对象中属性为id的数值--%>
经过参数赋值后获取到的参数是:
<jsp:getProperty name="student" property="id"/>
<br>
经过参数赋值后获取到的姓名是:
<jsp:getProperty name="student" property="name"/>
</body>
</html>
