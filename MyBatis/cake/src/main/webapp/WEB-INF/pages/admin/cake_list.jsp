<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/3/6
  Time: 4:33 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="top.jsp" %>
<section id="content_wrapper">
    <section id="content" class="table-layout animated fadeIn">
        <div class="tray tray-center">
            <div class="content-header">
                <h2> 蛋糕管理</h2>
                <p class="lead"></p>
            </div>
            <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
                <div class="panel  heading-border">
                    <div class="panel-menu">
                        <div class="row">
                            <div class="hidden-xs hidden-sm col-md-3">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default light">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                    <button type="button" class="btn btn-default light">
                                        <i class="fa fa-plus"
                                           onclick="javascript:window.location.href='${pageContext.request.contextPath}/admin/Cake/toAdd.do';"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-9 text-right">
                                <form action="${pageContext.request.contextPath}/admin/Cake/list.do" method="post">
                                    共${pageInfo.total}条 ${pageInfo.pageNum}/${pageInfo.pages}
                                    <div class="btn-group">
                                        <c:if test="${pageInfo.pageNum > 1}">
                                            <button type="submit" name="pageNum" value="${pageInfo.pageNum-1}"
                                                    class="btn btn-default light">
                                                <i class="fa fa-chevron-left"></i>
                                            </button>
                                        </c:if>
                                        <c:if test="${pageInfo.pageNum < pageInfo.pages}">
                                            <button type="submit" name="pageNum" value="${pageInfo.pageNum+1}"
                                                    class="btn btn-default light">
                                                <i class="fa fa-chevron-right"></i>
                                            </button>
                                        </c:if>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body pn">
                        <table id="message-table" class="table admin-form theme-warning tc-checkbox-1">
                            <thead>
                            <tr class="">
                                <th class="text-center hidden-xs">Select</th>
                                <th class="hidden-xs">名称</th>
                                <th class="hidden-xs">类型</th>
                                <th class="hidden-xs">价格</th>
                                <th class="hidden-xs">口味</th>
                                <th class="hidden-xs">尺寸</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pageInfo.list}" var="item">
                                <tr class="message-unread">
                                    <td class="hidden-xs">
                                        <label class="option block mn">
                                            <input type="checkbox" name="mobileos" value="FR">
                                            <span class="checkbox mn"></span>
                                        </label>
                                    </td>
                                    <td>${item.cake.title}</td>
                                    <td>${item.category.title}</td>
                                    <td>${item.cake.price}</td>
                                    <td>${item.cake.taste}</td>
                                    <td>${item.cake.size}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/Cake/toView.do?id=${item.cake.id}">查看</a>
                                        <a href="${pageContext.request.contextPath}/admin/Cake/toEdit.do?id=${item.cake.id}">编辑</a>
                                        <a href="${pageContext.request.contextPath}/admin/Cake/delete.do?id=${item.cake.id}">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
<%@include file="bottom.jsp" %>
