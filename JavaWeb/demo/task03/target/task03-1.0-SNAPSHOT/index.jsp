<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <%!
        int ia;//这是一个全局变量

        public void show() {
            System.out.println("这是全局方法");
        }

        public class MyClass {
            {
                //构造块
                System.out.println("这是一个全局类哦");
            }
        }
    %>
    <%
        int ib = 20;//这是一个局部变量
        for (int i = 0; i < 3; i++) {
            System.out.println("随便放入java程序代码");
        }
    %>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
现在的时间是: <%= new Date()%>

<br>
<%= ia + 1 %> <%-- 1 --%>
<%= ib %> <%-- 20 --%>
<%= "我来了！" %>
</body>
</html>