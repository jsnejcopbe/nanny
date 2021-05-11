<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>您是初次使用,请选择您所在的城市</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/cityselect.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
</head>



<body>
	<!-- 头部条 -->
	<c:if test="${empty loginUser}">
	<nav class="city-top">
		<div>
			<a href="/nanny/login.html">登录</a>
			<span>|</span>
			<a href="/nanny/register.html">注册</a>
		</div>
	</nav>
	</c:if>
	<div class="block">
		<div class="bl-title clearfix">
			<span class="tip-left">当前定位城市:</span>
			<span class="tip-right">定位不准时，请在城市列表中选择</span>
		</div>
		<c:if test="${code=='0'}">
		<div class="bl-con">
			<a class="clearfix" href="${pageContext.request.contextPath}/global_addName.html?city=${ctname}">
				<span>${ctname}</span>
				<i class="iconfont-gl">&#xe602;</i>
			</a>
		</div>
		</c:if>
		<c:if test="${code=='1'}">
		<div class="bl-con">
			<span class="tip-left">找不到所在城市，请在城市列表中选择</span>
		</div>
		</c:if>
	</div>
	<!-- 首字母循环 -->
	<c:forEach items="${areaList}" var="al">
	<div class="block">
		<div class="bl-title clearfix">
			<span class="tip-left font-14">${al.firNum} &nbsp;<span>(按字母排序)</span></span>
		</div>
		
		
		<!-- 内容循环 -->
		<div class="bl-city clearfix">
		<c:forEach items="${al.data}" var="ad">
			<a class="col-xs-3" href="${pageContext.request.contextPath}/global_addName.html?city=${ad}">
				${ad}
			</a>
		</c:forEach>
		</div>
	</div>
	</c:forEach>
<c:if test="${code=='0' && jump==null}">
<script type="text/javascript">
location.href="${pageContext.request.contextPath}/global_addName.html?city=${ctname}";


</script>

</c:if>

</body>
</html>
