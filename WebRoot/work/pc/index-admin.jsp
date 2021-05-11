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
  	<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
  	<link href="${pageContext.request.contextPath}/work/pc/css/brand.css" rel="stylesheet">
  	<link href="${pageContext.request.contextPath}/work/pc/css/adminIndex.css" rel="stylesheet">
  	<!-- 兼容性 -->
  	<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
  	<!--[if lt IE 9]>
  	<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  	<![endif]-->
	
  </head>
  
  <body>
    <div class="site-holder">
    <!-- 页面主体内容 开始 -->
    
    	<jsp:include page="head.jsp"/>
    	
    	<div class="box-holder">
    	
    		<jsp:include page="menu.jsp"/>
    	
    		<div class="content">
    			<!-- 导航栏 -->
    			<div class="row">
    				<div class="col-mod-12">
    					<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
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
								<h4>平台商品数   <span class="pull-right">${product}</span></h4>
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
								<h4>平台商户数   <span class="pull-right">${shop}</span></h4>
								<%--<p>本月新增 <span class="badge pull-right bg-white text-success"> 98家</span></p>
							--%></div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="info-box  bg-warning  text-white">
							<div class="info-icon bg-warning-dark">
								<i class="fa fa-dollar fa-4x"></i>
							</div>
							<div class="info-details">
								<h4> 平台现金  <span class="pull-right">${ye}</span></h4>
								<%--<p> 本月收入 <span class="badge pull-right bg-white text-warning"> 78  </span> </p>
							--%></div>
						</div>
					</div>
				</div>
				
				<!-- 折线图 -->
				<div class="row">
					<div class="col-md-3">
						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-check-square-o"></i>
									业务员排行
									<span class="pull-right">
										<a href="#" class="panel-minimize"><i class="fa fa-chevron-up"></i></a>
										<a href="#" class="panel-close"><strong><i class="fa fa-times"></i></strong></a>
									</span>
								</h3>
							</div>
							<div class="panel-body nopadding">
								<ul class="list-group list-todo">
									<c:forEach items="${salman}" var="sal">
									<li class="list-group-item">
										<span>
										
										
										 <c:set var="testStr" value="${sal.name}"/>
   														 <c:choose>  
        														<c:when test="${fn:length(testStr) > 5}">  
           															 <c:out value="${fn:substring(testStr, 0,4)}....." />  
       		 													</c:when>  
      													 		<c:otherwise>  
          															<c:out value="${testStr}"/>  
        														</c:otherwise>  
    													 </c:choose>  
										</span>
										<span>${sal.tel}</span>
										<label class="label label-success pull-right">推广${sal.sum}家</label>
									</li>
									</c:forEach>
									
								</ul>
							</div>
						</div>
					</div>
				
					<div class="col-md-9">
						<div class="caption" style="text-align: center;">平台完成订单数</div>
						<div id="myfirstchart"></div>
					</div>
				</div>
				
				
				<div class="row">
					<h4 class="page-header">系统设置</h4>
					<div class="clearfix sys-pabl">
			          <div class="col-md-3 pabl-item">
			          	<div class="item-con">
			          		<div class="left-block">
			          			<i class="iconfont-gl">&#xe60c;</i>
			          		</div>
				          	<div class="right-block">
				          		<p>积分参数设置</p>
				          		<p>
					          		<a href="javascript:updpass1()" class="btn btn-info btn-sm">积分比例设置</a>		          		
					          		<a href="javascript:updpass()" class="btn btn-info btn-sm">积分返现设置</a>		          		
				          		</p>
				          	</div>
				        </div>
			          </div>
			        </div>
				</div>
				
				
		        			
	<div class="modal fade" id="myPass1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">系统积分设置</h4>
				</div>
					<form action="" id="sform">
					<div class="modal-body">
						<div class="panel-body">
							<div id="pawork" class="form-horizontal">
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">1积分=￥</label>
									<div class="col-lg-5 col-md-6">
										 <input class="form-control form-cascade-control" id="cashset"
											name="cashset"  type="text" value="${arr1.cash} " style="width: auto;"> 
									</div>
								</div>		
							</div>
						</div>
					</div>	
					<div class="modal-footer" >
						<button type="button" class="btn btn-primary" onclick="upsys()">提交</button>
						<button type="button" id="clos" class="btn btn-default" data-dismiss="modal">关闭
					</div>
				</form>
				</div>
			</div>
		</div>
		
		
    
				 		          
				<div class="modal fade" id="myPass" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">设置金额与返现积分</h4>
					<button type="button" class="btn btn-primary" onclick="addNewLine()">新   增</button>
				</div>
				
					<form action="" id="setform">
					<input type="hidden" id="topdiv">
					<div class="modal-body<%-- _${arr.id } --%>" id="topdiv">
						 <div class="panel-body" id="middiv">
							<c:forEach items="${arr }" var="arr">
									 <div  class="form-horizontal" id="innerdiv">
										金额设定:<input type="text"  value="${arr.cash }" id="cash11" name="cash_${arr.id}" class="myInputcss"  style="width: 100px;">
										返现积分:<input type="text" value="${arr.integral }" id="integral11" name="integral_${arr.id}" class="myInputcss"  style="width: 100px">
										<button type="button" id="delt" class="btn btn-default" style="background-color: #C9302C;color: #FFFFFF" onclick="delSet( ${arr.id})">删   除
									</div> 
								</c:forEach>
						</div>
					</div>
					<div class="modal-footer" >
						<button type="button" class="btn btn-primary" onclick="upForm()">提交</button>
						<button type="button" id="clos" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
						</form>
					
				
				</div>
				
			</div>
		</div>
			
		</div>			          
		
				
    		</div>
    		
    	</div>
    		
						
							
						
    	<!-- 页面主体内容 结束 -->
    
    <!-- 加载js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/nanny/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    
    <script type="text/javascript" src="/nanny/work/pc/js/jquery.accordion.js"></script>
    
    <script src="${pageContext.request.contextPath}/work/pc/js/raphael-min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/morris.min.js"></script>
   <%--  <script src="${pageContext.request.contextPath}/work/pc/js/morris-0.4.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/morris-custom.js"></script>
     --%>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/indexadmin1.js"></script>
	
	<script type="text/javascript">
	var data=${orsum};
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
<script type="text/javascript">


 	function addNewLine(){
	var html= ' <div  class="form-horizontal" id="innerdiv">\
					金额设定:<input type="text"  class="myInputcss" id="cash11" name="cash11" style="width: 100px;">\
				返现积分:<input type="text"   class="myInputcss" id="integral11" name="integral11" style="width: 100px" >\
				<input type="hidden" name="number" id="number" >\
				<button type="button" id="delt" class="btn btn-default" style="background-color: #C9302C;color: #FFFFFF" >删   除\
					</div>';

 		$(".modal-body").find("#middiv").append(html);

}
 	
	function upsys(){
	
	var data=$("#cashset").val();
	if(!(/^\d+(\.\d+)?$/).test(data)){
		layer.msg("请输入数字");
		return;
	}
	else{
		$.ajax({
		 type: "POST",
		 url: "${pageContext.request.contextPath}/admin/upcash.html",
		 data: "cashset="+data,
		 dataType: "json",
		 success: function(data){
				 layer.msg(data.msg);
				 location.reload("true",1000);
				
				 },
		 error: function(){layer.msg("操作失败");}
		});
		}
	}
	
