<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 左侧菜单 -->
<div class="left-sidebar">
    <div class="sidebar-holder">
	    	<ul class="nav  nav-list" id="sup">
				<li><a href="${pageContext.request.contextPath}/supplier/supplierIndex.html" data-original-title="Dashboard"><i class="iconfont-gl">&#xe60a;</i><span class="hidden-minibar"> 首页</span></a>
	            </li>
	           
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar"> 供货商品管理 </span><span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
	          		 	 <li><a href="${pageContext.request.contextPath}/supplier/suproduct.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar">供货商品管理 </span></a>
	            		</li>
	            	</ul>
	            </li>
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe615;</i><span class="hidden-minibar">  订单管理 </span>&nbsp;<span class="badge bg-primary js-menuodcount">0</span><span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>

	          		 	<li><a href="${pageContext.request.contextPath}/supplier/order.html?pageIndex=1&pageSize=10" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe615;</i><span class="hidden-minibar">供货订单</span>&nbsp;<span class="badge bg-primary pull-right js-menuodcount-sec">0</span></a>

	          		 	

	          		 	</li>
	            	</ul>
	            </li>
	            <li>
	            	<a href="${pageContext.request.contextPath}/supplier/supinfo.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar">店铺编辑 </span></a>
			    </li>
			     <li>
	            	<a href="${pageContext.request.contextPath}/supplier/supshopth.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar">来往商户 </span></a>
			    </li>
	    	</ul>
    </div>
</div>


<!-- 左侧菜单 结束-->
