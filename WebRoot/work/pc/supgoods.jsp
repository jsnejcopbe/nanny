<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>一键铺货</title>
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
a {
	cursor: pointer;
}

#goods_table thead tr th:nth-child(3){display: none;}
#goods_table tbody tr td:nth-child(3){display: none;}
.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
.mail-options-nav{
padding: 10px;
border: 1px solid #B9C4D5;
border-bottom:none!important;
background-color: #F5F5F5;
}

.coverImg{
	width: 80px;
	height: 80px;
}

#goods_table .check{height: 18px;width: 18px;}

#all_check{height: 18px;width: 18px;}
</style>
</head>


<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var s_value="${type2}";
	var parid="${type1}";
	var brid="${brID}";
</script>


<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="suphead.jsp" />
		<!-- 页面主体内容 结束 -->

		<!-- 页面主体 开始 -->
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="supmenu.jsp"/>

			<div class="content">

				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">商品铺货</li>
						</ul>
						<h3 class="page-header">
							市场选货 <a href="#" class="panel-settings"> <i class="iconfont-gl" style="color: #2A6496;font-size: 24px;">&#xe617;</i>
							</a>
						</h3>
					</div>
				</div>

				<!-- 商品列表-->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							  <div class="pull-left">
							  	  <a href="javascript:void(0)" class="btn btn-success settings-toggle"><i class="fa fa-search"></i> 查询商品</a>
							  </div>
							  <div class="pull-right">
							  	  <a class="btn btn-info" id="goods_add" href="javascript:;"><i class="fa fa-plus"></i> 批量铺货</a>
							  	  <a class="btn btn-warning" href="${pageContext.request.contextPath}/supplier/suproduct.html" style="width: 93px;"><i class="fa fa-reply"></i> 返回</a>
							  </div>
						</div>
						<div class="panel panel-archon panel-todo">
						
							<div class="panel-body com-pan">
								<table class="table table-striped table-bordered" id="goods_table">
									<thead>
										<tr data-order="true" data-check="check">
										<th data-check="check"></th>
											<!-- <th data-auto="true">序号</th> -->
											<th data-diy="cover">预览图</th>
											<th data-hide="id"></th>
											<th data-value="name">商品名称</th>
											<th data-value="price">商品价格</th>
											<th data-value="disPrice">折扣价格</th>
											<th data-value="typeName">商品类型</th>
											<th data-value="createTime">时间</th>
											<%--<th data-value="viewCount">浏览量</th>
											<th data-value="buyCount">购买量</th>
											--%><th data-diy="true">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${objpro.product}" var="op">
											<tr class="tr">
											<td><input class="check" name="check" type="checkbox"></td>
											<td><img src="${op.cover}" class="coverImg"></td>
											<td style="display: none;"><input id="id" value="${op.id }" name="id" type="hidden"></td>
											<td><span id="name">${op.name } </span></td>
											<td><span id="price">${op.price }</span></td>
											<td><span id="disPrice">${op.disPrice }</span></td>
											<td><span id="typeName">${op.tname }</span></td>
											<td><span id="createTime">${op.createTime }</span></td>
											<td><a class="shop_good btn btn-primary" href="javascript:;"><i class="fa fa-plus"></i>铺货</a></td>
											</tr>
											</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="pagin-area" style="text-align: center;">
								<div class="paging_bootstrap pagination js-pagin" style="margin: 0px;"></div>
							</div>
						</div>
					</div>
					<!-- / col-md-12 -->
				</div>
			</div>

			<!-- 右侧菜单 -->
			<div class="right-sidebar right-sidebar-hidden" id="shop_query">
				<form class="form-inline" action="${pageContext.request.contextPath}/supplier/supshoplist.html?pageIndex=1&pageSize=${objpro.size}" method="post" id="sform" >
					<div class="right-sidebar-holder">
						<button href="screens.html" class="btn btn-danger btn-block">查询</button>
						
						<a href="javascript:void(0)" class="btn btn-info settings-toggle" style="display: block;margin-top: 5px;">返回</a>

						<h4 class="page-header text-primary text-center">筛选条件</h4>

						<ul class="list-group theme-options">
							<li class="list-group-item" href="#">商品关键词
								<div class="input-group" style="width: 100%;">
									<input class="form-control form-cascade-control " type="text" placeholder="商品名称" id="more" name="pname" value="${pname}"/>
								</div>
							</li>
							<li class="list-group-item" href="#">商品大类
								<div class="input-group" style="width: 100%;">
									<select class="form-control" name="typeid1">
									</select>
								</div>
							</li>
							<li class="list-group-item" href="#">二级分类
								<div class="input-group" style="width: 100%;">
									<select class="form-control" name="typeid2">
										<option value="-1">请选择</option>
									</select>
								</div>
							</li>
						</ul>

						<h4 class="page-header text-primary text-center">商品品牌</h4>
						<input type="hidden" id="hide_brand" name="brandID" value="-1"/>
						<div class="list-group contact-list" id="brands">
							<a class="list-group-item" id="load_more" style="text-align: center; line-height: 35px;"> 加载更多... </a>
						</div>
						<div id="bic_calendar_right" class="bg-white"></div>
					
					</div>
				</form>

			</div>
		</div>
	</div>
	

	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/verify.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/jquery.form.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/proType.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/imgChange.js"></script>
	
	
	
	
	
	
	
	
	<script type="text/javascript">
	
	$("#shop_query").niceScroll({cursorcolor:"#54728c"});
	$("#shop_query").css("height",$(".box-holder").height()+"px");
	</script>
		<script src="${pageContext.request.contextPath}/work/pc/js/supgoods.js?v=0.01"></script>
	<script type="text/javascript">
new Pagin({
		size :${objpro.size},
    	perPage :5, 
    	total  :${objpro.total},
    	nowPage:${objpro.nowPage},
	    dealFun:function(size,page){
	    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
	    $("#sform").attr("action","${pageContext.request.contextPath}/supplier/supshoplist.html?pageIndex="+page+"&pageSize="+size);
	    $("#sform").submit();
	}
});
	
	
	
	
	var pageold =0;	
	var aa=setInterval("imgChange2()",300);
	function imgChange2(){
			     //1.检查表格是否出来
			    if($("#goods_table:has(tr)").length!=0)         
			    {   
	         		 var pageindex= $(".page.active").attr("data-index");
	                 if(pageindex!=pageold ){
		                 pageold=pageindex;
		                 //2.初始化插件
			             $("#goods_table .tr").imgChange();   
		             };	             
			    };   
	};
	</script>
	

</body>
</html>
