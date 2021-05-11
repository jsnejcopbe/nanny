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
	var path="${pageContext.request.contextPath}";
</script>
<head>
<title>兑换明细</title>

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

</style>
<body>
	<!-- 头部 -->
	<nav class="sh-top"
		style="height:44px; background: #f8f8f8;position: relative;">
	<div class="top-con clearfix">
		<a href="${pageContext.request.contextPath}/shop/shopIndex.html"
			class="col-xs-3 sh-icon-left" style="line-height: 44px;"> <i
			class="iconfont"></i> </a>
		<div class="col-xs-6" style="text-align: center;line-height: 44px;">
			兑换明细</div>
	</div>

	</nav>
	<div class="space80"></div>
	<!-- 头部 结束 -->


	<div class="container">
		<!-- 搜索块 -->
		<div class="row fa-sec"
			style="margin-top:5px;margin-bottom:5px;background-color:#fff; ">
			<div class="col-xs-9 " style="margin-left:-15px;padding-right: 0px;" id="user_query">
				<%-- <form  id="uForm"
					action="${pageContext.request.contextPath}/users/ProductExchangeDetail.html?pageIndex=1&pageSize=${obj.size}"
					method="post"> --%>
							 <!-- <input id="memo" type="hidden"  name="memo"/> -->
					<div style="padding: 10px;padding-right: 0;" class="clearfix">
						<input type="text" class="inp" placeholder="请选择日期" id="logmin"
							name="logmin" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						-<input type="text" class="inp" placeholder="请选择日期" id="logmax"
							name="logmax" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
					</div>
					<div style="padding: 5px;margin-left: 20px;">
						<div class="btn-group" id="address">
						
							<%-- <a href="javascript:void(0)" class="btn btn-danger dropdown-toggle  btn-self1" data-toggle="dropdown">
									<c:if test="${memo==null || memo=='' || memo=='-1'}">全部</c:if>
									
									<c:if test="${memo=='0' }">等待确认</c:if>
									<c:if test="${memo=='1' }">兑换成功</c:if>
									<span class=""></span>
									</a>
									<ul class="dropdown-menu" role="menu" id="qqq">
									<li><a href="javascript:void(0)" data-id="-1">全部</a></li>
										<li><a href="javascript:void(0)" data-id="0">等待确认</a></li>
										<li><a href="javascript:void(0)" data-id="1">兑换成功</a></li>
									</ul> --%>
									
							<select name="state" id="state" class="btn btn-danger dropdown-toggle  btn-self1" >
								<option value="">类别</option>
								<option value="0">等待确认</option>
								<option value="1">兑换成功</option>
							</select> 
						</div>
						<button class="btn btn-danger btn-self2" type="button" id="btn_serch">搜索</button>
					</div>
				<!-- </form> -->
			</div>
			<div class="col-xs-3 "  style="padding-top: 10px;padding-right: 5px;">
				<span style="font-size: inherit;">总积分：</span>
					<h4>
					<span style="color: red">${obj.userPoint}</span>
				</h4>
			</div>
		</div>


		<!-- 列表信息 -->
		<div class="row rec-list ">
		
			<div id="forobj"></div>
			
			
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
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/work/mobile/js/money_detail.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/userProductExchange.js"></script>
		
 

</body>
</html>
