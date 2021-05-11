<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <title>欢迎登录掌上保姆管理后台</title>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 样式加载 -->
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
 	<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
  	<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/work/pc/css/morris.css" type="text/css"></link>
  	<link href="${pageContext.request.contextPath}/work/pc/css/qqfloat.css" type="text/css" rel="stylesheet" >
  	<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
  	<link href="${pageContext.request.contextPath}/work/pc/css/brand.css" rel="stylesheet">
  	<!-- 兼容性 -->
  	<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
  	<!--[if lt IE 9]>
  	<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  	<![endif]-->

  </head>
  
  <body>
    <div class="site-holder">
    <!-- 页面主体内容 开始 -->
    	
    	<jsp:include page="suphead.jsp"/>
    	
    	<div class="box-holder">
    	
    		<jsp:include page="supmenu.jsp"/>
    	
    		<div class="content">
    			<!-- 导航栏 -->
    			<div class="row">
    				<div class="col-mod-12">
    					<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/supplier/supplierIndex.html">首页</a></li>
						</ul>
    				</div>
    			</div>
    			
    			<!-- 统计条 -->
    			<div class="row">
					<div class="col-md-4">
						<div class="info-box  bg-info  text-white">
							<div class="info-icon bg-info-dark">
								<i class="fa fa-shopping-cart fa-4x"></i>
							</div>
							<div class="info-details">
								<h4>店铺商品数  <span class="pull-right">${product} </span></h4>
								<%--<p>本周新增 <span class="badge pull-right bg-white text-info"> 48件</span> </p>
							--%></div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="info-box  bg-success  text-white">
							<div class="info-icon bg-success-dark">
								<i class="fa fa-cloud-download fa-4x"></i>
							</div>
							<div class="info-details">
								<h4>店铺订单数  <span class="pull-right">${order}</span></h4>
								<%--<p>本月订单 <span class="badge pull-right bg-white text-success"> 40</span></p>
							--%></div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="info-box  bg-warning  text-white">
							<div class="info-icon bg-warning-dark">
								<i class="fa fa-dollar fa-4x"></i>
							</div>
							<div class="info-details">
								<h4> 来往商户 <span class="pull-right">${shop}</span></h4>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 未处理订单 -->
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-cascade">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-user"></i>
									配送单
									<span class="pull-right">
										<a href="#" class="panel-minimize"><i class="fa fa-chevron-up"></i></a>
										<a href="#" class="panel-settings"><i class="fa fa-cog"></i></a>
										<a href="#" class="panel-close"><strong><i class="fa fa-times"></i></strong></a>
									</span>
								</h3>
							</div>
							<div class="panel-body nopadding">
								<table class="table">
									<thead>
										<tr>
											<th><i class="fa fa-caret-right"></i> 订单编号</th>
											<th><i class="fa fa-caret-right"></i> 下单商户</th>
											<th><i class="fa fa-caret-right"></i> 联系方式</th>
											<th><i class="fa fa-caret-right"></i> 送货地址</th>
											<th><i class="fa fa-caret-right"></i> 订单价格</th>
											<th><i class="fa fa-caret-right"></i> 下单时间</th>
											<th><i class="fa fa-caret-right"></i> 操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${orlist}" var="ol">
										<tr>
											<td>${ol.orderCode}</td>
											<td>${ol.shop_name}</td>
											<td>${ol.memo}</td>
											<td>${ol.detAdd}${ol.addName}</td>
											<td>¥ ${ol.totalPrice}元</td>
											<td>${ol.createTime}</td>
											<%-- <td><a class="btn btn-success" href="${pageContext.request.contextPath}/shop/order-det-${ol.orderCode}.html">查看</a></td> --%>
											<td><a class="btn btn-success" href="${pageContext.request.contextPath}/supplier/orderDet.html?supplierID=${ol.id }">查看</a></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<%--  <div class="row visitors-list-summary text-center">
									<div class="col-md-3 col-sm-3 col-xs-3 visitor-item ">
										<h4>  待付款 </h4>
										<label class="label label-big label-info" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=0'"> <i class="fa fa-user text-white"></i> ${order0}</label>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3 visitor-item ">
										<h4>  待发货</h4>
										<label class="label label-big label-success" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=1'"> <i class="fa fa-calendar text-white"></i> ${order1}</label>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3 visitor-item ">
										<h4>  已发货 </h4>
										<label class="label label-big label-warning" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=2'"> <i class="fa fa-bullhorn text-white"></i> ${order4}</label>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3 visitor-item ">
										<h4>  已完成 </h4>
										<label class="label label-big label-danger" onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html?sta=a'"> <i class="fa fa-trash-o text-white"></i> ${order3}</label>
									</div>
								</div>  --%>
			
							</div>
						</div>
					</div>
				</div>
				
				<!-- 折线图 -->
				<!-- <div class="row">
					<div class="col-md-12">
					<div class="caption" style="text-align: center;">店铺完成订单数</div>
						<div id="myfirstchart"></div>
					</div>
				</div> -->
				<!-- QQ悬浮框 -->
				<div class="wide-bar fixed-bar" style="margin:0;padding:0;">
		<div class="consult-box"  style="margin:0;padding:0;" >
			<div class="consult-header clearfix">
			<h3 class="consult-title"  style="margin:0;padding:0;">免费咨询</h3 >
			</div>
			<ul class="consult-list" style="background-color:#fff;">
				<li class="clearfix">
					<span>遇到问题：</span>
					<a target="_blank" href="tencent://message/?uin=769057682&Site=在线咨询&Menu=yes'"><img border="0" src="${pageContext.request.contextPath}/work/pc/images/JS_qq.png" alt="QQ" title="点击开始QQ交谈/留言"></a>
				</li>								
				<li class="clearfix">
					<span>加入群聊：</span>
					<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=1b70e349d14c737baee1506402855aae3a9cd778c2505679e660d298df152534"><img border="0" src="${pageContext.request.contextPath}/work/pc/images/JS_qq.png" alt="QQ" title="点击开始QQ交谈/留言"></a>
				</li>
				<!-- <li class="clearfix"><span class="tel-icon">tel:400-123-1230</span></li> -->
			</ul>
         
		</div>
		
				
				
				
				<!-- 页面主体内容 结束 -->
    		</div>
    	</div>
    </div>
    
    <!-- 加载js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/raphael-min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/morris.min.js"></script>
   <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/morris-0.4.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/morris-custom.js"></script>
     --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>

	<script type="text/javascript">
	var data=${orsum};
	if(data==""){
		
		var d = new Date();
		var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		data=[{'days':str,'count':0}]
	}
	Morris.Line({
	    element: 'myfirstchart',
	    data: data,
	    xkey: 'days',
	    ykeys: ['count'],
	    labels: ['每日订单数量'],
	    parseTime: false,
	    numLines:5,
	    ymin: 'auto 0',
	    ymax: 'auto',
	    hideHover: true
	});
	</script>
  </body>
</html>

