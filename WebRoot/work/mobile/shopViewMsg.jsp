<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>${shopMsg.shop_name}</title>
<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopviewmsg.css" type="text/css"></link>

</head>
<body>
	<!-- 头部工具栏 -->
	<nav class="sm-top clearfix">
		<a class="col-xs-2 top-icon" href="${pageContext.request.contextPath}/shoplist.html">
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
		
		<a class="col-xs-2 top-icon" href="javascript:void(0)">
			<%--<i class="iconfont-gl">&#x3432;</i>
			<span>搜索</span>
		--%></a>
	</nav>
	<div class="space53"></div>

	<!-- 页面主体内容 开始 -->
	<div class="container">
		<!-- 快速导航页 -->
		<div class="row tab-con">
			<a href="${pageContext.request.contextPath}/store-${shopID }.html" class="col-xs-4">商品</a>
			<a href="${pageContext.request.contextPath}/store-dis-${shopID }.html" class="col-xs-4">评价</a>
			<a href="javascript:void(0)" class="col-xs-4 tab-selected">店铺</a>
		</div>
		
		<!-- 商户信息 -->
		<div class="row msg-con">
			<div class="col-xs-3" id="element_id">
				 <a href="${pageContext.request.contextPath}/store-photo-${shopID }.html">
				 <img class="msg-logo" src="${shopMsg.shop_icon }"></a>
			</div>
			<div class="col-xs-9">
				<p class="msg-name">${shopMsg.shop_name }</p>
				<div>
					<a class="msg-btn js-sQrCode" href="javascript:showQrCode()" data-src="${shopMsg.shop_des }">
						<i class="icon-qrcode"></i><br>
						<span>二维码</span>
					</a>
					<c:if test="${isCollected==null }">
					<a class="msg-btn  "  href="javascript:addcollection(${shopID})" >
						<i class="iconfont-gl color">&#xe608;</i><br>
						<span>收藏</span>
					</a>
					</c:if>
					<c:if test="${isCollected!=null }">
					<a class="msg-btn  "  href="javascript:addcollection(${shopID})">
						<i class="iconfont-gl color" style="color:#F44336">&#xe608;</i>
					</a>
					</c:if>
				</div>
			</div>
		</div>
		
		<!-- 商户打分 -->		
		<div class="row marking-con">
			<div class="col-xs-4">
				<div class="con-item">
					<p class="imp">${avgScore }</p>
					<p>用户评分</p>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="con-item">
					<c:if test="${isOutArea!=null }">
					<p style="font-size: 10px;margin-bottom: 11px;">不在配送范围内</p>
					</c:if>
					<c:if test="${isOutArea==null }">
					<p>${dispatchMsg.fee }</p>
					</c:if>
					<p>起送价/元</p>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="con-item">
					<p>${shopMsg.delivery_price }</p>
					<p>配送/元</p>
				</div>
			</div>
		</div>
		
		<!-- 商户信息 -->
		<div class="row det-con">
			<div class="col-xs-12 det-title">
				商户信息
			</div>
			<div class="col-xs-12 det-item">
				<span>商家地址：</span>
				<c:if test="${shopMsg.detAdd=='null' }">
					<span>暂未填写</span>
				</c:if>
				<c:if test="${shopMsg.detAdd!='null' }">
					<span>${shopMsg.detAdd }</span>
				</c:if>
			</div>
			<div class="col-xs-12 det-item">
				<span>营业时间：</span>
				<span>
				<c:forEach items="${timeList }" var="tl">
				${tl.startTime }/${tl.endTime } 
				</c:forEach>
				</span>
			</div>
			<div class="col-xs-12 det-item">
				<span>商家电话：</span>
				<c:forEach items="${shopMsg.telList }" var="tl">
				<a href="tel:${tl }">${tl }<i class="iconfont-gl">&#xe633;</i></a>
				</c:forEach>
			</div>
		</div>
	</div>
	<!-- 页面主体内容 结束 -->
	<!-- 底部菜单内容  -->
	<div class="space41"></div>
	<div class="sm-bot" style="display:none">
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
		<a class="col-xs-3" href="${pageContext.request.contextPath}/index.html">
			<i class="iconfont-gl">&#xe62b;</i>
			<span>我的</span>
		</a>
	</div>
	<!-- 引入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
		var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopViewMsg.js"></script>
</body>
</html>
