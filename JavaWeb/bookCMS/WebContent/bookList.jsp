<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>图书后台管理</title>
<link rel="stylesheet" href="css/index.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	function searchBook() {
		var searchContent = $('#searchContent').val();//获取搜索内容
		$.ajax({
			url : "searchbook.do",
			data: {"searchContent":searchContent},
			type: "POST",
			dataType : "json",
			success : function(json) {
				console.log(json);
				//更新数据
				var content = "";

				for (var i = 0; i < json.length; i++) {

				content = content + "<tr id='tr1'><td>" + (i+1)

				+ "</td><td>" + json[i].bookId

				+ "</td><td>" + json[i].bookName

				+ "</td><td>" + json[i].category.categoryName

				+ "</td><td>" + json[i].price

				+ "</td><td>" + "<img src='"+json[i].path+"'>"

				+ "</td><td><a href='updateBook.jsp?bookId="+json[i].bookId+"'>"+"修改"+"</a>"

				+ "<a href='${pageContext.request.contextPath }/deleteBook?bookId="+json[i].bookId+"' style='margin-left:5px;'>"+"删除"+"</a>"

				+ "</td></tr>"; 

				}
				$("#cont").html(content);//设置cont也就是<tbody>中的内容
			},
			error : function(error){
				alert("查询不到该分类");
			}
		});
	}
</script>
</head>
<body>
	<header>
		<div class="container">
			<nav>
				<a href="bookList.jsp">图书信息管理</a>
			</nav>
			<nav>
				<a href="categoryList.jsp">分类管理</a>
			</nav>

		</div>
	</header>
	<section class="banner">
		<div class="container">
			<div>
				<h1>图书管理系统</h1>
				<p>图书信息管理</p>
			</div>
		</div>
	</section>
	<section class="main">
		<div class="container">
				<div class="form-group" style="float: right;">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary"
							onclick="searchBook()">查询</button>
						&nbsp;&nbsp;&nbsp;
					</div>
				</div>
				<div class="form-group" style="float: right; width: 300px;">
					<div class="col-sm-8">
						<input name="searchContent" class="form-control"
							id="searchContent" placeholder="输入要查询的分类" style="width: 250px">
					</div>
				</div>
		</div>
		<div class="container">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>图书编号</th>
						<th>图书名称</th>
						<th>分类</th>
						<th>价格</th>
						<th>图书封面</th>
						<th>操作</th>

					</tr>
				</thead>
				<tbody id="cont">
					<c:forEach items="${applicationScope.books }" var="b"
						varStatus="idx">
						<tr id="tr1">
							<td>${idx.index+1 }</td>
							<td>${b.bookId }</td>
							<td>${b.bookName }</td>
							<td>${b.category.categoryName }</td>
							<td>${b.price }</td>
							<td><img src="${b.path }"></td>
							<td><a href="updateBook.jsp?bookId=${b.bookId }">修改</a> <a
								href="${pageContext.request.contextPath }/deleteBook?bookId=${b.bookId }">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<section class="page">
		<div class="container">
			<div id="fatie">
				<a href="addBook.jsp"><button>新建</button></a>
			</div>
		</div>
	</section>
	<footer> copy@慕课网 </footer>
</body>
</html>