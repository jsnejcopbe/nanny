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
						<h3 class="page-header"> <i class="iconfont-gl">&#xe615;</i>用户订单</h3>
					</div>
				</div>
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
						   <div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form id="queryForm" action="${pageContext.request.contextPath}/admin/userOrderList.html" method="post">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;width: 300px;">
						                 <%-- <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="收货人姓名、手机号、订单号" value="${queryInput }" type="text" name="query" > --%>
						                 <input id="hide_type" type="hidden" value="${sta }" name="sta"/>
						                 <input id="hide_uid" type="hidden" value="${uid }" name="uid"/>
						               <!--   <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span> -->
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
									<c:if test="${sta==null || sta=='' }">全部状态</c:if>
									<c:if test="${sta=='0' }">待付款</c:if>
									<c:if test="${sta=='1' }">待发货</c:if>
									<c:if test="${sta=='2' }">已发货</c:if>
									<c:if test="${sta=='3'||sta=='4' }">已关闭</c:if>
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/aadmin/userOrderList.html?uid=${uid }">全部</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/userOrderList.html?sta=0&uid=${uid }">待付款</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/userOrderList.html?sta=1&uid=${uid }">待发货</a></li>
										<li><a href="${pageContext.request.contextPath}/admin/userOrderList.html?sta=2&uid=${uid }">已发货</a></li>
										<li><a href="${pageContext.request.contextPath}/aadmin/userOrderList.html?sta=3&uid=${uid }">已关闭</a></li>
									</ul>
									
								</div >
									&nbsp;&nbsp;
									<div class="fresh">
									 	<a class="btn btn-info" href="${pageContext.request.contextPath}/admin/clientele.html">返回</a>
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
							
							<c:forEach items="${uorder.order}" var="od">
							<div class="panel-body">
								<div class="col-md-12 or-it">
									<div class="it-tit">
										<span>${od.createTime}</span>
										&nbsp;
										<span>订单号：${od.orderCode }</span>
										&nbsp;&nbsp;&nbsp;
										<span><a href="${pageContext.request.contextPath}/admin/toshopplat.html?shopId=${od.recShopID}" style="color: rgb(42, 117, 140); margin-left: 10px;">${od.shop_name}</a></span>
										<div class="pull-right">
											<a class="btn btn-info" href="${pageContext.request.contextPath}/admin/userOrDetails.html?uid=${uid }&orid=${od.id}">查看详情</a>
											
										</div>
									</div>
									<div class="it-con">
										<table class="todo-table table table-bordered">
											<tbody>
												<c:forEach items="${od.company }" var="det" varStatus="detcount">
												<tr class="tr">
													<td class="col-md-5">
														<div class="col-xs-3 con-img">
															<img src="${det.cover }">
														</div>
														<div class="col-xs-9 con-name">
															${det.name }
															<c:if test="${det.memo!='0'}">
							             							<p style="color:#E70012;">此商品为积分兑换</p>
							             					</c:if>
														</div>
													</td>
													<td class="col-md-2">
														<c:if test="${det.memo!='0'}">
										             	${det.point }</c:if>
								             			<c:if test="${det.memo=='0'}">
										             	¥${det.price }</c:if>
													</td>   
													<td class="col-md-2">
														${det.count }
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
													<c:if test="${od.vcID!=0 }">
													<p style="text-align: center;color: #E70012;">抵用金额：${od.svMoney }元</p>
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
												</c:forEach>
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
<!--     <script src="${pageContext.request.contextPath}/js/adutio.js"></script> -->
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
<!-- 	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script> -->
	<script type="text/javascript">
	var time=24;

	var BASEPATH = "${pageContext.request.contextPath}";
	var shid="${shid}";
	new Pagin({
		    size :${uorder.size},
		    perPage :5, 
		    total  :${uorder.total},
		    nowPage:${uorder.nowPage},
		    dealFun:function(size,page){
		    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
		    $("#queryForm").attr("action","${pageContext.request.contextPath}/admin/userOrderList.html?pageIndex="+page+"&pageSize="+size);
		    $("#queryForm").submit();
		}
	});

	</script>
	
</body>
</html>
