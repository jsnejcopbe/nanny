<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>店铺评价</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopdismsg.css" type="text/css"></link>
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
		
		<a class="col-xs-2 top-icon" href="javascript:void(0)" style="height: 45px;">
			<%--<i class="iconfont-gl">&#x3432;</i>
			<span>搜索</span>
		--%></a>
		<!-- 快速导航页 -->
		<div class="row tab-con">
			<a href="${pageContext.request.contextPath}/store-${shopID }.html" class="col-xs-4">商品</a>
			<a href="javascript:void(0)" class="col-xs-4 tab-selected">评价</a>
			<a href="${pageContext.request.contextPath}/store-det-${shopID }.html" class="col-xs-4">店铺</a>
		</div>
	</nav>
	<div class="space92"></div>
	
	<!-- 页面主体内容 开始 -->
	<div class="container">
		<!-- 评论列表 -->
		<div class="row dis-con">
			<c:forEach items="${disData.data }" var="di">
			<div class="clearfix con-item">
				<div class="col-xs-2 item-left">
					<img src="${di.headImg }">
				</div>
				<div class="col-xs-10 item-right">
					<p>
						<span>${di.nickName }</span>
						<span>
							<i class="iconfont-gl <c:if test="${di.score>1 }">checked</c:if>">&#xe650;</i>
							<i class="iconfont-gl <c:if test="${di.score>=2 }">checked</c:if>">&#xe650;</i>
							<i class="iconfont-gl <c:if test="${di.score>=3 }">checked</c:if>">&#xe650;</i>
							<i class="iconfont-gl <c:if test="${di.score>=4 }">checked</c:if>">&#xe650;</i>
							<i class="iconfont-gl <c:if test="${di.score==5 }">checked</c:if>">&#xe650;</i>
						</span>
					</p>
					<div class="con-des"><c:if test="${di.con!='null' }">${di.con }</c:if></div>
					<p class="con-time">${di.createTime }</p>
				</div>
			</div>
			</c:forEach>
		</div>
		
		<!-- 翻页 -->
		<div class="row pagin" <c:if test="${disData.total>disData.size}">style="display: block;"</c:if>>
			<a href="javascript:pagin()">加载更多...</a>
		</div>
	</div>
	
	<!-- 底部菜单 -->
	<div class="space41"></div>
	<div class="sm-bot" style="display:none">
		<a class="col-xs-3" href="${pageContext.request.contextPath}/">
			<i class="iconfont-gl">&#xe60a;</i>
			<span>首页</span>
		</a>
		<a class="col-xs-3" href="${pageContext.request.contextPath}/supermarket.html">
			<i class="iconfont-gl">&#xe609;</i>
			<span>自选超市</span>
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
	
	<!-- 隐藏表单 -->
	<input id="shopID" type="hidden" value="${shopID }">
	<input id="pageSize" type="hidden" value="${disData.size }">
	
	<!-- 引入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopDisMsg.js"></script>
</body>
</html>
