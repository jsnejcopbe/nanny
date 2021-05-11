<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>积分商品兑换明细</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css"
	rel="stylesheet" type="text/css">
<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="js/html5shiv.js"></script>
	  <![endif]-->
<style>
a {
	cursor: pointer;
}

th {
	text-align: center;
}

tr {
	height: 45px !important;
	text-align: center !important;
}

td {
	text-align: center;
}

.mybtns {
	margin-left: 10px;
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
</style>
<script type="text/javascript">
	var path = "/nanny/";
</script>
<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->


		<!-- 头部内容 -->
		<jsp:include page="head.jsp" />
		<!-- 头部 结束 -->


		<div class="box-holder">
			<!-- 左侧菜单 -->


			<!-- 左侧菜单 -->

			<jsp:include page="menu.jsp" />

			<!-- 左侧菜单 结束-->


			<div class="content">
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a>
							</li>
							<li class="active">积分商品兑换明细</li>
						</ul>

						<h3 class="page-header">积分商品兑换明细</h3>
					</div>
				</div>

				
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" >
							<c:if test="${is_shop=='1'}">
								<form action="${pageContext.request.contextPath}/shop/shopProductExchange.html?pageIndex=1&pageSize=${obj.size}" method="post" id="uForm">
								</c:if>
								<c:if test="${is_shop=='2'}">
								<form action="${pageContext.request.contextPath}/admin/ProductExchangeDetail.html?pageIndex=1&pageSize=${obj.size}" method="post" id="uForm">
								</c:if>
									 <input id="hide_type1" type="hidden" value="${memo}" name="memo"/>
									 <span>日期：</span>  
									 <input type="text" class="ui-ipt" placeholder="请选择开始日期" id="logmin" name="logmin" value="${ logmin }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  -
									 <input type="text" class="ui-ipt" placeholder="请选择结束日期" id="logmax" name="logmax" value="${ logmax }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  
									 <input type="text" class="ui-ipt" placeholder="兑换用户的用户名、手机号码" name="queryName" value="${queryName}"> 
									  
									 <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
								</form>
							</div>
					
							<div class="pull-right" id="state_query">
								
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									
									<c:if test="${memo==null || memo=='' || memo=='-1'}">全部状态</c:if>
									<c:if test="${memo=='0' }">未确认</c:if>
									<c:if test="${memo=='1' }">已确认</c:if>
									
									
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu" id="qqq">
									<c:if test="${is_shop=='1'}">
									
										<li><a href="javascript:void(0)" data-id="-1">全部</a></li>
										<li><a href="javascript:void(0)" data-id="0">未确认</a></li>
										<li><a href="javascript:void(0)" data-id="1">已确认</a></li>
										</c:if>
									<c:if test="${is_shop=='2'}">
										<li><a href="javascript:void(0)" data-id="-1">全部</a></li>
										<li><a href="javascript:void(0)" data-id="0">未确认</a></li>
										<li><a href="javascript:void(0)" data-id="1">已确认</a></li>
										</c:if>
									
									</ul>
								</div>
							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover"
									id="shop_table">
									<thead>
										<tr>
											<th></th>
											<th>兑换用户</th>
											<c:if test="${is_shop!='1'}">
											<th>店铺名</th>
											</c:if>
											<th></th>
											<th>兑换商品</th>
											<th>兑换数量</th>
											<th>消耗积分</th>				
											<th>时间</th>
											<th>状态</th>
											<%--<th>描述</th>--%>
											<c:if test="${is_shop=='1'}">
											<th>操作</th>
											</c:if>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${arr}" var="arr">
									 <tr class="tr">
									 
									 				<td style="vertical-align: middle;">
									 			<span><img alt="" src="${arr.headImg}" width="50px" style="float: right;">
															 </span>
															
									 				</td>
									 				
									 				<td style="vertical-align: middle;">
									 				<span>${arr.nickName}</span>	
									 				</td>
									 				
									 				<c:if test="${is_shop!='1'}">
									 				<td style="vertical-align: middle;">
									 						<span>
									 			<img alt="" src="${arr.shop_icon}" width="50px" >
									 			<span>${arr.shop_name}</span>
															 </span>
															
									 				</td>
									 				</c:if>
									 				
									 				<td style="vertical-align: middle;">
									 				<span><img alt="" src="${arr.cover}" width="50px" style="float: right;"></span>	
									 				</td>
									 				
									 				<td style="vertical-align: middle;">
									 				<span>${arr.name}</span>	
									 				</td>
									 				<td style="vertical-align: middle;">
									 				<span>${arr.number}</span>	
									 				</td>
									 				<td style="vertical-align: middle;">
									 					<span >${arr.point}</span>
									 				</td>
						 				
									 				<td style="vertical-align: middle;">
									 					<span >${arr.createTime}</span>
									 				</td>
									 				
									 				<c:if test="${arr.memo=='0'}">
									 				<td style="vertical-align: middle;">
									 					<span ><label class="label bg-success" name="state">未确认</label></span>
									 				</td>
									 				</c:if>
									 				
									 				<c:if test="${arr.memo=='1'}">
									 				<td style="vertical-align: middle;">
									 					<span ><label class="label bg-success" name="state">已确认</label></span>
									 				</td>
									 				</c:if>
									 				<%--<td style="vertical-align: middle;">
									 					<span >${arr.des}</span>
									 				</td>--%>
									 				
									 				<c:if test="${is_shop=='1'}">
									 				<td style="vertical-align: middle;">
									 				<c:if test="${arr.memo=='0'}">
									 					<span ><a class="btn btn-info" href="javascript:upMemo(${arr.id })">确认</a></span>
									 					<span ><a class="btn btn-info" href="javascript:delrecord(${arr.id })">拒绝</a></span>
									 				</c:if>
									 				</td>
									 				</c:if>
				
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="pagin-area" style="text-align: center;">
									<div class="paging_bootstrap pagination js-pagin" style="margin: 0px;"></div>
										</div>
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
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
<script type="text/javascript">

new Pagin({
	size :${obj.size},
	perPage :5, 
	total  :${obj.total},
	nowPage:${obj.nowpage},
    dealFun:function(size,page){
    if(${is_shop}=='1'){
    $("#uForm").attr("action","${pageContext.request.contextPath}/shop/shopProductExchange.html?pageIndex="+page+"&pageSize="+size);
    }
    if(${is_shop}=='2'){
    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/ProductExchangeDetail.html?pageIndex="+page+"&pageSize="+size);
    }
    $("#uForm").submit();
}
});


new g_fnImgCheck();

$("body").on("click","#qqq a",function(){
			var id = $(this).attr("data-id");
			$("#hide_type1").val(id);
			$("#shop_query").find("form").submit();
		});
</script>
<script type="text/javascript">
	function upMemo(id){
	
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/shop/ProductExchangeConfirm.json",
			data : "uprId="+id,
			success : function(msg) {
				var data = eval('(' + msg + ')');
				
				if(data.hasOwnProperty("success"))
					
					layer.msg("操作成功");
					setTimeout(location.reload(true), 1000);
			
					
				if (data.errorType !="codeError")
					changeCode();
			}
		});	
	}
	
	
	function delrecord(id){
	
		 $.ajax({
		        type: "post",
		        url: "${pageContext.request.contextPath}/shop/UprRefuse.json",
		        data:"uprId="+id,
		        dataType: "text",
		        success: function(msg){
		        	//var data = eval('(' + msg + ')');
		        	layer.msg(msg);
		        	setTimeout(location.reload(true), 1000);
		        },
		        error: function () {
		            layer.msg('操作有误!');
		        }
		    });
	}
</script>

</body>
</html>
