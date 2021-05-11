<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>配送价格设置</title>
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
</head>

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
							<li class="active">配送价格设置</li>
						</ul>
						
						<h3 class="page-header"> 配送价格设置</h3>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-10 col-md-offset-1">
						<div class="panel panel-cascade">
							<div class="panel-heading"></div>
							<div class="panel-body panel-border">
								<ul class="list-group">
									<c:forEach items="${dispacthList }" var="dl">
									<li class="list-group-item clearfix da-li">
										<div class="col-md-6">
											${dl.addName }
										</div>
										<div class="col-md-6">
											<input class="form-control dptPrice" type="text" placeholder="请输入起送价格" value="${dl.fee }" onkeyup="this.value=this.value.replace(/\D/g,'')" >
										</div>
										<input class="dptID" type="hidden" value="${dl.id }">
									</li>
									</c:forEach>
									<li class="list-group-item" style="text-align: center;">
										<a class="btn btn-info" href="javascript:updateDptPrice()">保存设置</a>
										<a class="btn btn-danger" href="javascript:history.go(-1)">返回上页</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	
	function updateDptPrice(){
		var data=createData();
		layer.load(1, {shade: [0.8,'#333']});
		var param={
			"sURL":path+"shop/updatedispatch.json",
			"Data":"jsonData="+JSON.stringify(data),
			"fnSuccess":function(data){
				layer.closeAll('loading');
				layer.msg(data.msg);
				location.href=path+"shop/shopInfo.html"
			},
			"fnError":function(){layer.closeAll('loading');layer.msg("设置失败");}
		};
		new g_fnAjaxUpload(param);
	}
	
	function createData(){
		var dataArray=new Array();
		$(".da-li").each(function(){
			var obj=new Object();
			var price=$(this).find(".dptPrice").val();
			obj.dptID=$(this).find(".dptID").val();
			obj.dptPrice=price==""?0:price;
			dataArray.push(obj);
		});
		return dataArray;
	}
	</script>
</body>
</html>
