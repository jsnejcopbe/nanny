<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<title>我的掌上保姆</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<meta name="format-detection"content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit"> <!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" > <!-- 优先用Chrome渲染 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

</head>

<body class="body-f6">
		<div class="storeManage">
			<div class="storeManage-banner">
		   		 <p style="font-size: 14px; text-align:center ; ">当前定位地址:${failInfo}<br> 暂未开通数据服务喔，换个地址试试吧</p> 
		   		
		   		 <div style="padding: 100px;">  <a class="btn btn-lg btn-danger" href="/nanny/global_city.html">切换地址</a></div>
			</div>
		</div>
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/util.js"></script>
</body>
</html>