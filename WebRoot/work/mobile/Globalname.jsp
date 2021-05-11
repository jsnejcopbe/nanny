<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>定位城市</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/globalname.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>  
<script type="text/javascript">
var BASEPATH="${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/globalname.js?v=0.02"></script>
</head>
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->



<body>
	
		<!-- 头部菜单 开始 -->
		<header class="m-topbarbox">
		<div class="m-topbar flexbox">
			<!-- 返回首页 -->
			<a class="m-goback"
				href="${pageContext.request.contextPath}/global_city.html?jump=false"><i
				class="iconfont">&#xe600;</i>
			</a>

			<div class="m-tlebox flex">
				<div class="">
					<h1>${city}</h1>
				</div>
			</div>
			<div>
				<a class="m-goback"
					href="${pageContext.request.contextPath}/global_city.html?jump=1"> 切换</a>
			</div>
		</div>
		</header>
		<div class="m-pagecont">
			<div class="storeManage storeManage-seller">

				<form action="/nanny/global_addName.html" method="get">
					<div style="padding: 10px" class="clearfix inputArea">
						<input class="form-control nav-input-search pull-right"
							type="search" placeholder="定位不到时请手动输入你所在的小区名称" name="addname"
							value="${name}" id="addname"> <input type="hidden" name="city"
							value="${city}">
					</div>
					<div style="padding: 5px;">
						<button class="btn btn-danger bn" type="submit">搜索</button>
						<br>
						<a href="javascript:getPostion();" class="btn btn-danger btn-block bn js-location">定位到当前地区</a>
					</div>
				</form>
			<div class="panel-body  clearfix">
				<div class="block">
					<div class="bl-city">
					<c:if test="${!empty ura}">
					<h3 class="">收货地址</h3>
					<ul class="list-group list-todo ">				
							<c:forEach items="${ura}" var="ura">
								<li class="list-group-item"><a class="" href="/nanny/areaJump.html?receid=${ura.id}">${ura.recName} &nbsp;${ura.tel}
								<span class="sp">${ura.community}&nbsp;${ura.doorplate}</span></a>
								</li>
							</c:forEach>
						</ul>
						</c:if>
						<c:if test="${!empty arealist}">
						<h3 class="m-fm-hd">地区</h3>
						<ul class="list-group list-todo ">
							<c:forEach items="${arealist}" var="al">
								<li class="list-group-item" id="${al.id}"><a class="" href="/nanny/areaJump.html?areaid=${al.id}">${al.addName}
								<span class="sp">${al.detAdd}</span></a>
								</li>
							</c:forEach>
						</ul>
						</c:if>
					</div>
				</div>
			</div>
			</div>
		</div>
</body>

</html>
