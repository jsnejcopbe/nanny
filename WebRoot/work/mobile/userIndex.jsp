<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<title>我的掌上保姆</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<meta name="format-detection"content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit"> <!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" > <!-- 优先用Chrome渲染 -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/userIndex.css">
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
<style type="text/css">
@font-face {font-family: 'iconfont-gl';
    src: url('/nanny/fonts/iconfont-gl.eot'); /* IE9*/
    src: url('/nanny/fonts/iconfont-gl.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
    url('/nanny/fonts/iconfont-gl.woff') format('woff'), /* chrome、firefox */
    url('/nanny/fonts/iconfont-gl.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
    url('/nanny/fonts/iconfont-gl.svg#iconfont') format('svg'); /* iOS 4.1- */
}
.iconfont-gl{
    font-family:"iconfont-gl" !important;
    font-size:16px;font-style:normal;
    -webkit-font-smoothing: antialiased;
    -webkit-text-stroke-width: 0.2px;
    -moz-osx-font-smoothing: grayscale;
}
</style>
<script type="text/javascript">
var uad="${uad}";
function load()
{
 if(uad='1'){
		/*  layer.confirm('您还未设置收货地址，是否前往设置', {
		  btn: ['是','否'] //按钮
		}, function(){
		  location.href="${pageContext.request.contextPath}/users/addUseRess.html";
		}, function(){
		 	layer.closeAll('dialog');
		}); */
 	/* layer.load(1, {shade : [ 0.8, '#333' ]});
	layer.msg("请设置收货地址");
 	setTimeout(function(){location.href="${pageContext.request.contextPath}/users/addUseRess.html";}, 2000); */
 	location.href="${pageContext.request.contextPath}/users/addUseRess.html";
 }
}
</script>
</head>

<body class="body-f6">
	<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
		<div class="m-topbar flexbox">
			<!-- 返回首页 -->
			<a class="m-goback" href="/mall/shop/mallCenter.html"><i class="iconfont">&#xe600;</i></a>
			
			<div class="m-tlebox flex">
				<div class="storeManage-menu">
					个人中心
				</div>
			</div>
			
			<!-- 下拉菜单 -->
			<a class="m-moremenu" id="j-m-moremenu" href="javascript:void(0)" data-flag='2'><i class="iconfont">&#xe601;</i></a>
		</div>
		<section class="m-menubox">
			<ul class="fixed">
				<!-- <li><a class="menu-link" href="/mall/shop/mallCenter.html"><span><i class="iconfont-gl">&#xe60a;</i></span><div class="name">直营商城</div></a></li>
				<li><a class="menu-link" href="/mall/admin/shoppingCar.html"><span><em class="badge"></em><i class="iconfont">&#xe61c;</i></span><div class="name">直营购物车</div></a></li> -->
				<li><a class="menu-link" href="${pageContext.request.contextPath}/supermarket.html"><span><i class="iconfont">&#xe615;</i></span><div class="name">自选超市</div></a></li>
				<li><a class="menu-link" href="${pageContext.request.contextPath}/users/shopcar.html"><span><em class="badge"></em><i class="iconfont">&#xe61c;</i></span><div class="name">自选购物车</div></a></li>
				<li><a class="menu-link" href="${pageContext.request.contextPath}/loginout.html"><span><i class="iconfont">&#xe614;</i></span><div class="name">退出</div></a></li>
			</ul>
		</section>
	</header>
	
	<div class="space-44"></div>
	
	<!-- 头部菜单 结束 -->
	
	<!-- 用户信息 开始-->
	<div class="storeManage storeManage-buyers">
		<div class="storeManage-banner">
			<div class="faceImg">
				<a class="_porimg" href="javascript:void(0);" >
					
		   			<img style="border: 1px solid #c9c9c9;" src="${user.headImg}"  onerror="this.src='/nanny/work/mobile/images/vddefault.png'">
		   		</a>
			</div>
		    <h1 class="buyerName">${user.nickName}</h1>
		    <!-- <p>这家伙很懒，什么都没有留下</p> -->
		</div>
		<div class="storeManage-buyersInfo">
		   <ul class="flexbox">
			    <li class="" style="width: 50%;padding: 10px;"><span style="display: block;width: 100%;font-size: xx-large;color: chocolate;">${user.balance}</span></li>			    
			    <li class="_mnotreceipt" style="padding: 10px;padding-top: 15px;"><a href="${pageContext.request.contextPath}/users/recharge.html" style="color: cadetblue; display: inline-block;"><i class="iconfont" style="font-size: 20px;color: cadetblue;"></i>充值</a></li>
			    <li class="_mrefund" style=" padding: 10px;padding-top: 15px;"><a href="${pageContext.request.contextPath}/users/transfer.html" style="color: cadetblue;display:inline-block;"><i class="iconfont" style="font-size: 20px;color: cadetblue;"></i>提现</a></li>
		    </ul>
		</div>
		<div class="storeManage-buyersInfo">
			<h1 class="fixed"><span class="fl">自选超市订单</span><a href="${pageContext.request.contextPath}/users/userOder.html" id="allOrder" class="fr">全部购物订单<i class="iconfont">&#xe602;</i></a></h1>
		    <ul class="flexbox">
			    <li class="_mnotpay"><a href="${pageContext.request.contextPath}/users/userOder.html?sta=0"><span><i class="iconfont">&#xe620;</i><em class="badge"></em></span><p class="name">待付款</p></a></li>
			    <li class="_mall"><a  href="${pageContext.request.contextPath}/users/userOder.html?sta=1"><span><i class="iconfont">&#xe64f;</i><em class="badge"></em></span><p class="name">待发货</p></a></li>
			    <li class="_mnotreceipt"><a href="${pageContext.request.contextPath}/users/userOder.html?sta=2"><span><i class="iconfont">&#xe651;</i><em class="badge"></em></span><p class="name">待收货</p></a></li>
			    <li class="_mnotreceipt"><a href="${pageContext.request.contextPath}/users/userOder.html?sta=3"><span><i class="iconfont">&#xe604;</i><em class="badge"></em></span><p class="name">已完成</p></a></li>
			    <li class="_mrefund"><a href="${pageContext.request.contextPath}/users/userOder.html?sta=4"><span><i class="iconfont">&#xe622;</i><em class="badge"></em></span><p class="name">退款</p></a></li>
		    </ul>
		</div>
		<!-- <div class="storeManage-buyersInfo">
			<h1 class="fixed"><span class="fl">直营商城订单</span><a href="/mall/users/userOder.html" id="allOrder" class="fr">全部购物订单<i class="iconfont">&#xe602;</i></a></h1>
		    <ul class="flexbox">
			    <li class="_mnotpay"><a href="/mall/users/userOder.html?sta=0"><span><i class="iconfont">&#xe620;</i><em class="badge"></em></span><p class="name">待付款</p></a></li>
			    <li class="_mall"><a  href="/mall/users/userOder.html?sta=1"><span><i class="iconfont">&#xe64f;</i><em class="badge"></em></span><p class="name">待发货</p></a></li>
			    <li class="_mnotreceipt"><a href="/mall/users/userOder.html?sta=2"><span><i class="iconfont">&#xe651;</i><em class="badge"></em></span><p class="name">待收货</p></a></li>
			    <li class="_mnotreceipt"><a href="/mall/users/userOder.html?sta=3"><span><i class="iconfont">&#xe604;</i><em class="badge"></em></span><p class="name">已完成</p></a></li>
			    <li class="_mrefund"><a href="/mall/users/userOder.html?sta=4"><span><i class="iconfont">&#xe622;</i><em class="badge"></em></span><p class="name">退款</p></a></li>
		    </ul>
		</div> -->
		<div class="storeManage-buyer-list">
		    <ul>
				<li><a href="${pageContext.request.contextPath}/users/ProductExchangeDetail.html"><span class="fl"><i class="iconfont-gl color1">&#xe627;</i>积分商品兑换</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
		   		<li><a href="${pageContext.request.contextPath}/users/Signjump.html"><span class="fl"><i class="iconfont-gl color5">&#xe650;</i>签到</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
		   		<li><a href="/mall/user/winning.html"><span class="fl"><i class="iconfont-gl color1">&#xe627;</i>中奖记录</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
		    	<li><a href="${pageContext.request.contextPath}/users/userProfile.html"><span class="fl"><i class="iconfont-gl color4">&#xe62b;</i>个人资料</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
		   	    <%--<li><a href="${user.id}"><span class="fl"><i class="iconfont-gl color1">&#xe62c;</i>评价管理</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>--%>
		   	    <li><a href="${pageContext.request.contextPath}/users/shopcar.html"><span class="fl"><i class="iconfont-gl color2">&#xe628;</i>购物车</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
				<li><a href="${pageContext.request.contextPath}/users/userAccount.html"><span class="fl"><i class="iconfont-gl color2">&#xe603;</i>资金明细</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
				<li><a href="${pageContext.request.contextPath}/users/userCoupon.html?sta=0"><span class="fl"><i class="iconfont-gl color2">&#xe608;</i>我的优惠券</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>

				<%-- <li><a href="${pageContext.request.contextPath}/users/integralDetail.html"><span class="fl"><i class="iconfont-gl color2">&#xe603;</i>积分明细</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li> --%>

				<%--<li><a href="${pageContext.request.contextPath}/users/recharge.html"><span class="fl"><i class="iconfont color1">&#xe623;</i>充值记录</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
			    <li><a href="${pageContext.request.contextPath}/users/transfer.html"><span class="fl"><i class="iconfont-gl color3">&#xe627;</i>我要提现</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>--%>
			    <li><a href="${pageContext.request.contextPath}/users/myCollection.html"><span class="fl"><i class="iconfont color3">&#xe619;</i>我的收藏</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
			    <li><a href="${pageContext.request.contextPath}/shop/shopMessage.html"><span class="fl"><i class="iconfont color3">&#xe653;</i>站内信</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
			    <li><a href="${pageContext.request.contextPath}/users/useraddress.html"><span class="fl"><i class="iconfont color4">&#xe616;</i>收货地址管理</span><span class="fr"><i class="iconfont">&#xe602;</i></span></a></li>
			    
		    </ul>
        </div>
	</div>
	<!-- 用户信息 结束-->
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/util.js"></script>
</body>
</html>