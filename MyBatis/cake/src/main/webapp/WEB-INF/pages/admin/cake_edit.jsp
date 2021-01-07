<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/3/6
  Time: 4:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="top.jsp" %>
<section id="content_wrapper">
    <section id="content" class="table-layout animated fadeIn">
        <div class="tray tray-center">
            <div class="content-header">
                <h2> 上架新商品</h2>
            </div>
            <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
                <div class="panel heading-border">
                    <form method="post"
                          action="${pageContext.request.contextPath}/admin/Cake/edit.do?id=${cakeDto.cake.id}"
                          enctype="multipart/form-data">
                        <div class="panel-body bg-light">
                            <div class="section row">
                                <div class="col-md-1" style="margin-top: 10px;"><b>名称</b></div>
                                <div class="col-md-5">
                                    <label for="title" class="field prepend-icon">
                                        <input type="text" name="title" id="title" class="gui-input" placeholder="名称..."
                                               required value="${cakeDto.cake.title}">
                                        <label for="title" class="field-icon">
                                            <i class="fa fa-navicon"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-1" style="margin-top: 10px;"><b>状态</b></div>
                                <div class="col-md-3" style="margin-top: 10px;">
                                    <input type="radio" name="status" id="status1" class="radio" value="普通" <c:if
                                            test="${cakeDto.cake.status=='普通'}"> checked </c:if> />
                                    <label for="status1">普通</label>
                                    <input type="radio" name="status" id="status2" class="radio" value="推荐" <c:if
                                            test="${cakeDto.cake.status=='推荐'}"> checked </c:if>/>
                                    <label for="status2">推荐</label>
                                    <input type="radio" name="status" id="status3" class="radio" value="特卖" <c:if
                                            test="${cakeDto.cake.status=='特卖'}"> checked </c:if>/>
                                    <label for="status3">特卖</label>
                                </div>
                            </div>
                            <div class="section row">
                                <div class="col-md-1" style="margin-top: 10px;"><b>图片</b></div>
                                <div class="col-md-1" id="showImage"></div>
                                <div class="col-md-7">
                                    <!-- 隐藏域图片的提交 -->
                                    <input type="hidden" name="imagePath" class="gui-file"
                                           value="${cakeDto.cake.imagePath}">
                                    <input type="file" name="image" id="image" class="gui-file" placeholder="图片..."
                                           value="${cakeDto.cake.imagePath}">
                                </div>
                            </div>
                            <div class="section row">
                                <div class="col-md-1" style="margin-top: 10px;"><b>所属分类</b></div>
                                <div class="col-md-2">
                                    <label class="field select">
                                        <select id="language" name="cid">
                                            <c:forEach items="${root.children}" var="c1">
                                                <c:forEach items="${c1.children}" var="c2">
                                                    <c:forEach items="${c2.children}" var="c3">
                                                        <c:if test="${cakeDto.cake.cid == c3.id }">
                                                            <option value="${c3.id}" selected>${c3.title}</option>
                                                        </c:if>
                                                        <c:if test="${cakeDto.cake.cid != c3.id }">
                                                            <option value="${c3.id}">${c3.title}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:forEach>
                                            </c:forEach>
                                        </select>
                                        <i class="arrow double"></i>
                                    </label>
                                </div>
                                <div class="col-md-1" style="margin-top: 10px;"><b>口味</b></div>
                                <div class="col-md-2">
                                    <label for="taste" class="field prepend-icon">
                                        <input type="text" name="taste" id="taste" class="gui-input" placeholder="口味..."
                                               value="${cakeDto.cake.taste}"
                                               required>
                                        <label for="taste" class="field-icon">
                                            <i class="fa fa-coffee"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-1" style="margin-top: 10px;"><b>甜度</b></div>
                                <div class="col-md-2">
                                    <label for="sweetness" class="field prepend-icon">
                                        <input type="number" name="sweetness" id="sweetness" class="gui-input"
                                               placeholder="甜度..." required min="1" max="5"
                                               value="${cakeDto.cake.sweetness}">
                                        <label for="sweetness" class="field-icon">
                                            <i class="fa fa-star"></i>
                                        </label>
                                    </label>
                                </div>
                            </div>
                            <div class="section row">
                                <div class="col-md-1" style="margin-top: 10px;"><b>价格</b></div>
                                <div class="col-md-2">
                                    <label for="price" class="field prepend-icon">
                                        <input type="text" name="price" id="price" class="gui-input" placeholder="价格..."
                                               value="${cakeDto.cake.price}"
                                               required>
                                        <label for="price" class="field-icon">
                                            <i class="fa fa-cny"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-1" style="margin-top: 10px;"><b>重量</b></div>
                                <div class="col-md-2">
                                    <label for="weight" class="field prepend-icon">
                                        <input type="number" name="weight" id="weight" class="gui-input"
                                               value="${cakeDto.cake.weight}"
                                               placeholder="重量..." required>
                                        <label for="weight" class="field-icon">
                                            <i class="fa fa-database"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-1" style="margin-top: 10px;"><b>尺寸</b></div>
                                <div class="col-md-2">
                                    <label for="size" class="field prepend-icon">
                                        <input type="number" name="size" id="size" class="gui-input" placeholder="尺寸..."
                                               value="${cakeDto.cake.size}"
                                               required>
                                        <label for="size" class="field-icon">
                                            <i class="fa fa-arrows"></i>
                                        </label>
                                    </label>
                                </div>
                            </div>
                            <div class="section row">
                                <div class="col-md-1" style="margin-top: 10px;"><b>材料</b></div>
                                <div class="col-md-8">
                                    <label for="material" class="field prepend-icon">
                                        <input type="text" name="material" id="material" class="gui-input"
                                               value="${cakeDto.cake.material}"
                                               placeholder="材料..." required>
                                        <label for="material" class="field-icon">
                                            <i class="fa fa-server"></i>
                                        </label>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <button type="submit" class="button"> 保存</button>
                            <button type="button" class="button"
                                    onclick="javascript:window.history.back(-1);"> 返回
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</section>
<%--<script>--%>
<%--    $("#image").change(function (e) {--%>
<%--        var file = e.target.files[0]--%>
<%--        var img = new Image(), url = img.src = URL.createObjectURL(file)--%>
<%--        img.width = 50;--%>
<%--        var $img = $(img)--%>
<%--        img.onload = function () {--%>
<%--            URL.revokeObjectURL(url)--%>
<%--            $('#showImage').empty().append($img)--%>
<%--        }--%>
<%--    })--%>

<%--</script>--%>

<%@include file="bottom.jsp" %>
