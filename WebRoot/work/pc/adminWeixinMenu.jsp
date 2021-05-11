<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>微信公众号菜单管理</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/work/pc/css/adminWeixinMenu.css" rel="stylesheet" type="text/css">
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
							<li class="active">微信公众号菜单管理</li>
						</ul>
						
						<h3 class="page-header" style="margin-bottom: 0px;margin-left: -5px;"> 微信公众号菜单管理</h3>
					</div>
				</div>
				
				<!-- 主体内容 开始 -->
				<div class="row">
					<div class="col-xs-12 fun-des">
						<div class="des-item">
							<i class="icon-exclamation-sign"></i>
						</div>
						&nbsp;&nbsp;
						<div class="des-item">
							<p class="item-tit">菜单编辑中</p>
							<p class="item-det">菜单未发布，请确认菜单编辑完成后点击“保存并发布”同步到手机</p>
						</div>
					</div>
				</div>
				
				<div class="row fun-ma">
					<div class="col-xs-4">
						<div class="ma-pre">
							<div class="pre-top">
								<div>掌上保姆</div>
							</div>
							
							<div class="pre-bot clearfix" data-limit="3">
							
							    <!-- menu roop  -->
								<c:forEach items="${menuList }" var="ml" varStatus="st">
								<div class="js-menu col-xs-4 <c:if test="${st.index==0 }">bot-sl</c:if>" data-url="${ml.url }">
									<a href="javascript:void(0)">${ml.name }</a>
									
									<!-- 浮动菜单 -->
									<div class="bot-fl" <c:if test="${st.index!=0 }">style="display:none"</c:if>>
										<ul class="fl-con" data-limit="5">
											<c:forEach items="${ml.sub_button }" var="ms" varStatus="mt">
											<li class="js-menu fl-con-it <c:if test="${mt.index==0 }">bot-sl</c:if>"" data-url="${ms.url }">
												<a href="javascript:void(0)">
													${ms.name }
												</a>
											</li>
											</c:forEach>
											
											<c:if test="${fn:length(ml.sub_button) <5 }">
											<li class="fl-con-it js-menu add-btn">
												<a href="javascript:void(0)">
													<i class="icon-plus icon-2x"></i>
												</a>
											</li>
											</c:if>
										</ul>
										<i class="arrow arrow_out"></i>
										<i class="arrow arrow_in"></i>
									</div>
									
								</div>
								</c:forEach>
								
								<c:if test="${fn:length(menuList) <3 }">
								<div class="js-menu col-xs-4 add-btn">
									<a href="javascript:void(0)">
										<i class="icon-plus"></i>
									</a>
								</div>
								</c:if>
								
							</div>
						</div>
					</div>
					
					<div class="col-xs-8">
						<div class="ma-set">
							<div class="clearfix set-tit">
								<span class="pull-left js-menuName">菜单名称</span>
								<a class="pull-right" href="javascript:deleteMenu()">删除菜单</a>
							</div>
							<div class="set-opt">	
								<div class="clearfix">
									<div class="col-xs-12 js-errortip" style="display: none;">
										<p style="color: #8d8d8d;margin: 0px 0px 20px -15px">已添加子菜单，仅可设置菜单名称。</p>
									</div>
									
									<div class="opt-lf col-xs-2">
										菜单名称
									</div>
									<div class="opt-rh col-xs-10">
										<input class="txt-inp js-menuNameInp" type="text" placeholder="请输入菜单名称">
										<p>一级菜单字数不超过4个汉字或8个字母，二级菜单字数不超过8个汉字或16个字母</p>
									</div>
								</div>
								<br>
								<div class="clearfix">
									<div class="opt-lf col-xs-2">
										菜单内容
									</div>
									<div class="opt-rh col-xs-10">
										<input type="radio" checked="checked">
										&nbsp;
										跳转网页
									</div>
								</div>
							</div>
							<br>
							<div class="set-con clearfix">
								<div class="col-xs-12">
									<p class="con-tip">订阅者点击该子菜单会跳到以下链接</p>
									<p>
										<span>页面地址</span>
										&nbsp;&nbsp;
										<input class="txt-inp js-menuUrl" type="text">
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row" style="text-align: center;">
					<a class="gre-btn" href="javascript:updateWeiXinMenu()">保存并发布</a>
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
	var MENUDATA=${menuListStr};
	</script>
	<script src="${pageContext.request.contextPath}/work/pc/js/adminWeixinMenu.js"></script>
</body>
</html>
