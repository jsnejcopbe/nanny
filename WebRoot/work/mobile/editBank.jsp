<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>绑定银行卡</title>
    
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
			<c:if test="${sid=='null'}">
			<a class="m-goback" href="${pageContext.request.contextPath}/users/transfer.html"><i class="iconfont">&#xe600;</i></a>
			</c:if>
			<c:if test="${sid!='null'}">
			<a class="m-goback" href="${pageContext.request.contextPath}/shop/transfer.html"><i class="iconfont">&#xe600;</i></a>
			</c:if>
			<div class="m-tlebox flex">
				<div class="storeManage-menu">
					<span class="fixed cur">提现账户</span>
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
					<h3 class="m-fm-hd"></h3>
					<c:forEach items="${info}" var="info">
					<ul class="ui-form-list fixed">
						<li class="fm-item">
		        			<div class="fm-cont">
		         				<label class="fm-tle">银行</label>
		          				<div class="fm-right">
		          				    <select id="sbank" name="bank" placeholder="请选择银行">
		          				    <c:forEach items="${list}" var="bank">
		          				    		<option value="${bank.id}">
		          				    		
		          				    		${bank.bankName}</option>
		          				    		</c:forEach>
		          				    </select>
		            				<input type="hidden" value="${info.bankID}" id="bankid" name="bankid">
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont">
		          				<label class="fm-tle">银行卡卡号</label>
		          				<div class="fm-right">
	            					<input type="text" class="ui-ipt" placeholder="填写银行卡卡号" id="bankcard" name="bankcard" value="${info.account}" onkeyup="this.value=this.value.replace(/\D/g,'')">
          							
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
		        			<div class="fm-cont">
		          				<label class="fm-tle">姓名</label>
		          				<div class="fm-right">
          							<input type="text" class="ui-ipt" placeholder="请写姓名" id="uname" name="uname" value="${info.accName}">
          							
		          				</div>
		        			</div>
		      			</li>
		      			<li class="fm-item">
							<div class="fm-cont">
								<label class="fm-tle">验证码</label>
								<div class="fm-right">
									<input type="text" class="ui-ipt " name="mocode" id="mocode" placeholder="请填写验证码" style="width: 120px;"/>
									<button type="button" class="btn btn-danger " id="yzm" onclick="codeChek()"> 获取验证码</button>
								</div>
								<div class="fm-left">
								</div>
							</div>
						</li>
		      		</ul>
					</c:forEach>
					<%--<h3 class="m-fm-hd">其他信息</h3>
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
				--%></section>
				</form>
				<div class="pad-10 fixed mt-10 ">
			    	<a href="javascript:updbankcard()" class="ui-btn-4">保存</a>
			    	<%--<a href="/nanny/user/userIndex.html" class="ui-btn-4 btn-back">返回</a>
			    --%></div>
			</div>
		</div>
	</div>
	
	<!-- 载入js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="text/javascript">
     var pxx=$("#bankid").val();
    //$("#sbank").find("option[value='"+pxx+"']").attr("selected",true);
   var wait=60;
    $("#sbank").val(pxx);
    var shopid="${sid}";
	  function updbankcard()
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
	  	  
	  	  var bank=$("#bank").val();
	  	  var bankcard=$("#bankcard").val();
	  	  var uname=$("#uname").val();
		 var mocode=$("#mocode").val();
	  	  var data= $("#pro").serialize();
		  $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/users/updbank.html",
                data:data,
                dataType: "text",
                success: function (msg) {
                var data=eval('(' + msg + ')');
                	if(data.msg=="0"){
                		layer.closeAll('loading');
                		layer.msg("已有绑定银行卡，请勿重新绑定");
                	}else if(data.msg=="1"){
                		
                		layer.closeAll('loading');
                		layer.msg("验证码错误");
                		
                	}else{
		                    layer.msg("修改成功");
	                    setTimeout(function(){
	                    	if (shopid=="null")
	     					location.href="${pageContext.request.contextPath}/users/transfer.html";
	     				else
	     					location.href="${pageContext.request.contextPath}/shop/transfer.html";
	                    }, 2000);
                	}
                	
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
	  }
	  
	  
	    /**
	 * 发验证码
	 */
	function codeChek(){
		if (wait == 0) {  
			$("#yzm").removeClass("disabled");            
			$("#yzm").text("获取验证码");  
	        wait = 60;  
	    } 
		else if(wait == 60){
			$("#yzm").addClass("disabled", true);  
	    	$("#yzm").text("重新发送(" + wait + ")"); 
	    	wait--;
	    	var param={
	    			"sURL":"/nanny/obtainCode.html",
	    			"fnSuccess":function(rtnData){
	    				layer.msg("请等待,验证码会发送到您的手机");
	    			},
	    			"fnError":function(){
	    				layer.msg('发送失败,请联系管理员');
	    			}
	    		};
	    	new g_fnAjaxUpload(param);
	    	setTimeout(function() {  
	        	codeChek();  
	        },  
	        1000); 
		} 
		else{ 
			$("#yzm").text("重新发送(" + wait + ")"); 
	        wait--;  
	        setTimeout(function() {  
	        	codeChek();  
	        },  
	        1000);  
	    }
	}
    </script>
</body>
</html>