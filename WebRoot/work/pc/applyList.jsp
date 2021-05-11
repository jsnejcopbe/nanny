<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>商户申请</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
#shop_table thead tr th:nth-child(2){display: none;}
#shop_table tbody tr td:nth-child(2){display: none;}
.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
.mail-options-nav{
	padding: 10px;
	border: 1px solid #B9C4D5;
	border-bottom:none!important;
	background-color: #F5F5F5;
}
</style>
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>


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
							<li class="active">商户申请</li>
						</ul>
						
						<%--<div class="form-group hiddn-minibar pull-right" id="shop_query" >
                  			<form action="${pageContext.request.contextPath}/admin/init_shop.html">
	                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
					                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="姓名,商店名,手机,微信号" type="text" name="many" >
					                 <input id="hide_type" type="hidden" value="-1" name="state"/>
					                 <span class="input-group-btn">
					                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
					                 </span>
					            </a>
				            </form>
               		    </div>
						
						--%><h3 class="page-header"> 商户申请</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form action="${pageContext.request.contextPath}/admin/init_shop.html">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="姓名,商店名,手机,微信号" type="text" name="many" >
						                 <input id="hide_type" type="hidden" value="-1" name="state"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							<div class="pull-right" id="state_query">
								<a href="javascript:void(0)" class="btn bg-purple text-white" data-id="-1">全部状态</a>
								<a href="javascript:void(0)" class="btn btn-info" data-id="0">待审核</a>
								<a href="javascript:void(0)" class="btn btn-success" data-id="1">已通过</a>
								<a href="javascript:void(0)" class="btn btn-warning" data-id="2">未通过</a>
							</div>
						</div>
						<div class="panel">
							<%--<div class="panel-heading text-primary">
								<h3 class="panel-title">
									<i class="fa fa-user"></i> 申请列表
									<span class="pull-right">
										<div class="btn-group code">
							                 <a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Classes used"><i class="fa fa-chevron-up"></i></a>
							                 <ul class="dropdown-menu pull-right list-group" id="state_query" role="menu">
							                    <li class="list-group-item" data-id="-1"><a href="javascript:void(0)"><code>全部</code></a></li>
							                    <li class="list-group-item" data-id="0"><a href="javascript:void(0)"><code>待审核</code></a></li>
							                    <li class="list-group-item" data-id="1"><a href="javascript:void(0)"><code>已通过</code></a></li>
							                    <li class="list-group-item" data-id="2"><a href="javascript:void(0)"><code>未通过</code></a></li>
							                 </ul>
							            </div>
									</span>
								</h3>
							</div>
							
							--%><div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							               <th data-auto="ture" class="visible-lg">序号</th>
							               <th data-hide="id"></th>
							               <th data-value="name" class="visible-lg">申请用户姓名</th>
							               <th data-value="tel" class="visible-lg">申请用户电话</th>
							               <th data-value="weixinAcc" class="visible-lg">申请用户微信号</th>
							               <th data-value="shopName" class="visible-lg">商店名</th>
							               <th data-diy="state" class="visible-lg">状态</th>
							               <th data-btns="btns" class="visible-lg">操作</th>
							           </tr>
							        </thead>
								</table>
							</div>
						</div>
					</div>
				</div>
		 	</div>
		 </div>
		 <!-- 页面主体内容 结束 -->
	</div>


	<!-- 加载js -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>

	<script src="${pageContext.request.contextPath}/work/pc/js/syl/shopApply.js?v=0.01"></script>
</body>
</html>
