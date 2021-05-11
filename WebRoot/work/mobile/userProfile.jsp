<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人资料</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta name="format-detection" content="telephone=no, email=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit"> <!-- 360浏览器指定默认极速模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"> <!-- 优先用Chrome渲染 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshopBase.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/vshop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/usersup.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/profile.css"></link>


</head>

<body>

<!-- 头部菜单 开始 -->
	<header class="m-topbarbox">
		<div class="m-topbar flexbox">
			<!-- 返回首页 -->
			<a class="m-goback" href="${pageContext.request.contextPath}/users/userIndex.html"><i class="iconfont">&#xe600;</i></a>
			
			<div class="m-tlebox flex" style="margin-left: -40px;">
				<div class="storeManage-menu">
					<span class="fixed cur">个人资料</span>
				</div>
			</div>
			
			
		</div>
		
	</header>
	
	<!-- 修改内容 -->
	<div class="ui-pageswitch ui-pageswitch-a" data-page="pa" >
		<div class="m-pagecont" style="overflow-x: hidden;top:0px;">
			<div class="m-editcard">
			<form  id="pro">
				<section class="fixed">
					<h3 class="m-fm-hd">头像上传</h3>
					<ul class="ui-form-list fixed">
						<li class="fm-item" style="padding: 5px 10px;">
							<label class="fm-tle" style="padding-top: 20px;">头像</label>
							<div class="fm-right" style="text-align: right;">
                   				<img src="${profile.headImg}" style="height: 60px;border-radius: 50%;">
								<a class="up_btn" href="javascript:void(0)">
								上传头像<input class="up_file" type="file" name="file" id="file">
								<input id="logoSrc" name="logoSrc" type="hidden" value="${profile.headImg}">
								</a>
							</div>
						</li>
					</ul>
				
					<h3 class="m-fm-hd">基本信息</h3>
					<ul class="ui-form-list fixed">
						<li class="fm-item">
		        			<div class="fm-cont">
		         				<label class="fm-tle">昵称</label>
		          				<div class="fm-right">
		            				<input type="text" id="name" name="nickName" class="ui-ipt" placeholder="请填写昵称" value="${ profile.nickName }">
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont">
		          				<label class="fm-tle">性别</label>
		          				<div class="fm-right">
	            					<select id="sex" name="sex">
	            						<option value="男" <c:if test="${ profile.sex=='男' }">selected</c:if>>男</option>
	            						<option value="女" <c:if test="${ profile.sex=='女' }">selected</c:if>>女</option>
	            					</select>
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont">
		          				<label class="fm-tle">生日</label>
		          				<div class="fm-right">
          							<input type="text" class="ui-ipt" placeholder="请选择生日日期" id="birthdate" name="birthdate" value="<fmt:formatDate value='${ profile.birthdate }'  type='both' pattern='yyyy-MM-dd'/>"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
          							
		          				</div>
		        			</div>
		      			</li>
		      			
		      			<li class="fm-item">
		        			<div class="fm-cont">
		          				<label class="fm-tle">手机</label>
		          				<div class="fm-right">
	            					<a class="fm-link" href="javascript:void(0)"><span>${ profile.tel }</span></a>
		          				</div>
		        			</div>
		      			</li>
					</ul>
					
					<h3 class="m-fm-hd">其他信息</h3>
					<ul class="ui-form-list fixed">
						<li class="fm-item">
		        			<div class="fm-cont">
		         				<label class="fm-tle">QQ</label>
		          				<div class="fm-right">
		            				<input type="text" class="ui-ipt" placeholder="请填写QQ号" id="qq" name="qq" onkeyup="this.value=this.value.replace(/\D/g,'')"  <c:if test="${ profile.qq!='null' }">value="${ profile.qq }" </c:if> >
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont">
		          				<label class="fm-tle">邮箱地址</label>
		          				<div class="fm-right">
          							<input type="text" class="ui-ipt" placeholder="请填写邮箱地址" id="mail" name="mail" <c:if test="${ profile.mail!='null' }">value="${ profile.mail }" </c:if>>
		          				</div>
		        			</div>
		      			</li>
		      			
		      		</ul>
		      		<h3 class="m-fm-hd"></h3>
					<ul class="ui-form-list fixed">
			      		<li class="fm-item">
				      		<div class="fm-cont">
				      			<a href="${pageContext.request.contextPath}/pass_jump.html">
							    	<label class="fm-tle">修改密码</label>
									<div class="fm-right">
										<span class="fr" style="margin-top: 15px;"><i class="iconfont">&#xe602;</i></span>
									</div>
						    	</a>
					    	</div>
			      		</li>
			      		
			      		<li class="fm-item">
				      		<div class="fm-cont">
				      			<a href="${pageContext.request.contextPath}/phone_jump.html">
						    		<label class="fm-tle">修改手机</label>
									<div class="fm-right">
										<span class="fr" style="margin-top: 15px;"><i class="iconfont">&#xe602;</i></span>
									</div>
						    	</a>
					    	</div>
			      		</li>
		      		</ul>
				</section>
				</form>
				
				<div class="pad-10 fixed mt-10 ">
			    	<a href="javascript:updateUsers()" class="btn btn-danger  btn-block">保存</a>
			    	<%--<a href="/nanny/user/userIndex.html" class="ui-btn-4 btn-back">返回</a>
			    --%></div>
			</div>
		</div>
	</div>
	<input type="hidden" id="id" name="id" value="${ profile.id }">
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="text/javascript">
    
     $(function () {
	 	 $("#file").on("change",function(){
	 	 	layer.load(1, { shade: [0.8,'#333']});
	 	 	var param={
	 	 		"sURL":"/nanny/upload/images.json",
	 	 		"sID":"file",
	 	 		"sContentType":"",
	 	 		"fnSuccess":function(data){
	 	 			$(".fm-right img").attr("src",data.path);
	 	 			layer.closeAll("loading");
	 	 			$("#logoSrc").val(data.path);
	 	 		},
	 	 		"fnError":function(){layer.closeAll("loading");layer.msg("上传失败,请联系管理员");}
	 	 	};
	 	 	new g_fnFileUpload(param);
	 	 });
	 });
    
	  function updateUsers()
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
	  	  
	  	  var q=$("#qq").val();
	  	  var ma=$("#mail").val();
	  	  var b=$("#birthdate").val();
		
	  	  var data= $("#pro").serialize();
		  $.ajax({
                type: "post",
                url: "/nanny/users/UpdateUser.html",
                data:data+"&id="+$("#id").val(),
                dataType: "text",
                success: function (msg) {
                    layer.closeAll("loading");
                	var data=eval('(' + msg + ')');
                    layer.msg("保存成功");
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
	  }
    </script>
</body>
</html>
