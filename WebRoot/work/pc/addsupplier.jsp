<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>注册用户</title>
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
			<h1 class="margin-bottom-15">用户注册</h1>
			
			<form id="userform" class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form" >		
				<div class="form-group">
		          <div class="col-xs-12">		            
		            <div class="control-wrapper">
		            	<label class="fm-tle">用&nbsp;&nbsp;户&nbsp;&nbsp;名:&nbsp;</label>
		            	<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" maxlength="11" style="width: 220px; display: inline-block;">
		            </div>		            	            
		          </div>              
		        </div>	
		        
		         <div class="form-group">
		        	<div class="col-md-12">
		        		<div class="control-wrapper">
		        			<label class="fm-tle">手机号码 :&nbsp;</label>
		        			<input id="tel1" name="tel1" type="text" class="form-control" placeholder="请输入手机号" style="width: 220px; display: inline-block;">
		        			<!--  <button type="button" class="btn btn-info " id="yzm" onclick="codeChek()"> 获取验证码</button>-->
							
		        		</div>
		        	</div>
		        </div>
		        
		        <div class="form-group">
		          <div class="col-xs-12">		            
		            <div class="control-wrapper">
		            	<label class="fm-tle">输入密码 :&nbsp;</label>
		            	<input type="password" class="form-control" id="pass1" name="pass1" placeholder="请输入新密码" maxlength="11" style="width: 220px; display: inline-block;">
		            </div>		            	            
		          </div>              
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		            	<label class="fm-tle">确认密码 :&nbsp;</label>
		            	<input type="password" class="form-control" id="pass2" name="pass2" placeholder="请再次输入密码" style="width: 220px; display: inline-block;">
		            </div>
		          </div>
		        </div>
		       
		       
		        <div class="form-group">
		          
		        </div>
		        <div style="text-align:center;" class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		    		          		
		          	</div>
		          		
		          </div>
		           <a href="javascript:upFrom()" class="btn btn-info">提交注册</a>		          		
		          		<a href="javascript:index()" class="btn btn-info">返回登录</a>	
		        </div>
		       
		        <hr>
		      </form>
		      
		</div>
	</div>
	<script type="text/javascript" src="/nanny/work/pc/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="/nanny/js/layer/layer.js"></script>
	<script type="text/javascript" src="/nanny/js/base.js"></script>
    <script type="text/javascript" src="/nanny/work/pc/js/login.js"></script>
    <script type="text/javascript">
	var wait=60;
	
		
	
	function upFrom() {
	
	
	if(validfn([ "isNotNull" ],"#username","请输入用户名")==false){
		return ;
	}
	else if (validfn([ "isNotNull", "isTel" ], "#tel1","手机格式有误，请重新输入") == false) {
		return;
	} else if (validfn([ "isNotNull" ], "#pass1","密码格式有误，请重新输入") == false) {
		return;
	}else if (validfn([ "isNotNull" ], "#pass2","密码格式有误，请重新输入") == false) {
		
		return;
	}else if(userform.pass1.value!=userform.pass2.value){
	layer.msg("密码不一致，请重新输入");
	return ;
	}

	else {
		var data = $("#userform").serialize();
		// 组织ajax参数
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/add/supplierct.json",
			data : data,
			success : function(msg) {
				var data = eval('(' + msg + ')');
				layer.msg(data.msg);
				if(data.hasOwnProperty("success"))
					window.location.href = "${pageContext.request.contextPath}/supplier/login.html";
			
					
				if (data.errorType !="codeError")
					changeCode();
			}
		});
	}

}
	
	
		
		function index(){
			location.href="${pageContext.request.contextPath}/supplier/login.html";
		}
	</script>
</body>
</html>
