<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>附近的商家</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopList.css" type="text/css"></link>
</head>

<body>
	<!-- 头部工具栏 -->
	<nav class="sm-top clearfix">
		<a class="col-xs-2 top-icon" href="javascript:void(0)">
			<i class="iconfont-gl">&#xe62e;</i>
			<span>附近商家</span>
		</a>
		
		<c:if test="${areaMsg!=null }">
		<a class="col-xs-8 top-add" href="${pageContext.request.contextPath}/global_addName.html?city=${areaMsg.city}">
			<span>${areaMsg.addName }</span>
			<span class="caret"></span>
		</a>
		</c:if>
		
		<c:if test="${areaMsg==null }">
		<a class="col-xs-8 top-add" href="/nanny/global_city.html">
			<span>请选择</span>
			<span class="caret"></span>
		</a>
		</c:if>
	</nav>
	<div class="space53"></div>
	
	<!-- 页面主体内容 开始 -->
	<div class="container">
		<c:forEach items="${shopList }" var="sl">
			<div class="row shop-item">
				<a class="clearfix" href="${pageContext.request.contextPath}/store-${sl.id }.html">
					<div class="col-xs-3 item-logo">
						<img src="${sl.shop_icon }">
					</div>
					<div class="col-xs-9 item-con">
						<p class="con-name">${sl.shop_name }</p>
						<p class="con-tel">${sl.tel }</p>
						<p class="con-add">${sl.detAdd }</p>
					</div>
				</a>
			</div>
		</c:forEach>
		<div class="row shop-item js-showmore">
			<a href="javascript:conPagin()">
			加载更多...
			</a>
		</div>
	</div>
	<!-- 页面主体内容 结束 -->
	
	<!-- 底部菜单 -->
	<div class="space41" style="display: none;"></div>
	<div class="sm-bot" style="display: none;">
		<a class="col-xs-3" href="${pageContext.request.contextPath}/">
			<i class="iconfont-gl">&#xe60a;</i>
			<span>首页</span>
		</a>
		<a class="col-xs-3" href="${pageContext.request.contextPath}/supermarket.html">
			<i class="iconfont-gl">&#xe609;</i>
			<span>闪送超市</span>
		</a>
		<a class="col-xs-3 js-shopcar" href="${pageContext.request.contextPath}/users/shopcar.html">
			<i class="iconfont-gl">&#xe628;</i>
			<span>购物车</span>
		</a>
		<a class="col-xs-3" href="${pageContext.request.contextPath}/users/userIndex.html">
			<i class="iconfont-gl">&#xe62b;</i>
			<span>我的</span>
		</a>
	</div>
	
	<!-- 引入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopList.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
</body>
</html>
