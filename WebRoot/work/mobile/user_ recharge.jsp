<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户充值</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta name="format-detection" content="telephone=no, email=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">
<!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<!-- 优先用Chrome渲染 -->


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/adminArea.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usersup.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usercharge.css">


</head>

<style>
#charge>span, input {
	border-radius: 0px !important;
}

body {
	height: 100%;
	width: 100%;
}

.charge-list {
	height: 350px;
	overflow-y: auto;
}

#charge>span {
	cursor: pointer;
}
</style>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var shopID = "${loginUser.shopID }";
</script>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
	<div class="top-con clearfix">
		<a href="${pageContext.request.contextPath}/index.html" id="return_" class="col-xs-3 sh-icon-left"> <i
			class="iconfont-gl">&#xe61b;</i>
		</a>
		<div class="col-xs-6">资金充值</div>
	</div>
	</nav>
	<div class="space44"></div>
	<div class="container">
		<div class="input-group" id="charge">
			<input class="form-control" type="text" placeholder="请输入充值金额">
			<span class="input-group-addon">
				<i class="icon-money"></i>充值
			</span>
		</div>

		<!-- 页面主体 开始 -->
		<!-- 充值列表 -->
		<div class="row rank-ranklist">
			<div class="col-xs-4 js-charge" data-money="10">
				<div class="list-item">
					<p class="name">10元</p>
				</div>
			</div>
			<div class="col-xs-4 js-charge" data-money="20">
				<div class="list-item">
					<p class="name">20元</p>
				</div>
			</div>
			<div class="col-xs-4 js-charge" data-money="30">
				<div class="list-item">
					<p class="name">30元</p>
				</div>
			</div>
			<div class="col-xs-4 js-charge" data-money="50">
				<div class="list-item">
					<p class="name">50元</p>
				</div>
			</div>
			<div class="col-xs-4 js-charge" data-money="100">
				<div class="list-item">
					<p class="name">100元</p>
				</div>
			</div>
			<div class="col-xs-4 js-charge" data-money="300">
				<div class="list-item">
					<p class="name">300元</p>
				</div>
			</div>
		</div>

		<!-- 间隔 -->
		<div class="space row">最近</div>

		<!-- 充值记录 -->
		<div class="charge-list row"></div>

		<!-- 分页按钮 -->
		<div class="row pagin-area">
			<a href="javascript:;" data-page="1">加载更多....</a>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/syl/common.js"></script>


	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/user_recharge.js?v=0.01"></script>
</body>
</html>