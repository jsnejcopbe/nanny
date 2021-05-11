<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
</script>
<head>

<title>我的订单</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shoporderlist.css">
</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/users/userIndex.html" class="col-xs-3 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-6">
				我的订单
			</div>
			<%--<a href="javascript:void(0)" class="col-xs-3 sh-icon-right">
				<i class="iconfont-gl">&#x3432;</i>
			</a>
		--%></div>
		<div class="top-tab clearfix">
			<a href="${pageContext.request.contextPath}/users/userOder.html" class="col-xs-2 <c:if test="${sta==null }">selected</c:if>">全部</a>
			<a href="${pageContext.request.contextPath}/users/userOder.html?sta=0" class="col-xs-2 <c:if test="${sta=='0' }">selected</c:if>">待付款</a>
			<a href="${pageContext.request.contextPath}/users/userOder.html?sta=1" class="col-xs-2 <c:if test="${sta=='1' }">selected</c:if>">待发货</a>
			<a href="${pageContext.request.contextPath}/users/userOder.html?sta=2" class="col-xs-2 <c:if test="${sta=='2' }">selected</c:if>">已发货</a>
			<a href="${pageContext.request.contextPath}/users/userOder.html?sta=3" class="col-xs-2 <c:if test="${sta=='3' }">selected</c:if>">已关闭</a>
			 
		</div>
	</nav>
	<div class="space80"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container sh-pro">
	
   	 <%-- <c:forEach items="${order.order}" var="od">
		<div class="row pro-it">
			<div class="col-xs-12 it-tit clearfix">
				<span class="tit-code">订单号:${od.orderCode }</span>
				<span class="tit-sta">${od.staName }</span>
				</div>
			<c:forEach items="${od.company }" var="det">
			<div class="col-xs-12 it-con">
				<div class="col-xs-3 con-img">
					<img src="${det.cover }">
				</div>
				  <c:if test="${det.memo=='0'}">
						<div class="col-xs-7 con-des">
							${det.name }
						</div>
						<div class="col-xs-2 con-pri">
							<p>¥${det.price }</p>
							<p>X${det.count }</p>
						</div>
				</c:if>
				 <c:if test="${det.memo!='0'}">
						<div class="col-xs-7 con-des">
							${det.name}
							<p style="color:#E70012">此商品为积分兑换</p>
						</div>
						<div class="col-xs-2 con-pri">
							<p>${det.point }</p>
							<p>X${det.count }</p>
						</div>
				</c:if>
			</div>
			</c:forEach>
			<div class="it-opt col-xs-12">
				<c:if test="${od.vcID!='0'}">
				<div class="opt-msg"> ${od.svName}<span>-¥${od.svMoney}</span>元</div>
				</c:if>
				<div class="opt-msg"> 实付<span>¥${od.totalPrice}</span>元(含配送费${od.fee}元)</div>
			</div>
			<div class="it-btn col-xs-12">
				<a href="${pageContext.request.contextPath}/users/orDetails.html?orid=${od.id }">查看详情</a>
				<c:if test="${od.status=='0' }">
				<a class="red-btn" href="javascript:orderpayment(${od.id })">付款</a>
				</c:if>
				<c:if test="${od.status=='2' }">
				<a class="red-btn" href="javascript:orderConfirm(${od.id })">确认收货</a>
				</c:if>
				<c:if test="${od.status=='1'}">
				<a href="javascript:orderRefund(${od.id })">取消订单</a>
				</c:if>
			</div>
		</div>
		</c:forEach>     --%>
		
	</div>
	<!-- 主体内容 结束 -->
	
	
	<!-- 隐藏表单 -->
	<input id="status" type="hidden" value="${sta }" >
	
		<input type="hidden" id="pageIndex" value="">
			<input type="hidden" id="pageSize" value="">
			<div class="pancel" >
				<div class="panel-body">
					<div class="row pagin-area">
						<a href="javascript:page();" style="margin-left: 40%; color: #4A4A4A" data-page="1">加载更多</a>
					</div>
				</div>
			</div>
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/usersOrder1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/usersorder.js"></script>
</body>
</html>
