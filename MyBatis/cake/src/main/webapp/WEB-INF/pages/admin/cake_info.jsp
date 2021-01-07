<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/3/6
  Time: 5:34 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="top.jsp" %>
<section id="content_wrapper">
    <section id="content" class="table-layout animated fadeIn">
        <div class="tray tray-center">
            <div class="content-header">
                <h2> 商品信息</h2>
            </div>
            <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
                <div class="panel heading-border">
                    <div class="panel-body bg-light">
                        <div class="section row">
                            <div class="col-md-5"><img src="${cakeDto.cake.imagePath}" width="300">
                            </div>
                            <div class="col-md-5">
                                <div class="section row">
                                    <div class="col-md-3"><b>名称</b></div>
                                    <div class="col-md-9">${cakeDto.cake.title}</div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>所属分类</b></div>
                                    <div class="col-md-9">${cakeDto.category.title}</div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>口味</b></div>
                                    <div class="col-md-9">${cakeDto.cake.taste}</div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>甜度</b></div>
                                    <div class="col-md-9">
                                        <c:forEach begin="1" end="${cakeDto.cake.sweetness}">
                                            <span class="glyphicon glyphicon-star"></span>
                                        </c:forEach>
                                        <c:forEach begin="1" end="${5 - cakeDto.cake.sweetness}">
                                            <span class="glyphicon glyphicon-star-empty"></span>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>价格</b></div>
                                    <div class="col-md-9">${cakeDto.cake.price}</div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>重量</b></div>
                                    <div class="col-md-9">${cakeDto.cake.weight}</div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>尺寸</b></div>
                                    <div class="col-md-9">${cakeDto.cake.size}</div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3"><b>材料</b></div>
                                    <div class="col-md-9">${cakeDto.cake.material}</div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
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
<%@include file="bottom.jsp" %>
