<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
      <meta charset="utf-8">
      <title>商品类别</title>
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">

      <!-- 样式加载 -->
	  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
	  <link href="${pageContext.request.contextPath}/work/pc/css/proType.css" rel="stylesheet" type="text/css">
	  <!-- 兼容性 -->
	  <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
	  <!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->
</head>

<body>
	
	<div class="site-holder">
  		<!-- 页面主体内容 开始 -->
        <jsp:include page="head.jsp"/>

			<!-- 页面主体 开始 -->
			<div class="box-holder">

				<jsp:include page="menu.jsp"/>

				<!-- 右侧内容页 -->
            	<div class="content">
            		<!-- 复制内容 开始 -->
					<div class="row">
						<div class="col-mod-12">

							<ul class="breadcrumb">
								<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
								<li class="active">商品类别添加</li>
							</ul>

			                <div class="form-group hiddn-minibar pull-right" style="margin-top: -10px;">
			                  <a class="btn btn-primary" href="javascript:addNewClass()"><i class="fa fa-plus"></i>新增大类</a>
			                </div>

							<h3 class="page-header"><i class="fa fa-th-list"></i> 商品种类管理</h3>

						</div>
					</div>
					
					<!-- 类别列表 -->
					<div class="price-list row">
					  
						
						<c:forEach items="${ulist}" var="ul">
						<div class="price-box mix temp" id="${ul.id}">		
							<div class="price-header bg-info" >
								<h3>${ul.name}<a class="text-white js-editclass" href="javascript:void(0)" ><i class="fa fa-pencil-square-o"></i></a></h3>
							<c:set var="ulid" scope="session"  value="${ul.id}"/>
							</div>
							<ul class="list-group features" data-classid="${ul.id}">
							<c:forEach items="${clist}" var="cl">
							<c:set var="clid"  scope="session" value="${cl.parId}"/>
							<c:choose>
								<c:when test="${clid==ulid}">
									<li class="list-group-item items" id="${cl.id}">${cl.name}
									<!-- <a class="js-editsecclass" href="javascript:void(0)">删除</a> -->
									<a class="js-editsecclass" href="javascript:void(0)">编辑</a></li>
								</c:when>
							</c:choose>
								
								</c:forEach>
								
								<!-- <li class="list-group-item js-noneclass" style="display:none;">尚无小类</li> -->
								<li class="list-group-item select">
									<a class="btn btn-block bg-info text-white btn-lg js-addsecclass" href="javascript:void(0)">
									新增小类 <i class="fa fa-plus"></i>
									</a>
								</li>
							</ul>
						</div>
						</c:forEach>
					</div>
				</div>

		</div>  <!-- /.right-sidebar -->
	</div> <!-- /.box-holder -->
	
	<!-- 加载js -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/proType.js"></script>
</body>
</html>