function upForm(){
		
		var x=0;
	($("#middiv").find(".form-horizontal")).each(function(){
		var dd=$(this).find("#cash11").val();
	if(!(/^\d+(\.\d+)?$/).test(dd)){
		x=1;
		
	}
	var dd1=$(this).find("#integral11").val();
	if(!(/^\d+(\.\d+)?$/).test(dd1)){
		x=1;
		
	}
	});
	if(x==0){
	
	var arr=new Array();
	($("#middiv").find(".form-horizontal")).each(function(){
	
		var obj=new Object();
		obj.cash11=$(this).find("#cash11").val();
		obj.integral11=$(this).find("#integral11").val();
		arr.push(obj);
	});
		$.ajax({
		 type: "POST",
		 url: "${pageContext.request.contextPath}/admin/setform.html",
		 data: "arr="+JSON.stringify(arr),
		 dataType: "json",
		 success: function(data){
				 layer.msg(data.msg);
				 location.reload("true",1000);
				
				 },
		
		});
		}else{
		
		 layer.msg("请输入数字");
		}
}	

function delSet(id){
	
	
	$(".modal-body_"+id).remove();
 	
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/admin/delSet.json",
		data : "id="+id,
		dataType : "text",
	
		error : function() {
			setTimeout("location.reload(true)", 3000);
			layer.msg(data.msg);
		}
	});
	
}

function updpass() {
	$("#myPass").modal("show");
}

function updpass1() {
	$("#myPass1").modal("show");
}


$(function(){
	
	$(document).on("click",".btn",function(){
		$(this).parents(".form-horizontal").remove();
	
	});
	});


</script>
	
  </body>
</html>

