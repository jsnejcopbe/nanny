<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>我的优惠券</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0,  maximum-scale=1.0,user-scalable=no">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shoporderlist.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css"
	type="text/css"></link>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/work/mobile/css/money_detail.css"
	type="text/css"></link>
	<script type="text/javascript">
	var path="${pageContext.request.contextPath}";
	</script>
</head>
<body>
	<!-- 头部 -->
	<nav class="sh-top"
		style="height:44px; background: #f8f8f8;position: relative;">
	<div class="top-con clearfix">
		<a href="${pageContext.request.contextPath}/users/userIndex.html"
			class="col-xs-3 sh-icon-left" style="line-height: 25px;"> <i
			class="iconfont"></i> </a>
		<div class="col-xs-6" style="text-align: center;line-height: 25px;">
			我的优惠券</div>
	</div>
			<div class="top-tab clearfix">
			<a href="${pageContext.request.contextPath}/users/userCoupon.html?sta=0" class="col-xs-4 <c:if test="${sta=='0' }">selected</c:if>" >未使用</a>
			 <a href="${pageContext.request.contextPath}/users/userCoupon.html?sta=1" class="col-xs-4 <c:if test="${sta=='1' }">selected</c:if>" >已使用</a>
			 <a href="${pageContext.request.contextPath}/users/userCoupon.html?sta=2" class="col-xs-4 <c:if test="${sta=='2' }">selected</c:if>" >已过期</a>
		</div>
	</nav>
	<div style="height: 35px"></div>
	<!-- 头部 结束 -->

	
	<div class="container sh-pro">
		

		<div class="myclass"></div>
	
		<input type="hidden" id="sta" value="${sta }">
		<input type="hidden" id="arrsize" value="${jso.count }">
		<input type="hidden" id="pageIndex" value="">
			<input type="hidden" id="pageSize" value="">
			<div class="pancel" style="display:none;">
				<div class="panel-body">
					<div class="row pagin-area">
						<a href="javascript:page();" style="margin-left: 40%; color: #4A4A4A" data-page="1">加载更多</a>
					</div>
				</div>
			</div>
	</div>
			

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/base.js"></script>
	
 	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/userCoupon.js">
 	
 	</script>

</body>
</html>
