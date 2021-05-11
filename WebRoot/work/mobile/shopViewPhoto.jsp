<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>${shopMsg.shop_name}</title>
<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/shopviewmsg.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<style>
	#photo{
		background : #E0E0E0 ;
		margin : 0 auto;
	}

</style>
</head>
<body>
	<!-- 图片信息 -->
	<div class="big">					
		<img style="max-width:100%;overflow:hidden;" src="${shopMsg.shop_icon }" >	
	</div>	
	<!-- 图片信息 -->

	<!-- 底部菜单内容  -->
	<div class="space41"></div>
	<div id="photo" class="sm-bot" style="display:block">
		<a class="col-xs-3" href="${pageContext.request.contextPath}/store-det-${shopID }.html">
			<i class="iconfont-gl">&#xe60a;</i>
			<span style="font-size:15px;">返回店铺</span>
		</a> 
	</div>
	
</body>
</html>
