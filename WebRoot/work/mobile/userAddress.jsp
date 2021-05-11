<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收货地址</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta name="format-detection" content="telephone=no, email=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit"> <!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"> <!-- 优先用Chrome渲染 -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usersup.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/globalname.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/profile.css"></link>
</head>

<body>

<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
		<div class="m-topbar flexbox">
			<!-- 返回首页 -->
			<a class="m-goback" href="${pageContext.request.contextPath}/users/userIndex.html"><i class="iconfont">&#xe600;</i></a>
			
			<div class="m-tlebox flex" style="margin-left: -40px;">
				<div class="storeManage-menu">
					<span class="fixed cur">收货地址</span>
				</div>
			</div>
			
			<!-- 下拉菜单 -->
			
		</div>
		
	</header>
	
	<!-- 修改内容 -->
	<div class="ui-pageswitch ui-pageswitch-a" data-page="pa" >
		<div class="m-pagecont" style="overflow-x: hidden;top:0px;">
			<div class="m-editcard">
				<section class="fixed">
					<h3 class="m-fm-hd">收货地址</h3>
					<ul class="ui-form-list fixed">
						<c:forEach items="${address}" var="ar">
						<li class="fm-item">
		        			
		        				 <div style="float: left;">
				        				 <c:if test="${addressType=='1'}">
				        				 	<a  href="/nanny/users/shopcar.html?id=${ar.id}" >
				        						 <span>${ar.recName} ${ar.tel}</span>
				        			    		 <span class="sp">${ar.community} ${ar.doorplate}</span>
				        			    		 <span class="sp">${ar.address}</span>
				        			    	 </a>
				        			    	 
				        			   	 </c:if>	 
				        			   	 
				        			   	  <c:if test="${addressType=='0'}">
				        				 	<a  href="/mall/admin/shoppingCar.html?arid=${ar.id}" >
				        						 <span>${ar.recName} ${ar.tel}</span>
				        			    		 <span class="sp">${ar.community} ${ar.doorplate}</span>
				        			    		 <span class="sp">${ar.address}</span>
				        			    	 </a>
				        			    	 
				        			   	 </c:if>	
		        			     </div>
		        			     <div style="float: right;">
		        			     <a href="/nanny/users/edituseress.html?id=${ar.id}"  style="color: #0079ff;"><i class="icon-edit">编辑</i></a><br>
		        			     <a href="javascript:delress(${ar.id})" class="als"><i class="icon-trash">删除</i></a></div>
		        			
		      			</li>
		      			</c:forEach>
					</ul>
					
					<h3 class="m-fm-hd"></h3>
					<ul class="ui-form-list fixed">
						<li class="fm-item">
		        			<div class="fm-cont">
		         				<a href="/nanny/users/addUseRess.html" class="btn btn-danger bn">新增地址</a>
		        			</div>	
		        		</li>	      			
		      		</ul>
				</section>
				<%--<div class="pad-10 fixed mt-10 ">
			    	<a href="javascript:updateUsers()" class="ui-btn-4">保存</a>
			    	<a href="/nanny/user/userIndex.html" class="ui-btn-4 btn-back">返回</a>
			   </div>
			--%></div>
		</div>
	</div>
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="text/javascript">
	  function delress(id)
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
		  $.ajax({
                type: "post",
                url: "/nanny/users/deluseress.html",
                data:"id="+id,
                dataType: "text",
                success: function (msg) {
                    layer.closeAll("loading");
                	var data=eval('(' + msg + ')');
                    layer.msg("删除成功");
                    location.reload(true);
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
	  }
    </script>
</body>
</html>
