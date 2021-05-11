<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>商户管理</title>
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
		text-decoration: none;
	}
	
	a:HOVER{text-decoration: none;}
	
	th{
		text-align: center;
	}
	
	tr{
		height: 45px !important;
	}
	
	td{
		text-align: center;
	}
	
	@font-face {font-family: 'iconfont';
    src: url('/nanny/fonts/iconfont-gl.eot'); /* IE9*/
    src: url('/nanny/fonts/iconfont-gl.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
    url('/nanny/fonts/iconfont-gl.woff') format('woff'), /* chrome、firefox */
    url('/nanny/fonts/iconfont-gl.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
    url('/nanny/fonts/iconfont-gl.svg#iconfont') format('svg'); /* iOS 4.1- */
	}
	.iconfont-gl{
	    font-family:"iconfont" !important;
	    font-size:16px;font-style:normal;
	    -webkit-font-smoothing: antialiased;
	    -webkit-text-stroke-width: 0.2px;
	    -moz-osx-font-smoothing: grayscale;
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
	.up_file{
	position:absolute!important;
	left: 100px!important;
    top: 50px!important;
    width: 300px!important;
    height: 200px!important;
	/* font-size: 100px!important; */
	z-index:999!important;
	opacity:0!important;
	filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0)!important;
	cursor: pointer;
	
	}
	.used{color: #D9534F;font-size: 12px;display: inline-block;text-align: center;margin-right: 3px;}
	.unuse{color: #5BC0DE;font-size: 12px;display: inline-block;text-align: center;margin-right: 3px;}
	.used>i,.unuse>i{font-size: 18px;}
	</style>
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}";
</script>


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
							<li><a href="${pageContext.request.contextPath}/supplier/index.html">首页</a></li>
							<li class="active">来往商户</li>
						</ul>
						
						<h3 class="page-header">来往商户</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form action="${pageContext.request.contextPath}/supplier/supshopth.html?pageIndex=1&pageSize=${shbean.size}" method="post" id="uForm">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="商店名" type="text" name="many" value="${shopname}">
						                 
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							             	<th>商标</th>
							               <th>商铺名</th>
							               
							               <th>联系电话</th>
							              
							               <th>总订单数</th>
							         
										
							               
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${shbean.shlist }" var="ss">
							           <tr class="map-con">
							           	   <td class="con-name">
							           	   		<img alt="" src="${ss.shop_icon }" width="50px">
							           	   </td>
							           	   <td class="con-name">${ss.shop_name}</td>
							           	   <td class="con-area">${ss.memo }</td>
							           	   <td class="con-city">${ss.orcount }</td>  
							           	   	<td>
										<a class="btn btn-info" href="${pageContext.request.contextPath}/supplier/order.html?pageIndex=1&pageSize=10&query=${ss.memo }">查看详情</a>
										
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
	
	<script type="text/javascript">
	new Pagin({
			size :${shbean.size},
	    	perPage :5, 
	    	total  :${shbean.total},
	    	nowPage:${shbean.nowPage},
		    dealFun:function(size,page){
		    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
		    $("#uForm").attr("action","${pageContext.request.contextPath}/supplier/supshopth.html?pageIndex="+page+"&pageSize="+size);
		    $("#uForm").submit();
		}
	});
	
	
	
	
	
	new g_fnImgCheck();
	</script>
</body>
</html>
