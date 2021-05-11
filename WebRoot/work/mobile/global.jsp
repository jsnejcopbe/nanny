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
<title>定位城市</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/globalname.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
</head>
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->



<body>
	
		<!-- 头部菜单 开始 -->
		<header class="m-topbarbox">
		<div class="m-topbar flexbox">
			<!-- 返回首页 -->
			<a class="m-goback"
				href="${pageContext.request.contextPath}/user/useraddress.html"><i
				class="iconfont">&#xe600;</i>
			</a>
			<div>
				
			</div>
		</div>
		</header>
		<div class="m-pagecont">
			<div class="storeManage storeManage-seller">

				<form action="/nanny/global_Name.html" method="get">
					<div style="padding: 10px" class="clearfix">
						<input class="form-control nav-input-search pull-right"
							type="search" placeholder="输入学校、商务楼、地址" name="addname"
							id="addname"> 
					</div>
					<div style="padding: 5px;">
						<button class="btn btn-danger bn"
							 type="submit">搜索</button>
					</div>
				</form>
			<div class="panel-body  clearfix">
				<div class="block">
					<div class="bl-city">
						<ul class="list-group list-todo ">
							<c:forEach items="${area}" var="al">
								<li class="list-group-item" id="${al.id}"><a class="" href="">${al.addName}
								<span class="sp">${al.detAdd}</span></a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			</div>
		</div>
	
</body>

</html>
