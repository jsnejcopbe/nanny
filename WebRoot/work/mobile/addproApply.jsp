<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>添加商品申请</title>

<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<meta name="format-detection"content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit"> <!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" > <!-- 优先用Chrome渲染 -->

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
	  <script src="js/html5shiv.js"></script>
	  <![endif]-->
<style>
img {
	width: 100%;
	height: 100px;
}

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
<body style="background:none;min-height:100%;">
	<div class="site-holder"  style="min-height: 480px;">
		<!-- 手机端 页面主体内容 开始 -->
        
		<div class="box-holder" style="background-color:#ffff;min-height:100%!important;">
			<!-- 左侧菜单 -->
			<div class=""  style="min-height: 100%!important;">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-dat">
							<div class="panel-body nopadding" style="background-color:#f3f3f3;min-height: 100%;">		
									<!-- 返回首页 -->
									<div class="m-tlebox flex" style="text-align:center;background-color:#f8f8f8 ;height: 44px; width: 100%; 
									   line-height: 44px;position: fixed;left: 0;top: 0;z-index:9998;">
									<a class="m-goback"  style="float:left; display:inline;"  href="${pageContext.request.contextPath}/shop/productList.html"><img style="width:38px;height:35px;" src="${pageContext.request.contextPath}/work/mobile/images/back.png">
									 </a>
										<div class="storeManage-menu" style="display:inline;margin-right:40px;"  >添加商品申请</div>
									</div>
	                             <!-- 数据添加 -->
								<div class="swMain" style=" margin-top: 44px;">
									<div style="height: auto;">
										<form class="form-horizontal cacade-forms" name="signup_form"
											id="pro2">
											<div class="form-group" style="background-color:#fff;border-top:0;">
												<label class="col-lg-2 col-md-3 control-label">商品图片上传</label>

												<div class="col-lg-10 col-md-9" id="upfile">
													<div class="drop"  style="height:150px;width: 45%!important;"  id="imgPreview" >
														<img id="preshow" src="/nanny/images/defalut.jpg"
															style="width:100%; height: 100% " name="img">
														<!-- <div id="imgPreview" style="width:73%;"></div> -->
													</div>
													<input type="hidden" name="fi" id="fi"
														value="/nanny/images/defalut.jpg" />

													<div id="drop" class="drop"  style="height:150px;padding:0!important;width: 45%!important;" >
														<a class="up_btn"  style="background-color: #fff;color: #555;width: 100%;height: 90%" >
														<span style="color:#999;margin-top: 35px;display: inline-block;">点击上传图片</span>
														 <input class="up_file"
															type="file" id="file1" name="file" accept="image/*"
															
															style="width:100%; height: 100%;"> </a>
															
													</div>
												</div>
											</div>
											<div class="form-group" style="background-color:#fff;border-top:0;margin-top:6px;">
												<label class="col-lg-2 col-md-3 control-label">商品类别</label>
												<div class="col-lg-10 col-md-9" id="selid">
													<select class="form-control proclass" name="shoptype1">


													</select> <select class="form-control  proclass" name="shoptype2">


													</select>
												</div>
											</div>
											<div class="form-group" style="background-color:#fff;border-top:0;margin-top:6px;">
												<label class="col-lg-2 col-md-3 control-label">商品品牌</label>
												<div class="col-lg-10 col-md-9">
												<select id="brandId" name="brandId" class="form-control " style="width: 70%;" >
														<option value="-1">请选择</option>
														<c:forEach items="${brand}" var="br">
															<option value="${br.id}">${br.name}</option>
														</c:forEach>
														
													</select>
													<%--<input type="hidden" name="brandId" value=""> <input
														class="form-control probrand" style="margin:0px;"
														size="20" placeholder="请输入品牌名称" type="text"
														name="brandName" value="" readonly="readonly"> <a
														class="btn btn-info brand-btn" id="brand">品牌一览</a>
												--%></div>
											</div>

										</form>
									</div>



									
									<a type="button"
										class=" btn    btn-danger btn-block  " style="margin-top:6px;'"
										href="javascript:addProapp(0)">提交申请</a>

									<!-- 		</div> -->
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
		var prefix = "${pageContext.request.contextPath}/";
		
	</script>
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	
	
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
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
		$(function() {
			$.brand({
				open : "#brand",
				url : path + 'admin/init_brand.html'
			}, function(id, name) {
				$("input[name='brandName']").val(name);
				$("input[name='brandId']").val(id);

			})
		})
	</script>
</body>
</html>
