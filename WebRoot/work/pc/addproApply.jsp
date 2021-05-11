<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>商品类别</title>
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
<link
	href="${pageContext.request.contextPath}/work/pc/css/icheck/skins/flat/all.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/work/pc/css/smart_wizard.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/js/baiduedit/themes/default/css/umeditor.min.css"
	rel="stylesheet" type="text/css"></link>
<link href="${pageContext.request.contextPath}/work/pc/css/style.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/work/pc/css/proadd.css"
	rel="stylesheet" type="text/css"></link>

<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->
<style>


.modal-dialog {
	margin-top: -30px;
	width: 1000px;
}

.modal-content {
	overflow-y: auto;
	height: 500px;
}

#Grid>.brand_li {
	width: 20%;
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
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a>
							</li>
							<li><a href="#">店铺商品管理</a>
							</li>
							<li class="active">新增商品列表</li>
						</ul>
						<h3 class="page-header">
							<i class="fa fa-indent"></i> 新增商品申请
						</h3>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-dat">
							<div class="panel-body nopadding">
								<!-- 数据添加 -->
								<div class="swMain">
									<div style="height: auto;">
										<form class="form-horizontal cacade-forms" name="signup_form"
											id="pro2">
											<div class="form-group">
												<label class="col-lg-2 col-md-3 control-label">商品图片上传</label>

												<div class="col-lg-10 col-md-9" id="upfile">
													<div class="drop" id="imgPreview">
														<img id="preshow" src="/nanny/images/defalut.jpg"
															style="width:100%; height: 100% " name="img">
														<!-- <div id="imgPreview" style="width:73%;"></div> -->
													</div>
													<input type="hidden" name="fi" id="fi"
														value="/nanny/images/defalut.jpg" />

													<div id="drop" class="drop">
														<a class="up_btn"> 点击上传图片 <input class="up_file"
															type="file" id="file1" name="file" accept="image/*"
															onchange='PreviewImage(this)'
															style="width: 135px; height: 38px;"> </a>
														<span style="font-size: 14px;color: red;margin-top: -20px;">提醒：图片内须有商品信息</span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-3 control-label">商品类别</label>
												<div class="col-lg-10 col-md-9" id="selid">
													<select class="form-control proclass" name="shoptype1">


													</select> <select class="form-control  proclass" name="shoptype2">


													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-3 control-label">商品品牌</label>
												<div class="col-lg-10 col-md-9">
													<input type="hidden" name="brandId" value=""> 
													<input class="form-control probrand" style="margin:0px;"
														size="20" placeholder="请输入品牌名称" type="text"
														name="brandName" value="" readonly="readonly">
													<a class="btn btn-info brand-btn" id="brand">品牌一览</a>
												</div>
											</div>

										</form>
									</div>



									<div class="actionBar">
										<%--<a class="buttonFinish" href="javascript:clear()">清空数据</a> --%>
										<a	class="buttonFinish" href="javascript:addProapp(${addtype})">提交申请</a>
									</div>
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
	<script type="text/javascript">
	var prefix="${pageContext.request.contextPath}/";
	
	</script>
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/proApply.js?v=0.01"></script>
	<script src="${pageContext.request.contextPath}/js/brand.js"></script>
	<script type="text/javascript">
		$(function(){
			 $.brand({
                 open:"#brand",
				 url:path+'admin/init_brand.html'
				 },function(id,name){
					$("input[name='brandName']").val(name);
					$("input[name='brandId']").val(id);			 	
				 	
			 	})
		})
	
	</script>
</body>
</html>
