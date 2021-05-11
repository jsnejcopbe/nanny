<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改密码</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-status-bar-style"
	content="black-translucent" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">
<!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<!-- 优先用Chrome渲染 -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usersup.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/profile.css"></link>
<script type="text/javascript">
 var tel="${loginUser.tel}";
 function load()
{
 if(tel=='null'){
 	layer.load(1, {shade : [ 0.8, '#333' ]});
	layer.msg("请设置手机号码");
 	setTimeout(function(){location.href="${pageContext.request.contextPath}/phone_jump.html";}, 2000);
 }
}
</script>
  </head>
  
  <body onload="load()">
    <!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
	<div class="m-topbar flexbox">
		<!-- 返回首页 -->
		<c:if test="${sid=='null'}">
			<a class="m-goback" href="${pageContext.request.contextPath}/users/userIndex.html"><i class="iconfont">&#xe600;</i></a>
			</c:if>
			<c:if test="${sid!='null'}">
			<a class="m-goback" href="${pageContext.request.contextPath}/shop/shopInfo.html"><i class="iconfont">&#xe600;</i></a>
			</c:if>
		<div class="m-tlebox flex" style="margin-left: -40px;">
			<div class="storeManage-menu">
				<span class="fixed cur">修改密码</span>
			</div>
		</div>
	</div>
	</header>
	
	
	
	<!-- 修改内容 -->
	<div class="ui-pageswitch ui-pageswitch-a" data-page="pa">
		<div class="m-pagecont" style="overflow-x: hidden;top:0px;">
			<div class="m-editcard">
				<section class="fixed">
				
				<h3 class="m-fm-hd"></h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">新密码：</label>
							<div class="fm-right">
								<input type="password" class="ui-ipt" name="pass1" id="pass1" placeholder="请填写密码" />
								
							</div>
						</div>
					</li>
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">再次输入：</label>
							<div class="fm-right">
								<input type="password" class="ui-ipt" name="pass2" id="pass2" placeholder="请再次填写密码" />
								
							</div>
						</div>
					</li>
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">验证码</label>
							<div class="fm-right">
								<input type="text" class="ui-ipt " name="mocode" id="mocode" placeholder="请填写验证码" style="width: 120px;"/>
								<button type="button" class="btn btn-danger " id="yzm" onclick="codeChek()"> 获取验证码</button>
							</div>
							<div class="fm-left">
							</div>
						</div>
					</li>
				</ul>
				<h3 class="m-fm-hd"></h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item" style="padding: 0;">
						<div class="fm-cont">
							
							<div style="width: 100%;">
								<button type="button" class="btn btn-danger  btn-block"  onclick="updpass()"> 修改</button>
								
							</div>
						</div>
					</li>

					
				</ul>
			
				
				
				</section>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var wait=60;
	
	var shopid=${loginUser.shopID};
	
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
	
	
	
	
		function updpass() {
			layer.load(1, {
				shade : [ 0.8, '#333' ]
			});
			var pa=$("#pass1").val();
			var  mocode=$("#mocode").val();
			var pa1=$("#pass2").val();
			
			var data = "pass1="+pa+"&mocode="+mocode;
			if(pa==pa1){
			$.ajax({
				type : "post",
				url : "/nanny/editPasswork.html",
				data : data,
				dataType : "text",
				success : function(msg) {
					var data = eval('(' + msg + ')');
					if(data.msg=="0"){
						layer.closeAll("loading");
						
						layer.msg("修改成功");
						 setTimeout(function(){
		                    	if (shopid=='null')
		     					location.href="${pageContext.request.contextPath}/users/userIndex.html";
		     				else
		     					location.href="${pageContext.request.contextPath}/shop/shopIndex.html";
		                    }, 2000);
		         			
					}else{
						layer.closeAll("loading");
						layer.msg("验证码错误");
					}
					//setTimeout(location.reload(true), 3000);
				},
				error : function() {
					layer.msg('操作有误!');
				}
			});
		}else{
			layer.closeAll("loading");
				layer.msg("两次输入密码不同！");
			}
	}
	</script>
  </body>
</html>
