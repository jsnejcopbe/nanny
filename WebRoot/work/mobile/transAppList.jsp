<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>提现申请记录</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/timeSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/transappList.css" type="text/css"></link>
</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
	<div class="top-con clearfix">
		<a href="javascript:history.go(-1)" id="return_" class="col-xs-3 sh-icon-left"> <i
			class="iconfont-gl">&#xe61b;</i>
		</a>
		<div class="col-xs-6">提现记录</div>
	</div>
	</nav>
	<div class="space43"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container">
	
		<!-- 搜索块 -->
		<div class="row fa-sec">
			<div class="col-xs-10 sec-left">
				<div class="inp-con">
					<input id="start" class="time_query" type="text" placeholder="输入开始时间" value="${start }">
					<i class="iconfont-gl">&#xe630;</i>
				</div>
				<div class="inp-con">
					<input id="end" class="time_query" type="text" placeholder="输入结束时间" value="${end }">
					<i class="iconfont-gl">&#xe630;</i>
				</div>
			</div>
			<div class="col-xs-2 sec-right">
				<a href="javascript:doQuery()" class="btn btn-danger btn-sm">
					<span>查询</span>
					<i class="iconfont-gl">&#x3432;</i>
				</a>
			</div>
		</div>
	
		<div class="row fa-list">
			<c:forEach items="${data.data }" var="dl">
			<div class="list-item clearfix">
				<div class="col-xs-9 item-left type2">
					<p> 提现卡号<span class="js-acc">${dl.account }</span></p>
					<p class="item-time">${dl.createTime }</p>
				</div>
				<div class="col-xs-3 item-right type2">
					<p class="item-money">${dl.money }</p>
					<p class="item-sta">${dl.statuTxt }</p>
				</div>
			</div>
			</c:forEach>

			<div class="pagin-btn col-xs-12" <c:if test="${data.totalCount<20 }">style="display: none;"</c:if>>
				<a href="javascript:paginAjax()">加载更多</a>
			</div>
		</div>
	</div>
	<!-- 主体内容 结束 -->
	
	
	<!-- js载入 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/TimeFloat.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/transappList.js"></script>
</body>
</html>
