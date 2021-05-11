<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>产品批量修改</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shopPriceEdit.css">

</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/shop/productList.html" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-8 sh-cen">
				产品批量修改
			</div>
		</div>
	</nav>
	<div class="space43"></div>
	
	<!-- 页面主体 开始 -->
	<div class="container">
		<!-- 表格标题块 -->
		<div class="row tbl-title">
			<div class="col-xs-3">商品图片</div>
			<div class="col-xs-3">商品名</div>
			<div class="col-xs-3">库存</div>
			<div class="col-xs-3">价格</div>
		</div>
		
		<!-- 表格主体 -->
		<div class="row tbl-con">
			<form id="proEditForm">
			<c:forEach items="${proList }" var="pl" varStatus="sta">
			<div class="con-item clearfix" data-index="${sta.index }">
				<div class="col-xs-3 it-img">
					<img src="${pl.img}" width="100%" height="100%"/>
				</div>
				<div class="col-xs-3 it-name">
					<span>${pl.proName }</span>
				</div>
				<div class="col-xs-3 it-inc">
					<input class="inv-inp" type="text" name="inventorys" placeholder="请输入库存数量" value="${pl.inventory }">
				</div>
				<div class="col-xs-3 it-pri">
					<input class="pri-inp" type="text" name="proPrice" placeholder="请输入商品价格" value="${pl.price }">
				</div>
				<input name="ids" type="hidden" value="${pl.id }">
			</div>
			</c:forEach>
			</form>
		</div>
	</div>
	<!-- 页面主体 结束 -->
	
	<!-- 底部固定工具栏 -->
	<div class="space50"></div>
	<div class="bt-menu clearfix">
		<div class="col-xs-8 check-list">
			<a id="js-inc" class="che-btn" href="javascript:void(0)">
				<i class="icon-check-empty"></i>
				应用上一个商品库存
			</a>
			<a id="js-pri" class="che-btn" href="javascript:void(0)">
				<i class="icon-check-empty"></i>
				应用上一个商品价格
			</a>
		</div>
		<div class="col-xs-4">
			<a class="confirm-btn" href="javascript:upForm()">
				保存修改
			</a>
		</div>
	</div>
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopPriceEdit.js?v=0.01"></script>
</body>
</html>
