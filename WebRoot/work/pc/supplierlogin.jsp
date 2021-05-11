<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>欢迎您，亲爱的用户 请登录</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/work/pc/css/templatemo_style.css" rel="stylesheet" type="text/css">	
	
</head>
  
  <body class="templatemo-bg-gray">
	<div class="container">
		<div class="col-md-12">
			<h1 class="margin-bottom-15">欢迎登录掌上保姆供应商中心</h1>
			
			<form id="userform" class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form">				
		        <div class="form-group">
		          <div class="col-xs-12">		            
		            <div class="control-wrapper">
		            	<label for="username" class="control-label fa-label"><i class="fa fa-user fa-medium"></i></label>
		            	<input type="text" class="form-control" id="touchweb_form-username" name="nickName" placeholder="请输入手机号码" maxlength="11">
		            </div>		            	            
		          </div>              
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		            	<label for="password" class="control-label fa-label"><i class="fa fa-lock fa-medium" ></i></label>
		            	<input type="password" class="form-control" id="touchweb_form-password" name="password" placeholder="密码" >
		            </div>
		          </div>
		        </div>
		        <div class="form-group">
		        	<div class="col-md-12">
		        		<div class="control-wrapper">
		        			<input id="verifycode" name="verifycode" type="text" class="form-control" placeholder="请输入验证码" style="width: 200px; display: inline-block;">
		        			<a id="reverifycode" href="javascript:changeCode()" class="pull-right" style="text-align: right;">
		        				<img id="m_captcha"  src="${pageContext.request.contextPath}/verifycode.html" style="height: 40px;">
		        			</a>
		        		</div>
		        	</div>
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
	             	<div class="checkbox control-wrapper">
	                	<label>
	                  		<input id="checkbox" name="autologin"  type="checkbox" checked="checked"> 记住
                		</label>
                		<a style="line-height: 20px;" href="${pageContext.request.contextPath}/chpass_jump.html" class="text-right pull-right">忘记密码?</a>
	              	</div>            	
		          </div>
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		          		<a href="javascript:upFrom()" class="btn btn-info">立即登录</a>		          		
		          	</div>
		          </div>
		        </div>
		        <hr>
		      </form>
		      <div class="text-center">
		      	<a href="${pageContext.request.contextPath}/supplier/add.html" class="templatemo-create-new">创建新帐户 <i class="fa fa-arrow-circle-o-right"></i></a>
		      </div> 
		  
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/supplierlogin.js"></script>
</body>
</html>
