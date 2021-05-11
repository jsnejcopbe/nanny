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
			资金流动</div>
	</div>

	</nav>
	<div class="space80"></div>
	<!-- 头部 结束 -->


	<div class="container">
		<!-- 搜索块 -->
		<div class="row fa-sec"
			style="margin-top:5px;margin-bottom:5px;background-color:#fff; ">
			<div class="col-xs-9 " style="margin-left:-15px;padding-right: 0px;">
				<form  id="uForm"
					action="${pageContext.request.contextPath}/shop/shopAccmo.html?pageIndex=1&pageSize=${rec.size}"
					method="post">
							 <input id="hide_type1" type="hidden" value="${statu}" name="state"/>
					<div style="padding: 10px" class="clearfix">
						<input type="text" class="inp" placeholder="请选择日期" id="logmin"
							name="logmin" value="${lognmin}"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						-<input type="text" class="inp" placeholder="请选择日期" id="logmax"
							name="logmax" value="${logbmax}"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
					</div>
					<div style="padding: 5px;margin-left: 50px;">
						<div class="btn-group" id="address">
							<%-- <a type="button" class="btn btn-danger dropdown-toggle  btn-self1"  
								data-toggle="dropdown">
								<c:if test="${statu==null || statu=='' || statu=='-1'}">全部</c:if>
									<c:if test="${statu=='0' }">退款</c:if>
									<c:if test="${statu=='1' }">充值</c:if>
									<c:if test="${statu=='2' }">付款</c:if>
									<c:if test="${statu=='3' }">提现</c:if>
									<c:if test="${statu=='4' }">返现</c:if>
								 	<span class="caret"></span> 
							</a>
							<input type="hidden" id="status" name="state" value="${statu}">
							<ul class="dropdown-menu" role="menu">
								<li><a data-value="-1">全部</a>
								</li>
								<li><a data-value="0">退款</a>
								</li>
								<li><a  data-value="1">充值</a>
								</li>
								<li><a  data-value="2">付款</a>
								</li>
								<li><a  data-value="3">提现</a>
								</li>
								<li><a  data-value="4">返现</a>
								</li>
							</ul> --%>
							<a href="javascript:void(0)" class="btn btn-danger dropdown-toggle  btn-self1" data-toggle="dropdown">
									<c:if test="${statu==null || statu=='' || statu=='-1'}">全部</c:if>
									<c:if test="${statu=='0' }">退款</c:if>
									<c:if test="${statu=='1' }">充值</c:if>
									<c:if test="${statu=='2' }">付款</c:if>
									<c:if test="${statu=='3' }">提现</c:if>
									<c:if test="${statu=='4' }">返现</c:if>
									<span class=""></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/shop/shopAccmo.html?state=-1">全部</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/shopAccmo.html?state=0">退款</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/shopAccmo.html?state=1">充值</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/shopAccmo.html?state=2">付款</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/shopAccmo.html?state=3">提现</a></li>
										<li><a href="${pageContext.request.contextPath}/shop/shopAccmo.html?state=4">返现</a></li>
									</ul>
						</div>
						<button class="btn btn-danger btn-self2" type="submit">搜索</button>
					</div>
				</form>
			</div>
			<div class="col-xs-3 "  style="padding-top: 10px;">
				<span style="font-size: inherit;">余额：</span>
					<h4>
					<span style="color: red">${balance}</span>
				</h4>
			</div>
		</div>


		<!-- 列表信息 -->
		<div class="row rec-list ">
			<c:forEach items="${rec.rec}" var="rec">
				<div class="list-item clearfix">
					<div class="col-xs-9 item-left type2">
						<p>
							<c:if test="${loginUser.userID==rec.userID}">
								<span> <c:if test="${rec.oname=='null'}">系统</c:if> <c:if
										test="${rec.oname!='null'}">
										<img alt="" src="${rec.uimg}" width="20px">${rec.oname}</c:if>
								</span>
							</c:if>
							<c:if test="${loginUser.userID==rec.otherSide}">
								<span> <c:if test="${rec.uname=='null'}">系统</c:if> <c:if
										test="${rec.uname!='null'}">
										<img alt="" src="${rec.uimg}" width="20px">${rec.uname}</c:if>
								</span>
							</c:if>
						</p>
						<p class="item-time"><span style="padding-right: 20px;">${rec.des}  </span> ${rec.createTime}</p>
					</div>
					<div class="col-xs-3 item-right type2">
						<c:if test="${loginUser.userID==rec.otherSide}">
							<p class="item-money">${rec.money}</p>
						</c:if>
						<c:if test="${loginUser.userID==rec.userID}">
							<p class="item-money">-${rec.money}</p>
						</c:if>
						<p class="item-sta">${rec.stats}</p>
					</div>
				</div>
			</c:forEach>
			
			<!-- 分页按钮 -->
			<div class="row trans-btn pa">
			
				<c:if test="${rec.nowPage!='1'}">
					<a href="javascript:pag(0);" class="btn btn-danger " >往上翻</a> 
				</c:if>	
				<c:if test="${fn:length(rec.rec) =='10'}">	
					<a href="javascript:pag(1);" class="btn btn-danger "  >往下翻</a>
				</c:if>
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
		<script type="text/javascript">
		
	/* 	$(function(){
			var sta=$("#status").val();
			if (sta!=""){
			$("#address span:eq(0)").text($("#address ul li:eq("+sta+")").text());}
			//$("#address span:eq(0)").attr("data-value",sta);
		})
		
		
		$("#address ul li a").click(function(){
			var more = $(this).attr("data-value");
			$("#status").val(more);
			$("#address span:eq(0)").text($(this).text());
			//$("#address span:eq(0)").attr("data-value",more);
			})
			 */
			
			
			function  pag(de){
				var size=${rec.size};
				var  page=${rec.nowPage};
				if(de==0){
					 page=page-1;
				}else{
					 page=page+1;
				}
				$("#uForm").attr("action","${pageContext.request.contextPath}/shop/shopAccmo.html?pageIndex="+page+"&pageSize="+size);
				$("#uForm").submit();
			}
	</script>
 

</body>
</html>
