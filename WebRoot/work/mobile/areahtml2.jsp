<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>

<title>系统地址管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/adminArea.css">

</head>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7E4B6fe3133523d2d23d71762d17d3fa"></script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>

<style>
#mapbox {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}
</style>

<body>
	<div id="allmap" ></div>
	<div id="add_address_query" class="sec-inp" style="display: none;">
			<input type="text" id="suggestId" class="" placeholder="输入小区名进行查询" style="width: 67%">
			<i class="iconfont-gl">&#x3432;</i> <a class="btn btn-default">确定地址</a>
	</div>
	<div id="searchResultPanel" style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>

	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/shop/shopIndex.html" class="col-xs-3 sh-icon-left"> <i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-6">系统地址管理</div>
		</div>
	</nav>
	<div class="space44"></div>
	<!-- 头部 结束 -->

	<!-- 主体内容 开始 -->
	<div class="container">
		<!-- 头部搜寻工具条 -->
		<div class="row ap-sec">
			<div id="plot_query">
				<div class="sec-inp">
					<input type="text" id="suggestId" class="" placeholder="输入小区名进行查询" style="width: 90%">
					<i class="iconfont-gl">&#x3432;</i>
				</div>

				<div class="opt-area">
					<div class="btn-group">
						<a href="javascript:void(0)" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 全部省份<span class="caret"></span>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">全部省份</a></li>
							<li><a href="#">省份一</a></li>
							<li><a href="#">省份二</a></li>
							<li><a href="#">省份三</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<a href="javascript:void(0)" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 全部省份<span class="caret"></span>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">全部省份</a></li>
							<li><a href="#">省份一</a></li>
							<li><a href="#">省份二</a></li>
							<li><a href="#">省份三</a></li>
						</ul>
					</div>
				</div>
				<!-- 地址列表 -->
				<div class="row ap-list">
					<div class="list-item clearfix">
						<div class="col-xs-9 it-des">
							<p>阳光曼哈顿</p>
							<p>福建省泉州市丰泽区刺桐南路</p>
							<p>24.884160,118.604160</p>
						</div>
						<div class="col-xs-3">
							<div class="it-btn">
								<a href="#"> <i class="iconfont-gl">&#xe635;</i> <br> <span>编辑地址</span>
								</a>
							</div>
						</div>
					</div>
					<div class="list-item clearfix">
						<div class="col-xs-9 it-des">
							<p>阳光曼哈顿</p>
							<p>福建省泉州市丰泽区刺桐南路</p>
							<p>24.884160,118.604160</p>
						</div>
						<div class="col-xs-3">
							<div class="it-btn">
								<a href="#"> <i class="iconfont-gl">&#xe635;</i> <br> <span>编辑地址</span>
								</a>
							</div>
						</div>
					</div>
				</div>
	
				<!-- 分页按钮 -->
				<div class="row pagin-area">
					<a href="#">加载更多....</a>
				</div>
			</div>
		</div>

	</div>
	<!-- 底部按钮 -->
	<div class="bot-btn">
		<a href="#"> <i class="iconfont-gl">&#xe629;</i> 新增地址
		</a>
	</div>

	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/areahtml.js"></script>
</body>
</html>
