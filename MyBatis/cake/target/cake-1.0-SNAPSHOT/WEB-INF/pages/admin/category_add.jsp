<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="top.jsp" %>
<section id="content_wrapper">
    <section id="content" class="table-layout animated fadeIn">
        <div class="tray tray-center">
            <div class="content-header">
                <h2> 添加分类</h2>
                <p class="lead"></p>
            </div>
            <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
                <div class="panel heading-border">
                    <form method="post" action="${pageContext.request.contextPath}/admin/Category/add.do"
                          id="admin-form">
                        <div class="panel-body bg-light">
                            <div class="section row">
                                <div>
                                    <div class="col-md-3"><b>分类编号</b></div>
                                    <div class="col-md-5"><b>分类名称</b></div>
                                    <div class="col-md-3"><b>所属父类</b></div>
                                    <div class="col-md-1" style="text-align:right;"><b>删除</b></div>
                                </div>
                            </div>
                            <div class="section row" id="batch_items">
                                <div>
                                    <div class="col-md-3">
                                        <label for="title" class="field prepend-icon">
                                            <input type="text" name="title" id="title" class="gui-input money"
                                                   placeholder="名称..." required>
                                            <label for="title" class="field-icon">
                                                <i class="fa fa-stack"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-3">
                                        <label class="field select">
                                            <select id="language" name="pid">
                                                <option value="${root.id}">${root.title}</option>
                                                <c:forEach items="${root.children}" var="c1">
                                                    <option value="${c1.id}">&nbsp;&nbsp;&nbsp;&nbsp;${c1.title}</option>
                                                    <c:forEach items="${c1.children}" var="c2">
                                                        <option value="${c2.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${c2.title}</option>
<%--                                                        <c:forEach items="${c2.children}" var="c3">--%>
<%--                                                            <option value="${c3.id}">${c3.title}</option>--%>
<%--                                                        </c:forEach>--%>
                                                    </c:forEach>
                                                </c:forEach>
                                            </select>
                                            <i class="arrow double"></i>
                                        </label>
                                    </div>
                                    <div class="col-md-5">
                                        <label for="info" class="field prepend-icon">
                                            <input type="text" name="info" id="info" class="gui-input"
                                                   placeholder="描述...">
                                            <label for="info" class="field-icon">
                                                <i class="fa fa-info"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-1" style="text-align:right;">
                                        <button type="button" class="button remove_item_button"> X</button>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer text-right">
                                <button type="button" class="button" id="add_item_button"> 添加</button>
                                <button type="submit" class="button"> 保存</button>
                                <button type="button" class="button"
                                        onclick="javascript:window.history.back(-1);"> 返回
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</section>
<%@ include file="bottom.jsp" %>