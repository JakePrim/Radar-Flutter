<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
	<title>用户注册</title>
	<link href="style/common.css" type="text/css" rel="stylesheet">
  <link href="style/add.css" type="text/css" rel="stylesheet">
  <link rel="stylesheet" href="style/login.css">
  <script type="text/javascript">
    //校验
  	function validate_from(){
  		var username = document.getElementById("username").value;
  		if (username == null || username == '') {
			alert("用户名不能为空");
			return false;
		}
  		var password = document.getElementById("password").value;
  		if (password == null || password == '') {
			alert("密码不能为空");
			return false;
		}
  		if (password.length < 6) {
			alert("密码不能小于6位");
			return false;
		}
  		var repassword = document.getElementById("repassword").value;
  		if (repassword == null || repassword == '') {
			alert("确认密码不能为空");
			return false;
		}
  		if (repassword != password) {
			alert("两次密码输入不一致");
			return false;
		}
  		return true;
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
           <p>用户注册</p>
         </div>
         <form id="regForm" class="login-table" enctype="multipart/form-data" action="${pageContext.request.contextPath }/regist" method="post" onsubmit="return validate_from()">
          <div class="login-left">
            <label class="username">用户名&nbsp&nbsp&nbsp&nbsp</label>
            <input type="text" id="username" class="yhmiput" name="username">
          </div>
          <div class="login-left">
            <label class="username">密码&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
            <input type="password" id="password" class="yhmiput" name="password">
          </div>
		  <div class="login-left">
            <label class="username">确认密码</label>
            <input type="password" id="repassword" class="yhmiput" name="repassword">
          </div>
		  <div class="login-left">
            <label class="username">上传头像</label>
            <input type="file" class="yhmiput" name="file">
          </div>
		  <%-- <div class="login-left">
            <label class="username">验证码&nbsp&nbsp&nbsp&nbsp</label>
            <input type="text" class="codeiput" name="checkCode">
			<img id="codeImg" onclick="changeImg()" src="${pageContext.request.contextPath }/CheckImgServlet">
          </div> --%>
        
        <div class="login-btn"><button>注册</button></div>
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