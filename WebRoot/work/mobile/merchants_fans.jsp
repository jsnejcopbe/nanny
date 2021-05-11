<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的粉丝</title>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopfans.css" type="text/css"></link>

</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var shopID = "${loginUser.shopID }";
	var temp_shopID = "${temp_shopID }"
</script>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
	<div class="top-con clearfix">
		<a href="${pageContext.request.contextPath}/shop/shopIndex.html" id="return_" class="col-xs-3 sh-icon-left"> <i
			class="iconfont-gl">&#xe61b;</i>
		</a>
		<div class="col-xs-6">我的粉丝</div>
	</div>
	</nav>
	<div class="space43"></div>
	<!-- 头部 结束 -->

	<!-- 主体内容 开始 -->
	<div class="container">
		<!-- 搜索块 -->
		<div class="row fa-sec">
			<div class="col-xs-3 sec-right">
				<div class="btn-group"  id="address">
					<a href="javascript:void(0)" class="btn btn-danger btn-sm dropdown-toggle" data-toggle="dropdown">
						<span>小区名</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
					</ul>
				</div>
			</div>
			<div class="col-xs-9 sec-left">
				<div class="inp-con">
					<input class="address_query" type="text" placeholder="输入手机号码进行查询">
					<i class="iconfont-gl">&#x3432;</i>
				</div>
			</div>
		</div>
		
		<!-- 用户列表块 -->
		<div class="row fa-list"  id="fans_content">
			
		</div>
		
		<!-- 分页按钮 -->
		<div class="row fa-pagin pagin-area">
			<a href="javascript:;" data-page="1">加载更多....</a>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/merchants_fans.js"></script>	
</body>
</html>