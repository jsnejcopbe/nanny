<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<!-- 微商申请 -->
<html>
<head>
<title>申请开店</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopapp.css" type="text/css"></link>
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script> 
<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>
<script src="${pageContext.request.contextPath}/work/mobile/js/syl/shopApply.js?v=0.01"></script>

<body>
	<!-- 页面主体内容 开始 -->
	<div class="container">
		<div class="row app-title">
			<img src="${pageContext.request.contextPath}/images/title-img.png"></img>
		</div>
		<!-- 申请表单 -->
		<div class="row app-form">
			<p class="col-xs-12">
				欢迎加入
				<span class="ft-org">掌上保姆</span>
				，请填写申请信息
			</p>
			<div class="col-xs-12">
				<form id="apply_form">
					<div class="form-group">
						<input type="hidden" value="1" id="userId" name="userId" />
						<input type="text" class="form-control" placeholder="请输入真实姓名,用于结算" data-verify="required" id="name" name="name">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="请输入手机号码,用于登录" data-verify="phone" id="tel" name="tel">
					</div>
					<div class="form-group">
						<!-- <input type="text" class="form-control" placeholder="请填写微信号" data-verify="required" id="weixinAcc" name="weixinAcc"> -->
						<input type="text" class="form-control" placeholder="请填写微信号" id="weixinAcc" name="weixinAcc">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="商店名" data-verify="required" id="shopName" name="shopName">
					</div>
					<div class="form-group">
						<button class="app-btn" href="javascript:void(0)">申请成为商户</button>
					</div>
					
					<c:if test="${shopID!=null }">
					<input name="shopID" type="hidden" value="${shopID }">
					</c:if>
				</form>
			</div>
		</div>

		<!-- 分销说明 -->
		<div class="row app-des">
			<p class="col-xs-12">分销特权</p>
			<ul class="list-group col-xs-12">
				<li class="list-group-item clearfix">
					<div class="col-xs-2 des-icon">
						<p class="bg-green">
							<i class="icon-qrcode icon-2x"></i>
						</p>
					</div>
					<div class="col-xs-10">
						<p>独立微店</p>
						<p class="ft-size12">拥有自己的微店及推广二维码</p>
					</div>
				</li>
				<li class="list-group-item clearfix">
					<div class="col-xs-2 des-icon">
						<p class="bg-yellow">
							<i class="icon-money icon-2x"></i>
						</p>
					</div>
					<div class="col-xs-10">
						<p>销售拿佣金</p>
						<p class="ft-size12">微店卖出商品，您可以获得佣金</p>
					</div>
				</li>
				<li class="list-group-item ft-size12">分销商的商品销售统一由厂家直接收款、直接发货，并提供产品的售后服务，分销佣金由厂家统一设置</li>
			</ul>
		</div>
	</div>
	<!-- 页面主体内容 结束 -->
</body>
</html>
