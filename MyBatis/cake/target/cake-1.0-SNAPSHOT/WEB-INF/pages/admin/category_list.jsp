<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="top.jsp" %>
<section id="content_wrapper">
    <section id="content" class="table-layout animated fadeIn">
        <div class="tray tray-center">
            <div class="content-header">
                <h2> 分类管理</h2>
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
                                           onclick="javascript:window.location.href='${pageContext.request.contextPath}/admin/Category/toAdd.do';"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body pn">
                        <table id="message-table" class="table admin-form theme-warning tc-checkbox-1">
                            <thead>
                            <tr class="">
                                <th class="text-center hidden-xs">Select</th>
                                <th class="hidden-xs">名称</th>
                                <th class="hidden-xs">描述</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${root.children }" var="c1">
                                <tr class="message-unread">
                                    <td class="hidden-xs">
                                        <label class="option block mn">
                                            <input type="checkbox" name="mobileos" value="FR">
                                            <span class="checkbox mn"></span>
                                        </label>
                                    </td>
                                    <td>${c1.title}</td>
                                    <td>${c1.info}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/Category/delete.do?id=${c1.id}">删除</a>
                                    </td>
                                </tr>
                                <!-- 二级分类 TODO 如果存在不确定的级别分类要如何处理？ 如果存在10级分类那要写10层循环吗？-->
                                <c:forEach items="${c1.children}" var="c2">
                                    <tr class="message-unread">
                                        <td class="hidden-xs">
                                            <label class="option block mn">
                                                <input type="checkbox" name="mobileos" value="FR">
                                                <span class="checkbox mn"></span>
                                            </label>
                                        </td>
                                        <td style="padding-left: 50px;">${c2.title}</td>
                                        <td>${c2.info}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/Category/delete.do?id=${c2.id}">删除</a>
                                        </td>
                                    </tr>
                                    <c:forEach items="${c2.children}" var="c3">
                                        <tr class="message-unread">
                                            <td class="hidden-xs">
                                                <label class="option block mn">
                                                    <input type="checkbox" name="mobileos" value="FR">
                                                    <span class="checkbox mn"></span>
                                                </label>
                                            </td>
                                            <td style="padding-left: 100px;">${c3.title}</td>
                                            <td>${c3.info}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/Category/delete.do?id=${c3.id}">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
<%@ include file="bottom.jsp" %>