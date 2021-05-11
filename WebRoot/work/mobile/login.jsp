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
<title>登录掌上保姆</title>
<meta name="viewport"
	content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<meta name="msapplication-tap-highlight" content="no">
<meta name="tp_page" content="5123.0">

<link rel="apple-touch-icon-precomposed"
	href="https://passport.yhd.com/front-passport/passport/images/screenLogo.png" />
<link type="text/css" rel="stylesheet" href="work/mobile/css/style-m-2.0.css" />
<link type="text/css" rel="stylesheet" href="work/mobile/css/login-m-2.0.css" />
<link type="text/css" rel="stylesheet" href="work/mobile/css/popup.css" />
<script type="text/javascript" src="/nanny/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript" src="work/mobile/js/H5ToNative.min.js"></script>

<script type="text/javascript" src="/nanny/js/layer/layer.js"></script>
<script type="text/javascript" src="/nanny/js/base.js"></script>
<script type="text/javascript">
	var URLPrefix = {
		"mymall" : "http://my.1mall.com",
		"passport_statics" : "https://passport.yhd.com/front-passport/passport",
		"yiwangauth" : "http://mall.yiwang.cn",
		"yaowang" : "http://www.111.com.cn",
		"tracker" : "tracker.yhd.com",
		"passport" : "https://passport.yhd.com",
		"my" : "http://my.yhd.com",
		"central" : "http://www.yhd.com",
		"validCodeShowUrl" : "https://captcha.yhd.com/public/validcode.do",
		"mall" : "http://www.1mall.com",
		"passportother" : "https://passport.1mall.com"
	};
</script>
<script>
	var LOGIN_RESULT = {
		SUCCESS : 0,
		FAIL : 1
	};
	var REGISTER_RESULT = {
		SUCCESS : 10,
		FAIL : 11
	};
	var DOMAIN_TYPE = {
		YHD : 1,
		MALL : 2,
		YW_111 : 3
	};
	var LOGIN_SOURCE = {
		NORMAL : 1,
		FRAME : 2
	};
	var URLPrefix = {
		"mymall" : "http://my.1mall.com",
		"passport_statics" : "https://passport.yhd.com/front-passport/passport",
		"yiwangauth" : "http://mall.yiwang.cn",
		"yaowang" : "http://www.111.com.cn",
		"tracker" : "tracker.yhd.com",
		"passport" : "https://passport.yhd.com",
		"my" : "http://my.yhd.com",
		"central" : "http://www.yhd.com",
		"validCodeShowUrl" : "https://captcha.yhd.com/public/validcode.do",
		"mall" : "http://www.1mall.com",
		"passportother" : "https://passport.1mall.com"
	};
	var currSiteId = 1;

	var returnUrl = "http://m.yhd.com/myH5/h5Index/index.do?tc=ad.0.0.16201-23541783.1&tp=5006.0.939.0.3.L64LxqQ-10-EnTAs";
	var autoLoginFlag = "1";
	var valid_code_service_flag = "1";
	var showValidCode = "0";
	var mUrl = "http://m.yhd.com";

	var no3wUrl = "yhd.com";
	var imgPath = "https://passport.yhd.com/front-passport/passport/images";
	var fromDomain = "";
	var resetIframeUrl = fromDomain + "/login/callback.do";

	var yhdUrl = "http://www.yhd.com";
	var yhdPassportUrl = "https://passport.yhd.com";
	var ywPassportUrl = "https://passport.111.com.cn";
	var pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXQG8rnxhslm+2f7Epu3bB0inrnCaTHhUQCYE+2X+qWQgcpn+Hvwyks3A67mvkIcyvV0ED3HFDf+ANoMWV1Ex56dKqOmSUmjrk7s5cjQeiIsxX7Q3hSzO61/kLpKNH+NE6iAPpm96Fg15rCjbm+5rR96DhLNG7zt2JgOd2o1wXkQIDAQAB";
</script>
</head>

<body>

	<div id="container">
		<div class="touchweb-com_header ">

			<!-- left start -->
			<a id="back" href="javascript: history.back(-1);" class="left na-icon-back"></a>
			<!-- left end -->



			<!-- title start -->
			<h1>登录</h1>
			<!-- title end -->

			<!-- right start -->
			<!-- activeClass:js的相关class(screening筛选的jsclass); class:普通class(按钮:rbtn_box；省略号:icon-more) -->
			<div class="rightBox">

				<a id="register_btn" href="${pageContext.request.contextPath}/register.html"
					class="right rbtn"> 注册 </a>

			</div>
			<!-- 下拉 start -->

			<!-- 下拉 end -->
			<!-- right end -->

		</div>

		<div class="touchweb_page-login">
			<!--
	<div id="error_tips" class="error_tips" style="display: block;">
            <span class="icon-warning icon_warning"></span>
            提示信息
    </div>
    -->

			<div class="login_box">
				<form id="userform">
					<div class="form_item">
						<label class="na-icon-my" for="touchweb_form-username"></label>
						<div class="input_box">
							<input type="text" id="touchweb_form-username" name="nickName"
								placeholder="邮箱/手机/用户名" maxlength="11"> <span
								class="icon_delete na-icon-delete"></span>
						</div>
					</div>

					<div class="form_item">
						<label class="na-icon-password" for="touchweb_form-password"></label>
						<div class="input_box">
							<input type="password" id="touchweb_form-password"
								name="password" placeholder="请输入密码"> <span
								class="icon_delete na-icon-delete"></span>
						</div>
					</div>
					<div class="form_item">
						<label class="na-icon-pencil" for="touchweb_form-password"></label>
						
							<input class="input_box" name="verifycode" id="verifycode" type="text"
								placeholder="请输入验证码" maxlength="4"> 
							<a id="reverifycode" href="javascript:changeCode()">
								<img id="m_captcha" class="pic_verification" alt=""
								src="/nanny/verifycode.html" height="43">
							</a>	
						</div>


					</div>


					<div class="remember_login">
					
						<label>
	                  		<input id="checkbox" name="autologin"  type="checkbox"> 两周内记住登录
                		</label>
						 <a href="${pageContext.request.contextPath}/fgpass_jump.html"   class="forgot_password">忘记密码？</a>
					</div>

					<div class="agreement">
						<label>点击登录，表示您同意<a href="javascript: void(0);"
							class="license-terms">《服务协议》</a> </label>
					</div>

					<div class="login_btn">
						<a id="login-btn" href="javascript:upFrom()" class="btn">登录</a>
					</div>
				</form>


			</div>

		</div>


		<script type="text/javascript" src='work/mobile/js/api.js'></script>
		<script type="text/javascript" src='work/mobile/js/login.js'></script>
</body>
</html>
