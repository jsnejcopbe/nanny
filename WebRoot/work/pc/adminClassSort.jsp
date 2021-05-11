<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>

<title>分类排序</title>

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
							<li class="active">分类排序</li>
						</ul>
						
						<h3 class="page-header"> 分类排序</h3>
					</div>
				</div>
				
				<!-- 一级类目列表 -->
				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<ul class="list-group par">
							<c:forEach items="${classList }" var="cl">
							<li class="list-group-item clearfix js-class">
								<div class="col-xs-6 it-lf">
									<a href="javascript:showChild(${cl.id})">${cl.name }</a>
								</div>
								<div class="col-xs-6 it-rh">
									<a href="javascript:void(0)" class="btn btn-danger js-top">
										<i class="fa fa-angle-double-up"></i>
										置顶
									</a>
									<a href="javascript:void(0)" class="btn btn-danger js-up">
										<i class="fa fa-angle-up"></i>
										上移
									</a>
									<a href="javascript:void(0)" class="btn btn-info js-down">
										<i class="fa fa-angle-down"></i>
										下移
									</a>
									<a href="javascript:void(0)" class="btn btn-info js-bottom">
										<i class="fa fa-angle-double-down"></i>
										置底
									</a>
								</div>
								<input class="classID" type="hidden" value="${cl.id }">
							</li>
							</c:forEach>
							<li class="list-group-item clearfix btn-area">
								<a href="javascript:updateClassSor(0)" class="col-xs-12 btn btn-block btn-primary">保存修改</a>
							</li>
						</ul>
						
						<ul class="list-group child" style="display: none;">
							
								
								<li class="list-group-item clearfix btn-area">
									<a href="javascript:updateClassSor(1)" class="col-xs-6 btn  btn-primary">保存修改</a>
									<a href="javascript:goblack()" class="col-xs-6 btn btn-danger">返回</a>
								</li>
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
	<script src="${pageContext.request.contextPath}/work/pc/js/adminClassSort.js"></script>
</body>
</html>
