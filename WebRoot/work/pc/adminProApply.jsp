<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>新增商品申请列表</title>
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
							<li class="active">新增商品申请</li>
						</ul>
						
						<h3 class="page-header">新增商品申请</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							
							<div class="pull-left" id="state_query">
								<form action="${pageContext.request.contextPath}/admin/adminapply.html?pageIndex=1&pageSize=${page.pageSize}" method="post" id="uForm">
									<input type="hidden" id="hide_type" name="stats" value="${stats}">
									<a href="javascript:void(0)" class="btn bg-purple text-white" data-id="-1">全部状态</a>
									<a href="javascript:void(0)" class="btn btn-info" data-id="0">待处理</a>
									<a href="javascript:void(0)" class="btn btn-success" data-id="1">可使用</a>
									<a href="javascript:void(0)" class="btn bg-purple text-white" data-id="2">不可用</a>	
								</form>
							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr>
							             	<th>商品图片</th>
							             	<th>商铺</th>
							               <th>品牌</th>
							               <th>分类</th>
							               <th>申请时间</th>
							              <th>状态</th> 
							              <th>操作</th>
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${app}" var="bu">
							           <tr class="map-con tr">
							           	   <td class="con-name">
							           	   		<img alt="" src="${bu.shopImg }" width="150px" height="110px">
							           	   </td>
							           	   <td class="con-city">${bu.shop_name }</td>
							           	   <td class="con-city">${bu.brName }</td>
							           	   <td class="con-area">${bu.tyName  }</td>
							           	   <td class="con-add">${bu.craeteTime }</td>
							           	   <td class="con-name">
												<c:if test="${bu.status==0 }">
							           	   			<label class="label bg-success" name="state">${bu.stats}</label>
							           	  		</c:if>
							           	  		<c:if test="${bu.status==1 }">
							           	   			<label class="label bg-info" name="state">${bu.stats}</label>
							           	  		</c:if>
							           	  		<c:if test="${bu.status==2 }">
							           	   			<label class="label bg-danger" name="state">${bu.stats}</label>
							           	  		</c:if>
							           	   </td>
							           	   <td>
							           	   		<c:if test="${bu.status==0 }">
							           	   			<div style="display: -moz-groupbox;">
							           	   				<a href="javascript:updapply(${bu.id},1)"  class="btn"><label class="label bg-info" >可使用</label></a>
							           	   			
														<a href="javascript:updapply(${bu.id},2)"  class="btn"><label class="label bg-danger" >不可用</label></a>	
							           	  			</div>
							           	  		</c:if>
							           	   </td>						           								         
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
				</div>
		 	</div>
		 </div>
		 <!-- 页面主体内容 结束 -->
	</div>

	<!-- 加载js -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/js/imgChange.js"></script>
	
	<script type="text/javascript">
	new Pagin({
		size :${page.pageSize},
    	perPage :5, 
    	total  :${page.totalCount},
    	nowPage:${page.pageIndex},
	    dealFun:function(size,page){
	    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
	    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/adminapply.html?pageIndex="+page+"&pageSize="+size);
	    $("#uForm").submit();
	}
});
	$("body").on("click","#state_query a",function(){
		var id = $(this).attr("data-id");
		$("#hide_type").val(id);
		$("#state_query").find("form").submit();
		})

	new g_fnImgCheck();
	
	function updapply(id,sta) {
		 layer.load(1, { shade: [0.8,'#333']});
		 $.ajax({
             type: "post",
             url: "/nanny/admin/updProApply.html",
             data:"id="+id+"&sta="+sta,
             dataType: "text",
             success: function (msg) {
             	var data=eval('(' + msg + ')');
             	if(data.msg=='1'){
             		 layer.msg("审核成功，前往添加商品");
                     setTimeout(location.href="/nanny/product/url_product.html?aid="+id, 3000);
             	}else{
             		layer.msg("审核成功");
                    setTimeout(location.reload(true), 2000);
             	}
                   
             },
             error: function () {
                 layer.msg('操作有误!');
             }
         });
	}
	
	
	function clearNoNum(obj)
	{
	 //先把非数字的都替换掉，除了数字
	 obj.value = obj.value.replace(/[^\d]/g,"");
	}
	
	
	
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
