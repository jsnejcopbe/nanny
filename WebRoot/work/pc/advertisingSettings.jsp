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
<title>广告编辑</title>

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
<style type="text/css">
.up_file{
	position:absolute!important;
	left:0!important;
	top:0!important;
	width:90%!important;
	height:100%!important;
	/* font-size: 100px!important; */
	z-index:999!important;
	opacity:0!important;
	filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0)!important;
	cursor: pointer;
	
}
.up_btn{
	position: relative;
	display:block;
	width: 90%;
	margin-bottom: 10px;
	border-radius: 50%;
}

</style>	


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
							<li class="active">广告编辑</li>
						</ul>

						<h3 class="page-header">广告编辑</h3>
					</div>	
				</div>
				<div id="step-1">


					<h2 class="StepTitle">广告基础信息</h2>


					<div class="panel">
						<div class="panel-body">
							<form class="form-horizontal" role="form" id="pro1">
							<input type="hidden" name="id" value="${id}">
								<div class="form-group">
									<div class="col-lg-4 col-md-5"></div>
									<div class="col-lg-4 col-md-5">
										<a class="btn  up_btn" href="javascript:void(0)"> <img
											id="preshow" src="${imgStc}"
											style="display: block;width:300px;height: 300px; "
											 name="img">

											<div id="imgPreview" style="width:100px;"></div> <input
											type="hidden" name="fi" id="fi" value="${imgStc}"> <input
											class="up_file" type="file" id="upfile" name="file"
											accept="image/*"> </a>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">跳转 地址</label>
									<div class="col-lg-10 col-md-9">
									<input type="text" class="form-control form-cascade-control" placeholder="跳转地址" name="jumpSrc" id="jumpSrc" value="${jumpSrc}">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">全局</label>
									<div class="col-lg-10 col-md-9">
										<c:if test="${isAllUser=='0'}">
								<input name="isUse" type="radio" onclick="yan();" value="0" class="flat- input" checked>&nbsp;是										
         				     <input name="isUse" type="radio" onclick="xian();" value="1" class="flat-input">&nbsp;否
                                        </c:if>
										<c:if test="${isAllUser=='1'}">
								<input name="isUse" type="radio"onclick="yan();"value="0" class="flat-input">&nbsp;是										
         				<input name="isUse" type="radio" onclick="xian();" value="1" class="flat-input" checked>&nbsp;否										
         				              </c:if>
									</div>
								</div>
								
								<c:if test="${isAllUser=='1'}">
								<div class="form-group" id="bank1">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">使用者</label>
									<div class="col-lg-6 col-md-7">
										<select class="form-control" id="bank" name="bank">
											<c:forEach items="${baseshop}" var="b">
												<option value="${b.id}"
									 <c:if test="${b.id==shopID}" >selected="selected"</c:if>>${b.shop_name}</option>
											</c:forEach>
										</select>
									</div>
		                              	</div>
		                              	</c:if>
		                              	
		                              	<c:if test="${isAllUser=='0'}">
		                              	<div class="form-group" id="bank1" style="display:none">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">使用者</label>
									<div class="col-lg-6 col-md-7">
										<select class="form-control" id="bank" name="bank">
											<c:forEach items="${baseshop}" var="b">
												<option value="${b.id}"
									 <c:if test="${b.id==shopID}" >selected="selected"</c:if>>${b.shop_name}</option>
											</c:forEach>
										</select>
									</div>
		                              	</div>
		                              	
		                              	</c:if>
								<div class="form-group">
									<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">是否 启用</label>
									<div class="col-lg-10 col-md-9">
								<c:if test="${isUser=='0'}">
								<input name="isCom" type="radio" value="0"class="flat-input" checked>&nbsp;是
         			        	<input name="isCom" type="radio" value="1"class="flat-input">&nbsp;否
								</c:if>
								<c:if test="${isUser=='1'}">
								<input name="isCom" type="radio" value="0" class="flat- input">&nbsp;是
								<input name="isCom" type="radio" value="1" class="flat-input" checked>&nbsp;否									
         				</c:if>
									</div>
								</div>
								<div >
					<button type="button" class="btn btn-primary" onclick="preservation()" style="margin-left: 300px;">提交</button>
					</button>
				</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 页面主体内容 结束 -->
	</div>



	<!-- 加载js -->
	<script >var BASEPATH="${pageContext.request.contextPath}"</script>
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
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
	<script src="${pageContext.request.contextPath}/work/pc/js/adminadverits.js"></script>

</body>
</html>
