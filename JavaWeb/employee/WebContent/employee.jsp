<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>员工列表</title>
<link href="css/bootstrap.css" type="text/css" rel="stylesheet"></link>

<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>

<style type="text/css">
.pagination {
	margin: 0px
}

.pagination>li>a, .pagination>li>span {
	margin: 0 5px;
	border: 1px solid #dddddd;
}

.glyphicon {
	margin-right: 3px;
}

.form-control[readonly] {
	cursor: pointer;
	background-color: white;
}

#dlgPhoto .modal-body {
	text-align: center;
}

.preview {
	max-width: 500px;
}
</style>
<script>
	$(function() {

		$("#btnAdd").click(function() {
			$('#dlgForm').modal()
		});
	})
</script>
</head>
<body>

	<div class="container">
		<div class="row">
			<h1 style="text-align: center">IMOOC员工信息表</h1>
			<div class="panel panel-default">
				<div class="clearfix panel-heading ">
					<div class="input-group" style="width: 500px;">
						<button class="btn btn-primary" id="btnAdd">
							<span class="glyphicon glyphicon-zoom-in"></span>新增
						</button>
					</div>
				</div>

				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>员工编号</th>
							<th>姓名</th>
							<th>部门</th>
							<th>职务</th>
							<th>工资</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${applicationScope.employees }" var="e"
							varStatus="idx">
							<tr>
								<td>${idx.index+1 }</td>
								<td>${e.empno }</td>
								<td>${e.ename }</td>
								<td>${e.department }</td>
								<td>${e.job }</td>
								<td style="color: red; font-weight: bold">￥<fmt:formatNumber value="${e.salary }" pattern="0,000.00"></fmt:formatNumber></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- 表单 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="dlgForm">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增员工</h4>
				</div>
				<div class="modal-body">
					<form action="/employee/create" method="post">
						<div class="form-group">
							<label for="empno">员工编号</label> <input type="text" name="empno"
								class="form-control" id="empno" placeholder="请输入员工编号">
						</div>
						<div class="form-group">
							<label for="ename">员工姓名</label> <input type="text" name="ename"
								class="form-control" id="ename" placeholder="请输入员工姓名">
						</div>
						<div class="form-group">
							<label>部门</label> <select id="dname" name="department"
								class="form-control">
								<option selected="selected">请选择部门</option>
								<option value="市场部">市场部</option>
								<option value="研发部">研发部</option>
								<option value="后勤部">后勤部</option>
							</select>
						</div>

						<div class="form-group">
							<label>职务</label> <input type="text" name="job"
								class="form-control" id="sal" placeholder="请输入职务">
						</div>

						<div class="form-group">
							<label for="sal">工资</label> <input type="text" name="salary"
								class="form-control" id="sal" placeholder="请输入工资">
						</div>

						<div class="form-group" style="text-align: center;">
							<button type="submit" class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


</body>
</html>