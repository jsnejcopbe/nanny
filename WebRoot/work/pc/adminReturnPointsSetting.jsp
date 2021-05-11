<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>系统返现积分设定</title>
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
		 <jsp:include page="head.jsp" />
		 
		 <div class="box-holder">
		 	<!-- 左侧菜单 -->
		 	<jsp:include page="menu.jsp" />
		 	
		 	<div class="content">
		 		<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">系统返现积分设定</li>
						</ul>
						
						<h3 class="page-header">系统返现积分设定</h3>
					</div>
				</div>
				
					
						
						<div class="pull-center" id="shop_query" style="text-align: center;">
						
								 <form action="${pageContext.request.contextPath}/admin/upSysIntegralSetting.html" method="post" id="sform">
		                  		
						           <p><span style="font-size: 1.17em "><strong>1积分=￥</strong></span><input type="text" value="${arr.cash }"  id="cashset" name="cashset" style="color: black; width: 150px;height: 32px" maxlength="23" >
						            
						            <button class="btn btn-info" id="sq_submit" type="submit"><a href="javascript:upsys()">确定</a></button>
						           </p>
						          
					            </form> 
					           
							</div>
						
						
							
						</div>
					</div>
				</div>
	
		<!--  	</div>
		 </div>
		 页面主体内容 结束
	</div> -->
					


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
    <script type="text/javascript" src="/nanny/work/pc/js/login.js"></script>


	<script type="text/javascript">
 	
	function upsys(){
	var data = $("#sform").serialize();
	$.ajax({
		 type: "POST",
		 url: path+"/admin/upSysIntegralSetting.html",
		 data: data,
		 dataType: "json",
		 success: function(data){
				 layer.msg("操作成功");
				location.reload(true);
				 },
		 error: function(){layer.msg("操作失败");}
		});
	
	}
	

	</script>
</body>
</html>
