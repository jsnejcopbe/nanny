<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>

<title>起送价格设置</title>

<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min-3.2.1.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/dispatchEdit.css">
</head>

<body>
	<!-- 头部 -->
	<nav class="sh-top">
		<div class="top-con clearfix">
			<a href="${pageContext.request.contextPath}/shop/shopInfo.html" class="col-xs-2 sh-icon-left">
				<i class="iconfont-gl">&#xe61b;</i>
			</a>
			<div class="col-xs-8 sh-cen">
				起送价格设置
			</div>
		</div>
	</nav>
	<div class="space43"></div>
	<!-- 头部 结束 -->
	
	<!-- 主体内容 开始 -->
	<div class="container">
		<div class="row">
			<ul class="list-group col-xs-12 dpt-con">
				<c:forEach items="${dispacthList }" var="dl">
				<li class="list-group-item clearfix">
					<div class="col-xs-6">
						${dl.addName }
					</div>
					<input class="dptID" type="hidden" value="${dl.id }">
					<div class="col-xs-6">
						<input class="form-control dptPrice" type="text" placeholder="请输入起送价格" value="${dl.fee }" onkeyup="this.value=this.value.replace(/\D/g,'')" >
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="row">
			<a href="javascript:updateDptPrice()" class="btn btn-block btn-danger">保存设置</a>
		</div>
	</div>
	
	<!-- 加载js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
	<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	
	function updateDptPrice(){
		var data=createData();
		layer.load(1, {shade: [0.8,'#333']});
		var param={
			"sURL":path+"shop/updatedispatch.json",
			"Data":"jsonData="+JSON.stringify(data),
			"fnSuccess":function(data){
				layer.closeAll('loading');
				layer.msg(data.msg);
				location.href=path+"shop/shopInfo.html"
			},
			"fnError":function(){layer.closeAll('loading');layer.msg("设置失败");}
		};
		new g_fnAjaxUpload(param);
	}
	
	function createData(){
		var dataArray=new Array();
		$(".list-group-item").each(function(){
			var obj=new Object();
			var price=$(this).find(".dptPrice").val();
			obj.dptID=$(this).find(".dptID").val();
			obj.dptPrice=price==""?0:price;
			dataArray.push(obj);
		});
		return dataArray;
	}
	</script>
</body>
</html>
