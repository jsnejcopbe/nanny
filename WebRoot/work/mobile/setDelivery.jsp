<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送地址设置</title>
</head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/adminArea.css">

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var shopID = "${loginUser.shopID }";
</script>


<body>
	<div id="list1" >
		<!-- 头部 -->
		<nav class="sh-top">
			<div class="top-con clearfix">
				<a href="${pageContext.request.contextPath}/shop/shopIndex.html" id="return_" class="col-xs-3 sh-icon-left"> <i class="iconfont-gl">&#xe61b;</i>
				</a>
				<div class="col-xs-6">我的配送地址</div>
			</div>
		</nav>
		<div class="space44"></div>
		<!-- 头部 结束 -->	
	
		<input type="hidden" id="hide_url" value="shop/delivery/scopelist.html"/>
		<!-- 主体内容 开始 -->
		<div class="container">
			<!-- 头部搜寻工具条 -->
			<div class="row ap-sec">
				<div class="sec-inp">
					<input type="text" class="address" placeholder="输入小区名进行查询" style="width: 90%">
					<i class="iconfont-gl">&#x3432;</i>
				</div>
			</div>
			
			<!-- 地址列表 -->
			<div class="row ap-list">
			</div>
					<!-- 分页按钮 -->
			<div class="row pagin-area" >
				<a href="#" data-page="1">加载更多....</a>
			</div>
		</div>
		
		<!-- 底部按钮 -->
		<div class="space44"></div>
		<div class="bot-btn">
			<a href="#"> <i class="iconfont-gl">&#xe629;</i> 
				选择配送地址
			</a>
		</div>
	</div>
	
	<div id="list2" style="display: none">
		<!-- 头部 -->
		<nav class="sh-top">
			<div style="background: #ff5500" class="top-con clearfix">
				<a href="javascript:;"  id="list1_return" class="col-xs-3 sh-icon-left">
					 <i style="color:#F0E0DC" class="iconfont-gl">&#xe61b;</i>
				</a>
				<div style="color: #F0E0DC" class="col-xs-6">选择配送地址</div>
			</div>
		</nav>
		<div class="space44"></div>
		<!-- 头部 结束 -->	
	
		<input type="hidden" id="hide_url" value="shop/adress/getAdresslist.html"/>
		<!-- 主体内容 开始 -->
		<div class="container">
			<!-- 头部搜寻工具条 -->
			<div class="row ap-sec">
				<div class="sec-inp">
					<input type="text" class="address" placeholder="输入小区名进行查询" style="width: 90%">
					<i class="iconfont-gl">&#x3432;</i>
				</div>
			</div>
			
			<!-- 地址列表 -->
			<div class="row ap-list">
			</div>
					<!-- 分页按钮 -->
			<div class="row pagin-area" >
				<a href="#" data-page="1">加载更多....</a>
			</div>
		</div>
		
		
	</div>
	
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/setDelivery.js?v=0.01"></script>
</body>
</html>