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
<title>修改店铺电话</title>
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
		
			<a class="m-goback" href="${pageContext.request.contextPath}/shop/shopInfo.html"><i class="iconfont">&#xe600;</i></a>
			
		<div class="m-tlebox flex" style="margin-left: -40px;">
			<div class="storeManage-menu">
				<span class="fixed cur">修改店铺电话</span>
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
							<label class="fm-tle">电话1：</label>
							<div class="fm-right">
								<input type="text" class="ui-ipt" name="tel1" id="tel1"  value="${tel1}" maxlength="11" placeholder="请填写联系电话" />
								
							</div>
						</div>
					</li>
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">电话2：</label>
							<div class="fm-right">
								<input type="text" class="ui-ipt" name="tel2" id="tel2" value="${tel2}" maxlength="11" placeholder="请填写联系电话" />
								
							</div>
						</div>
					</li>
					
				</ul>
				<h3 class="m-fm-hd"></h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item" style="padding: 0;">
						<div class="fm-cont">
							
							<div style="width: 100%;">
								<button type="button" class="btn btn-danger  btn-block"  onclick="upd_tel()"> 修改</button>
								
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
	
	function upd_tel() {
		var memo=$("#tel1").val()+","+$("#tel2").val();
		$.ajax({
				type:"post",
	     		url:"${pageContext.request.contextPath}/shop/updshoptel.html",
	     		data:"&memo="+memo,
	     		dataType: "text",
	     		success: function(data){
	     			layer.msg("修改成功");
	     			setTimeout("location.href='${pageContext.request.contextPath}/shop/shopInfo.html'", 2000);
	         	  },
	         	error: function(){layer.msg("修改失败");}
	     	});
	
	}
		
	</script>
  </body>
</html>
