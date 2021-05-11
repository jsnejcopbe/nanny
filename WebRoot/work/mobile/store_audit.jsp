<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>

<title>商户申请</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/applyList.css">
</head>


<style>
	.cz_btn1{
		width: 50%;
		float: left; 
	}
	.cz_btn2{
		width: 50%;
		float: left;
	}
	
	.cz_btn2:HOVER{
		color: #f00;
	}
	
	.it-btn{
		height: 47px;
		text-align: center;
		padding-top: 15px;
	}
</style>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/salesman/index.html" class="col-xs-3 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-6">
				商户申请
			</div>
		</div>
	</nav>
	<div class="space44"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container">
		<!-- 头部搜寻工具条 -->
		<div class="row ap-sec">
			<div class="sec-inp">
				<input id="many" type="text" placeholder="姓名,商店名,手机,微信号">
				<i class="iconfont-gl">&#x3432;</i>
			</div>
			<div class="btn-group">
				<a id="stata_a" href="javascript:void(0)" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
					<span>全部状态</span>
					<i class="caret"></i>
				</a>
				<ul id="stata_ul" class="dropdown-menu" role="menu">
					<li data-type="-1"><a href="javascript:;">全部状态</a></li>
					<li data-type="0"><a href="javascript:;">待审核</a></li>
					<li data-type="1"><a href="javascript:;">通过</a></li>
					<li data-type="2"><a href="javascript:;">未通过</a></li>
				</ul>
			</div>
		</div>	
		<!-- 申请列表 -->
		<div class="row ap-list">
			 
		</div>
		
		<!-- 分页按钮 -->
		<div class="row pagin-area" data-page="1">
			<a href="#" >加载更多....</a>
		</div>
	</div>
	<!-- 主体内容 结束 -->
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/store_audit.js"></script>
</body>
</html>
