<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>商户提现</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/transApp.css" type="text/css">
</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/shop/shopIndex.html" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-8 sh-cen">
				商户提现
			</div>
		</div>
	</nav>
	<div class="space43"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container">
		
		<div id="block1">
			<c:if test="${acccount!=null}">
			<div class="row mo-con">
				<div class="col-xs-12 con-item" onclick="location.href='${pageContext.request.contextPath}/users/bankshow.html'">
					<c:if test="${acccount.isUse==0 }">
					<span class="pull-left">提现账户</span>
					<div class="pull-right">
						<span>${acccount.bankName } 尾号${splitAcc} ${acccount.accName }</span>
						<i class="iconfont-gl">&#xe602;</i>
					</div>
					</c:if>
					
					<c:if test="${acccount.isUse==1 }">
					<span class="pull-left">该银行的提现功能已关闭，请选择其他银行</span>
					<div class="pull-right">
						<i class="iconfont-gl">&#xe602;</i>
					</div>
					</c:if>
				</div>
			</div>
			</c:if>
			
			<c:if test="${acccount==null }">
			<div class="row mo-con">
				<div class="col-xs-12 con-item" onclick="location.href='${pageContext.request.contextPath}/users/selectbank.html'">
					<span class="pull-left">设置提现账户</span>
					<div class="pull-right">
						<i class="iconfont-gl">&#xe602;</i>
					</div>
				</div>
			</div>
			</c:if>
			
			<div class="row mo-con">
				<div class="col-xs-12 con-item">
					<span class="pull-left">可提现金额</span>
					<span class="pull-right imp">¥${canUserBalance }</span>
				</div>
				<div class="col-xs-12 con-item">
					<span class="pull-left">不可用金额</span>
					<span class="pull-right">¥${forbidBalance }</span>
				</div>
				<div class="col-xs-12 con-item js-totranrec">
					<span class="pull-left">提现记录</span>
					<span class="pull-right"><i class="iconfont-gl">&#xe602;</i></span>
				</div>
				<div class="col-xs-12 con-item clearfix">
					<span class="col-xs-3">本次提现</span>
					<div class="col-xs-9">
						<input id="price" type="text" placeholder="单笔最高两万">
					</div>
				</div>
			</div>
			
			<div class="row trans-btn">
				<a href="javascript:upCashApp()">立即提现</a>
			</div>
		</div>
	</div>
	<!-- 主体内容 结束 -->
	
	<!-- 隐藏表单 -->
	<input id="userID" type="hidden" value="${userID }">
	<input id="canUserBalance" type="hidden" value="${canUserBalance }">
	<input id="accountID" type="hidden" value="${acccount.accID }">
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopTransApp.js"></script>
</body>
</html>
