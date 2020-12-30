<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2020/12/26
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL实现集合的获取和打印</title>
</head>
<body>
<%
    List<String> list = new LinkedList<>();
    list.add("two");
    list.add("one");
    list.add("three");
    pageContext.setAttribute("list",list);

    //准备一个map集合
    Map<String,String> map = new HashMap<>();
    map.put("name","张飞");
    map.put("na.me","刘备");
    //注意需要将变量放置到内置对象中
    request.setAttribute("map",map);

    String str1 = null;
    String str2 = "";
    //EL要访问变量需要放到内置对象中 才能访问到
    request.setAttribute("str1",str1);
    request.setAttribute("str2",str2);
%>
<%--EL表达式获取集合的数据内容--%>
下标为0：${list[0]} <br>
下标为1：${list[1]} <br>
下标为2：${list[2]} <br>
<%--EL表达式map集合数据内容的获取--%>
整个map集合的元素有:${map} <br>
获取带有特殊字符key的数值为:${map["na.me"]} <br>
${map.name}

<br>
<%--验证运算符 返回布尔值判断表达式是否为空值，--%>
${empty str1} <br>
${empty str2} <br>
${empty list} <br>

</body>
</html>
