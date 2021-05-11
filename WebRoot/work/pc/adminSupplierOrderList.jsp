<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>我的订单</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
<![endif]-->
<link href="${pageContext.request.contextPath}/work/pc/css/shopOrderList.css" rel="stylesheet" type="text/css">
</head>
<style>
a {
	cursor: pointer;
}

th{
	text-align: center;
}

tr{
	height: 45px !important;
}

td{
	text-align: center;
}

.mybtns{
	margin-left: 10px;
}
.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
.mail-options-nav{
	padding: 10px;
	border: 1px solid #B9C4D5;
	border-bottom:none!important;
	background-color: #F5F5F5;
}
</style>

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
							<li class="active">店铺订单列表</li>
						</ul>
						<h3 class="page-header"> <i class="iconfont-gl">&#xe615;</i> 店铺订单</h3>
					</div>
				</div>
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
						   <div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form  action="${pageContext.request.contextPath}/admin/supplierOrder.html?pageIndex=1&pageSize=10&supplierId=${jso.supplierId}" method="post" id="sform">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;width: 300px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="订单号、商品名称" value="${jso.query }" type="text" name="query" >
						                 <input id="hide_type" type="hidden" value="${jso.sta }" name="sta"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							
							<div class="pull-right" id="state_query">
								<%--<div class="fresh">
									<a href="javascript:void(0)"><i class="fa fa-repeat"></i> <span class="js-fr-time">24</span>秒后刷新</a>
								</div>
								&nbsp;
								--%><div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<c:if test="${jso.sta==null || jso.sta=='' }">全部状态</c:if>
									<c:if test="${jso.sta=='0' }">未确认</c:if>
									<c:if test="${jso.sta=='1' }">已确认</c:if>
									<c:if test="${jso.sta=='2' }">已取消</c:if>
						
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/admin/supplierOrder.html?supplierId=${jso.supplierId}">全部</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/supplierOrder.html?sta=0&supplierId=${jso.supplierId}">未确认</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/supplierOrder.html?sta=1&supplierId=${jso.supplierId}">已确认</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/supplierOrder.html?sta=2&supplierId=${jso.supplierId}">已取消</a></li>
										
									</ul>
								</div >
									&nbsp;&nbsp;
									<div class="fresh">
									 	<a class="btn btn-info" href="${pageContext.request.contextPath}/admin/supplier.html">返回</a>
									</div>
							</div>
						</div>
						
						<div class="panel com-pan">
							<div class="panel-body">
								<table class="todo-table table table-bordered top-border">
									<thead>
										<tr class="header-row">
							               <th class="visible-lg col-md-5">订单商品</th>
							               <th class="visible-lg col-md-2">单价(元)</th>
							               <th class="visible-lg col-md-2">数量</th>
							               <th class="visible-lg col-md-2">实付款</th>
							               <th class="visible-lg col-md-1">交易状态</th>
							           </tr>
									</thead>
								</table>
							</div>
							
							<c:forEach items="${ orderList}" var="od">
							<div class="panel-body">
								<div class="col-md-12 or-it">
									<div class="it-tit">
										<span>${od.spTime}</span>
										&nbsp;
										<span>订单号：${od.orderCode }</span>
										<div class="pull-right">
											<a class="btn btn-info" href="${pageContext.request.contextPath}/admin/supplierOrderDet.html?suOrderId=${od.suOrderId }">查看详情</a>
											
										</div>
									</div>
									<div class="it-con">
										<table class="todo-table table table-bordered">
											<tbody>
											<%-- 	<c:forEach items="${od.detList }" var="det" varStatus="detcount"> --%>
												<tr class="tr">
													<td class="col-md-5">
														<div class="col-xs-3 con-img">
															<img src="${od.cover }">
														</div>
														<div class="col-xs-9 con-name">
															${od.name }
														</div>
													</td>
													<td class="col-md-2">
														¥${od.price }
													</td>
													<td class="col-md-2">
														${od.count }
													</td>
													<c:if test="${detcount.count==1 }">
													<td class="col-md-2  td-total">
													¥${od.totalPrice }元(含配送费${od.fee }元)
													
													<c:if test="${od.memo!='null' }">
													<p style="text-align: center;color: #E70012;">${od.memo }</p>
													</c:if>
													<c:if test="${od.memo=='null' }">
													<p style="text-align: center;color: #E70012;">在线付款</p>
													</c:if>
													
													</td>
													</c:if>
													
													<c:if test="${detcount.count!=1 }">
													<td class="col-md-2  td-total-none"></td>
													</c:if>
													
													<c:if test="${detcount.count==1 }">
													<td class="col-md-1  td-total">${od.staName }</td>
													</c:if>
													
													<c:if test="${detcount.count!=1 }">
													<td class="col-md-1  td-total-none"></td>
													</c:if>
												</tr>
												<%-- </c:forEach> --%>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							</c:forEach>
							
							<div class="btn-area pagin-area">
								<div class="paging_bootstrap pagination js-pagin"></div>
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
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	var time=24;

	
	new Pagin({
		size :${jso.size},
    	perPage :5, 
    	total  :${jso.total},
    	nowPage:${jso.nowpage},
	    dealFun:function(size,nowPage){
	    $("#sform").attr("action","${pageContext.request.contextPath}/admin/supplierOrder.html?pageIndex="+nowPage+"&pageSize="+size+"&supplierId=${jso.supplierId}");
	    $("#sform").submit();
	}
});
	

	</script>
	
</body>
</html>
