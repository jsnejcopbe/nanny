<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title>注册掌上保姆</title>
<meta name="format-detection" content="telephone=no">
<meta name="viewport"
	content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="tp_page" content="5124.0">

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/work/mobile/css/register-m-2.0.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/public-m-2.0.css">

<style type="text/css">
.head-con{
	text-align: center;
	display: block!important;
    margin-bottom: 10px!important;
    padding-top: 10px;
}
.head-con .head-img{
	 height: 50px;
	 width: 50px;
     border-radius: 500px;
     border: 1px solid #ddd;
     display: inline-block;
}
</style>
</head>

<body>
	<div id="container">
		<div class="touchweb-com_header ">

			<!-- left start -->
			<a id="nav_back" href="javascript: history.back(-1);" class="left na-icon-back"></a>
			<!-- left end -->



			<!-- title start -->
			<h1>注册掌上保姆</h1>
			<!-- title end -->


			<!-- right start -->
			<!-- activeClass:js的相关class(screening筛选的jsclass); class:普通class(按钮:rbtn_box；省略号:icon-more) -->
			<div class="rightBox">

				<a id="login_btn" href="${pageContext.request.contextPath}/login.html"
					class="right rbtn"> 登录 </a>

			</div>
			<!-- 下拉 start -->

			<!-- 下拉 end -->
			<!-- right end -->

		</div>
		<div class="touchweb_page-register">
			<!--
    <div class="tab_box" style="">
        <div class="item current">手机注册</div>
        <div class="item">邮箱注册</div>
    </div>
    -->
			<input id="returnUrl" type="hidden" name="returnUrl" value="">
			<div class="con_box show">


				<form id="userregform" class="demoform">
					<div class="step_1">
						<div class="form_box">
							<div class="form_item head-con">
								<img class="head-img" src="${userInfo.headimgurl }">
								<input type="hidden" value="${userInfo.headimgurl }" name="hedImg">
							</div>
							<div class="form_item">
								<label for="register_form-phone" class="na-icon-cell_phone"></label>
								<div class="input_box">
									<input class="text_box" name="nickname"
										type="text" placeholder="请输入昵称" value="${userInfo.nickname }">
								</div>
							</div>
							<div class="form_item">
								<label for="register_form-phone" class="na-icon-cell_phone"></label>
								<div class="input_box">
									<input id="register_form-phone" class="text_box" name="tel"
										type="tel" errormsg="手机号码格式不正确！" placeholder="请输入手机号码"
										maxlength="11">
								</div>
							</div>
							<div class="form_item">
								<label for="register_form-phone-pwd" class="na-icon-password"></label>
								<div class="input_box">
									<input id="register_form-phone-pwd" class="text_pwd"
										name="password" type="password" placeholder="6-20位字母，数字或符号组合"
										maxlength="20">
								</div>
							</div>
							<div class="form_item">
								<label for="register_form-phone-pwd" class="na-icon-password"></label>
								<div class="input_box">
									<input id="register_form-phone-repwd" class="text_pwd"
										name="repassword" type="password" placeholder="确认密码"
										maxlength="20">
								</div>
							</div>
							
							<input name="recShopID" type="hidden" value="${recShopID }">

						</div>
						<div class="error_tips" style="display: none;">
							<span class="na-icon-warning icon_warning"></span> 格式错误，请输入正确的手机号码
						</div>
						<!--
            <div class="agreement">
                <input id="register_form-phone-checkbox" type="checkbox" checked>
                <label for="register_form-phone-checkbox">同意<a href="javascript: void(0);" class="license-terms">《1号店协议》</a></label>
            </div>
            -->
						<div class="agreement">
							<label>点击注册，表示您同意掌上保姆<a href="javascript: void(0);"
								class="license-terms">《服务协议》</a>
							</label>
						</div>
						<div class="btn_box">
							<a href="javascript:upFromreg()"
							style="background-color: #FF3C3C;"	class="btn phone_reg_btn disable">注册</a>
						</div>
					</div>
					<!-- step_1 -->
				</form>

		
                   
            
     
			</div>
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
			<script type="text/javascript" src='${pageContext.request.contextPath}/work/mobile/js/register.js'></script>
			
		</div>
</body>
</html>
