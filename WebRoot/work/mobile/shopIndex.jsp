<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>我的微店</title>
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

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/work/mobile/css/shopIndex.css">
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
<style type="text/css">
@font-face {
	font-family: 'iconfont-gl';
	src: url('/nanny/fonts/iconfont-gl.eot'); /* IE9*/
	src: url('/nanny/fonts/iconfont-gl.eot?#iefix')
		format('embedded-opentype'), /* IE6-IE8 */ 
     url('/nanny/fonts/iconfont-gl.woff') format('woff'),
		/* chrome、firefox */ 
     url('/nanny/fonts/iconfont-gl.ttf') format('truetype'),
		/* chrome、firefox、opera、Safari, Android, iOS 4.2+*/ 
     url('/nanny/fonts/iconfont-gl.svg#iconfont') format('svg');
	/* iOS 4.1- */
}

.iconfont-gl {
	font-family: "iconfont-gl" !important;
	font-size: 16px;
	font-style: normal;
	-webkit-font-smoothing: antialiased;
	-webkit-text-stroke-width: 0.2px;
	-moz-osx-font-smoothing: grayscale;
}
</style>
</head>

<style>
li:HOVER {
	cursor: pointer;
}
</style>

<body>
	<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
	<div class="m-topbar flexbox">
		<!-- 返回首页 -->
		<a class="m-goback" href="${pageContext.request.contextPath}/"><i
			class="iconfont">&#xe600;</i>
		</a>

		<div class="m-tlebox flex">
			<div class="storeManage-menu">我的店铺</div>
		</div>

		<!-- 下拉菜单 -->
		<a class="m-moremenu"></a>
	</div>
	<section class="m-menubox">
	<ul class="fixed">
		<li><a class="menu-link"
			href="http://mp.soqi.cn/CD69E33ED687F1717CB0BCD31B48A3E7/szone"><span><i
					class="iconfont">&#xe644;</i>
			</span>
			<div class="name">分类</div>
		</a>
		</li>
		<li><a class="menu-link"
			href="http://mp.soqi.cn/vd/tominishopmanagepage.xhtml?type=1"><span><i
					class="iconfont">&#xe615;</i>
			</span>
			<div class="name">我的微店</div>
		</a>
		</li>
		<li><a class="menu-link"
			href="http://mp.soqi.cn/vd/findmcsls.xhtml"><span><em
					class="badge"></em><i class="iconfont">&#xe61c;</i>
			</span>
			<div class="name">购物车</div>
		</a>
		</li>
		<li><a class="menu-link"
			href="http://mp.soqi.cn/login/loginOut.xhtml"><span><i
					class="iconfont">&#xe614;</i>
			</span>
			<div class="name">退出</div>
		</a>
		</li>
	</ul>
	</section> </header>
	<!-- 头部菜单 结束 -->

	<!-- 页面主体内容 开始 -->
	<div class="m-pagecont">
		<div class="storeManage storeManage-seller">
			<div class="storeManage-banner">
				<div class="faceImg">
					<a class="_porimg" href="javascript:void(0);"> <img
						style="border: 1px solid #c9c9c9;" src="${loginUser.shop_icon }">
					</a>
				</div>
				<h1 class="buyerName">${loginUser.shop_name }</h1>
			</div>

			<div class="storeManage-sellerInfo">
				<p class="viewAllOrder">
					<a href="${pageContext.request.contextPath}/shop/order-1.html">所有订单<i
						class="iconfont">&#xe602;</i>
					</a>
				</p>
				<ul class="flexbox">
					<li class="i_notpay" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=0'"> <span> ${order0}</span>
					<p class="name">待付款</p>
					</li>
					<li class="i_notdeliver" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=1'"><span>${order1}</span>
					<p class="name">待发货</p>
					</li>
					<li class="i_receipt" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=a'"><span>${order4}</span>
					<p class="name">退款</p>
					</li>
					<li class="i_refund" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=a'"><span>${order3}</span>
					<p class="name">交易完成</p>
					</li>
				</ul>
			</div>

			<div class="storeManage-buyer-list">
				<ul id="mainMenu"  id="8585">
					<li id="productGo"><span class="fl"><i
							class="iconfont color1">&#xe617;</i>商品管理</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li id="fansinit"><span class="fl"><i
							class="iconfont color3">&#xe608;</i>我的粉丝</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li id="setShop"><span class="fl"><i
							class="iconfont color2">&#xe606;</i>店铺设置</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li id="setDelivery"><span class="fl"><i
							class="iconfont color2">&#xe606;</i>配送地址设置</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li id="shopAccount"><span class="fl"><i
							class="iconfont color4">&#xe603;</i>资金明细</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li id="shopStatising"><span class="fl"><i
							class="iconfont color4">&#xe603;</i>业务统计</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li id="shopMessage"><span class="fl"><i
							class="iconfont color4">&#xe653;</i>站内信</span><span class="fr"><i
							class="iconfont">&#xe602;</i>
					</span>
					</li>
					<li><a href="${pageContext.request.contextPath}/shop/transfer.html"><span class="fl"><i class="iconfont color2">&#xe623;</i>提现申请</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
					<li><a href="${pageContext.request.contextPath}/loginout.html"><span
							class="fl"><i class="iconfont-gl color3">&#xe62d;</i>切换账户</span><span
							class="fr"><i class="iconfont">&#xe602;</i>
						</span>
					</a>
					</li>
					<c:if test="${loginUser.status==0 || loginUser.status==null}" var="condition" >
					<button type="button"  style="margin-top:10px;" class="btn btn-danger btn-block"  onclick="closeshop()">	店铺歇业
					</button>
					</c:if>
					<c:if test="${loginUser.status==1}" var="condition" >
					<button type="button" style="margin-top:10px;"  class="  btn btn-primary   btn-success btn-block btn-open"  onclick="openshop()">	店铺营业
					</button>
				
					</c:if>
				</ul>
			</div>
		</div>
	</div>

	<!-- 载入js -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/work/mobile/js/util.js"></script>
			<script type="text/javascript"
		src="${pageContext.request.contextPath}/work/mobile/js/shopIndex.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/layer/layer.js"></script>

	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}/"; 
		
		$("#mainMenu li").click(function(){
			var type = $(this).attr("id");
			switch (type) {
			case "productGo":
				window.location.href = "${pageContext.request.contextPath}/shop/productList.html";
				break;
			case "setShop":
				window.location.href = "${pageContext.request.contextPath}/shop/shopInfo.html";
				break;
			case "setDelivery":
				window.location.href = "${pageContext.request.contextPath}/shop/setDelivery.html";
				break;
			case "fansinit":
				window.location.href = "${pageContext.request.contextPath}/shop/fansinit.html";
				break;
			case "shopAccount":
				window.location.href = "${pageContext.request.contextPath}/shop/shopAccmo.html";
				break;
			case "shopMessage":
				window.location.href = "${pageContext.request.contextPath}/shop/shopMessage.html";
				break;
			case "shopStatising":
				window.location.href = "${pageContext.request.contextPath}/shop/jump_shopdate.html";
				break;
			default:
				break;
			}
		})
	</script>

</body>
</html>
