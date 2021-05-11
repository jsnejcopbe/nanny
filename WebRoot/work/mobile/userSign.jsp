<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">

<title>我的签到</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css"></link>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css"
	type="text/css"></link>

<style type="text/css">
.topDiv {
	width: 100%;
	height: 10%;
	text-align: center;
}

.top-Internal {
	margin-top: 13px;
}

.badyDiv {
	width: 100%;
	height: 40%;
	background-color: #F5F5F5;
}

.flootDiv {
	width: 100%;
}

.pancel {
	margin-bottom: 0px;
	border-bottom: 2px solid #F5F5F5;
}

.badyTable {
	width: 100%;
	text-align: center;
	height: 100%;
}

.word {
	position: absolute;
	font-size: 20px;
	color: #1EA660;
	text-align: center;
	margin-left: 5%;
	margin-top: 8%;
}

.worda {
	position: absolute;
	font-size: 20px;
	color: #7d8da9;
	text-align: center;
	margin-left: 5%;
	margin-top: 8%;
}

.wordhou {
	position: absolute;
	font-size: 20px;
	color: #7d8da9;
	text-align: center;
	margin-top: 8%;
	margin-left: 6%;
}

.spanTime {
	float: right;
	margin-top: 8px;
}

.newTime {
	position: absolute;
	left: 35%;
	top: 130px;
}
</style>


<script type="text/javascript">
var path="${pageContext.request.contextPath}";
</script>

</head>
<body>
	<nav class="topnav">
		<div class="row" style="height: 44px; background: #f8f8f8;width:110%;
    position:fixed;left: 0;top: 0;color: #5f5f5f;z-index: 9998;">
			<a style="margin-top: 5px;" class="topnav-btn col-xs-3"
				href="${pageContext.request.contextPath}/users/userIndex.html"> <i
				class="icon-angle-left icon-2x" style="margin-left:15px;"></i> <span></span> </a>
			<div class="topnav-btn col-xs-6">
				<span style=" margin-top:11px;width:100%; height:20px; line-height:20px; text-align:center; display:block;">每日签到</span>
			</div>
			<a class="topnav-btn col-xs-3" href=""></a>
		</div>
	</nav>

	<div class="topDiv"  style="
    margin-top: 57px;border-bottom: 1px solid #ddd;">
		<div class="top-Internal">

			<div style="display: inline-block;">

				<div>
					<div style="display: inline-block; float: left;">
						<span style="color: rgb(30, 166, 96); font-size: 25px; ">
						${usign[0].signCount }
						</span><br> 
						<span>连续签到</span>
					</div>

					<div style="display: inline-block; padding-bottom: 20;">
						<img src="${usign[0].headImage }"
							style="border: 1px solid #c9c9c9;width: 60px; height:60px; position: absolute; margin-top: -35px; margin-left: 7%;"
							class="img-circle" onerror="this.src='/nanny/work/mobile/images/vddefault.png'">
					</div>

					<div style="display: inline-block; margin-left: 120px;">
						<span style="color: #e85d30;font-size: 25px;">${usign[0].point }</span><br>
						<span>总积分</span>
					</div>


				</div>




			</div>
		</div>
	</div>

	<div class="badyDiv">
		<table class="badyTable">
			<tr>
				<td colspan="3" class="newTime" style="padding-top: 15;">${newdate}</td>
			</tr>
			<tr>
				<td class="imgone">
					<div>
							<span class="word">${day1}日</span>
							<c:if test="${usign[0].signCount!=0}">
							<img src="${pageContext.request.contextPath}/work/mobile/images/beforesign.png"
								style="width:80px;height:80px;">
								</c:if>
								<c:if test="${usign[0].signCount==0}">
								<img src="${pageContext.request.contextPath}/work/mobile/images/afterSign.png"
								style="width:80px;height:80px;">
								</c:if>
					</div>
				</td>
				<td>

					
						<img onclick="add_sign()" src="${pageContext.request.contextPath}/work/mobile/images/sign.png"
								style="width:120px;height:120px;-moz-box-shadow:2px 1px 9px #333333; -webkit-box-shadow:2px 1px 9px #333333; box-shadow:2px 1px 9px #333333;"
								class="img-circle">
						
					
				</td>
				<td>
					<div>
						<span class="wordhou">${day2}日</span><img
							src="${pageContext.request.contextPath}/work/mobile/images/afterSign.png"
							style="width:80px;height:80px;">
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div class="flootDiv">
		<div class="pancel">
			<div class="panel-body">签到记录</div>
		</div>
		<div class="pancel  page" >
		</div>
		<%--<c:forEach items="${usign }" var="us">
			<div class="pancel  page" style="height: 60px;">
				<div class="panel-body">
					<img src="${us.headImage }" style="width:40px;height:40px;" class="img-circle">
						${us.nickName } &nbsp &nbsp &nbsp
						<span class="spanTime">  ${us.score }  &nbsp ${us.signTime }</span>
				</div>
			</div>
		</c:forEach>
		--%>
			<input type="hidden" id="pageIndex" value="">
			<input type="hidden" id="pageSize" value="">
			<div class="pancel">
				<div class="panel-body">
					<div class="row pagin-area">
						<a href="javascript:page();" style="margin-left: 40%;" data-page="1">加载更多</a>
					</div>
				</div>
			</div>
		 
	</div>



	<br>
	<br>
	<br>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/syl/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/usersign.js"></script>
</body>
<script type="text/javascript">
  function add_sign(){
	  $.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/users/updsign.html",
			
			dataType : "text",
			success : function(msg) {
				
				var data = eval('(' + msg + ')');
				if(data.msg=='0'){
					layer.msg("签到成功！");
					setTimeout("location.reload(true)", 2000);
				}else{
					layer.msg("已签到！请勿重复签到");
				}
				//layer.msg("保存成功");
			},
			error : function() {
				layer.closeAll("loading");
				layer.msg('操作有误!');
			}
		});
  }
  
  
  </script>

</html>
