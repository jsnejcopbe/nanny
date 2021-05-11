<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>打印设备设置</title>

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
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">设置</li>
						</ul>

						<h3 class="page-header">小票打印设备设置</h3>
					</div>
				</div>


				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<div class="panel panel-cascade">
							<div class="panel-heading">
								
									<div class="panel-title clearfix">
	                 						<span style="color: red; font-size: 14px">USB设备无需填写编号、密钥</span> 
	                				 		<div class="pull-right">
												  <a href="javascript:updprinter();" style="padding: 3px 10px ! important;color: white;"
													class="btn btn-info ">保存
												</a>  
	                						</div>
              							</div>
									
								
							</div>
							<div class="panel-body">
							
								<form id="sb" class="form-horizontal" role="form" >
									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">设备类型</label>
										<div class="col-lg-6 col-md-7" style="padding-top: 7px;text-align: center;">
											<input class=" " id="wifi" name="detype"  type="radio"  value="0"  checked="checked" onclick="inst(0)"><span style="margin-left: 5px;margin-right: 10px;">无线接口</span>
											
											<input class=" "  id="usb" name="detype"  type="radio"  value="2"  onclick="inst(2)"><span style="margin-left: 10px;">USB接口</span>
											
											<input class=" "  id="none" name="detype"  type="radio"  value="1"  onclick="inst(1)"><span style="margin-left: 10px;">无接口</span>
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">设备编号</label>
										<div class="col-lg-6 col-md-7">
											<input class="form-control form-cascade-control"
												id="deno" name="deno"  placeholder="请填写设备编号"
												type="text"  value='<c:if test="${print[0].deNo!='null'}">${print[0].deNo}</c:if>'  maxlength="" >
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">设备密钥</label>
										<div class="col-lg-6 col-md-7">
											<input class="form-control form-cascade-control"
												id="dekey" name="dekey" value='<c:if test="${print[0].deKey!='null'}">${print[0].deKey}</c:if>'  placeholder="请填写密钥"
												type="text" >
										</div>
									</div>
									
			        	
								</form>
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
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	
	var pt= "${print[0].printerType}";
	 var pxx=$("#bankid").val();
	    $("#bank").val(pxx);
	 var wait=60;
	    
	    $(function() {
	    
			inst(pt);
			if(pt=="1"){
				$("#none").attr("checked","checked");
			}else if(pt=="2"){
				$("#usb").attr("checked","checked");
			}
		})
	    
	  function updprinter()
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
	  	  
	  	 	  
	  	  var deno=$("#deno").val();
	  	  var dekey=$("#dekey").val();
	  	  var detype=$("input[name='detype']:checked").val();
	  	  if((dekey!=""&&deno!="")||detype=="1"||detype=="2"){
	  	  var data= $("#sb").serialize();
		  $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/shop/updatePrint.html",
                data:data,
                dataType: "text",
                success: function (msg) {
                	var data=eval('(' + msg + ')');
                	
                       layer.msg("保存成功");
                       setTimeout(location.reload(true), 3000);
                       
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
           }else{
                layer.closeAll("loading");
           		layer.msg('请填写设备信息!');
           }
	  }
		 
	  function inst(io) {
		  if(io==1||io==2){
				$("#deno").attr("readonly","readonly");
				$("#dekey").attr("readonly","readonly");
			 }else{
				$("#deno").removeAttr("readonly");
				$("#dekey").removeAttr("readonly");
			}

	}
	  
	</script>
</body>
</html>
