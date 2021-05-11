<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE HTML>
<html>
<head>

<title>店铺群发信息管理</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/work/pc/css/shopMsgRec.css" rel="stylesheet" type="text/css">
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
							<li class="active">店铺群发信息管理</li>
						</ul>
					</div>
				</div>
				
				<!-- 主体内容 开始 -->
				<div class="row">
					<div class="col-xs-7">
						<div class="panel panel-archon panel-todo">
							<div class="panel-body">
								<h2 class="pa-title">店铺群发信息管理</h2>
								<div class="pa-search clearfix">
									<form id="queryForm" action="${pageContext.request.contextPath}/" method="post">
										<div class="input-group col-xs-4">
											<input name="time" placeholder="输入日期" class="form-control" type="text" value="" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
										</div>
										<div class="input-group col-xs-8">
				                            <input name="name" placeholder="查找指定商家" class="input form-control" type="text" value="">
				                            <span class="input-group-btn">
		                                        <a href="javascript:void(0)" class="btn btn btn-primary js-doquery"> <i class="iconfont-gl">&#x3432;</i> 搜索</a>
			                                </span>
		                                </div>
									</form>
								</div>
								
								<!-- 左侧商户列表 -->
								<table class="table table-striped table-hover con-list">
									<tbody>
										<tr>
											<td class="con-img">
												<img src="http://wx.qlogo.cn/mmopen/yeh5DdCuj3YAaggnicBJf51pbibKGraLnkk6G4AMcTKK4xM4G1icnoJqZZfPgPzJUrQjDnejmD1t9ySh3ekuzJ2DicqK9lZVWTiab/0">
											</td>
											<td class="con-name">
												<span>陈伙秀</span>
											</td>
											<td class="con-shop">逸秀便利店</td>
											<td>
												<a href="javascript:void(0)" class="js-checkshop label label-primary" data-shop="">点击查看</a>
											</td>
										</tr>
										<tr>
											<td class="con-img">
												<img src="http://wx.qlogo.cn/mmopen/yeh5DdCuj3YAaggnicBJf51pbibKGraLnkk6G4AMcTKK4xM4G1icnoJqZZfPgPzJUrQjDnejmD1t9ySh3ekuzJ2DicqK9lZVWTiab/0">
											</td>
											<td class="con-name">
												<span>陈伙秀</span>
											</td>
											<td class="con-shop">逸秀便利店</td>
											<td>
												<a href="javascript:void(0)" class="js-checkshop label label-primary" data-shop="">点击查看</a>
											</td>
										</tr>
										<tr>
											<td class="con-img">
												<img src="http://wx.qlogo.cn/mmopen/yeh5DdCuj3YAaggnicBJf51pbibKGraLnkk6G4AMcTKK4xM4G1icnoJqZZfPgPzJUrQjDnejmD1t9ySh3ekuzJ2DicqK9lZVWTiab/0">
											</td>
											<td class="con-name">
												<span>陈伙秀</span>
											</td>
											<td class="con-shop">逸秀便利店</td>
											<td>
												<a href="javascript:void(0)" class="js-checkshop label label-primary" data-shop="">点击查看</a>
												<a href="javascript:void(0)" class="js-checkshop label label-warning" data-shop="">取消权限</a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
					<div class="col-xs-5 msg-stm">
						<div class="clearfix stm-item">
							<div class="it-left col-xs-2">
								<img src="http://wx.qlogo.cn/mmopen/yeh5DdCuj3YAaggnicBJf51pbibKGraLnkk6G4AMcTKK4xM4G1icnoJqZZfPgPzJUrQjDnejmD1t9ySh3ekuzJ2DicqK9lZVWTiab/0">
							</div>
							<div class="it-right col-xs-10">
								<p class="na-cl">
									<span>逸秀便利店</span>
									&nbsp;
									<span>2016-05-06</span>
								</p>
								<p>新技术新概念很多，而且有了新定律：前端开发每18月会难一倍 </p>
							</div>
						</div>
						<div class="clearfix stm-item">
							<div class="it-left col-xs-2">
								<img src="http://wx.qlogo.cn/mmopen/yeh5DdCuj3YAaggnicBJf51pbibKGraLnkk6G4AMcTKK4xM4G1icnoJqZZfPgPzJUrQjDnejmD1t9ySh3ekuzJ2DicqK9lZVWTiab/0">
							</div>
							<div class="it-right col-xs-10">
								<p class="na-cl">
									<span>逸秀便利店</span>
									&nbsp;
									<span>2016-05-06</span>
								</p>
								<p>新技术新概念很多，而且有了新定律：前端开发每18月会难一倍 </p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
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
</body>
</html>
