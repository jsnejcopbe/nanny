<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>商品类别</title>
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">

      <!-- 样式加载 -->
	  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/icheck/skins/flat/all.css" rel="stylesheet" type="text/css">
	  <link href="${pageContext.request.contextPath}/work/pc/css/smart_wizard.css" rel="stylesheet" type="text/css">
	  <link href="${pageContext.request.contextPath}/js/baiduedit/themes/default/css/umeditor.min.css" rel="stylesheet"  type="text/css"></link>
	  <link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
	  <link href="${pageContext.request.contextPath}/work/pc/css/proadd.css" rel="stylesheet"  type="text/css"></link>
	  <!-- 兼容性 -->
	  <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
	  <!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->
<style>

  
  .modal-dialog{
  	margin-top:-30px;
  	width: 1000px;
  }
  
  .modal-content{
  	overflow-y: auto;
	height: 500px;
  }
  
  #Grid >.brand_li{
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
		                   <li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
		                   <li><a href="#">商品列表</a></li>
		                   <li class="active">新增商品</li>
		                </ul>
		                <h3 class="page-header"><i class="fa fa-indent"></i> 新增商品</h3>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-dat">
							<div class="panel-body nopadding">
								<!-- 数据添加 -->
								<div class="swMain">
								<div>
									<%--<ul class="anchor">
										<li>
											<a class="done" href="#step-1" data-toggle="tab">
												<label class="stepNumber">1</label>
												<span class="stepDesc">基础数据</span>
											</a>
										</li>
										<li>
											<a class="done" href="#step-2" data-toggle="tab">
												<label class="stepNumber">2</label>
												<span class="stepDesc">图文详情</span>
											</a>
										</li>
										<li>
											<a class="done" href="#step-3" data-toggle="tab">
												<label class="stepNumber">3</label>
												<span class="stepDesc">商品规格</span>
											</a>
										</li>
									</ul>--%>
									<div style="height: auto;">
										
										<div id="step-1">	
											<h2 class="StepTitle">商品基础信息</h2>
											<div class="panel">
												<div class="panel-body">
													<form class="form-horizontal" role="form"  id="pro1">
														<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">商品名称</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input type="text" class="form-control form-cascade-control" placeholder="请输入商品名称" name="shName">
										         			</div>
										         		</div>
										         		<c:if test="${shopid=='0'}">
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">成本价格</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input type="text" class="form-control form-cascade-control"  placeholder="请输入成本价格"  name="costPrice"   onkeyup="clearNoNum(this)" value="1">
										         			</div>
										         		</div>
										         		</c:if>
										         		<c:if test="${shopid=='0'}">
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">建议售价</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input type="text" class="form-control form-cascade-control" placeholder="请输入建议售价" name="shopPrice" onkeyup="clearNoNum(this)" value="1">
										         			</div>
										         		</div>
										         		</c:if>
										         		<c:if test="${shopid!='0'}">
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">商品售价</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input type="text" class="form-control form-cascade-control" placeholder="请输入商品售价" name="shopPrice" onkeyup="clearNoNum(this)">
										         			</div>
										         		</div>
										         		</c:if>
										         		<c:if test="${shopid!='0'}">
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">折扣价格</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input type="text" class="form-control form-cascade-control" placeholder="请输入折扣价格" name="shopDisprice" onkeyup="clearNoNum(this)"> 
										         			</div>
										         		</div>
										         		</c:if>
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">商品库存</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input type="text" class="form-control form-cascade-control" placeholder="请输入商品库存" name="Inventory" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="99999">
										         			</div>
										         		</div>
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">商品状态</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input name="isUse" type="radio" value="0"  class="flat-input" checked >&nbsp;上架
										         				<input name="isUse" type="radio" value="1" class="flat-input">&nbsp;下架
										         			</div>
										         		</div>
										         		<div class="form-group">
										         			<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">是否计算佣金</label>
										         			<div class="col-lg-10 col-md-9">
										         				<input name="isCom" type="radio" value="0" class="flat-input">&nbsp;是
										         				<input name="isCom" type="radio" value="1" class="flat-input" checked>&nbsp;否
										         			</div>
										         		</div>
													</form>
												</div>
											</div>
										</div>
										
										<div>
											<h2 class="StepTitle">图文详情</h2>
											<form class="form-horizontal cacade-forms" name="signup_form" id="pro2">
												<div class="form-group">
										          <label class="col-lg-2 col-md-3 control-label">封面图片上传</label>
										          
										          <div class="col-lg-10 col-md-9" id="upfile">
										           <div class="drop"  id="imgPreview">
										          	<img id="preshow" src="/nanny/images/defalut.jpg" style="width:100%; height: 100% " name="img"> 
						             				<!-- <div id="imgPreview" style="width:73%;"></div> -->
										           </div>
													 <input type="hidden" name="fi" id="fi" value="/nanny/images/defalut.jpg"/>
										          
										          <div id="drop" class="drop">
										          	
										              <a class="up_btn">
										              	 点击上传图片
										             	 <input class="up_file" type="file" id="file1" name="file" accept="image/*" onchange='PreviewImage(this)' style="width: 135px; height: 38px;">
										              </a>
										           </div>
										           </div>
										        </div>
										        <div class="form-group" style="display: none;">
										          <label class="col-lg-2 col-md-3 control-label">商品描述</label>
										          <div class="col-lg-10 col-md-9">
										          		<script type="text/plain" id="myEditor" name="prodes"></script>
										          </div>
										        </div>
											</form>
										</div>
										
										<div id="step-3">
											<h2 class="StepTitle">商品规格</h2>
											<form class="form-horizontal cacade-forms" name="signup_form" id="pro3">
												<div class="form-group">
										          <label class="col-lg-2 col-md-3 control-label">商品类别</label>
										          <div class="col-lg-10 col-md-9" id="selid">
										          		<select class="form-control proclass" name="shoptype1">
										    				
														    
													    </select>
													    <select class="form-control  proclass" name="shoptype2">
										    		
														    
													    </select>
										          </div>
										        </div>
										        <div class="form-group">
										        	<label class="col-lg-2 col-md-3 control-label">商品品牌</label>
										        	<div class="col-lg-10 col-md-9">
										        		<input type="hidden" name="brandId" value="${proa.brandId}">
										        		<input class="form-control probrand" style="margin:0px;" size="20" placeholder="请输入品牌名称" type="text" name="brandName" value="${pbname}" readonly="readonly">
										                <a class="btn btn-info brand-btn" id="brand">品牌一览</a>
										        	</div>
										        </div>
										        <div class="form-group">
										        	<label class="col-lg-2 col-md-3 control-label">商品属性</label>
										        	<div class="col-lg-10 col-md-9">
										        		<a class="btn btn-success" href="javascript:showarrt()">新增商品规格</a>
										        	</div>
										        </div>
										       <!--  <div class="form-group">
										        	<label class="col-lg-2 col-md-3 control-label">商品标签</label>
										        	<div class="col-lg-10 col-md-9 taglist">
										        		<span class="time badge bg-info">标签1&nbsp;<a class="js-removetag" href="javascript:void(0)"><i class="fa fa-times"></i></a></span>
										        		<span class="time badge bg-info">标签2&nbsp;<a class="js-removetag" href="javascript:void(0)"><i class="fa fa-times"></i></a></span>
										        		<span class="time badge bg-info">标签3&nbsp;<a class="js-removetag" href="javascript:void(0)"><i class="fa fa-times"></i></a></span>
										        		<a href="javascript:addNewTag()" class="time badge bg-info js-addnewtag">新增标签<i class="fa fa-plus"></i></a>
										        	</div>
										        </div> -->
											</form>
										</div>
									</div>
								</div>
								<div class="actionBar">
							    	<a class="buttonFinish" href="javascript:clear()">清空数据</a>
							    	<a class="buttonFinish" href="javascript:addPro()">新增商品</a>
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
	window.UMEDITOR_HOME_URL="${pageContext.request.contextPath}/js/baiduedit/";
	/* UM.getEditor('myEditor').getContent(); */
	/* UM.getEditor('myEditor').setContent(""); //清空*/
	</script>
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/js/baiduedit/umeditor.config.js"></script>
    <script src="${pageContext.request.contextPath}/js/baiduedit/umeditor.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/baiduedit/lang/zh-cn/zh-cn.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/addproduct.js?v=0.01"></script>
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
			 
			 	var img="${proa.shopImg}";
			 	var pid="${proa.brandId}";
			 	var tid="${pt.id}";
			 	var tpid="${pt.parId}";
			 	var pname="${pbname}";
			 	if(tpid==0){
			 		 $("#selid").find("[name='shoptype1']").val(tid);
			 	}else{
			 		$("#selid").find("[name='shoptype1']").val(tpid);
					 $("#selid").find("[name='shoptype1']").change();
					 $("#selid").find("[name='shoptype2']").val(tid);
					
			 	}
			
			 $("input[name='fi']").val(img);
			document.getElementById("preshow").src =img;
		})
	
		
	</script>
</body>
</html>
