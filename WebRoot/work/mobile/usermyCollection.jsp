<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>我的收藏</title>
<link
	href="${pageContext.request.contextPath}/work/mobile/css/myCollection.css"
	rel="stylesheet" type="text/css">
<link rel="apple-touch-startup-image"
	href="http://d2.lashouimg.com/wap/img/startup.png">
<!--百度轻应用-->
<meta name="baidu-tc-cerfication"
	content="688cdfaa49f7c8f87fd5883492587d8e">
<style type="text/css">
</style>
</head>

<body>
	<div id="wrap">
		<header class="index">
		<div class="c-hd">
			<a class="m-goback" href="${pageContext.request.contextPath}/users/userIndex.html"><i class="iconfont"></i> </a>
			<section class="hd-title "> 我的收藏 </section>
			<section class="side"  style=""> <a href="" class="arrowside-fun"><span class="c" style=" color:#fff;" id="order-del-btn">保存</span>
			</a> </section>
		</div>
		</header>
		
		
		<div class="content">
			<div class="list-view">
				<ul class="list-ul" data-type="shoucang_goods">	
				<c:forEach items="${mycollectionlist}" var="li">		
					<li class="li_76252700 ${li.shopID}"  id="">
						<div class="list-item" style="float:left; width:80%;">
							<div class="pic" style="width:50px;height:50px;">
								<img
									src="${li.shop_icon}"	alt="${li.address}" style="display: inline;">
							</div>
							<div class="info" style="height:40px">
								<h2 class="title"> ${li.shop_name}
	                                    <label> <input type="checkbox"
										name="gids[]" value=""> <i id="76252700"
										class="checkbox check-icon-toggle" data-id="76252700"></i> </label>
								</h2>
								<h3 class="title">&nbsp;&nbsp;${li.address}</h3>
								<div class="main"></div>
							</div>
						</div> <a href=" /nanny/store-${li.shopID}.html"  class="btn-combg" style="float:left; width:80%;"></a>
						<a href="javascript:void(0);"  class="btn-combg  a"  user-id="${li.userID}"   shop-id="${li.shopID}"  style=" right:0!important; float:right; width:16%;">
						<span style="display:block;  text-align:center; height:60px; line-height:60px; text-align:center; height:60px; line-height:60px">删除</span>
						</a>
				   </li>
				</c:forEach>
				</ul>
			</div>
		</div>

		<!--js-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
		<script type="text/javascript"> 
			var BASEPATH="${pageContext.request.contextPath}";
		    $(document).ready(function(){
		      $("body").on("click",".a",function(){
		       b= $(this).attr("user-id");   
		       d= $(this).attr("shop-id");
		   	    $("."+d).remove();
		   	       delcollection(); 	         	   
		   });
		});
		</script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/usermyCollection.js"></script>
	
			
</body>
</html>
