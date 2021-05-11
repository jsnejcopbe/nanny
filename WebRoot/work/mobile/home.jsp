<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>

<title>欢迎您，亲爱的用户</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nivo-slider.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/home.css">

</head>

<body>
	<!-- 页面主体内容 开始 -->
	<div class="container">
		<!-- 首页轮播 & 自定义导航块 -->
		<div class="row">
			<div class="slider-wrapper theme-default">
				<div id="slider" class="nivoSlider">
					<a href="javascript:void(0)">
						<img src="/nanny/images/tes7.jpg"/>
					</a>
					<a href="javascript:void(0)">
						<img src="/nanny/images/tes6.jpg"/>
					</a>
					<a href="javascript:void(0)">
						<img src="/nanny/images/tes5.jpg"/>
					</a>
				</div>
			</div>
			
			<div class="ho-navbar clearfix">
				<a class="col-xs-3 bar-item" href="javascript:void(0)">
					<img src="/nanny/images/icon1.png"><br>
					测试按钮1
				</a>
				<a class="col-xs-3 bar-item" href="javascript:void(0)">
					<img src="/nanny/images/icon2.png"><br>
					测试按钮2
				</a>
				<a class="col-xs-3 bar-item" href="javascript:void(0)">
					<img src="/nanny/images/icon3.png"><br>
					测试按钮3
				</a>
				<a class="col-xs-3 bar-item" href="javascript:void(0)">
					<img src="/nanny/images/icon4.png"><br>
					测试按钮4
				</a>
			</div>
		</div>
		
		<!-- 商户橱窗块 -->
		<div class="row ho-shop">
			<a class="col-xs-12 shop-item" href="javascript:void(0)">
				<img src="/nanny/images/tes1.jpg">
			</a>
			<a class="col-xs-12 shop-item" href="javascript:void(0)">
				<img src="/nanny/images/tes2.jpg">
			</a>
			<a class="col-xs-12 shop-item" href="javascript:void(0)">
				<img src="/nanny/images/tes3.jpg">
			</a>
			<a class="col-xs-12 shop-item" href="javascript:void(0)">
				<img src="/nanny/images/tes4.jpg">
			</a>
		</div>
	</div>
	<!-- 页面主体内容 结束 -->
	
	<!-- 底部菜单 -->
	<div class="space41"></div>
	<div class="sm-bot" style="display:none">
		<a class="col-xs-3" href="javascript:void(0)">
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
	
	<!-- 载入js 开始 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.nivo.slider.js"></script>
	<script type="text/javascript">
    $(window).load(function() {
        $('#slider').nivoSlider({
        	"directionNav":false,
            "controlNav":false
        });
    });
	</script>
</body>
</html>
