<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>系统 积分兑换</title>
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
							<li class="active">系统积分兑换</li>
						</ul>
						
						<h3 class="page-header">系统积分兑换</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								
							</div>
							<div class="pull-right" id="state_query">
							<button type="button" class="btn btn-info" data-toggle="modal"  data-target="#myPass">新     增</button>
					
								
							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							               <th>订单金额</th>
							             	<th>返现积分</th>
							             
							               <th>创建时间</th>
							            
							               <th>操作</th>
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${arr }" var="arr">
							           <tr class="map-con">
							           	  
							           	   <td class="con-city" >${arr.cash }</td>
							           	   <td class="con-name">${arr.integral}</td>
							           	 
							           	   <td class="con-add">${arr.createTime }</td>
							           	
							           	
							           
										<td>
							           	       <a class="used" href="javascript:opp1(${arr.id})">
							           	   		<i class="iconfont-gl">&#xe60a;</i><br>
							           	   		查看详情
							           	   </a>  
							           	   <a class="used" href="javascript:deleteRet(${arr.id})" >
							           	   		<i class="iconfont-gl">&#xe60a;</i><br>
							           	   		删除兑换
							           	   </a>  
							         
							         </td>
							           	   
							           </tr>
							          <!--详情st -->  <tr style="display:none;"  id="sname_${arr.id }">
							           		<td style="text-align: left;padding: 15px;width: 500px;" >
							           		
							           		  <div class="clearfix">
							           		  <div class="pull-left">
							           		  
							           		  		<p><span>现金<input type="text" value="${arr.cash }"  id="cash_${arr.id }" style="color: black; width: 200px;" maxlength="23"></span></p>
							           		  		<p><span>积分<input type="text" value="${arr.integral }"  id="interal_${arr.id }" style="color: black; width: 200px;" maxlength="23"></span></p>
							           		  		
						
									           	 	  <p>时间：${arr.createTime}</p>
									          </div>
							           		 
							           	  	</div>
							           		   <div style=" text-align: center;"> 
							           		  		 <a href="javascript:updetails(${arr.id})" class="btn btn-info" >保存</a>
							           		   </div>
							           	  </td>
							           </tr> <!--详情en --> 
							           
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
					
	<!-- 添加模板 -->
	
	<div class="modal fade" id="myPass" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">设置金额与返现积分</h4>
					
				</div>
				
					<form action="" id="userform">
					<div class="modal-body">
						<div class="panel-body">

							<div id="pawork" class="form-horizontal">
								
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">金额设定:</label>
									<div class="col-lg-5 col-md-6">
										 <input class="form-control form-cascade-control" id="cash"
											name="cash"  type="text" placeholder="请填写金额设定" style="width: auto;"> 
											
											
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">返现积分:</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id="integral"
											name="integral"  type="text" placeholder="请填写返现积分" style="width: auto;">
											
											
									</div>
								</div>
									
								
							</div>
						</div>
				
					</div>
					
					<input type="hidden" id="sys_id" name="sys_id"  type="text" >
					<div class="modal-footer" >
						<button type="button" class="btn btn-primary" onclick="upFrom()">提交</button>
						<button type="button" id="clos" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</form>
				</div>
				
			</div>
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
    <script type="text/javascript" src="/nanny/work/pc/js/login.js"></script>


	<script type="text/javascript">
 	
 	function upFrom() {
 
 	 if(userform.cash.value==""){
 	layer.msg("请输入金额设定");
 		return ;	
 	}
 	else if(userform.integral.value==""){
 		layer.msg("请输入返现积分");
 		return ;	
 	}
 	else{
	layer.load(1, {shade : [ 0.8, '#333' ]});
	var data = $("#userform").serialize();
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/admin/integralSetting.json",
		data : data,
		dataType : "text",
		success : function(msg) {
			var data = eval('(' + msg + ')');
			
				layer.msg(data.msg);
				if(data.hasOwnProperty("success")){
					setTimeout("location.reload(true)", 1000);
				}
				layer.closeAll('loading');
			
		},
		error : function() {
			setTimeout("location.reload(true)", 3000);
			layer.msg(data.msg);
		}
	});
	}
	
} 

	function deleteRet(id){
	
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/admin/integralDel.json",
		data : "id="+id,
		dataType : "text",
		success : function(msg) {
			var data = eval('(' + msg + ')');
			
				layer.msg(data.msg);
				if(data.hasOwnProperty("success")){
					setTimeout("location.reload(true)", 1000);
				}
				layer.closeAll('loading');
			
		},
		error : function() {
			setTimeout("location.reload(true)", 3000);
			layer.msg(data.msg);
		}
	});

	}
	
	
	


function opp1(id){
	var interal=$("#interal_"+id).val();
	var cash=$("#cash_"+id).val();
	$("#integral").val(interal);
	$("#sys_id").val(id);
	$("#cash").val(cash);
	$("#myPass").modal("show");
	
	 $("#clos").click(function(){
	 	$("#integral").val("");
		$("#sys_id").val("");
		$("#cash").val("");
	 });

	}
	
	</script>
</body>
</html>
