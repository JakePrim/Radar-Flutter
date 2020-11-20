<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
	<title>用户登录</title>
	<link href="style/common.css" type="text/css" rel="stylesheet">
  <link href="style/add.css" type="text/css" rel="stylesheet">
  <link rel="stylesheet" href="style/login.css">
	<script type="text/javascript">
		function changeImg(){
			var codeImg = document.getElementById("codeImg");
			codeImg.src="${pageContext.request.contextPath }/KaptchaServlet?time="+new Date().getTime();
		}
	</script>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="content">
  <div class="center">
    <div class="center-login">
      <div class="login-banner">
       <a href="#"><img src="image/login_banner.png" alt=""></a>
     </div>
     <div class="user-login">
       <div class="user-box">
         <div class="user-title">
           <p>用户登录</p>
           <p>${msg }</p>
         </div>
         <form id="loginForm" class="login-table" method="post" action="${pageContext.request.contextPath }/login">
          <div class="login-left">
            <label class="username">用户名</label>
            <input type="text" class="yhmiput" name="username">
          </div>
          <div class="login-left">
            <label class="username">密码&nbsp&nbsp&nbsp</label>
            <input type="password" class="yhmiput" name="password">
          </div>
		  <div class="login-left">
            <label class="username">验证码</label>
            <input type="text" class="codeiput" name="checkCode">
			<img id="codeImg" onclick="changeImg()" src="${pageContext.request.contextPath }/KaptchaServlet">
          </div>
        <div class="login-btn"><button>登录</button></div>
        </form>
      </div>
    </div>
  </div>
</div>
</div>
<div class="footer">
 <p><span>M-GALLARY</span> ©2017 POWERED BY IMOOC.INC</p>
</div>

</body>
</html>