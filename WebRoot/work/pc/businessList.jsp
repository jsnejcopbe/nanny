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
		text-decoration: none!important;
	}
	
	a:HOVER{text-decoration: none!important;}
	
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
		 <jsp:include page="head.jsp" />
		 
		 <div class="box-holder">
		 	<!-- 左侧菜单 -->
		 	<jsp:include page="menu.jsp" />
		 	
		 	<div class="content">
		 		<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">平台商户管理</li>
						</ul>
						
						<h3 class="page-header">平台商户管理</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form action="${pageContext.request.contextPath}/admin/business.html?pageIndex=1&pageSize=${page.pageSize}" method="post" id="uForm">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="商店名、用户名、电话" type="text" name="many" value="${shopname}">
						                 <input id="hide_type" type="hidden" value="${situation}" name="state"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							<div class="pull-right" id="state_query">
								<a href="javascript:void(0)" class="btn bg-purple text-white" data-id="-1">全部状态</a>
								<a href="javascript:void(0)" class="btn btn-info" data-id="0">开启</a>
								<a href="javascript:void(0)" class="btn btn-success" data-id="1">关闭</a>
							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							             	<th>商标</th>
							               <th>商铺名</th>
							               <th>用户名</th>
							               <th>联系电话</th>
							               <th>总营业额(含货到付款)</th>
							               <th>总订单数</th>
							               <th>创建时间</th>
							               <th>状态</th>
							               <th>操作</th>
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${busin }" var="bu">
							           <tr class="map-con">
							           	   <td class="con-name">
							           	   <img alt="" src="${bu.shop_icon }" width="50px">
							           	   
							           	   </td>
							           	   <td class="con-name">${bu.shop_name}</td>
							           	   <td class="con-city">${bu.nickName }</td>
							           	   <td class="con-area">${bu.tel }</td>
							           	   <td class="con-add">${bu.turnover }</td>
							           	   <td class="con-add">${bu.orderCount }</td>
							           	   <td class="con-add">${bu.createTime }</td>
							           	   <td class="con-pos">${bu.status }</td>
							           	   <td>
							           	   <c:if test="${bu.situation==0 }">
							           	   		<a class="used" href="javascript:updbusin(${bu.id})">
							           	   			<i class="iconfont-gl">&#xe62d;</i><br>
							           	   			关店
							           	   		</a>           	   		
							           	   </c:if>
							           	   <c:if test="${bu.situation==1 }">
							           	   		<a class="unuse" href="javascript:updbusin(${bu.id})">
							           	   			<i class="iconfont-gl">&#xe62d;</i><br>
							           	   			开店
							           	   		</a>
							           	   </c:if>
							           	   <%--<a class="used" href="${pageContext.request.contextPath}/shop/fans/findby_${bu.id}.html">
							           	   		<i class="iconfont-gl">&#xe62b;</i><br>
							           	   		粉丝
							           	   </a>
							           	   --%><a class="used" href="javascript:opp(${bu.id})">
							           	   		<i class="iconfont-gl">&#xe60a;</i><br>
							           	   		详情
							           	   </a>
							           	   <%--<a class="used" href="${pageContext.request.contextPath}/shop/account/bussac-${bu.id}.html">
							           	  		 <i class="iconfont-gl">&#xe627;</i><br>
							           	  		 账目
							           	   </a>
							           	   <a class="used" href="${pageContext.request.contextPath}/admin/order-1-${bu.id}.html">
							           	  		<i class="iconfont-gl">&#xe615;</i><br>
							           	  		 订单
							           	   </a>--%>
						           	   	   <c:if test="${bu.situation==0 }">         	   		
						           	   	    <a data-id="${bu.id}" class="unuse js-setSta" href="javascript:void(0)" <c:if test="${bu.isSubsidy==1 }">style="display: none;"</c:if>>
						           	   	  		<i class="iconfont-gl">&#xe60c;</i><br>
						           	   	  		返现
						           	   	   </a>  
							           	   <a data-id="${bu.id}" class="used js-remveSta" href="javascript:void(0)" <c:if test="${bu.isSubsidy==0 }">style="display: none;"</c:if>>
							           	   		<i class="iconfont-gl">&#xe60c;</i><br>
							           	   		返现
							           	   </a> 
							           	   
							           	   <a data-id="${bu.id}" class="unuse js-setExSta" href="javascript:void(0)" <c:if test="${bu.isTransfer==1 }">style="display: none;"</c:if>>
						           	   	  		<i class="iconfont-gl">&#xe650;</i><br>
						           	   	  		积分
						           	   	   </a>  
							           	   <a data-id="${bu.id}" class="used js-remveExSta" href="javascript:void(0)" <c:if test="${bu.isTransfer==0 }">style="display: none;"</c:if>>
							           	   		<i class="iconfont-gl">&#xe650;</i><br>
							           	   		积分
							           	   </a>
							           	   
							           	    <a data-id="${bu.id}" class="unuse js-setExIsVouvhers" href="javascript:void(0)" <c:if test="${bu.isVouchers==1 }">style="display: none;"</c:if>>
						           	   	  		<i class="iconfont-gl">&#xe608;</i><br>
						           	   	  		优惠券
						           	   	   </a>  
							           	   <a data-id="${bu.id}" class="used js-remveExIsVoucher" href="javascript:void(0)" <c:if test="${bu.isVouchers==0 }">style="display: none;"</c:if>>
							           	   		<i class="iconfont-gl">&#xe608;</i><br>
							           	   		优惠券
							           	   </a>
							           	  <%--  <c:if test="${bu.isVouchers==0 }">
							           	   <a data-id="${bu.id}" class="unuse js1" href="javascript:void(0)" >
							           	   		<i class="iconfont-gl">&#xe608;</i><br>
							           	   		优惠券
							           	   </a>
							           	   </c:if>
							           	   <c:if test="${bu.isVouchers==1 }">
							           	   <a data-id="${bu.id}" class="used js1" href="javascript:void(0)">
							           	   		<i class="iconfont-gl">&#xe608;</i><br>
							           	   		优惠券
							           	   </a>
							           	   </c:if> --%>
							           	   </c:if>
							           	   
							           	   <c:if test="${bu.situation==0 }">
							           	   <a class="used" href="${pageContext.request.contextPath}/admin/toshopplat.html?tel=${bu.tel}">
							           	  		<i class="iconfont-gl">&#xe626;</i><br>
							           	  		 管理
							           	   </a>
							           	   </c:if>
							           	   </td>
							           </tr>
							           <tr style="display:none;"  id="sname_${bu.id }">
							           		<td style="text-align: left;padding: 15px;width: 500px;" >
							           		<div>
							           		  <p><span >店标： </span>
							           		  <img alt="" src="${bu.shop_icon }" id="img_${bu.id }"  width="300px" style="margin: auto;display: block;">
							           		 
							           		  <input class="up_file" type="file" id="file1_${bu.id }" name="file" accept="image/*" > 
							           		  
							           		  </p>
							           		  		<input type="hidden" id="fi_${bu.id }" value="" > 
							           		  
							           		  </div>
							           		  <div class="clearfix">
							           		  <div class="pull-left"><p><span >商铺：<input type="text" value="${bu.shop_name }"  id="shname_${bu.id }" style="color: black; border: none;"></span></p>
							           		  
							           		  		<p><span>电话：<input type="text" value="${bu.memo }"  id="shmemo_${bu.id }" style="color: black;border: none; width: 200px;" maxlength="23"></span></p>
							           		  		<p><span style="display: block;float: left;">公告：</span>
							           		  		<textarea  id="shcon_${bu.id }" style="color: black;">${bu.con }</textarea></p>
							           		 		<p><span>小区：${bu.addName }</span></p>
									           	 	  <p>地址：${bu.detAdd}</p>
									          </div>
							           		   <div class="pull-right"> <p><span >二维码： </span><img alt="" src="${pageContext.request.contextPath}/image/TwoCode.html?data=${bu.shop_des}" width="150px" style="text-align: center;display: block;"></p>
							           		  <%--  <a href="javascript:updshop(${bu.id })" class="btn btn-info" >保存</a> --%>
							           		   </div>
							           	  	</div>
							           		   <div style=" text-align: center;"> 
							           		  		 <a href="javascript:updshop(${bu.id})" class="btn btn-info" >保存</a>
							           		   </div>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/business.js?v=0.01"></script>
	<script type="text/javascript">
	new Pagin({
			size :${page.pageSize},
	    	perPage :5, 
	    	total  :${page.totalCount},
	    	nowPage:${page.pageIndex},
		    dealFun:function(size,page){
		    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
		    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/business.html?pageIndex="+page+"&pageSize="+size);
		    $("#uForm").submit();
		}
	});
	
	
	
	
	
	new g_fnImgCheck();
	</script>
</body>
</html>
