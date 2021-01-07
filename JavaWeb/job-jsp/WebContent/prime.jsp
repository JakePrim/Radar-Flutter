<%@page contentType="text/html; charset=UTF-8" %>
<%
   int x = -5;
   int y = 0;
   if(x < 0){
	   y = -1;
   }else if(x == 0){
	   y = 0;
   }else{
	   y = 1;
   }
%>
<div style="text-align: center;">
     <p>当x<0时,输出</p>
     <p>x=<%=x %></p>
     <p>y=<%=y %></p>
</div>