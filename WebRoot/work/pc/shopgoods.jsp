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

.tr td>#price{font-size: 18px;color: #E70012;font-weight: bold;}

.jump-pagin{
	display: inline-block;
	vertical-align: text-bottom;
	margin-left: 10px!important;
	padding-left: 0;
	margin: 20px 0;
}
.jump-pagin>div{padding-bottom: 8px;}
.jump-pagin .jump-page{width: 35px; text-align: center;}
</style>
</head>


<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>


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
							  <div style="margin-bottom: 10px;">
							  	  <input id="inputQuery" type="text" class="form-control" placeholder="请输入商品名称" style="display: inline-block; width: 250px; vertical-align: middle;">
							  	  <a href="javascript:doEasyQuery()" class="btn btn-success"><i class="fa fa-search"></i> 查询</a>
							  </div>
							  <div>
								  <div class="pull-left">
								  	  <a href="javascript:void(0)" class="btn btn-success settings-toggle"><i class="fa fa-search"></i> 高级查询</a>
								  </div>
								  <div class="pull-right">
								  	  <a class="btn btn-info" id="goods_add" href="javascript:;"><i class="fa fa-plus"></i> 批量铺货</a>
								  	  <a class="btn btn-warning" href="${pageContext.request.contextPath}/shop/productList.html" style="width: 93px;"><i class="fa fa-reply"></i> 我的商品</a>
								  </div>
							  </div>
						</div>
						<div class="panel panel-archon panel-todo">
						
							<div class="panel-body com-pan">
								<table class="table table-striped table-bordered" id="goods_table">
									<thead>
										<tr data-order="true" data-check="check">
											<!-- <th data-auto="true">序号</th> -->
											<th data-diy="cover">预览图</th>
											<th data-hide="id"></th>
											<th data-hide="isRecInt"></th>
											<th data-value="name">商品名称</th>
											<th data-value="price">商品价格</th>
											<th data-hide="disPrice">折扣价格</th>
											<th data-value="isRecommond">是否推荐</th>
											<th data-value="typeName">商品类型</th>
											<th data-value="createTime">时间</th>
											<%--<th data-value="viewCount">浏览量</th>
											<th data-value="buyCount">购买量</th>
											--%><th data-diy="true">操作</th>
										</tr>
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
				<form class="form-inline" action="${pageContext.request.contextPath}/shop/init_shop.html">
					<div class="right-sidebar-holder">
						<button href="screens.html" class="btn btn-danger btn-block">查询</button>
						
						<a href="javascript:void(0)" class="btn btn-info settings-toggle" style="display: block;margin-top: 5px;">返回</a>

						<h4 class="page-header text-primary text-center">筛选条件</h4>

						<ul class="list-group theme-options">
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
						<input type="hidden" id="hide_brand" name="brandID" value="-1"/>
						<div class="list-group contact-list" id="brands">
							<a class="list-group-item" id="load_more" style="text-align: center; line-height: 35px;"> 加载更多... </a>
						</div>
						<div id="bic_calendar_right" class="bg-white"></div>
					
						<input id="checkType" name="cs" type="hidden" value="${checkType }">
					</div>
				</form>

			</div>
			
			<!-- 模态框（Modal） -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
				   aria-labelledby="myModalLabel" aria-hidden="true">
				   <div class="modal-dialog">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" 
				               data-dismiss="modal" aria-hidden="true">
				                  &times;
				            </button>
				            <h4 class="modal-title" id="myModalLabel">
				               		供应商
				            </h4>
				         </div>
				         <div class="modal-body">
				         		 <table class="table table-striped table-bordered">
									  	<thead>
									  		<tr>
									  			<th>供应商</th>
									  			<th>联系电话</th>
									  		</tr>
									  	</thead>
									  	<tbody id="suplist">
									  		
									  	</tbody>
					 			 </table>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
				         </div>
				      </div><!-- /.modal-content -->
				</div><!-- /.modal -->
							
			
			
		</div>
	</div>
	
	<!-- 价格修改模态框 -->
	<div class="modal fade" id="priceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" 
		               data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="myModalLabel">
		        	    请设置商品价格
		            </h4>
		         </div>
		         <div class="modal-body">
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-default" 
		               data-dismiss="modal">关闭
		            </button>
		            <a type="button" class="btn btn-primary" href="javascript:savePrice()">
		               	保存
		            </a>
		         </div>
		      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
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
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
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
	<script>
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
	
	
	
	
		//查看供应商
	$("#goods_table").on("click",".shop_sup",function(){
		var id = $(this).parents("tr").find("#id").val();
		
		$.ajax({
              type: "post",
              url: path+"shop/pro_supplier.html",
              data:"proid="+id,
              dataType: "json",
              success: function(data){
              		//$("#myModal table #suplist").remove("tr");
              		      $("#myModal table #suplist").empty();
              		var jnon=data.msg;
              		//var jnon=eval("("+data.msg+")");
            		$.each(jnon,function(i){
        				$("#myModal table #suplist").append
        				(' <tr><td><span class="">'+jnon[i].supplier_name+'</span></td>\
        				 <td><span class="">'+jnon[i].tel+'</span></td>\
        				</tr>');
        			});
        			$('#myModal').modal('show');
              },
              error: function () {
                  layer.msg('操作有误!');
              }
          });
		
	})
	
	</script>
	
	<script src="${pageContext.request.contextPath}/work/pc/js/syl/shopgoods.js?v=0.03"></script>
</body>
</html>
