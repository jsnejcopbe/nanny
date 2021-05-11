<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>资金明细</title>

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
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
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
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a>
							</li>
							<li class="active">资金明细</li>
						</ul>

						<h3 class="page-header">平台资金明细</h3>
					</div>
				</div>

				
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" >
								<form action="${pageContext.request.contextPath}/admin/adminAccount.html?pageIndex=1&pageSize=${rec.size}" method="post" id="uForm">
										 <input id="hide_type" type="hidden" value="${statu}" name="state"/>
									
										 <span id="state_query">日期：</span>  
										 <input type="text" class="ui-ipt" placeholder="请选择日期" id="logmin" name="logmin" value="${ lognmin }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
										  -
										  <input type="text" class="ui-ipt" placeholder="请选择日期" id="logmax" name="logmax" value="${ logbmax }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
										  
										  <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
								</form>
							</div>
							
							<div class="pull-right" >
								
									 <%--<span>日期：</span>  
									 <input type="text" class="ui-ipt" placeholder="请选择日期" id="logmin" name="logmin" value="${ lognmin }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  -
									  <input type="text" class="ui-ipt" placeholder="请选择日期" id="logmax" name="logmax" value="${ logbmax }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  
									  <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
                              
									<a href="javascript:void(0)" class="btn bg-purple text-white" data-id="-1">全部状态</a> 
									
									<a href="javascript:void(0)" class="btn btn-success" data-id="1">充值</a>
									
									<a href="javascript:void(0)" class="btn btn-info" data-id="3">提现</a>
									<a href="javascript:void(0)" class="btn btn-info" data-id="4">返现</a>
								--%>
								
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<c:if test="${statu==null || statu=='' || statu=='-1'}">全部状态</c:if>
									<c:if test="${statu=='1' }">充值</c:if>
									<c:if test="${statu=='3' }">提现</c:if>
									<c:if test="${statu=='4' }">返现</c:if>
									<c:if test="${statu=='5' }">提现退款</c:if>
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/admin/adminAccount.html?state=-1">全部</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/adminAccount.html?state=1">充值</a></li>
										
										<li><a href="${pageContext.request.contextPath}/admin/adminAccount.html?state=3">提现</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/adminAccount.html?state=4">返现</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/adminAccount.html?state=5">提现退款</a></li>
									</ul>
								</div>
								
							</div>
							
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover"
									id="shop_table">
									<thead>
										<tr>
											<th></th>
											<th>对方</th>
											<th>订单号</th>
											<th>金额</th>
											<th>类别</th>
											<th>时间</th>
											<th>描述</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${rec.rec}" var="rec">
									 <tr class="tr">
									 				<td style="vertical-align: middle;">
									 				<c:if test="${rec.userID==0}">

														<span><img alt="" src="${rec.oimg}" width="50px" style="float: right;">
															 </span>
													</c:if> <c:if test="${rec.otherSide==0}">
														<span><img alt="" src="${rec.uimg}" width="50px" style="float: right;">
															 </span>
													</c:if>
									 				</td>
					
												<td style="vertical-align: middle;"><c:if test="${rec.userID==0}">
														<span>${rec.oname} </span>
													</c:if> <c:if test="${rec.otherSide==0}">
														<span>${rec.uname}</span>
													</c:if></td>

												<td style="vertical-align: middle;"><span id="">${rec.orderCode}</span>
											</td>
											<td style="vertical-align: middle;">
											<c:if test="${rec.type==1 || rec.type==5}">
												<span>${rec.money}</span>
											</c:if>
											<c:if test="${rec.type==3||rec.type==4}">
												<span>-${rec.money}</span>
											</c:if>
											
											</td>
											<td style="vertical-align: middle;"> <label class="label bg-success" name="state">${rec.stats}</label>
											</td>
											<td style="vertical-align: middle;"><span id="">${rec.createTime}</span>
											</td>
											<td style="vertical-align: middle;">${rec.des}</td>
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
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
<script type="text/javascript">

new Pagin({
	size :${rec.size},
	perPage :5, 
	total  :${rec.total},
	nowPage:${rec.nowPage},
    dealFun:function(size,page){
    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/adminAccount.html?pageIndex="+page+"&pageSize="+size);
    $("#uForm").submit();
}
});

/* $("body").on("click","#state_query a",function(){
var id = $(this).attr("data-id");
$("#hide_type").val(id);

$("#state_query").find("form").submit();
})
 */
new g_fnImgCheck();
</script>

</body>
</html>
