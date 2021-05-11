<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款申请</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">

</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var shopID = "${loginUser.shopID }";
</script>

<style>
.box{position:absolute;width:600px;left:20%;height:auto;z-index:100;background-color:#fff;border:0px  solid;border-radius: 10px;}
.sold{height:30px;text-indent:10px;line-height:42px;color:#fff;background:#495b79;overflow:hidden; border-top-left-radius: 10px; border-top-right-radius: 10px;}
.sold a img{height:15px;padding-right:10px;} 
.box .list{padding:20px 20px 0 20px;}
.box .list p{height:24px;line-height:24px;} 
.box .list p span{margin:0 5px 0 0;font-family:"宋体";font-size:12px;font-weight:400;}
.box .button{text-align: center;padding:5px}
#bg{background-color:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter: alpha(opacity=50);-moz-opacity: 0.5;}
	.list-inline{
		margin-left: 13px;
	}
	.input-group-addon {
		border-radius: 4px 0 0 4px;
	}
	
	#more_search {
		border-radius: 0 4px 4px 0;
	}
</style>
<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="head.jsp" />
		<!-- 页面主体内容 结束 -->

		<!-- 页面主体 开始 -->
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="menu.jsp" />

			<div class="content">

				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">退款申请</li>
						</ul>
						<h3 class="page-header">
							<span id="span_title">退款列表</span>
							<i class="iconfont-gl" style="color: #2A6496; font-size: 24px;">&#xe628;</i>
						</h3>
					</div>
				</div>

				<!-- 商品列表-->
				<div class="row">
					<div class="col-md-12">
						<div id="refund_query" class="mail-options-nav clearfix">
							<form action="${pageContext.request.contextPath}/shop/refund/init.html">
								<div class="pull-left">
									<ul class="list-inline">
										<li style="vertical-align: middle">
											<div class="input-group margin-bottom-sm">
												<span id="search_btn" class="input-group-addon">
													<i class="fa fa-search"></i>
												</span>
												<input class="form-control" id="more_search" name="more_search" type="text" placeholder="订单编号,电话,收货人">
											</div>
										</li>
										<li>
											<button class="btn btn-primary" type="submit">查询</button>
										</li>
									</ul>
								
								</div>
							</form>
						</div>
					
						<div class="panel panel-archon panel-todo">
							<div class="panel-body com-pan">
								<table class="table table-striped table-bordered" id="refund_table">
									<thead>
										<tr>
											<th data-hide="id"></th>
											<th data-hide="refund_status"></th>
											<th data-value="orderCode">订单编号</th>
											<th data-value="nickName">下单用户</th>
											<th data-value="recName">收货人</th>
											<th data-value="recTel">联系电话</th>
											<th data-value="totalPrice">订单价 </th>
											<th data-value="fee">运费</th>
											<th data-value="createTime">创建时间</th>
											<th data-value="status">状态</th>
											<th data-btns="btns">操作</th>
										</tr>
									</thead>
									
							    <tbody>
							    <div id="bg"></div>
							    <c:forEach items="${orderMsg}" var="msg">
								  <div id="liyou_${msg.orderCode }" class="box" style="display:none">
   								 	<div class="sold"><a href="#" class="close"><img src="${pageContext.request.contextPath}/work/pc/images/close.png"></a></div>
    								<div class="list">
           									<p><span>订单编号：${msg.orderCode }</span></p>
							           		  
							           		<p><span>下单用户：${msg.nickName }</span></p>
							           		  		
							           		<p><span>联系电话：${msg.recTel }</span></p>
							           		  		
							           		<p><span>订单价格：${msg.totalPrice }</span></p>
							           		
							           		<p><span>创建时间：${msg.createTime }</span></p>
							           		  		
							           		<p><span>退款理由：${msg.mo }</span></p>
   									 </div>
   									 <div class="button"> 
							           	<a href="${pageContext.request.contextPath}/shop/order-det-${msg.orderCode }.html
							           	" class="btn btn-info" >订单详情</a>
							         </div>
								</div>
								</c:forEach>
							  </tbody>
							</table> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/proType.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/syl/refund_list.js?v=0.01"></script>
</body>
</html>