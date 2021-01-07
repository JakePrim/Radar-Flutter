<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <aside id="sidebar_left" class="nano nano-light affix">
        <div class="sidebar-left-content nano-content">
            <ul class="nav sidebar-menu">
                
                <li class="sidebar-label pt15">油画管理</li>
                <li>
                    <a href="#">
                        <span class="glyphicon glyphicon-home"></span>
                        <span class="sidebar-title">商品管理</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="nav sub-nav">
                        <li>
                            <a href="${pageContext.request.contextPath }/admin/product?method=findAll">
                                <span class="glyphicon glyphicon-calendar"></span> 商品列表 </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath }/admin/product?method=saveUI">
                                <span class="glyphicon glyphicon-check"></span> 添加商品 </a>
                        </li>
                    </ul>
                </li>
				<li>
                    <a href="#">
                        <span class="glyphicon glyphicon-home"></span>
                        <span class="sidebar-title">分类管理</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="nav sub-nav">
                        <li>
                            <a href="${pageContext.request.contextPath }/admin/category?method=findAll">
                                <span class="glyphicon glyphicon-calendar"></span> 分类列表 </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath }/admin/category?method=saveUI">
                                <span class="glyphicon glyphicon-check"></span> 添加分类 </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <div class="sidebar-toggle-mini">
                <a href="#">
                    <span class="fa fa-sign-out"></span>
                </a>
            </div>
        </div>
    </aside>