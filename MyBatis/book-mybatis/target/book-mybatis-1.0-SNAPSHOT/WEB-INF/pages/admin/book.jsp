<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="top.jsp" %>
<section class="banner">
    <div class="container">
        <div>
            <h1>图书</h1>
            <p>图书列表</p>
        </div>
    </div>
</section>
<section class="main">
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>名称</th>
                <th>分类</th>
                <th>创建时间</th>
                <th>最后修改时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="b">
                <tr>
                    <td>${b.name}</td>
                    <td>${b.category.name}</td>
                    <td>${b.createTime}</td>
                    <td>${b.updateTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<section class="page">
    <div class="container">
        <div id="fatie">
            <a href="${pageContext.request.contextPath}/admin/Book/toAdd.do?id=${cid}">
                <button>新建</button>
            </a>
        </div>
    </div>
</section>
<footer>
    copy@慕课网
</footer>
</body>
</html>
