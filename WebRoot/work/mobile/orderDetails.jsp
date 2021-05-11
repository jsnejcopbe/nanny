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
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/orDetails.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shopOrderDet.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shoporderlist.css">
<style type="text/css">
.up_file {
	height: 100%;
	width: 60px;
	position: absolute !important;
	left: 0 !important;
	top: 0 !important;
	z-index: 999 !important;
	opacity: 0 !important;
	filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0 ) !important;
	cursor: pointer;
	display: inline-block !important;
}

.up_btn {
	position: relative;
	font-size: 12px;
	border: 2px solid #29A1F7;
	height: 60px;
	width: 60px;
	display: inline-block;
	vertical-align: top;
	padding-top: 20px;
	text-align: center;
	color: #29A1F7;
}

</style>
</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/users/userOder.html?sta=${sta}" class="col-xs-3 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-6">
				订单详情
			</div>
			
		</div>
		
	</nav>
	<div class="" style="height: 50px"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container sh-pro">
		
	   <%--<div class="row pro-it">
			<span class="xpp">收货人：${order.recName} </span>
			<span class="xpp" >联系电话：${order.recTel}</span>
			<span class="xpp">收货地址：${order.address}</span>
			<span class="xpp">下单时间：${order.createTime}</span>
	   </div>
		--%><!-- 订单状态 -->
		<div class="row or-sta">
			<p>
				<i class="iconfont-gl">&#xe6d5;</i>
				&nbsp;&nbsp;
				<span>订单状态：${order.staName }</span>
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
					${order.recName }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe633;</i>&nbsp;
					联系电话:
				</div>
				<div class="col-xs-8 item-con">
					${order.recTel }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe60e;</i>&nbsp;
					收货地址:
				</div>
				<div class="col-xs-8 item-con">
					${order.address }
				</div>
			</div>
			
			<div class="bamsg-item clearfix">
				<div class="col-xs-4 item-ico">
					<i class="iconfont-gl">&#xe630;</i>&nbsp;
					下单时间:
				</div>
				<div class="col-xs-8 item-con">
					${order.createTime }
				</div>
			</div>
		</div>
		
		<div class="row pro-it or-bamsg" >
				<%--<div class="col-xs-12 it-tit clearfix">
				<span class="tit-code"></span>
				</div>
			--%><div class="col-xs-12 bamsg-item it-con" style="background-color: white;border: none;">
				<div class="col-xs-3 con-img">
					<img alt="" src="${order.shop_icon }">
				</div>
				<div class="col-xs-7 con-des">
					<p style="margin:0 0 5px">
					<span style="font-size: 14px;display: block;">${order.shop_name }</span>
					<span class="hpp"style="margin-top: -10px;display: block;" > ${order.tel }</span>
					<span class="hpp">${order.detAdd }</span></p>
					
				</div>
					
					
				</div>
				<%--<div class="it-opt col-xs-12">
				<div class="opt-msg"> </div>
			</div>
			--%></div>
			
			<div class="row pro-it">
			<div class="col-xs-12 it-tit clearfix">
				<i class="iconfont-gl">&#xe62c;</i>&nbsp;
				<span class="tit-code" style="float: none;">订单留言</span>
			</div>
			<div class="col-xs-12 it-con" style="background-color: white;">
					${order.message}
			</div>
		</div>
		<div class="row pro-it">
			<div class="col-xs-12 it-tit clearfix">
				<span class="tit-code">订单号:${order.orderCode }</span>
				<%--<span class="tit-sta">${order.staName }</span>
				--%></div>
			<c:forEach items="${order.company }" var="det">
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
							<p>${det.point }</p>
							<p>X${det.count }</p>
						</div>
				</c:if>
			</div>
			</c:forEach>
			<div class="it-opt col-xs-12">
				<c:if test="${order.vcID!='0'}">
					<div class="opt-msg"> ${order.svName}<span>-¥${order.svMoney}</span>元</div>
				</c:if>
				<div class="opt-msg"> 实付<span>¥${order.totalPrice }</span>元(含配送费${order.fee }元)</div>
			</div>
			<div class="it-btn col-xs-12"  id="dis" style="display:block;">
				
				<c:if test="${order.status=='3' }">
				<a class="red-btn" href="javascript:discuss()">评价</a>
				</c:if>
				
			</div>
		</div>
		<div class="row pro-it" id="dis1" style="display: none;">
					<div class="col-xs-12 it-tit clearfix">
					<span class="tit-code spp"><h4>评分</h4></span>
					<div id="xzw_starSys">
								<div id="xzw_starBox">
									<ul class="star" id="star">
										<li><a href="javascript:void(0)" title="1"
											class="one-star">1</a>
										</li>
										<li><a href="javascript:void(0)" title="2"
											class="two-stars">2</a>
										</li>
										<li><a href="javascript:void(0)" title="3"
											class="three-stars">3</a>
										</li>
										<li><a href="javascript:void(0)" title="4"
											class="four-stars">4</a>
										</li>
										<li><a href="javascript:void(0)" title="5"
											class="five-stars">5</a>
										</li>
									</ul>
									<div class="current-rating" id="showb"></div>
								</div>
								<!--评价文字-->
								<div class="description"></div>
								 <input type="hidden" value="" id="startP">
							</div>
							
							<span class="tit-code spp"><h4>评论 </h4></span>
							<div class="fm-right">
									
									<textarea rows="" cols=""  style="height: 100px;width: 200px;margin-top: 10px" id="con" name="con">
									</textarea>
								</div>
					</div>
					<div style="float: right;margin: 5px;">
						<a type="button" class="btn red-btn" href="javascript:addDiscuss(${order.recShopID});">就是这样</a>
					</div>
			</div>
		
		
	</div>
	<!-- 主体内容 结束 -->
	
	
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/orDetails.js"></script>
	<script type="text/javascript">
	var BASEPATH="${pageContext.request.contextPath}";
	

	</script>
	
</body>
</html>
