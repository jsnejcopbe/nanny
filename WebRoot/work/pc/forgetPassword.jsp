<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>忘记密码</title>
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
			<h1 class="margin-bottom-15">重置密码</h1>
			
			<form id="userform" class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form">				
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
		        	<div class="col-md-12">
		        		<div class="control-wrapper">
		        			<label class="fm-tle">手机号码 :&nbsp;</label>
		        			<input id="tel1" name="tel1" type="text" class="form-control" placeholder="请输入手机号" style="width: 220px; display: inline-block;">
		        			<button type="button" class="btn btn-info " id="yzm" onclick="codeChek()"> 获取验证码</button>
							
		        		</div>
		        	</div>
		        </div>
		        <div class="form-group">
		        	<div class="col-md-12">
		        		<div class="control-wrapper">
		        			<label class="fm-tle">验&nbsp;&nbsp;证&nbsp;&nbsp;码 :&nbsp;</label>
		        			<input id="mocode" name="mocode" type="text" class="form-control" placeholder="请输入验证码" style="width: 220px; display: inline-block;">
		        			
		        		</div>
		        	</div>
		        </div>
		        <div class="form-group">
		          
		        </div>
		        <div style="text-align:center;" class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		          		<button type="button" class="btn btn-info"  onclick="updpass()"> 修改密码</button>
		          		&nbsp;&nbsp;&nbsp;&nbsp;
		          		<button type="button" onclick="index()" class="btn btn-info">返回登陆</button>		          		
		          	</div>
		          </div>
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
	
	
	/**
	 * 发验证码
	 */
	function codeChek(){
		var te= $("#tel1").val();
		if((/^0?1[3|4|5|8][0-9]\d{8}$/).test(te)==true){
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
	    			"sURL":"/nanny/pcobtainCode.html",
	    			"Data":"tell="+te,
	    			"fnSuccess":function(rtnData){
	    				//var data = eval('(' + rtnData + ')');
	    				if(rtnData.msg=="0"){
	    					layer.msg("请等待,验证码会发送到您的手机");
	    				}else{
	    					layer.msg("请检查输入的号码是否正确或者前往注册");
	    				}
	    			},
	    			"fnError":function(){
	    				layer.msg("发送失败,请联系管理员");
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
	}else{
		layer.msg("手机格式不对，请重新输入");
		 }
	
	}	
	
	
		function updpass() {
			layer.load(1, {
				shade : [ 0.8, '#333' ]
			});
			var pa=$("#pass1").val();
			var  mocode=$("#mocode").val();
			var pa1=$("#pass2").val();
			var tell=$("#tel1").val();
			if(pa1==pa){
			var data = "pass1="+pa+"&mocode="+mocode+"&tell="+tell;
			$.ajax({
				type : "post",
				url : "/nanny/pceditPasswork.html",
				data : data,
				dataType : "text",
				success : function(msg) {
					var data = eval('(' + msg + ')');
					if(data.msg=="0"){
						layer.closeAll("loading");
						
						layer.msg("修改成功");
						 setTimeout(function(){
								location.href="${pageContext.request.contextPath}/login.html";}, 2000);
		         			
					}else{
						layer.closeAll("loading");
						layer.msg("验证码错误或内容为空");
					}
					//setTimeout(location.reload(true), 3000);
				},
				error : function() {
					layer.closeAll("loading");
					layer.msg('操作有误!');
				}
			});
			}else{
				layer.msg("两次输入密码不一样！");
				layer.closeAll("loading");
			}
		}
		
		function index(){
			location.href="${pageContext.request.contextPath}/login.html";
		}
	</script>
</body>
</html>
