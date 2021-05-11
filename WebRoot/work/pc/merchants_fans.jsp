<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户粉丝</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var shopID = "${loginUser.shopID }";
	var temp_shopID = "${temp_shopID }";
</script>

<style>
.input-group-addon {
	border-radius: 4px 0 0 4px;
}

#more_search {
	border-radius: 0 4px 4px 0;
}

#plot_select {
	width: 120px;
}

.headImg{
	width: 50px;
	height: 50px;
	border: 3px #5C7399 solid;
	border-radius: 50%;
}
</style>

<body>
	
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="head.jsp" />
		<!-- 页面主体内容 结束 -->

		<!-- 页面主体 开始 -->
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="menu.jsp" />

			<div class="content">

				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">商户粉丝</li>
						</ul>
						<h3 class="page-header">
							<span id="span_title">商户粉丝 </span>
						</h3>
					</div>
				</div>

				<!-- 商品列表-->
				<div class="row">
					<div class="col-md-12">
						<div id="fans_query" class="mail-options-nav clearfix">
							<form action="${pageContext.request.contextPath}/shop/fans/init.html">
								<div class="pull-left">
									<ul style="margin-left: 15px" class="list-inline">
										<li><span>小区:</span></li>
										<li><select class="form-control" name="memo" id="plot_select"></select></li>
										<li style="vertical-align: middle">
											<div class="input-group margin-bottom-sm">
												<span id="search_btn" class="input-group-addon">
													<i class="fa fa-search"></i>
												</span>
												<input class="form-control" id="more_search" name="more_search" type="text" placeholder="姓名,电话,qq">
											</div>
										</li>
										<li>
											<button class="btn btn-primary" type="submit">查询</button>
										</li>
										<c:if test="${loginUser.isAdmin == '1' }">
											<li><a href="${pageContext.request.contextPath}/admin/business.html" class="btn btn-default">返回</a></li>
										</c:if>
									</ul>
								</div>
							</form>
						</div>

						<div class="panel panel-archon panel-todo">
							<div class="panel-body com-pan">
								<table class="table table-striped table-bordered" id="fans_table">
									<thead>
										<tr>
											<th data-hide="id"></th>
											<th data-diy="headImg" width="60px">头像</th>
											<th data-value="nickName">姓名</th>
											<th data-value="tel">电话</th>
											<th data-value="qq">qq</th>
											<th data-value="mail">邮箱</th>
											<th data-value="point">用户积分</th>
											<th data-value="createTime">创建时间</th>
											<!-- <th data-diy="true">操作</th> -->
										</tr>
									</thead>
								</table>
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
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/proType.js"></script>

	<script src="${pageContext.request.contextPath}/work/pc/js/syl/merchants_fans.js"></script>
</body>
</html>