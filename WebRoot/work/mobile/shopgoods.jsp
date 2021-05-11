<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>市场选货</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopdistribution.css" type="text/css"></link>
</head>
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

<script type="text/javascript">
	var base_url = "${pageContext.request.contextPath}";
</script>

<body>
	<!-- 顶部导航条 -->
	<nav class="dt-top">
		<div class="top-con">
			<div class="top-title clearfix">
				<div>市场选货</div>	
				<a href="javascript:showQuery()"><i class="iconfont-gl">&#x3432;</i></a>
			</div>
			<div class="dt-serarea">
				<div class="serch-int">
					<input id="search_input" type="text" placeholder="请输入产品名称">
					<a id="goods_search" href="javascript:void(0)"><i class="iconfont-gl">&#x3432;</i></a>
				</div>
			</div>	
		</div>
		
		<div class="top-navbar">
			<div class="navbar-tot">
				<a id="thisf5" href="javascript:void(0)">全部</a>
			</div>
			<div id="wrapper" class="navbar-item">
				<div id="scroller">
					<ul class="clearfix"> 
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<div class="space-90"></div>
	<!-- 顶部导航条 结束 -->
	
	<!-- 页面主体内容 开始 -->
	<div class="container">
		<!-- 大类下小类列表 开始 -->
		<div id="type2" class="dt-cla row">
		</div>
		
		<!-- 商品列表 开始 -->
		<div class="dt-pro row">
			<div class="pro-toolbar">
				<div class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
					<span id="order_title">综合排序</span>
					<span class="caret">
					</span>
					</a>
					<ul class="dropdown-menu" id="orderUl">
			         <li><a href="javascript:;">综合排序</a></li>
			         <li><a href="javascript:;">人气最高</a></li>
			         <li><a href="javascript:;">利润最高</a></li>
			      </ul>
				</div>
				<a id="all_check" class="all_check" href="javascript:void(0)"><i class="icon-check-empty"></i></a>
			</div>
			
			<!-- 加载更多 -->
			<div id="main_content">
				<span id="more_temp">暂无可选购的新商品</span>
				<a id="load_more" class="add-more js-load" data-page="1" href="javascript:void(0)">加载更多....</a> 
			</div>
			<div class="space-43"></div>
			<div class="bot-menu clearfix">
				<a class="col-xs-6 cla-item" href="${pageContext.request.contextPath}/shop/productList.html">
					<i class="iconfont-gl">&#xe60a;</i> 返回
				</a>
				<a class="col-xs-6 cla-item" id="more_add" href="javascript:void(0)">
					<i class="iconfont-gl">&#xe628;</i> 批量铺货</a>
			</div>
			<%--<div class="pro-li clearfix">
				<a class="col-xs-6 cla-item" id="all_check" href="javascript:void(0)">选择所有</a>
				<a class="col-xs-6 cla-item" id="more_add" href="javascript:void(0)">批量铺货</a>
			</div>--%>
		</div>
	</div>
	<!-- 页面主体内容 结束 -->
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/iscroll.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/shopgoods.js"></script>
	<script type="text/javascript">
	//顶部滚动初始化
	var myScroll;
	$(function(){
		myScroll = new IScroll('#wrapper', { scrollX: true, scrollY: false, mouseWheel: true,click: true });
	});
	
	</script>
	
	
</body>
</html>
