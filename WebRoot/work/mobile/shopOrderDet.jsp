<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>订单详情</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shopOrderDet.css">
</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top dtop">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/shop/order-1.html" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-8 sh-cen">
				订单详情
			</div>
		</div>
	</nav>
	<nav class="sh-top" style="display: none;" id="top">
		<div class="top-con clearfix">
			
			
			<div class="col-xs-12 sh-cen">
				退款理由
			</div>
			
		</div>
		
	</nav>
	<div class="space43"></div>
	<!-- 头部 结束 -->
	
	<!-- 页面主体 开始 -->
	<div class="container">
		<!-- 订单状态 -->
		<div class="row or-sta">
			<p>
				<i class="iconfont-gl">&#xe6d5;</i>
				&nbsp;&nbsp;
				<span>订单状态：${orderBase.staName }</span>
			</p>
		</div>
		
		<!-- 订单基础信息 -->
		<div class="row or-bamsg">
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe62b;</i>&nbsp;
					收货人:
				</div>
				<div class="col-xs-8 item-con">
					${orderBase.recName }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe633;</i>&nbsp;
					联系电话:
				</div>
				<div class="col-xs-8 item-con">
					${orderBase.recTel }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe60e;</i>&nbsp;
					收货地址:
				</div>
				<div class="col-xs-8 item-con">
					${orderBase.address }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe630;</i>&nbsp;
					下单时间:
				</div>
				<div class="col-xs-8 item-con">
					${orderBase.createTime }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe627;</i>&nbsp;
					付款方式:
				</div>
				<div class="col-xs-8 item-con">
					<c:if test="${orderBase.memo!='null' }">
					货到付款
					</c:if>
					<c:if test="${orderBase.memo=='null' }">
					在线付款
					</c:if>
				</div>
			</div>
		</div>
		
		<!-- 订单留言 -->
		<div class="row pro-it">
			<div class="col-xs-12 it-tit clearfix">
				<i class="iconfont-gl">&#xe62c;</i>&nbsp;
				<span class="tit-code" style="float: none;">订单留言</span>
			</div>
			<div class="col-xs-12 it-con">
					${orderBase.message }
			</div>
		</div>
		
		<!-- 订单商品列表 -->
		<div class="row pro-it">
			<div class="col-xs-12 it-tit clearfix">
				<span class="tit-code">订单号:${orderBase.orderCode }</span>
			</div>
			
			<c:forEach items="${orderDet }" var="od">
			<div class="col-xs-12 it-con">
				<div class="col-xs-3 con-img">
					<img src="${od.cover }">
				</div>
				 <c:if test="${od.memo=='0'}">
						<div class="col-xs-7 con-des">
							${od.name }
						</div>
						<div class="col-xs-2 con-pri">
							<p>¥${od.price }</p>
							<p>X${od.count }</p>
						</div>
				</c:if>
				 <c:if test="${od.memo!='0'}">
						<div class="col-xs-7 con-des">
							${od.name}
							<p style="color:#E70012;">此商品为积分兑换</p>
						</div>
						<div class="col-xs-2 con-pri">
							<p>${od.point }积分</p>
							<p>X${od.count }</p>
						</div>
				</c:if>
		
			</div>
			</c:forEach>
			
			<div class="it-opt col-xs-12">
				<c:if test="${orderBase.vcID!='0'}">
					<div class="opt-msg"> ${orderBase.svName}<span>-¥${orderBase.svMoney}</span>元</div>
				</c:if>
				<div class="opt-msg"> 合计<span>¥${orderBase.totalPrice }</span>元(含配送费${orderBase.fee }元)</div>
			</div>
		</div>
	</div>
	
	<!-- 操作按钮 商户使用 -->
	<div class="space64"></div>
	<div class="or-opt">
		<!--<a class="nor-btn" href="javascript:void(0)">发货</a>
		<a class="dan-btn" href="javascript:void(0)">退款</a>  -->
	</div>
	<!-- 页面主体 结束 -->
	
	<!-- 隐藏表单 -->
	<input id="status" type="hidden" value="${orderBase.status }">
	<input id="orderID" type="hidden" value="${orderBase.id }">
	<input id="totalprice" type="hidden" value=${orderBase.totalPrice }>
	
	<div id="shmsg_${orderBase.id }" style="display: none;">
			
			<div class="" style="text-align: left;font-size: 18px;margin-left:25%;">
				<p><input class="rad" type="radio" value="超过配送范围" checked="checked" name="smsg_${orderBase.id }">超过配送范围 </p>
										 
				<p><input class="rad" type="radio" value="一个人走不开"  name="smsg_${orderBase.id }">一个人走不开</p>
									
				<p><input class="rad" type="radio" value="商品没有库存" name="smsg_${orderBase.id }">商品没有库存</p>
				
				<p><span style="display: block;"><input class="rad" type="radio" value="1" name="smsg_${orderBase.id }">
				其它：</span>
				<textarea name="inp_${orderBase.id }" rows="4"></textarea>
				</p>
			</div>
			<div style="text-align: center;padding-bottom: 10px;padding-right: 10px;">
				<a class="btn btn-danger" href="javascript:orderRefund(${orderBase.id},4,${orderBase.totalPrice })">确定</a>
				&nbsp;&nbsp;&nbsp;
				<a class="btn btn-danger" href="javascript:clearmsg(${orderBase.id })">取消</a>
			</div>
		</div>
	
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopOrderDet.js?v=0.0.3"></script>
</body>
</html>
