<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>新增商品申请</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">

<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
<![endif]-->
</head>

<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="head.jsp" />
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="menu.jsp" />
			
			<div class="content">
			
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
		                   <li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
		                   <li><a href="#">商品列表</a></li>
		                   <li class="active">新增商品申请</li>
		                </ul>
		                <h3 class="page-header"><i class="fa fa-indent"></i> 新增商品申请</h3>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-cascade">
						</div>
					</div>
				</div>
			
			</div>
		</div>
	</div>
</body>
</html>
