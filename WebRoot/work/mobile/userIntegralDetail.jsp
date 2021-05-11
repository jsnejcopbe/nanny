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
<title>积分明细</title>

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
			积分明细</div>
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
					action="${pageContext.request.contextPath}/users/integralDetail.html?pageIndex=1&pageSize=${obj.size}"
					method="post">
							 <input id="hide_type1" type="hidden" value="${status}" name="state"/>
					<div style="padding: 10px" class="clearfix">
						<input type="text" class="inp" placeholder="请选择日期" id="logmin"
							name="logmin" value="${logmin}"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						-<input type="text" class="inp" placeholder="请选择日期" id="logmax"
							name="logmax" value="${logmax}"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
					</div>
					<div style="padding: 5px;margin-left: 50px;">
						<div class="btn-group" id="address">
						
							<a href="javascript:void(0)" class="btn btn-danger dropdown-toggle  btn-self1" data-toggle="dropdown">
									<c:if test="${status==null || status=='' || status=='-1'}">全部</c:if>
									<c:if test="${status=='0' }">消费积分</c:if>
									<c:if test="${status=='1' }">获得积分</c:if>
									<span class=""></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="${pageContext.request.contextPath}/users/integralDetail.html?status=-1">全部</a></li>
										<li><a href="${pageContext.request.contextPath}/users/integralDetail.html?status=0">消费积分</a></li>
										<li><a href="${pageContext.request.contextPath}/users/integralDetail.html?status=1">获得积分</a></li>
									
									</ul>
						</div>
						<button class="btn btn-danger btn-self2" type="submit">搜索</button>
					</div>
				</form>
			</div>
			<div class="col-xs-3 "  style="padding-top: 10px;">
				<span style="font-size: inherit;">总积分：</span>
					<h4>
					<span style="color: red">${obj.userPoint}</span>
				</h4>
			</div>
		</div>


		<!-- 列表信息 -->
		<div class="row rec-list ">
			<c:forEach items="${arr}" var="arr">
				<div class="list-item clearfix">
					<div class="col-xs-9 item-left type2">
						<p>
							
								<span> 
										<img alt="" src="${arr.headImg}" width="20px">${arr.nickName}
								</span>
							
							
						</p>
						<p class="item-time"><span style="padding-right: 20px;">${arr.des}  </span> ${arr.createTime}</p>
					</div>
					<div class="col-xs-3 item-right type2">
						<c:if test="${arr.status=='0'}">
						<p class="item-money">-${arr.point}</p>
						</c:if>
						<c:if test="${arr.status=='1'}">
						<p class="item-money">${arr.point}</p>
						</c:if>
						<c:if test="${arr.status=='0'}">
						<p class="item-sta">消费积分</p>
						</c:if>
						<c:if test="${arr.status=='1'}">
						<p class="item-sta">获得积分</p>
						</c:if>
					</div>
				</div>
			</c:forEach>
			
			<!-- 分页按钮 -->
			<div class="row trans-btn pa">
			
				<c:if test="${obj.nowpage!='1'}">
					<a href="javascript:pag(0);" class="btn btn-danger " >往上翻</a> 
				</c:if>	
				<c:if test="${fn:length(obj.shopIntgral) =='10'}">	
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
		

			
			function  pag(de){
				var size=${obj.size};
				var  page=${obj.nowpage};
				if(de==0){
					 page=page-1;
				}else{
					 page=page+1;
				}
				$("#uForm").attr("action","${pageContext.request.contextPath}/users/integralDetail.html?pageIndex="+page+"&pageSize="+size);
				$("#uForm").submit();
			}
	</script>
 

</body>
</html>
