<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>我的订单</title>

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
<link href="${pageContext.request.contextPath}/work/pc/css/shopOrderList.css" rel="stylesheet" type="text/css">
</head>
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
.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
.mail-options-nav{
	padding: 10px;
	border: 1px solid #B9C4D5;
	border-bottom:none!important;
	background-color: #F5F5F5;
}
</style>

<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="suphead.jsp" />
		
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="supmenu.jsp" />
			
			<div class="content">
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/supplier/supplierIndex.html">首页</a></li>
							<li class="active">店铺订单管理</li>
						</ul>
						<h3 class="page-header"> <i class="iconfont-gl">&#xe615;</i> 店铺订单管理</h3>
					</div>
				</div>
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
						   <div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form action="${pageContext.request.contextPath}/supplier/order.html?pageIndex=1&pageSize=10" id="sform" method="post">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;width: 300px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="收货人姓名、手机号、订单号" value="${query }" type="text" name="query" >
						                 <input id="hide_type1" type="hidden" value="${sta }" name="sta"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							
							<div class="pull-right" id="state_query">
								<div class="fresh">
									<a href="javascript:void(0)"><i class="fa fa-repeat"></i> <span class="js-fr-time">24</span>秒后刷新</a>
								</div>
								&nbsp;
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<c:if test="${sta==null || sta=='' }">全部状态</c:if>
									<c:if test="${sta=='0' }">未确定</c:if>
									<c:if test="${sta=='1' }">已确定</c:if>
									<c:if test="${sta=='2' }">已取消</c:if>
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu" id="qqq">
										<li><a href="${pageContext.request.contextPath}/supplier/order.html">全部状态</a></li>
										<li><a href="javascript:void(0)" data-id='0'>未确定</a></li>
										<li><a href="javascript:void(0)" data-id='1'>已确定</a></li>
										<li><a href="javascript:void(0)" data-id='2'>已取消</a></li>
									</ul>
								</div>
							</div>
						</div>
						
						<div class="panel com-pan">
							<div class="panel-body">
								<table class="todo-table table table-bordered top-border">
									<thead>
										<tr class="header-row">
							               <th class="visible-lg col-md-5">订单商品</th>
							               <th class="visible-lg col-md-2">单价(元)</th>
							               <th class="visible-lg col-md-2">数量</th>
							               <th class="visible-lg col-md-2">实付款</th>
							               <th class="visible-lg col-md-1">交易状态</th>
							           </tr>
									</thead>
								</table>
							</div>
							
					
							
							<c:forEach items="${jso}" var="jso" varStatus="detcount">
							<div class="panel-body">
								<div class="col-md-12 or-it">
									<div class="it-tit">
										<span >${jso.createTime}</span>
										&nbsp;
										<span>订单号：${jso.orderCode }</span>
										<div class="pull-right">
											<a class="btn btn-info" href="${pageContext.request.contextPath}/supplier/orderDet.html?suOrderId=${jso.id }">查看详情</a>
											<c:if test="${jso.status=='0'}">
											<a class="btn btn-warning" href="javascript:supplierorderConfirm(${jso.id },${jso.payShopID},1)" >确认订单</a>
											<a class="btn btn-warning" href="javascript:supplierorderConfirm(${jso.id },${jso.payShopID},2)" >取消订单</a>
											</c:if>
											
								
										</div>
									</div>
									<div class="it-con">
										<table class="todo-table table table-bordered">
											<tbody>
											 	 
												<tr class="tr">
													<td class="col-md-5">
														<div class="col-xs-3 con-img">
															<img src="${jso.shop_icon }">
														</div>
														<div class="col-xs-9 con-name">
															${jso.shop_name }
														</div>
													</td>
													<td class="col-md-2">
														¥${jso.totalPrice }
													</td>
													<td class="col-md-2">
														${jso.recSupplierID}
													</td>
												
													<td class="col-md-2  td-total">
													¥${jso.totalPrice }元(含配送费${jso.fee }元)
													
							
													
													</td>
													
											
													<c:if test="${jso.status=='1' }">
													<td class="col-md-1  td-total-none">已确认</td>
													</c:if>
													<c:if test="${jso.status=='2' }">
													<td class="col-md-1  td-total-none">已取消:${jso.memo }</td>
													</c:if>
													<c:if test="${jso.status=='0' }">
													<td class="col-md-1  td-total-none">未确认</td>
													</c:if>
												</tr>
												
											</tbody>
										</table>
									</div>
								</div>
							
							</div>
							</c:forEach>
							
							<div class="btn-area pagin-area">
								<div class="paging_bootstrap pagination js-pagin"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 隐藏表单 -->
	<!--  <form id="printForm" action="${pageContext.request.contextPath}/printpage.html" method="post" target="_blank" style="display: none;">
		<input id="printProMsg" name="printProMsg" type="hidden" value="">
	</form>
	-->
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/extend/layer.ext.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	var BASEPATH = "${pageContext.request.contextPath}";

	
		new Pagin({
		size :${jsopage.size},
    	perPage :5, 
    	total  :${jsopage.total},
    	nowPage:${jsopage.nowpage},
	    dealFun:function(size,nowPage){
	    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
	    $("#sform").attr("action","${pageContext.request.contextPath}/supplier/order.html?pageIndex="+nowPage+"&pageSize="+size);
	    $("#sform").submit();
	}
});
	

	
	
	function supplierorderConfirm(orderID,payShopID,sta){
	
	if(sta=='2'){
	 layer.prompt({title: '取消订单理由：', formType: 2}, function(text){
	     var param={
			"sURL":BASEPATH+"/supplierorder/Confirm.json",
			"Data":"orderID="+orderID+"&sta="+sta+"&text="+text,
			"fnSuccess":function(data){
				layer.closeAll('loading');
				layer.msg(data.msg);
				
				
			//	$("#printForm").submit();
				
				setTimeout("location.reload(true)", 1000);
			},
			"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
		};
		new g_fnAjaxUpload(param);
	    });
	    }
	    
	     else if(sta==1){
	    var param={
			"sURL":BASEPATH+"/supplierorder/Confirm.json",
			"Data":"orderID="+orderID+"&sta="+sta+"&payShopID="+payShopID,
			"fnSuccess":function(data){
				layer.closeAll('loading');
				layer.msg(data.msg1);
				
				
			//	$("#printForm").submit();
				
				setTimeout("location.reload(true)", 1000);
			},
			"fnError":function(){layer.closeAll('loading');layer.msg1("操作有误");}
		};
		new g_fnAjaxUpload(param);
		}
	   

}
	 $("body").on("click","#qqq a",function(){
			var id = $(this).attr("data-id");
			$("#hide_type1").val(id);
			$("#shop_query").find("form").submit();
		}); 
	</script>
	
	  <script src="${pageContext.request.contextPath}/work/pc/js/shopOrderList.js"></script>
</body>
</html>
