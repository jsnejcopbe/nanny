<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>商品详情</title>
<meta charset="utf-8" http-equiv="Content-Type" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/prodetails.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/proList.css" />
<style type="text/css">
.bottombox {
	background-color: #CCC;
	width: 100%;
}

.fixed-bottom {
	position: fixed;
	bottom: 0;
	border: #ccc solid 1px;
	box-shadow: 0px 0px 10px #ccc;
}

.btn {
	background-color: #D2340F;
	color: white;
	text-decoration: none;
	font-family: Arial;
	font-weight: 900;
	font-size: 15px;
	padding: 10px 13px;
	border-radius: 3px;
}

.btn-buynow {
	background-color: #D2340F;
}

.btn-buynow:hover {
	background-color: #EF411A;
}
</style>
</head>
<body class='new_bod_bg'>
	<c:forEach items="${pdlist}" var="pl">
		<div id='error_infors'>
			<span></span>
		</div>

		<div class="shop_header">
			<div class="shop_head_fl">
				<a href="javascript:void(0)" id='p_go'>
					<img alt="" src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/hd_lt.png">
				</a>
			</div>
			<div class="shop_head_fm">
				<h1>商品详情</h1>
			</div>
			<!-- <div class="shop_head_fr" id="shop_open_down">
						<a href="javascript:void(0)"><img alt=""
							src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/hd_rt.png">
						</a>
					</div> -->
			<!-- 下拉菜单  -->
			<%--<div class="shop_down_ul" style="display: none;">
				<ul class="shop_out_ul">
					<li><a href="javascript:void(0)"
						onclick="addProductCollection('/wap',412934);"> <img
							class="succ_procoll"
							src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/shopPro3.png">
							<span id="old_coll">收藏</span> </a>
					</li>
					<li class="shop_no_bot"><a href="javascript:void(0)"
						id="shop_open_share"> <img
							src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/shopPro40.png">分享
					</a>
					</li>
				</ul>
				<span class="shop_sj"></span>
			</div>
			<div class="shop_in_ul" style="display: none;">
				<div class="share_icons">
					<a href="javascript:void(0)" onclick="share_product('qzone')">
						<img
						src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/pro08.png">
						<span>QQ空间</span> </a> <a href="javascript:void(0)"
						onclick="share_product('qqt')"> <img
						src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/pro06.png">
						<span>腾讯微博</span> </a> <a href="javascript:void(0)"
						onclick="share_product('sina')"> <img
						src="http://rep3.mmb.cn/wap/upload/touch/newWap/icon/pro07.png">
						<span>新浪微博</span> </a>
				</div>
				<a class="shop_close_tc" onclick="hiddenDiv()">关闭</a>
			</div>--%>
		</div>
		<div class="shop_down_bg" onclick="hiddenDiv()" style="display: none;"></div>
		<div class="container"
			style="position:relative;background-color: #ffffff;" id="ls_con">
			<!-- 首页图片  -->
			<div class=" min_main_vis" id='pro_slider'>
				<div class="main_image">
					<ul>
						<li><img src="${pl.cover}"/></li>
					</ul>
				</div>
			</div>

			<!-- 标题部分()商品名称和活动状况)  -->
			<div class="jdGoods" style="border-width: 0;">

				<p id='js_title'>${pl.name}</p>

				<div class="order_line"></div>
				<h1>
					<em id='product_price' style="float:left;">￥<c:if
							test="${ pl.disPrice!='0.00' }">${pl.disPrice }</c:if> <c:if
							test="${ pl.disPrice=='0.00' }">${pl.price }</c:if> </em> &nbsp;
					<p style="text-decoration:line-through; float:left;">
						<c:if test="${ pl.disPrice!='0.00' }">￥${pl.price }</c:if>
					</p>
				</h1>
				<!-- 	<div class="order_line"></div> 
						<!-- <h2>
						<div class="pro_active">
							<p>&nbsp;&nbsp;活动</p>
						</div>
					</h2> -->
				<div class="order_line"></div>
				<p style=" float:left;">
				<div
					style=" display:inline-block; position:relative;left:10px;color:#999;">
					品&nbsp;&nbsp;&nbsp;牌&nbsp;:</div>
				<p
					style=" display:inline-block; font-weight: 500;color: #333;font-size:16px;">
					${pl.spbname }</p>
				</p>


				<p style=" float:left;">
				<div class="order_line"></div>
				<span
					style=" display:inline-block; position:relative;left:10px;color:#999;">
					类&nbsp;&nbsp;&nbsp;别&nbsp;:</span>
				<p
					style=" display:inline-block; font-weight:blod;color: #333;font-size:16px;">
					${pl.sptname }</p>
				</p>

				<p style=" float:left;">
				<div class="order_line"></div>
				<span
					style=" display:inline-block; position:relative;left:10px;color:#999;">
					库&nbsp; 存&nbsp;量 :</span>
				<p
					style="display: inline-block; font-weight:500;color: #333; font-size: 16px;">
					${pl.inventory}</p>
				</p>
				<!-- <p style=" float:left;">
                   <span
						style=" display:inline-block; position:relative;left:10px;color:#999;">
					</span>
				<p
					style="display: inline-block; font-weight:500;color: #333; font-size: 16px;">
				</p> -->
				</p>
			</div>
			<!-- 购买  -->

			<div id='content_tab'>
				<div class="main_li">
					<c:if test="${ pl.proDes!='null' }">${pl.proDes }</c:if>
				</div>
			</div>
			<div class="jdGoods2">
				<div class="go-left">
					<a class="btn-del" href="javascript:void(0)">
						<i class="icon-minus-sign"></i>
					</a>
					<input class="con-cou" readonly="readonly" type="text" value="0">
					<a class="btn-add" href="javacript:void(0)">
						<i class="iconfont-gl">&#xe629;</i>
					</a>
				</div>
				<div class="go-right">
					<c:if test="${isClosed==null }">
					<a class="btn-car" href="javascript:updateshop()">加入购物车</a>
					</c:if>
					<c:if test="${isClosed!=null }">
					<a class="btn-car" href="javascript:notOpenYet()">加入购物车</a>
					</c:if>
				</div>
			</div>
		</div>


	<input id="proID" type="hidden" value="${proID }">
	<form id="storeJumpForm" action="${pageContext.request.contextPath}/store-${pl.shopID }.html" method="post" style="display: none;">
		<input id="proCarList" name="proCarList" type="hidden" value="">
		<c:if test="${parID!=null }">
		<input id="parID" name="proParID" type="hidden" value="${parID }">
		</c:if>
	</form>
	</c:forEach>
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	
	<c:if test="${proCarList!=null }">
	<script type="text/javascript">
	var shopCarPro=${proCarList};
	</script>
	</c:if>
	<c:if test="${proCarList==null }">
	<script type="text/javascript">
	var shopCarPro=new Array();
	</script>
	</c:if>
	<script type="text/javascript">
	function notOpenYet(){
		layer.msg("店铺尚未开张");
		return;
	}
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/productdetails.js"></script>
</body>
</html>