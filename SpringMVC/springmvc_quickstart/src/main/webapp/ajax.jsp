<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2021/1/18
  Time: 10:47 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--ajax异步交互--%>
<button id="btn">ajax交互</button>
<script src="js/jquery-3.5.1.js"></script>
<script>
    $("#btn").click(function () {
        let url = '${pageContext.request.contextPath}/user/ajaxRequest'
        let data = '[{"id":1,"username":"张飞"},{"id":2,"username":"关羽"}]'
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                console.log(result)
            }
        })
    });
</script>
</body>
</html>
