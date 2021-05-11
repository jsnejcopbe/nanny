<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>提款帐户</title>

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

						<h3 class="page-header">提款帐户</h3>
					</div>
				</div>


				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<div class="panel panel-cascade">
							<div class="panel-heading">
								
									<div class="panel-title">
                 						<span style="color: red; font-size: 14px">提示：请认真、准确的填写您的帐户信息</span>
                				 <div class="pull-right">
                 					  <a href="javascript:addbankcard();" style="padding: 3px 10px ! important;color: white;"
										class="btn btn-info ">绑定银行卡
									</a> 
									  <!-- <a href="javascript:updbankcard();" style="padding: 3px 10px ! important;color: white;"
										class="btn btn-info ">保存设置
									</a>  --> 
                						</div>
              							</div>
									
								
							</div>
							<div class="panel-body">
							
								<form id="sb" class="form-horizontal" role="form" >
									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">银行</label>
										<div class="col-lg-6 col-md-7">
											<select class="form-control" id="bank" name="bank">
												<c:forEach items="${list}" var="bank">
													<option value="${bank.id}">${bank.bankName}</option>
												</c:forEach>
											</select>
										</div>
										<input type="hidden" value="${info[0].bankID}" id="bankid" name="bankid">
									</div>

									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">银行卡号</label>
										<div class="col-lg-6 col-md-7">
											<input class="form-control form-cascade-control"
												id="bankcard" name="bankcard"  placeholder="请填写银行卡号"
												type="text"  value="${info[0].account}" onkeyup="this.value=this.value.replace(/\D/g,'').replace(/(\d{4})/g,'$1 ')" maxlength="23">
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">姓名</label>
										<div class="col-lg-6 col-md-7">
											<input class="form-control form-cascade-control"
												id="uname" name="uname" value="${info[0].accName}" placeholder="请填写姓名"
												type="text">
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail1"
											class="col-lg-2 col-md-3 control-label">验证码</label>
										<div class="col-lg-6 col-md-7">
											<input id="mocode" name="mocode" type="text" class="form-control" placeholder="请输入验证码" style="width: 220px; display: inline-block;">
											<button type="button" class="btn btn-info " id="yzm" onclick="codeChek()"> 获取验证码</button>
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
	 var pxx=$("#bankid").val();
	    $("#bank").val(pxx);
	 var wait=60;
	    
	 /*  function updbankcard()
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
	  	  
	  	  var bank=$("#bank").val();
	  	  var bankcard=$("#bankcard").val();
	  	  var uname=$("#uname").val();
		var mocode=$("#mocode").val();
	  	  var data= $("#sb").serialize();
		  $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/shop/updbankset.html",
                data:data,
                dataType: "text",
                success: function (msg) {
                	var data=eval('(' + msg + ')');
                	 if(data.msg=="1"){
                		
                		layer.closeAll('loading');
                		layer.msg("验证码错误");	
                	}else{
                       layer.msg("保存成功");
                       setTimeout(location.reload(true), 3000);
                       }
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
	  }
	   */
	  
	  function addbankcard()
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
	  	  
	  	  var bank=$("#bank").val();
	  	  var bankcard=$("#bankcard").val();
	  	  var uname=$("#uname").val();
		  var mocode=$("#mocode").val();
	  	 
	  	  var data= $("#sb").serialize();
		  $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/shop/addbankBind.html",
                data:data,
                dataType: "text",
                success: function (msg) {
                	var data=eval('(' + msg + ')');
                	/* if(data.msg=="0"){
                		layer.closeAll('loading');
                		layer.msg("已有绑定银行卡，请勿重新绑定");
                	}else  */
                	if(data.msg=="1"){
                		
                		layer.closeAll('loading');
                		alert("验证码错误");
                		
                	}else{
                       alert("保存成功");
                       setTimeout(location.reload(true), 3000);
                	}
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
	  }
	  
	  
	      /**
	 * 发验证码
	 */
	function codeChek(){
		if (wait == 0) {  
			$("#yzm").removeClass("disabled");            
			$("#yzm").text("获取验证码");  
	        wait = 60;  
	    } 
		else if(wait == 60){
			$("#yzm").addClass("disabled", true);  
	    	$("#yzm").text("重新发送(" + wait + ")"); 
	    	wait--;
	    	var param={
	    			"sURL":"/nanny/obtainCode.html",
	    			"fnSuccess":function(rtnData){
	    				layer.msg("请等待,验证码会发送到您的手机");
	    			},
	    			"fnError":function(){
	    				layer.msg('发送失败,请联系管理员');
	    			}
	    		};
	    	new g_fnAjaxUpload(param);
	    	setTimeout(function() {  
	        	codeChek();  
	        },  
	        1000); 
		} 
		else{ 
			$("#yzm").text("重新发送(" + wait + ")"); 
	        wait--;  
	        setTimeout(function() {  
	        	codeChek();  
	        },  
	        1000);  
	    }
	}
	</script>
</body>
</html>
