<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>我的订单</title>

<meta name="renderer" content="webkit">
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
							<li class="active">店铺订单管理</li>
						</ul>
						<h3 class="page-header"> <i class="iconfont-gl">&#xe615;</i> 店铺订单管理</h3>
					</div>
				</div>
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
						   <div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form id="queryForm" action="${pageContext.request.contextPath}/shop/order-1.html">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;width: 300px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="收货人姓名、手机号、订单号" value="${queryInput }" type="text" name="query" >
						                 <input id="hide_type" type="hidden" value="${sta }" name="sta"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							
							<div class="pull-right" id="state_query">
								<div class="fresh">
									<a href="javascript:void(0)"><i class="fa fa-repeat"></i> <span class="js-fr-time">24</span>秒后刷新</a>
								</div>
								&nbsp;
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<c:if test="${sta==null || sta=='' }">全部状态</c:if>
									<c:if test="${sta=='0' }">待付款</c:if>
									<c:if test="${sta=='1' }">待发货</c:if>
									<c:if test="${sta=='2' }">已发货</c:if>
									<c:if test="${sta=='a' }">已关闭</c:if>
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/shop/order-1.html">全部</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/order-1.html?sta=0">待付款</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/order-1.html?sta=1">待发货</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/order-1.html?sta=2">已发货</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/order-1.html?sta=a">已关闭</a></li>
									</ul>
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
							
							<c:forEach items="${ orderData.data}" var="od">
							<div class="panel-body">
								<div class="col-md-12 or-it">
									<div class="it-tit">
										<span>${od.createTime}</span>
										&nbsp;
										<span>订单号：${od.orderCode }</span>
										<div class="pull-right">
											<a class="btn btn-info" href="${pageContext.request.contextPath}/shop/order-det-${od.orderCode }.html">查看详情</a>
											<c:if test="${od.status=='1' }">
											<a class="btn btn-warning" href="javascript:orderConfirm(${od.id },2)">确认并发货</a>
											</c:if>
											<c:if test="${od.status=='1' || od.status=='2'}">
											<%-- <a class="btn btn-danger" href="javascript:orderRefund(${od.id },4,${od.totalPrice })">取消订单</a> --%>
											<a class="btn btn-danger" href="javascript:shopmsg1(${od.id })">取消订单</a>
											
											</c:if>
										</div>
									</div>
									<div class="it-con">
										<table class="todo-table table table-bordered">
											<tbody>
												<c:forEach items="${od.detList }" var="det" varStatus="detcount">
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
										             	${det.point }积分</c:if>
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
													<c:if test="${od.vcID!='0'}">
													${od.svName}<%-- (编号:${od.svCode}) --%>
													<p style="text-align: center;color: #E70012;">-¥${od.svMoney}元</p>
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
							<%--  <div id="shmsg_${od.id }" style="display: none;">
									<div style="text-align: left;margin-left:25%;margin-top: 10px;">
										<p><input type="radio" value="超过配送范围" checked="checked" name="smsg_${od.id }">超过配送范围 </p>
										 
										<p><input type="radio" value="一个人走不开"  name="smsg_${od.id }">一个人走不开</p>
									
										<p><input type="radio" value="商品没有库存" name="smsg_${od.id }">商品没有库存</p>
										<p><span style="display: block;"><input class="rad" type="radio" value="1" name="smsg_${od.id }">
										其它：</span>
										<textarea name="inp_${od.id }" ></textarea></p>
									</div>
									<div class="pull-right" style="padding-bottom: 10px;padding-right: 10px;">
										<a class="btn btn-danger" href="javascript:orderRefund(${od.id },4,${od.totalPrice })">确定</a>
									</div>
								</div> 
								 --%>
								
								<div class="modal fade" id="myModal_${od.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px;height: 270px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">取消订单</h4>
				</div>
				<form action="" id="editba">
				<div class="modal-body" >
						<div class="panel-body">

							<div id="sb" class="form-horizontal">
									
								<div style="text-align: left;margin-left:25%;margin-top: 10px;">
										<p><input type="radio" value="超过配送范围" checked="checked" name="smsg_${od.id }">超过配送范围 </p>
										 
										<p><input type="radio" value="一个人走不开"  name="smsg_${od.id }">一个人走不开</p>
									
										<p><input type="radio" value="商品没有库存" name="smsg_${od.id }">商品没有库存</p>
										<p><span style="display: block;"><input class="rad" type="radio" value="1" name="smsg_${od.id }">
										其它：</span>
										<textarea name="inp_${od.id }" ></textarea></p>
								</div>

							</div>
						</div>
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="orderRefund(${od.id },4,${od.totalPrice })">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
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
	
	<!-- 隐藏表单 -->
	<form id="printForm" action="${pageContext.request.contextPath}/printpage.html" method="post" style="display: none;">
		<input id="printProMsg" name="printProMsg" type="hidden" value="">
	</form>
	
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
	var BASEPATH = "${pageContext.request.contextPath}";
	new Pagin({
		    size :${orderData.size},
		    perPage :5, 
		    total  :${orderData.total},
		    nowPage:${orderData.nowPage},
		    dealFun:function(size,page){
		    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
		    $("#queryForm").attr("action","${pageContext.request.contextPath}/shop/order-"+page+".html");
		    $("#queryForm").submit();
		}
	});
	
	function shopmsg1(id){
		$("#myModal_"+id).modal('show');
	}
	</script>
	<script src="${pageContext.request.contextPath}/work/pc/js/shopOrderList.js?v=0.01"></script>
	
</body>
</html>
