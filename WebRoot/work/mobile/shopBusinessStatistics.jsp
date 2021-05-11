<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店铺业务统计</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-status-bar-style"
	content="black-translucent" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">
<!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<!-- 优先用Chrome渲染 -->
<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">

<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/clndr.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/css/clndr-pix.css" rel="stylesheet" >

<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
<![endif]-->
<style type="text/css">

.div-hi{
	text-align: center;height: 100%;
	color: white;
}
.div-hi i{
font-size: 5.4rem;margin-top: 35px;display: inline-block;color: white;"
}
</style>
<script type="text/javascript">
var datee="${newdate}";
</script>
</head>

<body>
	<!-- 头部工具栏 -->
	<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
	<div class="m-topbar flexbox">
		<!-- 返回首页 -->
		<a class="m-goback" href="${pageContext.request.contextPath}/shop/shopIndex.html"><i
			class="iconfont">&#xe600;</i>
		</a>

		<div class="m-tlebox flex">
			<div class="storeManage-menu">店铺业务统计</div>
		</div>

		<!-- 下拉菜单 -->
		<a class="m-moremenu"></a>
	</div>
	 </header>
	<!-- 头部菜单 结束 -->
	<div class="space53"></div>
	
	<!-- 页面主体内容 开始 -->
	<!-- 页面主体内容 开始 -->
	<div class="m-pagecont">
		<div class="storeManage storeManage-seller" style="padding-bottom: 0;">
			

								<div class="block-item block-years" style="height: 33%;">
									
									<div class="col-xs-2 div-hi">
									<a class="m-goback " href="javascript:calculation('0y');"><i class="iconfont">&#xe600;</i></a>
									</div>
									<div class="col-xs-8 div-hi">
									<p class="item-time ">
										<span id="year"></span>年
									</p>

									<p class="item-data " style="margin-top: 5%;">
										总营业额：<span id="yearmo"></span>
									</p>
									</div>
									<div class="col-xs-2 div-hi" >
									<a class="m-goback " href="javascript:calculation('1y');"><i class="iconfont">&#xe602;</i></a>
									</div>
		
								</div>

								<div class="block-item block-month" style="height: 33%;">
									<div class="col-xs-2 div-hi">
									<a class="m-goback " href="javascript:calculation('0m');"><i class="iconfont">&#xe600;</i></a>
									</div>
									<div class="col-xs-8 div-hi">
									<p class="item-time">
										<span id="month"></span>月
									</p>

									<p class="item-data" style="margin-top: 5%;">
										<span>月订单数：<span id="moor"></span> </span><br/>
										<span>月营业额：<span id="momo"></span> </span>
									</p>
									</div>
									<div class="col-xs-2 div-hi" >
									<a class="m-goback " href="javascript:calculation('1m');"><i class="iconfont">&#xe602;</i></a>
									</div>

								</div>

								<div class="block-item block-day" style="height: 33%;">
									<div class="col-xs-2 div-hi">
									<a class="m-goback " href="javascript:calculation('0d');"><i class="iconfont">&#xe600;</i></a>
									</div>
									<div class="col-xs-8 div-hi">
									<p class="item-time">
										<span id="day"></span>日
									</p>

									<p class="item-data" style="margin-top: 5%;">
										<span>日订单数：<span id="dayor"></span> </span><br/>
										<span>日营业额：<span id="daymo"></span> </span>
									</p>
									</div>
									<div class="col-xs-2 div-hi" >
									<a class="m-goback " href="javascript:calculation('1d');"><i class="iconfont">&#xe602;</i></a>
									</div>

								</div>

							</div>

						</div>
		
	<!-- 页面主体内容 结束 -->
	
	<!-- 载入js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/js/underscore-min.js"></script>
    <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/clndr.min.js"></script>
    <script src="${pageContext.request.contextPath}/work/mobile/js/shopBusinessStatistics.js"></script>
</body>
</html>
