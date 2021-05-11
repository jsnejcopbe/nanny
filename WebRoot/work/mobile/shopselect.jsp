<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>门店选择</title>
<meta name="description" content="" />
<meta charset="utf-8" />
<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link href="${pageContext.request.contextPath}/work/mobile/css/shopselect.css" rel="stylesheet" type="text/css" />

<script>
function setTab(name,cursel){
	cursel_0=cursel;
	for(var i=1; i<=links_len; i++){
		var menu = document.getElementById(name+i);
		var menudiv = document.getElementById("con_"+name+"_"+i);
		if(i==cursel){
			menu.className="off";
			menudiv.style.display="block";
		}
		else{
			menu.className="";
			menudiv.style.display="none";
		}
	}
}
function Next(){                                                        
	cursel_0++;
	if (cursel_0>links_len)cursel_0=1
	setTab(name_0,cursel_0);
} 
var name_0='one';
var cursel_0=1;
var links_len,iIntervalId;
onload=function(){
	var links = document.getElementById("tab1").getElementsByTagName('li')
	links_len=links.length;
	
	document.getElementById("con_"+name_0+"_"+links_len).parentNode.onmouseover=function(){
		clearInterval(iIntervalId);
		this.onmouseout=function(){
			iIntervalId = setInterval(Next,ScrollTime);;
		}
	}
}
</script>
</head>
<body>
<header id="hometop">
<div class="clearfix" style="background:#f2f2f2">
    <div class="top title" style="text-align:center;color:#fff;font-size:18px;padding:10px;"><span>选择门店</span></div>
   <div style="background:#f2f2f2;padding:10px 0 ;">
	<!-- <div style="padding:0 10px 10px;font-size:14px;background:#f2f2f2;">
		<div style="padding:10px;border:1px solid #ddd;font-size:14px;background:#fff;">
			<p style="width: 100%; font-size: 14px; line-height: 16px;">亲们，我们的送货上门同一时间为上午的11:00-13:00、以及下午的17:00-19:00，请先选择以下附近的门店进入商城。</p>
		</div>
	</div> -->
	
	<div style="background:#fff;padding:10px;width:100%;height:40px">
	<div style="float:left;width:89%;">
		  <div style="float:left;width:6%;line-height: 40px;"><img src="${pageContext.request.contextPath}/work/mobile/img/posi.png"></div>
		  <div style="width:86%;padding-left: 8%;">
		  <div style="font-size:14px;float:inherit;">${backData.nowPos }</div>
		  <div style="font-size:12px;color:#ddd;" onclick="location.href='${pageContext.request.contextPath}/global_city.html'">定位不准或其他地址</div>
		  </div>
	</div>
	<div style="float:right;line-height:16px;width:8%;"><span>></span></div>
</div>
</div>
</div>

</header>
<div class="tab1" id="tab1">
	<banner>
		<div class="nav-list menu">
			<ul>
				<a class="active"><li class="cur active" id="one1" onclick="setTab('one',1)" style="border-right:1px solid #b5b5b5;">附近门店</li></a>
			    <a><li id="one2" onclick="setTab('one',2)" style="font-weight:bold;">常用门店</li></a>
			</ul>
		</div>
	</banner>


	<article>
		<div class="modebox  clearfix menudi">
			<div class="hot-list clearfix"  id="con_one_1">
				
				<c:forEach items="${backData.listData }" var="bl">
				<c:forEach items="${bl.shopList }" var="bs">
				<div class="mendian" onclick="location.href='${pageContext.request.contextPath}/store-${bs.id}.html?addid=${bl.id }'">
					<dl>
						<dd>
							<a href="">${bs.shop_name }</a>
							<span style="float:right;font-size:font-weight:normal;">  ${bl.dsitance }km</span>
						</dd>
						<dd class="f12">${bl.detAdd }</dd>
						<dd class="f12"">
							电话：${bs.memo }
						</dd>
					</dl>
				</div>
				</c:forEach>
				</c:forEach>
			</div>
		</div>
		
		
	</article>
	</div>
</body>
</html>

