<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
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
	<nav class="sh-top dtop">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/shop/shopIndex.html" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-10 con-sec">
				<div class="sec-inp">
					<a href="javascript:closeQuery()"><i class="icon-remove-circle"></i></a>
					<input id="query" name="query" type="text" placeholder="订单号、手机号、收件人姓名">
				</div>
				<a class="sec-btn" href="javascript:doQuery()"><i class="iconfont-gl">&#x3432;</i>查询</a>
			</div>
			<div class="col-xs-8 sh-cen">
				我的订单
			</div>
			<a href="javascript:showQuery()" class="col-xs-2 sh-icon-right">
				<i class="iconfont-gl">&#x3432;</i>
			</a>
		</div>
		<div class="top-tab clearfix">
			<a href="${pageContext.request.contextPath}/shop/order-1.html" class="col-xs-2 <c:if test="${sta==null }">selected</c:if>">全部</a>
			<a href="${pageContext.request.contextPath}/shop/order-1.html?sta=0" class="col-xs-2 <c:if test="${sta=='0' }">selected</c:if>">待付款</a>
			<a href="${pageContext.request.contextPath}/shop/order-1.html?sta=1" class="col-xs-2 <c:if test="${sta=='1' }">selected</c:if>">待发货</a>
			<a href="${pageContext.request.contextPath}/shop/order-1.html?sta=2" class="col-xs-2 <c:if test="${sta=='2' }">selected</c:if>">已发货</a>
			<a href="${pageContext.request.contextPath}/shop/order-1.html?sta=a" class="col-xs-2 <c:if test="${sta=='a' }">selected</c:if>">已关闭</a>
		</div>
	</nav>
	<nav class="sh-top" style="display: none;" id="top">
		<div class="top-con clearfix">
			
			
			<div class="col-xs-12 sh-cen">
				退款理由
			</div>
			
		</div>
		
	</nav>
	<div class="space80"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container sh-pro">
	
		<c:forEach items="${ orderData.data}" var="od">
		<div class="row pro-it" style="display:block;">
			<div class="col-xs-12 it-tit clearfix">
				<span class="tit-code">订单号:${od.orderCode }</span>
				<span class="tit-sta">${od.staName }</span>
			</div>
			<c:forEach items="${od.detList }" var="det">
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
							<p style="color:  #E70012;">此商品为积分兑换</p>
						</div>
						<div class="col-xs-2 con-pri">
							<p>${det.point }积分</p>
							<p>X${det.count }</p>
						</div>
				</c:if>
				</div>
			</c:forEach>
			<div class="it-opt col-xs-12">
				<c:if test="${od.vcID!='0'}">
					<div class="opt-msg"> ${od.svName}<span>-¥${od.svMoney}</span>元</div>
				</c:if>
				<div class="opt-msg">实付<span>¥${od.totalPrice }</span>元(含配送费${od.fee }元)
				<c:if test="${od.memo!='null' }">,<span style="font-size: 14px;color: #E70012;">${od.memo }</span></c:if>
				<c:if test="${od.memo=='null' }">,<span style="font-size: 14px;color: #E70012;">在线付款</span></c:if>
				</div>
			</div>
			<div class="it-btn col-xs-12">
				<a href="${pageContext.request.contextPath}/shop/order-det-${od.orderCode }.html">查看详情</a>
				<c:if test="${od.status=='1' }">
				<a class="red-btn" href="javascript:orderConfirm(${od.id },2)">确认并发货</a>
				</c:if>
				<c:if test="${od.status=='1' || od.status=='2'}">
				<%-- <a href="javascript:orderRefund(${od.id },4,${od.totalPrice })">取消订单</a> --%>
				<a  href="javascript:shopmsg(${od.id })">取消订单</a>
				</c:if>
			</div>
		</div>
		<div id="shmsg_${od.id }" style="display: none;">
			
			<div class="" style="text-align: left;font-size: 18px;margin-left:25%;">
				<p><input class="rad" type="radio" value="超过配送范围" checked="checked" name="smsg_${od.id }">超过配送范围 </p>
										 
				<p><input class="rad" type="radio" value="一个人走不开"  name="smsg_${od.id }">一个人走不开</p>
									
				<p><input class="rad" type="radio" value="商品没有库存" name="smsg_${od.id }">商品没有库存</p>
				
				<p><span style="display: block;"><input class="rad" type="radio" value="1" name="smsg_${od.id }">
				其它：</span>
				<textarea name="inp_${od.id }" rows="4"></textarea>
				</p>
			</div>
			<div style="text-align: center;padding-bottom: 10px;padding-right: 10px;">
				<a class="btn btn-danger" href="javascript:orderRefund(${od.id },4,${od.totalPrice })">确定</a>
				&nbsp;&nbsp;&nbsp;
				<a class="btn btn-danger" href="javascript:clearmsg(${od.id })">取消</a>
			</div>
		</div>
		</c:forEach>
		
		<div class="row js-more" <c:if test="${orderData.total>20 }">style="display:block"</c:if>>
			<a href="javascript:pagin()">加载更多...</a>
		</div>
	</div>
	<!-- 主体内容 结束 -->
	
	<!-- 隐藏表单 -->
	<input id="status" type="hidden" value="${sta }">
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shoporderlist.js"></script>
</body>
</html>
