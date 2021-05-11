<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>打印预览</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

</head>

<body style="background-color: #f0f0f0">
	<div id="printCon" class="con" style="padding: 10px 20px;background-color: #fff;">
		<div class="title">
			<h2 style="margin: 20px 0px 0px 0px;font-weight: bold;">掌上保姆</h2>
			<h3 style="margin: 0px 0px 20px 0px;font-weight: bold;">${printProMsg.shop_name }</h3>
		</div>
		
		<div class="split" style="border-bottom: 2px dashed #000;width: 100%;"></div>
		
		<div class="base" style="width: 100%;">
			<ul style="list-style: none;padding: 0px;margin: 5px 0px;">
				<li style="font-size: 12px;">${printProMsg.orderCode }</li>
				<li style="font-size: 12px;"><span>收货人：</span>${printProMsg.recName }</li>
				<li style="font-size: 12px;"><span>电话：</span>${printProMsg.recTel }</li>
				<li style="font-size: 12px;"><span>地址：</span>${printProMsg.address }</li>
				<li style="font-size: 12px;"><span>下单时间：</span>${printProMsg.createTime }</li>
				<li style="font-size: 12px;"><span>支付方式：</span>${printProMsg.memo }</li>
			</ul>
		</div>
		
		<div class="split" style="border-bottom: 2px dashed #000;width: 100%;"></div>
		
		<div class="list">
			<table style="border: none;width: 100%;">
				<thead>
					<tr>
						<th align="center" style="font-weight: bold;font-size: 12px;">名称</th>
						<th align="center" style="font-weight: bold;font-size: 12px;">单价</th>
						<th align="center" style="font-weight: bold;font-size: 12px;">数量</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${printProMsg.detList }" var="dl">
					<tr>
						<td align="center" style="font-size: 12px;">${dl.name }</td>
						<td align="center" style="font-size: 12px;">${dl.price }</td>
						<td align="center" style="font-size: 12px;">${dl.count }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div class="split" style="border-bottom: 2px dashed #000;width: 100%;"></div>
		
		<div class="list"><div style="padding: 10px 0px;font-size: 12px;">留言：${printProMsg.message }</div></div>
		
		<div class="split" style="border-bottom: 2px dashed #000;width: 100%;"></div>
		
		<div class="count"><h4 style="margin: 5px 0px;">总计：${printProMsg.totalPrice }元</h4></div>
		
		<div class="shopmsg" style="margin-bottom: 20px;">
			<h4 style="margin-bottom: 0px;">足不出户轻松购物</h4>
			<div class="qrCode" style="text-align: center;">
				<img style="height: 100px;width: 100px;" src="${pageContext.request.contextPath}/image/TwoCode.html?data=${printProMsg.shop_des}">
			</div>
		</div>
	</div>
	<div style="text-align: center;margin-top: 10px;"><button onclick="location.href='${pageContext.request.contextPath}/shop/order-1.html'" style="font-size: 25px;font-weight: bold;">返回</button></div>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.PrintArea.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
<script type="text/javascript">
$(function(){
	new g_fnImgCheck(function(){
		var src=$(".qrCode img").attr("src");
		if(src.indexOf("defalut")==-1)
			$("#printCon").printArea();
		else
		{
			if(confirm("二维码生成失败,是否仍要打印?"))
			{
				$(".qrCode img").remove();
				$("#printCon").printArea();
			}
		}
	});
});
</script>
</html>
