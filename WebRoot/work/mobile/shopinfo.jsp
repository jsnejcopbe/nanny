<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户资料</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta name="format-detection" content="telephone=no, email=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">
<!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<!-- 优先用Chrome渲染 -->

<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usersup.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/profile.css"></link>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timepicki.css"></link>
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var shopID = ${loginUser.shopID };
</script>

<style>
a, a:HOVER, a:ACTIVE, a:VISITED {
	text-decoration: none;
	cursor: pointer;
}

.input-group-addon{
	cursor: pointer;
}

.blue {
	color: #3071A9;
}

#shop_img {
	width: 60px;
	height: 60px;
	border-radius: 50%;
	border: 3px #428BCA solid;
}

.times_remove {
	margin-top: 6px;
}

.li_cont {
	width: 44%;
	float: left;
}

.li_cont>label {
	font-size: 16px;
	padding: 10px 5px;
	display: inline-block;
	line-height: 1.3;
	font-size: 1.5rem;
}

.input-group-addon,.form-control{
	border-radius: 0px;
}

p {
	width: 80%;
	display: inline-block;
	padding: 15px;
}
.pps{
	width:auto;
	display: block;
}
</style>
<body>
	<div id="hide_search" style="display: none;">
		<div class="list-group demo-list-group list">
			<div class="input-group">
				<input class="form-control area_search" type="text" placeholder="请输入地址">
				<span class="input-group-addon"><i class="fa fa-search fa-lg"></i></span>
			</div>
		</div>
	</div>

	<div id="hide_time_select" style="display: none;">
		<div style="display: block; top: 0px; left: 0px;" class="timepicker_wrap">
			<div class="time">
				<div class="prev_t action-prev"></div>
				<div class="ti_tx">
					<input class="timepicki-input time_hour" value="16" type="text">
				</div>
				<div class="next_t action-next"></div>
			</div>
			<div class="mins">
				<div class="prev_t action-prev"></div>
				<div class="mi_tx">
					<input class="timepicki-input time_min" type="text">
				</div>
				<div class="next_t action-next"></div>
			</div>
		</div>
	</div>

	<header class="m-topbarbox">
	<div class="m-topbar flexbox">
		<!-- 返回首页 -->
		<a class="m-goback" href="${pageContext.request.contextPath}/shop/shopIndex.html"><i class="iconfont">&#xe600;</i></a>

		<div class="m-tlebox flex" style="margin-left: -40px;">
			<div class="storeManage-menu">
				<span class="fixed cur">商户资料</span>
			</div>
		</div>
	</div>
	</header>

	<!-- 修改内容 -->
	<div class="ui-pageswitch ui-pageswitch-a" data-page="pa">
		<div class="m-pagecont" style="overflow-x: hidden; top: 0px;">
			<div class="m-editcard">
				<section class="fixed">
				<h3 class="m-fm-hd">商标上传</h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item" style="padding: 5px 10px;"><label class="fm-tle" style="padding-top: 20px;">商标</label>
						<div class="fm-right" style="text-align: right;">
							<label class="" for="head_file"><img id="shop_img" src="${pageContext.request.contextPath}/work/pc/images/defalut.jpg"></img></label>
							<input type="file" id="head_file" name="file" accept="image/jpeg" style="position: absolute; clip: rect(0, 0, 0, 0);">
						</div></li>
				</ul>

				<h3 class="m-fm-hd">基本信息</h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">店铺名</label>
							<div id="shopName" class="fm-right">
								<p></p>
							</div>
						</div>
					</li>

					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle" style="width: 40px;">地址</label>
							<div id="shopArea" class="fm-right">
								<p style="width: 100%"></p>
							</div>
						</div>
					</li>
	
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">店铺公告</label>
							<div id="shopNotice" class="fm-right">
								<p style="min-height: 100px;height: auto;width: 98%"></p>
							</div>
						</div>
					</li>

					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">店铺电话</label>
							<div id="shopTel" class="fm-right">
								<p></p>
								<%--<a href="javascript:edit_tel();" class="blue  pull-right" style="margin-top: 15px;">编辑</a>--%>
								</div>
						</div>
					</li>
					
	
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">店铺二维码</label>
							<div class="fm-right">
								<label class="fm-tle"> <img id="two_code" width="120px" height="120px" src="#" />
								</label>
							</div>
						</div>
					</li>
					
					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">配送费设置</label>
							<div class="fm-right">
								<input type="text" class="ui-ipt price" name="delivery_price" placeholder="请填写配送费" />
								<input type="hidden"/>
							</div>
						</div>
					</li>

					<li class="fm-item">
						<div class="fm-cont">
							<label class="fm-tle">起送价设置</label>
							<div class="fm-right" style="display: none;">
								<input type="text" class="ui-ipt price" name="min_send_price" placeholder="请填写起送价" />
							</div>
							<div class="pull-right" style="margin-top: 13px;">
								<a style="color: #3071A9;" href="${pageContext.request.contextPath}/shop/dispatchedit.html">修改起送价</a>
							</div>
						</div>
					</li>
				</ul>

				<h3 class="m-fm-hd">营业时间设置</h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item" id="times">
						<div class="fm-cont" id="time">
							<div class="li_cont">
								<label class="">起始时间</label> <a class="blue startTime">请选择</a>
							</div>
							<div class="li_cont">
								<label class="">终止时间</label> <a class="blue endTime">请选择</a>
							</div>
							<a class="times_remove btn btn-danger"><i class="fa fa-trash-o"></i></a>
						</div>

						<div id="time_btn" style="text-align: center;">
							<a class="times_add blue"><i class="fa fa-plus fa-lg"></i></a>
						</div>
					</li>
				</ul>
				
				<h3 class="m-fm-hd">安全设置</h3>
				<ul class="ui-form-list fixed">
					<li class="fm-item" id="">
						<div class="fm-cont">
							<a href="${pageContext.request.contextPath}/shop/etitshoptel.html">
							<label class="fm-tle">修改店铺电话</label>
							<div class="fm-right">
								<span class="fr" style="margin-top: 15px;"><i class="iconfont">&#xe602;</i></span>
							</div>
							</a>
						</div>
					</li>
					<li class="fm-item" id="">
						<div class="fm-cont">
							<a href="${pageContext.request.contextPath}/pass_jump.html">
							<label class="fm-tle">修改密码</label>
							<div class="fm-right">
								<span class="fr" style="margin-top: 15px;"><i class="iconfont">&#xe602;</i></span>
							</div>
							</a>
						</div>
					</li>
				</ul>
				</section>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/syl/basis.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/work/mobile/js/syl/shopinfo.js?v=0.01"></script>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/mytimepicki.js"></script> --%>
</body>
</html>