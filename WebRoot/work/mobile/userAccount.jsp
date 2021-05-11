<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
</script>
<head>
<title>资金明细</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0,  maximum-scale=1.0,user-scalable=no">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css"></link>
	
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css"
	type="text/css"></link>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/work/mobile/css/money_detail.css"
	type="text/css"></link>
<style type="text/css">
.inp {
	border: 1px solid #ddd;
	position: relative;
	padding: 5px;
	border-radius: 5px;
	width: 100px
}

.btn-self1 {
	margin-right: 10px;
	    padding-top: 4px;
    padding-right: 12px;
    padding-bottom: 5px;
    padding-left: 12px;
}

.btn-self2 {
	padding-top: 4px;
	padding-right: 17px;
	padding-bottom: 5px;
	padding-left: 17px;
}
.pa{
    margin: 0 auto;
    text-align: center;
    padding-top: 10px;
}

.pa a{
   width:150px;
   
}
.storeManage-fix-add{
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
   
    
    height: 48px;
    background-color: #fff;
    text-align: center;
    font-size: 1.4rem;
    color: #030303;
    border-top: 1px solid #e5e5e5;
    z-index: 1005;
 
}
.storeManage-fix-add span{
 	    float: left;
    line-height: 34px;
    margin-left: 10px;
    color: #CA4242;
}
.storeManage-fix-add i{
 	
    color: #e34d4f;
    font-size: 2.4rem;
    float: left;
}
.storeManage-div{
float:left; width:50%; padding-left:30px;margin:5px auto;
}
</style>
<body>
	<!-- 头部 -->
	<nav class="sh-top"
		style="height:44px; background: #f8f8f8;position: relative;">
	<div class="top-con clearfix">
		<a href="${pageContext.request.contextPath}/users/userIndex.html"
			class="col-xs-3 sh-icon-left" style="line-height: 44px;"> <i
			class="iconfont"></i> </a>
		<div class="col-xs-6" style="text-align: center;line-height: 44px;">
			资金流动</div>
	</div>

	</nav>
	<div class="space80"></div>
	<!-- 头部 结束 -->


	<div class="container">
		<!-- 搜索块 -->
		<div class="row fa-sec"
			style="margin-top:5px;margin-bottom:5px;background-color:#fff; ">
			<div class="col-xs-9 " style="margin-left:-15px; padding-right: 0px;">
				<%-- <form  id="uForm" action="${pageContext.request.contextPath}/users/userAccount.html?pageIndex=1&pageSize=${rec.size}"
					method="post"  > --%>
					<div style="padding: 5px" class="clearfix">
						<input type="text" class="inp" placeholder="请选择日期" id="logmin"
							name="logmin" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						-<input type="text" class="inp" placeholder="请选择日期" id="logmax"
							name="logmax" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
					</div>
					<div style="padding: 5px;margin-left: 50px;">
						<div class="btn-group" id="address">
						<!-- 	<button type="button" class="btn btn-danger dropdown-toggle  btn-self1"  
								data-toggle="dropdown">
								<span class="">类别</span> <span class="caret"></span>
							</button>
							<input type="hidden" id="status" name="state" > 
						 <ul class="dropdown-menu" role="menu">
								<li><a data-value="0">退款</a>
								</li>
								<li><a  data-value="1">充值</a>
								</li>
								<li><a  data-value="2">付款</a>
								</li>
								<li><a  data-value="3">提现</a>
								</li>
								
							</ul>  -->
							<select name="state" id="state" class="btn btn-danger dropdown-toggle  btn-self1" >
								<option value="">类别</option>
								<option value="0">退款</option>
								<option value="1">充值</option>
								<option value="2">付款</option>
								<option value="3">提现</option>
							</select> 
						</div>
						<button class="btn btn-danger" type="button" id="btn_serch">搜索</button>
					</div>
			<!-- 	</form> -->
				
			</div>
			<div class="col-xs-3 "  style="padding-top: 10px;">
				<span style="font-size: inherit;">余额：</span>
					<h4>
					<span style="color: red">${uar.balance}</span>
				</h4>
			</div>
		</div>


		<!-- 列表信息 -->
		<div class="row rec-list ">
			<div id="forrec"></div>

		<!-- 分页按钮 -->
			<input type="hidden" id="pageIndex" value="">
			<input type="hidden" id="pageSize" value="">
			<div class="pancel" >
				<div class="panel-body">
					<div class="row pagin-area">
						<a href="javascript:page();" style="margin-left: 40%; color: #4A4A4A" data-page="1">加载更多</a>
					</div>
				</div>
			</div>
			
			
	<div  style="height: 48px;">
	</div>
	<div class="storeManage-fix-add" >
		<div  class="storeManage-div">
			<a style="float:left;" href="${pageContext.request.contextPath}/users/recharge.html"> <i class="iconfont color1">&#xe623;</i><span>充值记录</span>
			</a>	
		</div>
				<div  class="storeManage-div">
			<a style="float:left;" href="${pageContext.request.contextPath}/users/transfer.html"> <i class="iconfont color1">&#xe632;</i> <span>我要提现</span>
			</a>	
		</div>
	</div>
		</div>
	</div>


	<!-- 载入js -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/userAccount1.js"></script>


</body>
</html>
