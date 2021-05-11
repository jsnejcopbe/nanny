<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>商品管理</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">
<!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<!-- 优先用Chrome渲染 -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shopproduct.css">
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

</head>

<style>
a {
	cursor: pointer;
}
</style>

<script type="text/javascript">
	var base_url = "${pageContext.request.contextPath}";
</script>

<body>
	<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
	<div class="m-topbar flexbox">
		<!-- 返回首页 -->
		<a class="m-goback" href="${pageContext.request.contextPath}/shop/shopIndex.html"><i class="iconfont">&#xe600;</i></a>

		<div class="m-tlebox flex" style="margin-left: -40px;">
			<div class="storeManage-menu" style="background: none;">商品管理</div>
		</div>

		<!-- 下拉菜单 -->
	</div>
	</header>

	<div class="space-44"></div>

	<!-- 头部菜单 结束 -->
	<div class="m-pagecont">
		<div class="storeManage storeManage-seller">
			<div class="storeManage-banner">
				<div class="faceImg">
					<a class="_porimg" href="javascript:void(0);"> <img style="border: 1px solid #c9c9c9;"
						src="${loginUser.shop_icon }"
						 >
					</a>
				</div>
				<h1 class="buyerName">${loginUser.shop_name }</h1>
			</div>

			<div class="sea-area">
				<div class="blc-fl">
					<select class="js-firclass">
						<option value="-1">一级类目</option>
						<c:forEach items="${parClass }" var="pl">
						<option class="op-item" value="${pl.id }">${pl.name }</option>
						</c:forEach>
					</select> 
					<select class="js-secclass">
						<option value="-1">二级类目</option>
					</select> 
				</div>
			</div>
			<div class="sea-area">
				<div class="blc-fl">
					<a href="javascript:jumpToProDataEdit()">批量修改</a>
					<a href="javascript:proUpAndRemove()">批量上下架</a>
				</div>
				<a class="all_check js-selall" href="javascript:void(0)">
					<i class="icon-check-empty"></i>
				</a>
			</div>

			<div class="storeManage-seller-list">
				<div class="mainMenu">
					<ul class="fixed" >
						<li class="cur sellProduct" id="onoffer_btn" data-flag="false">出售中</li>
						<li class="sellProduct" id="soldout_btn" data-flag="true">已下架</li>
					</ul>
				</div>

				<div class="subMenu">
					<ul class="fixed">
						<li class="productSort" data-info="createTime" data-flag="false" data-exist="false">时间<i class="iconfont up">&#xe626;</i><i
							class="iconfont down">&#xe627;</i></li>
						<li class="productSort" data-info="buyCount" data-flag="false" data-exist="false">销售<i class="iconfont up">&#xe626;</i><i
							class="iconfont down ">&#xe627;</i></li>
						<li class="productSort" data-info="inventory" data-flag="false" data-exist="false">库存<i class="iconfont up">&#xe626;</i><i
							class="iconfont down ">&#xe627;</i></li>
					</ul>
				</div>
			<!-- 	<div class="subMenu" style="display: none;">
					<ul class="fixed">
						<li class="productSort" data-info="createTime" data-flag="false" data-exist="false">
							时间
							<i class="iconfont up">&#xe626;</i>
							<i class="iconfont down">&#xe627;</i>
						</li>
						<li class="productSort" data-info="buyCount" data-flag="false" data-exist="false">销售<i class="iconfont up">&#xe626;</i><i
							class="iconfont down ">&#xe627;</i></li>
						<li class="productSort" data-info="inventory" data-flag="false" data-exist="false">库存<i class="iconfont up">&#xe626;</i><i
							class="iconfont down ">&#xe627;</i></li>
					</ul>
				</div> -->

				<!-- tab1 -->
				<div class="storeManage-goodsbox">
					<div class="storeManage-goodsbox-list" id="onoffer">
					
					</div>

					<!-- tab2 -->
					<div class="storeManage-goodsbox-list" id="soldout">
					
					</div>
				</div>
			</div>
			<a class="btn btn-default" id="load_more" data-page="1" style="display: block; text-align: center; margin-top: 10px;">
				加载更多
			</a>
		</div>
	</div>

	<div class="storeManage-fix-add" data-active="addGoodsPage">
		<div class="fixed" style="float:left; width:50%; padding-left:30px;">
			<a style="float:left;" href="${pageContext.request.contextPath}/shop/goods.html"> <i class="iconfont">&#xe646;</i> <span>一键铺货</span>
			</a>	
		</div>
				<div class="fixed" style="float:left; width:50%;padding-left:30px;">
			<a style="float:left;" href="${pageContext.request.contextPath}/shop/jump_Apply.html"> <i class="iconfont-gl">&#xe615;</i> <span>新增商品</span>
			</a>	
		</div>
	</div>
	
	<!-- 隐藏表单提交 -->
	<form id="jumpForm" action="${pageContext.request.contextPath}/shop/price_edit.html" method="post" style="display: none;">
		<input type="hidden" id="jsonData" name="jsonData">
	</form>
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/vdshop.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/shopProduct.js?v=0.02"></script>

</body>
</html>
