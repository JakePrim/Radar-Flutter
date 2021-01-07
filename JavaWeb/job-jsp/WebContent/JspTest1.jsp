<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<% 
   List<String> list = new ArrayList<>();
   list.add("JSP基础入门");
   list.add("Servlet视频详解");
   list.add("EL表达式初识");
   list.add("JSTL标签库初识");
%>

<% for(int i=0;i<list.size();i++){ %>
	<p>第 <%=i+1 %> 条:  <%=list.get(i) %></p>
<% } %>

<%@ include file="prime.jsp" %>    
<%! public void test(){

} 
%>