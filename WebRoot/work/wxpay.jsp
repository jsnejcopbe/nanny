<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/> 
    <title>微信支付</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
	//调用微信JS api 支付
	function jsApiCall()
	{
		WeixinJSBridge.invoke(
			'getBrandWCPayRequest',
			${jsApiParameters},
			function(res){
			   staDefine(res);
			   //callBack();
			   //if(res.err_msg=='get_brand_wcpay_request:ok')
			   	//	location.href=$("#sendPath").val();
			 //  else
				//   location.href="${pageContext.request.contextPath}/index.html";
			}
		);
	}

	function callpay()
	{
		if(isWeiXin() == false){
			alert('请在微信客户端打开');
			location.href="${pageContext.request.contextPath}/index.html";
			return;
		}
		
		if (typeof WeixinJSBridge == "undefined"){
		    if( document.addEventListener ){
		        document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
		    }else if (document.attachEvent){
		        document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
		        document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
		    }
		    
		}else{
		    jsApiCall();
		}
	}
	
	function callBack(){
		$.ajax({
			type : "POST",
			url  : "${pageContext.request.contextPath}/callbackfun.json",
			data : "product_id=${orderCode }",
			success : function(rtnData){
				var data=eval('(' + rtnData + ')');
				alert(data.msg);
				if(data.hasOwnProperty("path"))
					location.href = data.path;
				else
					location.href="${pageContext.request.contextPath}/index.html";
			},error: function(){
				alert("订单确认失败，请联系管理员");
			}
		});
	}
	
	function isWeiXin(){
			var ua = window.navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i) == 'micromessenger'){
			return true;
			}else{
			return false;
			}
		} 
	
	function staDefine(res){
		switch(res.err_msg) {
        case 'get_brand_wcpay_request:cancel':
            layer.msg('用户取消支付！');
            location.href="${pageContext.request.contextPath}/index.html";
            break;
        case 'get_brand_wcpay_request:fail':
        	layer.msg('支付失败！（'+res.err_desc+'）');
            location.href="${pageContext.request.contextPath}/index.html";
            break;
        case 'get_brand_wcpay_request:ok':
        	layer.msg('正在自动付款...请千万不要关闭页面');
        	setTimeout("location.href='${pageContext.request.contextPath}/users/userOder.html'", 3000);
        	/* if($("#sendPath").val()!="")
            	location.href=$("#sendPath").val();
        	else{
        		layer.msg("自动付款失败，请您手动付款");
        		location.href="${pageContext.request.contextPath}/users/userOder.html";
        	} */
            break;
        default:
        	layer.msg(JSON.stringify(res));
       	    location.href="${pageContext.request.contextPath}/index.html";
            break;
    }
	}
	</script>

</head>
<body>
<c:if test="${call_back_url!=null }">
<input id="sendPath" type="hidden" value="${call_back_url }">
</c:if>
<c:if test="${call_back_url==null }">
<input id="sendPath" type="hidden" value="${pageContext.request.contextPath}/index.html">
</c:if>
<script type="text/javascript">
var error="${error}";
$(function(){
	if(error!=""){
		layer.msg("身份信息过期");	
		location.href="${pageContext.request.contextPath}/index.html";
	}else{
		layer.load(1, {shade: [0.8,'#333']});
		layer.msg("正在拉起支付，请稍后....");
		callpay();
	}
});
</script>

</body>
</html>
