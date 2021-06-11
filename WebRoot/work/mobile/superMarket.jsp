<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>

<head>

<title>${shopMsg.shop_name }</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<meta http-equiv="description" content="this is my page">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nivo-slider.css" type="text/css"></link>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/style-m-2.0.css" />

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/login-m-2.0.css" />

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/popup.css" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/supermarket.css?v=201610271640" type="text/css"></link>

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

<c:if test="${isClosed!=null || isOutArea!=null }">

<style type="text/css">

.btn-add i{color: #666!important;}

</style>		

</c:if>

<style type="text/css">

.pro-con{
	font-size: 0;
}
.pro-con .pro-item{
	width: 50%;
	display: inline-block;
	border: none;
	
	font-size: 10px;
}
.pro-con .pro-item>a{
	width: 100%;
}
.pro-con .pro-item>a img{
	height: 125px!important;
}
.pro-con .pro-item>div{
	width: 100%;
}
.pro-con .pro-item>div>p{
	width: 90%;
	margin-left: auto;
	margin-right: auto;
	margin-top: 5px;
}
.pro-con .pro-item>div>p.pro-name{
	overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.pro-con .pro-item .btn-area{
	right: 5%;
}
.pro-con .pro-item .pro-price .now{
	line-height: 34px;
}
.pro-con .pro-item .exc-btn>a{
	line-height: 20px;
}
</style>

</head>



<body>

	<!-- 头部工具栏 -->

	<nav class="sm-top clearfix">

	<c:if test="${not empty wheel}"><div  style="background:#000;display: ab;position: absolute;width: 100%;opacity: 0.6;"class="clearfix" ></c:if>

		<a class="col-xs-2 top-icon" href="${pageContext.request.contextPath}/shoplist.html" <c:if test="${not empty wheel}"> style="padding-left: 0px;color:#FFFFFF;background:#000;"</c:if>>

			<i class="iconfont-gl" <c:if test="${not empty wheel}"> style="margin-left: 20px;"</c:if>>&#xe62e;</i>

			<span <c:if test="${not empty wheel}"> style="width: 50px; margin-left: 10px;"</c:if>>附近商家</span>

		</a>

		

		<c:if test="${areaMsg!=null }">

		<a class="col-xs-8 top-add" href="${pageContext.request.contextPath}/global_addName.html?city=${areaMsg.city}"<c:if test="${not empty wheel}"> style="background:#000;color:#FFFFFF;height: 100%;display: block;text-align: center;"</c:if>>

			<span margin-top="40px">${areaMsg.addName }</span>

			<span class="caret"></span>

		</a>

		</c:if>

		

		<c:if test="${areaMsg==null }">

		<a class="col-xs-8 top-add" href="/nanny/global_city.html"<c:if test="${not empty wheel}"> style="background:#000;height: 100%;color:#FFFFFF;display: block;text-align: center;"</c:if>>

			<span >请选择</span>

			<span class="caret"></span>

		</a>

		</c:if>

		

		<a class="col-xs-2 top-icon" href="javascript:toQueryPage()"<c:if test="${not empty wheel}"> style="color:#FFFFFF;background:#000;"</c:if>>

			<i class="iconfont-gl">&#x3432;</i>

			<span>搜索</span>

		</a>

		<c:if test="${not empty wheel}"></div></c:if>

		<!-- 快速导航页 -->

		<c:if test="${not empty wheel}">

		<div class="slider-wrapper theme-default">

		<div id="slider" class="nivoSlider" style="height:160px; position:absolute;z-index:-1;">

					<c:forEach items="${wheel}" var="w">

					<a href="${w.jumpSrc}">

						<img src="${w.imgStc}" />

					</a>

					</c:forEach>

				</div>

				</div>

			</c:if>

		<div class="tab-con clearfix"<c:if test="${not empty wheel}"> style="margin-top: 160px;";</c:if>>

			<a href="javascript:void(0)" class="col-xs-4 tab-selected">商品</a>

			<a href="${pageContext.request.contextPath}/store-dis-${shopID }.html" class="col-xs-4">评价</a>

			<a href="${pageContext.request.contextPath}/store-det-${shopID }.html" class="col-xs-4">店铺</a>

		</div>

		<!-- 公告通知 -->

		<div class="notice-con clearfix">

			<i class="iconfont-gl">&#xe613;</i>

			

			<c:if test="${isClosed==null && isOutArea==null}">

				<span> 

					<c:if test="${notice!='null' }">

					<marquee onmouseover=this.stop() onmouseout=this.start() scrollAmount=2 scrollDelay=60 width=80% height=20>

						<a class="#"  style="color:#666;" href="#" onclick="javascript:window.open('', 'newwindow')" title="\">${notice.total }</a>

					</marquee> 

					</c:if> 

				</span>

			</c:if>

			<c:if test="${isClosed!=null }">

				<span> 

					<marquee onmouseover=this.stop() onmouseout=this.start() scrollAmount=2 scrollDelay=60 width=80% height=20>

						<a class="#"  style="color:#666;" href="#" onclick="javascript:window.open('', 'newwindow')" title="\">店铺歇业中请在营业时间下单，谢谢 </a>

					</marquee> 

				</span>

			</c:if>

			<c:if test="${isClosed==null && isOutArea!=null }">

				<span> 

					<marquee onmouseover=this.stop() onmouseout=this.start() scrollAmount=2 scrollDelay=60 width=80% height=20>

						<a class="#"  style="color:#666;" href="#" onclick="javascript:window.open('', 'newwindow')" title="\">您当前不在该店的配送地区内哦，请换个地址试试 </a>

					</marquee> 

				</span>

			</c:if>

			

			<i class="iconfont-gl">&#xe602;</i>

		</div>

	</nav>

	

	<div class="space123"  <c:if test="${not empty wheel}">style="height:250px;"</c:if>></div>

	

	<!-- 页面主体内容 开始 -->

	<div class="container">

		

		<div class="row page-con">

			<!-- 左侧菜单 -->

			<div id="wrap-left" class="col-xs-3">

				<div class="menu-con">

					<div class="menu-item item-0">

						<a href="javascript:void(0)" data-id="0">积分兑换</a>

					</div>

					<c:forEach items="${firstClassList }" var="fc">

					<div class="menu-item item-${fc.id }">

						<a href="javascript:void(0)" data-id="${fc.id }">${fc.name }</a>

					</div>

					</c:forEach>

					

				</div>

			</div>

			<!-- 右侧商品列表 -->

			<div id="wrap-right-${firstClassID }" class="col-xs-9 js-wrap" pageIndex = "1">

				<div class="pro-con" id="pro-con-${firstClassID }">

				<c:forEach items="${proData }" var="pr">

					<c:if test="${fn:length(pr.data)>0}">

						<c:if test="${pr.className!= firstClassName}">

						<div class="pro-span">${pr.className }</div>

						</c:if>

						
						<c:if test="${shopID==182 }">
						<div class="pro-item clearfix">
							<a class="col-xs-4" href="http://zhangshangshequ.flzhan.com/index.html?rd=0.936534379701011">
								<img width="45" height="60" src="/nanny/images/logoX80.jpg" style=" height: auto!important;">
							</a>
							<div class="col-xs-8">
								<p class="pro-name" style="font-size: 16px;">掌上保姆社区服务</p>
								<p style="font-size: 14px;margin-bottom: 5px;color: #333;">您的生活好帮手！</p>
								<p class="exc-btn">
									<a href="http://zhangshangshequ.flzhan.com/index.html?rd=0.936534379701011" style="background-color: #06A7E1;">点我立即访问</a>
								</p>
							</div>
						</div>
						</c:if>
						

						<c:forEach items="${pr.data }" var="pd">

						<div class="pro-item clearfix" data-proid=${pd.id }>

							<a class="col-xs-4" href="javascript:toDetPage(${pd.id })">

								<img  width="45" height="45" src="${pd.cover }">

							</a>

							<div class="col-xs-8">

								<p class="pro-name">${pd.name }</p>

								<p class="pro-sale">销量：${pd.buyCount }</p>

								<p class="pro-price">

									<c:if test="${pd.point!='0' && pd.point!=null}">

										<span class="point-txt">${pd.point}积分</span>

										<span class="now hide">${pd.price }</span>

										<input type="hidden" name="isex" value="${pd.isExchange}">

									</c:if>

									<c:if test="${pd.point=='0'||pd.point==null}">

										<c:if test="${pd.disPrice=='0.00'}">

											<span class="now">¥ ${pd.price }</span>

										</c:if>

										

										<c:if test="${pd.disPrice!='0.00'}">

											<span class="now">¥ ${pd.disPrice }</span>

											<span class="past">¥ ${pd.price }</span>

										</c:if>

									</c:if>

								</p>

								

								<c:if test="${pd.point!='0' && pd.point!=null}">

								<p class="exc-btn">

									<a href="javascript:void(0)">立即兑换</a>

								</p>

								</c:if>

								

								<div class="btn-area">

									<a class="btn-del" href="javascript:void(0)">

										<i class="icon-minus-sign"></i>

									</a>

									<span class="tip-procount">0</span>

									<a class="btn-add" href="javascript:void(0)">

										<i class="iconfont-gl">&#xe629;</i>

									</a>

								</div>

							</div>

						</div>

						</c:forEach>

					</c:if>

				</c:forEach>

				<div class="pro-logo"><img src="${pageContext.request.contextPath}/images/logo.jpg" style="width:100%;"></div>
				
				</div>

			</div>

		</div>

	</div>

	<!-- 页面主体内容 结束 -->

	

	

	<!-- 底部菜单 -->

	<div class="space41"></div>

	<div class="sm-bot">

		<div class="col-xs-12 bot-pay clearfix">

			<div class="left">

				<span>¥<span class="js-price">0</span></span>

				<span>共<span class="js-procount">0</span>个商品</span>

				<span>还差<span class="js-send">${dispatchMsg.fee }</span>元起送</span>

			</div>

			<div class="right">

				<a class="useless" href="javascript:addToShop()">选好了,前往付款</a>

			</div>

		</div>

	<div style="display:none">

		<a class="col-xs-3" href="${pageContext.request.contextPath}/">

			<i class="iconfont-gl">&#xe60a;</i>

			<span>首页</span>

		</a>

		<a class="col-xs-3" href="${pageContext.request.contextPath}/supermarket.html">

			<i class="iconfont-gl">&#xe609;</i>

			<span>闪送超市</span>

		</a>

		<a class="col-xs-3 js-shopcar" href="javascript:addToShop()">

			<i class="iconfont-gl">&#xe628;</i>

			<span>购物车</span>

		</a>

		<a class="col-xs-3" href="${pageContext.request.contextPath}/index.html">

			<i class="iconfont-gl">&#xe62b;</i>

			<span>我的</span>

		</a>

	</div>

	</div>

	

	<!-- 隐藏表单页 -->

	<input id="shopID" type="hidden" value="${shopID }">

	<input id="userID" type="hidden" value="${sessionScope.loginUser.userID }">

	<input id="firstClaID" type="hidden" value="${firstClassID }">

	<input id="isClosed" type="hidden" value="${isClosed }">

	<input id="isOutArea" type="hidden" value="${isOutArea }">

	<input id="userInter" type="hidden" value="${loginUser.point }">

	<form id="queryJumpForm" action="${pageContext.request.contextPath}/search-shop-${shopID }.html" method="post" style="display: none;">

		<input id="proCarList" name="proCarList" type="hidden" value="">

	</form>

	

	<!-- 引入js -->

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>

	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/iscroll-lite.js?v=0.01"></script> -->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/iscroll-probe.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.scrollLoading.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>

	<script type="text/javascript">

	var BASEPATH="${pageContext.request.contextPath}";

	var shopCarPro=${proCarList};

	var min_send_price=${dispatchMsg.fee };

	var ERRORMSG

	</script>

	

	<c:if test="${isClosed!=null }">

	<script type="text/javascript">

	ERRORMSG="店铺歇业中";

	layer.msg(ERRORMSG);

	</script>

	</c:if>

	

	<c:if test="${isOutArea!=null }">

	<script type="text/javascript">

	ERRORMSG="您当前不在该店的配送地区内哦";

	layer.msg(ERRORMSG);

	</script>

	</c:if>

	

	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/supermarket.js?v=20161029"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/loginalert.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.nivo.slider.js"></script>

	<script type="text/javascript">

    $(window).load(function() {

        $('#slider').nivoSlider({

        	"directionNav":false,

            "controlNav":false

        });

    });
	</script>

</body>

</html>

