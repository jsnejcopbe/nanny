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
<title>新增资料</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta name="format-detection" content="telephone=no, email=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit"> <!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"> <!-- 优先用Chrome渲染 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usersup.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/profile.css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/globalname.css">
<script type="text/javascript">
function show(){
	$("#area").hide();
}
var UR="${pageContext.request.contextPath}";
</script>

</head>

<body onLoad="show()">

<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
		<div class="m-topbar flexbox">
			<!-- 返回首页 -->
			<a class="m-goback" href="javascript:history.go(-1)"><i class="iconfont">&#xe600;</i></a>
			
			<div class="m-tlebox flex">
				<div class="storeManage-menu">
					<span class="fixed cur">新增地址</span>
				</div>
			</div>
			
			<!-- 下拉菜单 -->
			<a class="m-moremenu" id="j-m-moremenu" href="javascript:void(0)" data-flag='2'><i class="iconfont">&#xe601;</i></a>
		</div>
		<section class="m-menubox">
			<ul class="fixed">
				<li><a class="menu-link" href=""><span><i class="iconfont">&#xe644;</i></span><div class="name">分类</div></a></li>
				<li><a class="menu-link" href=""><span><i class="iconfont">&#xe615;</i></span><div class="name">我的微店</div></a></li>
				<li><a class="menu-link" href=""><span><em class="badge"></em><i class="iconfont">&#xe61c;</i></span><div class="name">购物车</div></a></li>
				<li><a class="menu-link" href=""><span><i class="iconfont">&#xe614;</i></span><div class="name">退出</div></a></li>
			</ul>
		</section>
	</header>
	
	<!-- 修改内容 -->
	<div id="addre" class="ui-pageswitch ui-pageswitch-a" data-page="pa" >
		<div class="m-pagecont" style="overflow-x: hidden;top:0px;">
			<div class="m-editcard">
				<section class="fixed">
				
				
					<h3 class="m-fm-hd">基本信息</h3>
					<ul class="ui-form-list fixed">
						<li class="fm-item">
		        			<div class="fm-cont " style="border-bottom: none;">
		         				<label class="fm-tle">姓名</label>
		          				<div class="fm-right">
			          				<input type="hidden" id="id" name="id" value="${sga.id }">
		            				<input type="text" id="recName" name="recName" class="ui-ipt" placeholder="请填写姓名" value="${sga.recName }">
		          				</div>
		        			</div>
		      			</li>
		      			
		      			<li class="fm-item">
		        			<div class="fm-cont" style="border-bottom: none;">
		          				<label class="fm-tle">电话</label>
		          				<div class="fm-right">
          							<input type="text" class="ui-ipt" placeholder="请填写联系电话" id="tel" name="tel" value="${sga.tel }">
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont" style="border-bottom: none;">
		          				<label class="fm-tle">地址</label>
		          				<div class="fm-right">
          							<input type="hidden" id="areaID" name="areaID" value="${sga.memo }">
          							<input type="text" class="ui-ipt" placeholder="请选择地址" id="address" name="address" value="${sga.address }"  onclick="areashow()">
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont" style="border-bottom: none;">
		          				<label class="fm-tle">小区</label>
		          				<div class="fm-right">
          							<input type="text" class="ui-ipt" placeholder="请填写小区或住址" id="community" name="community" value="${sga.community }">
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont" style="border-bottom: none;">
		          				<label class="fm-tle">详细地址</label>
		          				<div class="fm-right">
          							<input type="text" class="ui-ipt" placeholder="请填写楼栋或门牌" id="doorplate" name="doorplate" value="${sga.doorplate }">
		          				</div>
		        			</div>
		      			</li>
					</ul>
				</section>
				<div class="pad-10 fixed mt-10 ">
			    	<a href="javascript:updateUsers()" class="btn btn-danger bn">保存</a>
			    	<%--<a href="/nanny/user/userIndex.html" class="ui-btn-4 btn-back">返回</a>
			    --%></div>
			</div>
		</div>
	</div>
	
	
		<div id="area" class="m-pagecont" >
			<div class="storeManage storeManage-seller">

				<form id="posa">
					<div style="padding: 10px" class="clearfix">
						<input class="form-control nav-input-search pull-right"
							type="search" placeholder="输入学校、商务楼、地址" name="addname"
							id="addname"> 
					</div>
					<div style="padding: 5px;">
						<a class="btn btn-danger bn"
							 type="button"  href="javascript:psot()">搜索</a>
					</div>
				</form>
			<div class="panel-body  clearfix">
				<div class="block">
					<div class="bl-city">
						<ul class="list-group list-todo" id="ulist">
							
						</ul>
					</div>
				</div>
			</div>
			</div>
		</div>
	
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/edituseress.js"></script>
</body>
</html>
