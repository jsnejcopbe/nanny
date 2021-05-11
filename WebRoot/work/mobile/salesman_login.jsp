<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="minimal-ui,width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>业务员登录</title>

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>

<style>
	.input-group-addon{
		border-radius:0;
		border-radius-left:4px;
	}
	.form-control{
		border-radius:0;
		border-radius-right:4px;
	}
	.input-group{
		width: 95%;
		padding-left: 20px;
		padding-bottom: 15px;
	}
	.form-group{
		width: 95%;
		padding-left: 20px;
	}
	.btn-submit{
		width: 70%;
		height:40px;
		border-radius:30px;
	}
	 
</style>

<body> 
	<section class="active" id="login" >
		<div class="row animated fadeILeftBig">
			<div class=" col-xs-12">
				<h2 class="page-header text-center text-primary">业务员登录</h2>
				<form id="saleman_login">
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-user"></i></span>
						<input class="form-control" name="tel" data-verify="phone" placeholder="请输入手机号" >
					</div> 
					<div class="input-group ">
						<span class="input-group-addon"><i class="fa fa-lock"></i></span>
						<input type="password" class="form-control" name="password" id="password" data-verify="required" placeholder="请输入密码" >
					</div>
					<div class="form-group">
							<input class="col-xs-6 form-control" data-verify="required" style="width: 73%;height: 34px;" id="verifycode" type="text"
								placeholder="请输入验证码" maxlength="4">
							<a id="reverifycode" href="javascript:changeCode()">
								<img id="m_captcha" class="pic_verification" title="点击刷新" src="/nanny/verifycode.html" height="34">
							</a>	
					</div>
					
					<div class="form-footer" style="text-align: center;">
						<!-- <label>
							<input class="hidden" id="input-checkbox" value="0" type="checkbox">
							<i class="fa fa-check-square-o input-checkbox fa-square-o"></i> 记住密码?
						</label>  -->
						<button type="submit" class="btn btn-info  btn-submit">登录</button>
					</div>
	
				</form>
			</div>
		</div>
	</section>

	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/verify.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/jquery.form.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	
	<script src="${pageContext.request.contextPath}/work/mobile/js/syl/salesman_login.js?v=0.01"></script>
</body>
</html>