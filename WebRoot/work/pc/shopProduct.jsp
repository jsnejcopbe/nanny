<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>商品一览</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->

<style>
#shop_table thead tr th:nth-child(2) {
	display: none;
}

#shop_table tbody tr td:nth-child(2) {
	display: none;

}

.com-pan {
	border: 1px solid;
	border: 1px solid #B9C4D5;
}

.mail-options-nav {
	padding: 10px;
	border: 1px solid #B9C4D5;
	border-bottom: none !important;
	background-color: #F5F5F5;
}

.coverImg{
	width: 80px;
	height: 80px;
}

#shop_table .check{height: 18px;width: 18px;}

#all_check{height: 18px;width: 18px;}

.tr td>span{display: block;height: 80px;}

.tr td>#price{font-size: 18px;color: #E70012;font-weight: bold;}
</style>
</head>


<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var isAdmin = ${loginUser.isAdmin };
</script>

<style>
a {
	cursor: pointer;
}

#caozuo {
	text-align: center;
}

/* .modal-dialog{
  	margin-top:-30px;
  	width: 1000px;
}
  
.modal-content{
  	overflow-y: auto;
	height: 500px;
} */
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
							<li class="active">商品一览</li>
						</ul>
						<h3 class="page-header">
							<span id="span_title">商品列表 </span>
							<i class="iconfont-gl" style="color: #2A6496; font-size: 24px;">&#xe628;</i>
						</h3>
					</div>
				</div>

				<!-- 商品列表-->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div style="margin-bottom: 10px;">
								<input id="inputQuery" type="text" class="form-control" placeholder="请输入商品名称" style="display: inline-block; width: 250px; vertical-align: middle;">
								<a href="javascript:doEasyQuery()" class="btn btn-success"><i class="fa fa-search"></i> 查询</a>
								&nbsp;
								<input type="radio" name="showDown" value="0" checked="checked">展示上架商品
								<input type="radio" name="showDown" value="1">展示下架商品
							</div>
							<div>
								<div class="pull-left">
									<a href="javascript:void(0)" class="btn btn-success settings-toggle"><i class="fa fa-search"></i> 高级查询</a>
									<c:if test="${loginUser.isAdmin  == '1'}">
									<a class="btn btn-info" href="${pageContext.request.contextPath}/product/url_product.html"><i class="fa fa-plus"></i>新建商品</a> 
									</c:if>
									<%--<c:if test="${loginUser.isAdmin  == '0'}">
									<a class="btn btn-info" href="${pageContext.request.contextPath}/work/pc/addproApply.jsp"><i class="fa fa-plus"></i>新增商品</a> 
									</c:if> --%>
								</div>
								<div class="pull-right">
									<%--<a id="goods" class="btn btn-info" href="${pageContext.request.contextPath}/shop/goods.html"><i class="fa fa-plus"></i>我要选货</a>--%> 
									<a class="btn btn-primary" id="many_stock" href="javascript:void(0)"><i class="fa fa-edit"></i> 批量修改</a> 
									<a class="btn btn-success" id="many_up" href="javascript:void(0)"><i class="fa fa-hand-o-up"></i> 批量上架</a> 
									<a class="btn btn-warning" id="many_down" href="javascript:void(0)"><i class="fa fa-hand-o-down"></i> 批量下架</a>
									<a class="btn btn-danger" id="many_remove" href="javascript:void(0)"><i class="fa fa-trash-o"></i> 批量删除</a>
									<c:if test="${loginUser.isAdmin  == '1'}">
									<a class="btn btn-default" href="javascript:setProAsRec()"><i class="fa fa-star-empty"></i>更改选中项的推荐状态</a> 
									</c:if>
								</div>
							</div>
						</div>
						<div class="panel panel-archon panel-todo">
							<!-- <div class="panel-heading text-primary" id="caozuo">
								<%-- <c:if test="${loginUser.isAdmin  == '0'} "> --%>
								<a id="goods" class="btn btn-default" href="${pageContext.request.contextPath}/shop/goods.html"><i class="fa fa-plus"></i>我要铺货</a>
								<%-- </c:if> --%>
								<a class="btn btn-default" href="${pageContext.request.contextPath}/product/url_product.html"><i class="fa fa-plus"></i>添加</a>
							</div> -->

							<div class="panel-body com-pan">
								<table class="table table-striped table-bordered" id="shop_table">
									<thead >
										<c:if test="${loginUser.isAdmin  == '1'}">
											<tr data-order="true" data-check="true" >
												<!-- <th data-auto="true">序号</th> -->
												<!-- <th data-value="proCode">商品编号</th> -->
												<th data-hide="id"></th>
												<th data-hide="proCode"></th>
												<th data-hide="isRecInt"></th>
												<th data-hide="isExInt"></th>
												<th data-diy="cover">预览图</th>
												<th data-value="name">商品名称</th>
												<th data-hide="costPrice">成本价格</th>
												<th data-value="price">商品价格</th>
												<th data-value="typeName">商品类型</th>
												<th data-value="isRecommond">是否推荐</th>
												<th data-value="inventory">商品库存</th>
												<th data-value="isUse">状态</th>
												<!-- <th data-value="isExchange">兑换</th> -->
												
												<th data-diy="true">操作</th>
											</tr>
										</c:if>
										<c:if test="${loginUser.isAdmin  == '0'}">
											<tr data-order="true" data-check="true" >
												<!-- <th data-auto="true">序号</th> -->
												<!-- <th data-value="proCode">商品编号</th> -->
												<th data-hide="id"></th>
												<th data-hide="isExInt"></th>
												<th data-diy="cover">预览图</th>
												<th data-hide="proCode"></th>
												<th data-value="name">商品名称</th>
												<th data-value="price">商品价格</th>
												<th data-hide="disPrice"></th>
												<th data-value="typeName">商品类型</th>
												<th data-value="inventory">商品库存</th>
												<th data-value="isUse">状态</th>
												
												<th  data-value="isExchange" id="isExchange" >兑换</th>
											
												<%--<th data-value="viewCount">浏览量</th>
												<th data-value="buyCount">购买量</th>
												--%><th data-diy="true">操作</th>
											</tr>
										</c:if>
									</thead>
								</table>
							</div>
						</div>
					</div>
					<!-- / col-md-12 -->
				</div>
			</div>

			<!-- 右侧菜单 -->
			<div class="right-sidebar right-sidebar-hidden" id="shop_query">
				<form class="form-inline" action="${pageContext.request.contextPath}/admin/init_shop.html">
					<div class="right-sidebar-holder">
						<button href="screens.html" class="btn btn-danger btn-block">查询</button>
						
						<a href="javascript:void(0)" class="btn btn-info settings-toggle" style="display: block;margin-top: 5px;">返回</a>

						<h4 class="page-header text-primary text-center">筛选条件</h4>

						<ul class="list-group theme-options" >
							<li class="list-group-item" href="#">商品关键词
								<div class="input-group" style="width: 100%;">
									<input class="form-control form-cascade-control " type="text" placeholder="商品编号、商品名称" id="more" name="more" />
								</div>
							</li>
							<li class="list-group-item" href="#">商品大类
								<div class="input-group" style="width: 100%;">
									<select class="form-control" name="typeID1">
									</select>
								</div>
							</li>
							<li class="list-group-item" href="#">二级分类
								<div class="input-group" style="width: 100%;">
									<select class="form-control" name="typeID2">
										<option value="-1">请选择</option>
									</select>
								</div>
							</li>
						</ul>

						<h4 class="page-header text-primary text-center">商品品牌</h4>
						<input type="hidden" id="hide_brand" name="brandID" value="-1" />
						<div class="list-group contact-list" id="brands">
							<a class="list-group-item" id="load_more" style="text-align: center; line-height: 35px;"> 加载更多... </a>
						</div>
						<div id="bic_calendar_right" class="bg-white"></div>
						
						<input id="checkType" name="cs" type="hidden" value="${checkType }">
						
						<input id="showDown" type="hidden" name="showDown" value="0">
						 
					</div>
				</form>

			</div>
		</div>
	</div>

	<div class="modal fade" id="stock_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">批量修改</h4>
				</div>
				<div class="modal-body">
					<form>
						
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="save" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 隐藏input -->
	<c:if test="${pro_query_items!=null }">
	<input id="seNowPage" type="hidden" value="${pro_query_items.now_page }">
	<input id="seMore" type="hidden" value="${pro_query_items.more }">
	<input id="seType1" type="hidden" value="${pro_query_items.typeID1 }">
	<input id="seTypeID2" type="hidden" value="${pro_query_items.typeID2 }">
	<input id="seBrandID" type="hidden" value="${pro_query_items.brandID }">
	</c:if>

	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/imgChange.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/proType.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/imgChange.js"></script>
	<script type="text/javascript">
	
	$("#shop_query").niceScroll({cursorcolor:"#54728c"});
	$("#shop_query").css("height",$(".box-holder").height()+"px");
	</script>
	<script src="${pageContext.request.contextPath}/work/pc/js/syl/shopProduct.js?v=0.10"></script>
<script>
var pageold =0;	
var aa=setInterval("imgChange2()",300);
function imgChange2(){
		     //1.检查表格是否出来
		    if($("#shop_table:has(tr)").length!=0)         
		    {   
         		 var pageindex= $(".page.active").attr("data-index");
                 if(pageindex!=pageold ){
	                 pageold=pageindex;
	                 //2.初始化插件
		             $("#shop_table .tr").imgChange();   
	             };	             
		    };   
};
</script>
</body>
</html>
