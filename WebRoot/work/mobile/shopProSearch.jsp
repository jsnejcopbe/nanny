<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>商品查询</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/style-m-2.0.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/login-m-2.0.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/popup.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopProSearch.css" type="text/css"></link>
</head>

<body>
	<!-- 头部搜寻框 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="javascript:backToStore()" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			
			<div class="col-xs-8 con-sec">
				<div class="sec-inp">
					<i class="iconfont-gl">&#x3432;</i>
					<input id="query" name="query" type="text" placeholder="请输入商品关键字">
					<a class="js-clear" href="javascript:queryClear()"><i class="icon-remove-circle"></i></a>
				</div>
			</div>
			
			<a href="javascript:doQuery()" class="col-xs-2 sh-icon-right">
			确认
			</a>
		</div>
	</nav>
	<div class="space43"></div>
	<!-- 头部 结束 -->
	
	<!-- 页面主体内容 开始 -->
	<div class="container">
		<!-- 查询页面 -->
		<div class="sec-tab">
			<c:forEach items="${treeData }" var="td">
			<c:if test="${fn:length(td.childArray)>0 }">
			<div class="row tab-item">
				<span class="col-xs-12 it-fircla">
					${td.name }
				</span>
				<c:forEach items="${td.childArray }" var="tc">
				<div class="col-xs-3">
					<a id="st-seccla-${tc.id }" class="it-seccla" href="javascript:doQuery(${tc.id })" data-truename="${tc.name }">${tc.splitName }</a>
				</div>
				</c:forEach>
			</div>
			</c:if>
			</c:forEach>
		</div>
		
		<!-- 结果页面 -->
		<div class="sec-reu" style="display: none;">
			<div class="reu-item row">
				
			</div>
			
			<!-- 分页按钮 -->
			<div class="row sec-pagin">
				<a href="javascript:pagin()">加载更多</a>
			</div>
		</div>
	</div>
	
	<!-- 底部结算页 -->
	<div class="space50"></div>
	<div class="sm-bot">
		<div class="col-xs-12 bot-pay clearfix">
			<div class="left">
				<span>¥<span class="js-price">0</span></span>
				<span>共<span class="js-procount">0</span>个商品</span>
				<span>还差<span class="js-send">${dispatchMsg.fee }</span>元起送</span>
			</div>
			<div class="right">
				<a class="useless" href="javascript:addToShop()">选好了,前往付款</a>
			</div>
		</div>
	</div>
	
	<!-- 隐藏表单页 -->
	<input id="shopID" type="hidden" value="${shopID }">
	<input id="pageSize" type="hidden" value="${pageSize }">
	<input id="userID" type="hidden" value="${sessionScope.loginUser.userID }">
	<input id="userInter" type="hidden" value="${loginUser.point }">
	<form id="storeJumpForm" action="${pageContext.request.contextPath}/store-${shopID }.html" method="post" style="display: none;">
		<input id="proCarList" name="proCarList" type="hidden" value="">
	</form>
	
	<!-- 引入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	var shopCarPro=${proCarList};
	var min_send_price=${dispatchMsg.fee };
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopProSearch.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/loginalert.js"></script>
</body>
</html>
