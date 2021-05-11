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
<title>修改手机</title>
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

  </head>
  
  <body>
    <!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
	<div class="m-topbar flexbox">
		<!-- 返回首页 -->
		
			<a class="m-goback" href="${pageContext.request.contextPath}/users/userProfile.html"><i class="iconfont">&#xe600;</i></a>
			
			
		<div class="m-tlebox flex" style="margin-left: -40px;">
			<div class="storeManage-menu">
				<span class="fixed cur">修改手机</span>
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
							<label class="fm-tle">新手机：</label>
							<div class="fm-right">
								<input type="text" class="ui-ipt" name="tel" id="tel" placeholder="请填写手机号码" />
								
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
	 <script type="text/javascript" src="/nanny/work/pc/js/login.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var wait=60;
	

	
	/**
	 * 发验证码
	 */
	function codeChek(){
		if (validfn([ "isNotNull", "isTel" ], "#tel","手机格式有误，请重新输入") == false) {
		
		return;
		}
		else{
		var tel=$("#tel").val();
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
	    			"sURL":"/nanny/phoneCode.html",
	    			"Data":"tel="+tel,
	    			"fnSuccess":function(rtnData){
	    				if(rtnData.msg2=="1"){
	    				layer.msg(rtnData.msg1);
	    				$("#tel").attr("readonly","readonly");
	    				}
	    				else if(rtnData.msg2=="2"){
	    				layer.msg(rtnData.msg1);
	    				$("#yzm").text("获取验证码");
	       					 wait = 0;  	
	    					return;
	    				}
	    				else{
	    				layer.msg(rtnData.msg1);
	    				}
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
	}
	
	
	
	
		function updpass() {
		
		if (validfn([ "isNotNull", "isTel" ], "#tel","手机格式有误，请重新输入") == false) {
		
		return;
		}else{
			layer.load(1, {
				shade : [ 0.8, '#333' ]
			});
			var tel=$("#tel").val();
			var  mocode=$("#mocode").val();
	
			var data = "tel="+tel+"&mocode="+mocode;
			
			$.ajax({
				type : "post",
				url : "/nanny/editPhonework.html",
				data : data,
				dataType : "text",
				success : function(msg) {
					var data = eval('(' + msg + ')');
					if(data.msg=="0"){
						layer.closeAll("loading");
						
						layer.msg("修改成功");
						setTimeout(function(){
		                    	
		     					location.href="${pageContext.request.contextPath}/users/userIndex.html";}, 2000);
		     					
						
					}else{
						layer.closeAll("loading");
						layer.msg(data.msg1);
					}
					
				},
				error : function() {
					layer.msg('操作有误!');
				}
			});
			}
		
	}
	</script>
  </body>
</html>
