<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE HTML>
<html>
<head>
<title>抵用券明细</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css"
	rel="stylesheet" type="text/css">
<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="js/html5shiv.js"></script>
	  <![endif]-->
<style>
a {
	cursor: pointer;
}

th {
	text-align: center;
}

tr {
	height: 45px !important;
	text-align: center !important;
}

td {
	text-align: center;
}

.mybtns {
	margin-left: 10px;
}


.com-pan {
	border: 1px solid;
	border: 1px solid #B9C4D5;
}

.mail-options-nav {
	padding: 10px;
	border: 1px solid #B9C4D5;
	border-bottom: none !important;
	background-color: #F5F5F5;
}
</style>
<script type="text/javascript">
	var path = "/nanny/";
</script>
</head>

<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->


		<!-- 头部内容 -->
		<jsp:include page="head.jsp" />
		<!-- 头部 结束 -->


		<div class="box-holder">
			<!-- 左侧菜单 -->


			<!-- 左侧菜单 -->

			<jsp:include page="menu.jsp" />

			<!-- 左侧菜单 结束-->


			<div class="content">
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
						<c:if test="${isAdmin==1 }">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a>
							</c:if>
							<c:if test="${isAdmin==0 }">
								<li><a href="${pageContext.request.contextPath}/shop/shopIndex.html">首页</a>
							</c:if>
							</li>
							<li class="active">抵用券明细</li>
						</ul>

						<h3 class="page-header">抵用券明细</h3>
					</div>
				</div>

				
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" >
							<c:if test="${isAdmin==1 }">
								<form action="${pageContext.request.contextPath}/admin/coupon.html?pageIndex=1&pageSize=${jso.pageSize}" method="post" id="uForm">
									</c:if>
							<c:if test="${isAdmin==0 }">
								<form action="${pageContext.request.contextPath}/shop/shopCoupon.html?pageIndex=1&pageSize=${jso.pageSize}" method="post" id="uForm">
									</c:if>
									
									 <input id="hide_type1" type="hidden" value="${status}" name="status"/>
									 <span>日期：</span>  
									 <input type="text" class="ui-ipt" placeholder="请选择开始日期" id="logmin" name="logmin" value="${ logmin }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  -
									 <input type="text" class="ui-ipt" placeholder="请选择结束日期" id="logmax" name="logmax" value="${ logmax }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  
									  <c:if test="${isAdmin==1 }">
									 <input type="text" class="ui-ipt" placeholder="商户名、用户名、手机号码" name="queryName" value="${queryName}"> 
									</c:if>
									  <c:if test="${isAdmin==0 }">
									 <input type="text" class="ui-ipt" placeholder="用户名、手机号码" name="queryName" value="${queryName}"> 
									</c:if>
									  
									 <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
									<!--  <button class="btn btn-info" id="add" type="submit" style="margin: 0 0 0 300px" data-toggle="modal" data-target="#myPass1">新增</button> -->
								</form>
							</div>
					
							<%-- <div class="pull-right" id="state_query">
								
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									
									<c:if test="${status==null || status=='' || status=='-1'}">全部状态</c:if>
									<c:if test="${status=='0' }">未使用</c:if>
									<c:if test="${status=='1' }">已使用</c:if>
									<c:if test="${status=='2' }">已过期</c:if>
									
									
									
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu" id="qqq">
										<li><a href="javascript:void(0)" data-id="-1">全部</a></li>
										<li><a href="javascript:void(0)" data-id="0">未使用</a></li>
										<li><a href="javascript:void(0)" data-id="1">已使用</a></li>
										<li><a href="javascript:void(0)" data-id="2">已过期</a></li>
										
									</ul>
								</div>
							</div> --%>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover"
									id="shop_table">
									<thead>
										<tr>
									
											<!-- <th>兑换券</th> -->
											<th>兑换码</th>
											<th>兑换金额</th>
											<th>使用日期</th>
											<th>用户</th>
											<!-- <th>状态</th> -->
										
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${jso.arr}" var="arr">
									 <tr class="tr">
									 
									 				<%-- <td style="vertical-align: middle;">
									 					
															${arr.name }
									 				</td> --%>
									 				<td style="vertical-align: middle;">
									 					
															${arr.vouCode }
									 				</td>
									 				<td style="vertical-align: middle;">
									 					
															￥${arr.money }
									 				</td>
									 				<td style="vertical-align: middle;">
									 					
															${arr.usageTime}
									 				</td>
									 				<td style="vertical-align: middle;">
									 				<img alt="" src="${arr.headImg}" width="50px">
															${arr.nickName }
									 				</td>
									 			<%-- 	<td style="vertical-align: middle;">
									 					<c:if test="${arr.status=='0' }">未使用</c:if>
									 					<c:if test="${arr.status=='1' }">已使用</c:if>
									 					<c:if test="${arr.status=='2' }">已过期</c:if>	
									 				</td> --%>
									 			
				
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="pagin-area" style="text-align: center;">
									<div class="paging_bootstrap pagination js-pagin" style="margin: 0px;"></div>
										</div>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 页面主体内容 结束 -->
		
		</div>


	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
<script type="text/javascript">

new Pagin({
	size :${jso.pageSize},
	perPage :5, 
	total  :${jso.total},
	nowPage:${jso.pageIndex},
    dealFun:function(size,page){
     if(${isAdmin}=='1'){
    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/coupon.html?pageIndex="+page+"&pageSize="+size);
    }
    if(${isAdmin}=='0'){
     $("#uForm").attr("action","${pageContext.request.contextPath}/shop/shopCoupon.html?pageIndex="+page+"&pageSize="+size);
    }
    $("#uForm").submit();
}
});

new g_fnImgCheck();


	$("body").on("click","#qqq a",function(){
	var id=$(this).attr("data-id");
	$("#hide_type1").val(id);
	$("#shop_query").find("form").submit();
	
	});
	
</script>

</body>
</html>
