<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>

<title>商品显示</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/work/pc/css/adminClassSort.css" rel="stylesheet" type="text/css">
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
							<li class="active">商品显示</li>
						</ul>
						
						<h3 class="page-header"> 商品显示</h3>
					</div>
				</div>
				
				<!-- 一级类目列表 -->
				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<ul class="list-group">
							<c:forEach items="${classList }" var="cl">
							<li class="list-group-item clearfix js-class">
								<div class="col-xs-6 it-lf">
									<span>${cl.name }</span>
								</div>
								<div class="col-xs-6 it-rh">
									<c:if test="${cl.type==0 || cl.type==null}" >
									<a href="javascript:showClassSor(${cl.id })"   class="btn btn-info  " >显示商品</a>
									</c:if>
									<c:if test="${cl.type==1}">
									<a href="javascript:hideClassSor(${cl.id })"   class="btn btn-danger " >隐藏商品</a>
									</c:if>
								</div>
								<input class="classID" type="hidden" value="${cl.id }">
							</li>
							</c:forEach>
							
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script src="${pageContext.request.contextPath}/work/pc/js/syl/classSort.js"></script>
</body>
</html>
