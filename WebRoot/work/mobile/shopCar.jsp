<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>我的购物车</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/shopcar.css">
<script type="text/javascript">
var path="${pageContext.request.contextPath}";
</script>
</head>

<body>
	<!-- 头部 开始 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="javascript:clear(${loginUser.userID});" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-8 sh-cen">
				我的购物车
			</div>
		</div>
	</nav>
	<div class="space41"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container">
		<!-- 收货地址 -->
		<div class="row rec-ad">
			<div class="col-xs-3 ad-left">
				<span>收货人</span>
			</div>
			<div class="col-xs-9 ad-right">
				<span>${rece.recName}</span>
			</div>
			<div class="col-xs-3 ad-left">
				<span>联系电话</span>
			</div>
			<div class="col-xs-9 ad-right">
				<span>${rece.tel}</span>
			</div>
			<div class="col-xs-3 ad-left">
				<span>收货地址</span>
			</div>
			<div class="col-xs-9 ad-right">
				<span>${rece.community} ${rece.doorplate}</span>
			</div>
			<a class="ad-btn" href="${pageContext.request.contextPath}/users/useraddress.html?id=${rece.userId}"><span>修改</span>&nbsp;<i class="iconfont-gl">&#xe602;</i></a>
		
			<form id="pro" style="display: none;">
			<input  type="hidden" name="recName" value="${rece.recName}">
			<input  type="hidden" name="tel" value="${rece.tel}">
			<input  type="hidden" name="address" value="${rece.address}">
			<input  type="hidden" name="community" value="${rece.community}">
			<input  type="hidden" name="doorplate" value="${rece.doorplate}">
			</form>
		</div>
		<div class="row rec-ad">
		
			<div class="col-xs-6 " style="text-align: center;">
				<span style="font-size: 16px;">余额:<label style="color: red;padding-left: 5px;">${mony}</label></span>
			</div>
			
			<div class="col-xs-6 " style="text-align: center;">
				<span style="font-size: 16px;">积分:<label style="color: red;padding-left: 5px;">${point}</label></span>
			</div>
			<!-- <div class="col-xs-12" style="border-top: 2px solid #ddd;">
				<span>如果您有选购积分商品，请注意您的积分是否足够兑换此商品。若积分不足，将自动转换为原价购买！</span>
			</div> -->
		</div>
		<div class="row rec-ad">
			<div class="col-xs-6 " style="text-align: center;">
				<span style="font-size: 16px;"><input name="Fruit" type="radio" value="0" checked="checked" style="width: 20px;height: 18px;vertical-align: bottom;" />在线支付</span>
			</div>
			<div class="col-xs-6 " style="text-align: center;">
				<span style="font-size: 16px;"><input name="Fruit" type="radio" value="1" style="width: 20px;height: 18px;vertical-align: bottom;" />货到付款</span>
			</div>
		
		</div>
		<!-- 商品列表 -->
		<div class="rec-pr" id="tab">
			
			<c:forEach items="${shop}"  var="sh">
			<div class="row pr-item">
				
			
				<div class="item-sh clearfix">
					<div class="col-xs-8 sh-left">
						<i class="iconfont-gl">&#xe62e;</i>
						&nbsp;
						<span>${sh.shop_name }</span>
					</div>
					<div class="col-xs-4 sh-right it-tit">
						<span>起送价:</span>
						<span class="delpr">${sh.fee }</span>
					</div>
				</div>
				
				<c:forEach items="${sh.shopde}" var="shde">
				<div class="item-con clearfix">
					<div class="col-xs-5 con-left">
						<c:if test="${fn:length(shde.name) > 10}">
						<span>${fn:substring(shde.name, 0,6)}...</span>
						</c:if>
						
						<c:if test="${fn:length(shde.name) < 11}">
						<span>${shde.name}</span>
						</c:if>
					</div>
					<div class="col-xs-7 con-right fm-txt pro">
						 <c:if test="${shde.memo=='0'}">   
								<span style="font-size: 18px;">¥</span>
								<span class="price">${shde.price}</span>
								<a class="btn-del min" href="javascript:void(0)">
									<i class="icon-minus-sign"></i>
								</a>
								<input class="con-cou text_box" readonly="readonly" type="text" value="${shde.count}">
								<a class="btn-add add" href="javacript:void(0)">
									<i class="iconfont-gl">&#xe629;</i>
								</a>
						  </c:if> 
						 <c:if test="${shde.memo!='0'}"> 
								<span style="font-size: 18px;" >(需积分:${shde.point})¥</span><span class="price">${shde.price}</span> 
							  
								 <a class="btn-del" href="javascript:void(0)">
									<i class=" icon-ban-circle"></i>
								</a> 
								<input class="con-cou text_box" readonly="readonly" type="text" value="${shde.count}">
								 <a class="btn-add" href="javacript:void(0)">
									<i class=" icon-ban-circle"></i>
								</a>
							 </c:if>
						<input  type="hidden" name="isExchange" value="${shde.memo}">
						<input  type="hidden" name="shop" value="${shde.shopID}">
						<input  type="hidden" name="proID" value="${shde.proID}">
					</div>
				</div>
				</c:forEach>
				
				<div class="item-sh clearfix vvc">
				<c:if test="${sh.isVouchers=='1'}">
					<select name="vcid" class="col-xs-9 fm-txt" style="font-size: 20px;border: navajowhite;padding-left: 5px;">
					  <option value ="0" data-money="0">无优惠</option>
						<c:forEach items="${vc}"  var="vc">
							<option value="${vc.id}" data-money="${vc.money}">${vc.money}元抵用卷</option>
						</c:forEach>
					</select>
					<div class="col-xs-3 fm-txt sh-right"><span style="font-size: 20px;color: red;"></span></div>
				</c:if>
				</div>
				
			<input  type="hidden" class="shid" name="shopId" value="${sh.id}">
			<input type="hidden" class="shfee" name="sendFee" value="${sh.fee }">
			</div>
			</c:forEach>
			
		</div>
		
		<div class="row rec-ad">
			<div class="col-xs-3" style="padding-right: 0;">
				订单留言：
			</div>
			<div class="col-xs-7 " style="padding-left: 0;">
				<textarea  id="mesg" maxlength='100' placeholder="请输入留言" style="border: 1px solid #CCCCCC;width: 100%;height: 100px;"></textarea>
			</div>
		
		</div>
		
		
	</div>
	<!-- 主体内容 结束 -->
	
	<!-- 底部结算 -->
	<div class="space54"></div>
	<div class="rec-bt clearfix">
		<div class="col-xs-8 bt-tot">
			<span id="total-tex">共 ¥ 18.8</span>
			<input type="hidden" id="total" name="total">
		</div>
		<div class="col-xs-4 bt-btn">
			<a href="javascript:updateshop()">
				选好了
			</a>
		</div>
	</div>
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/shopCar.js?v=0.04"></script>

</body>
</html>
