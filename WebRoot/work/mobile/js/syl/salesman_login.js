$(function(){
	basis();
	login();
})

// basis 
function basis(){
	// 取消奇怪虚线
	$("a,img").focus(function(){
		$(this).blur();
	})
	// checkbox切换
	$(".input-checkbox").click(function(){
		$(this).toggleClass("fa-check-square fa-square");
	})
	
	$("#m_captcha").click(function(){
		codef5();
	}) 
}

function codef5(){
	$("#m_captcha").attr("src","/nanny/verifycode.html?"+Math.random());
}

function login(){
	var msgtime = 2000;
	$("#saleman_login").submit(function(){
		var b = $(this).verifys({msg:["登录手机号码格式有误","登录密码不能为空","验证码不能为空"],time:msgtime})
		if(!b)return false;
		$(this).formSubmit({
			url:path+"salesman/login.html",
			data:{bs:JSON.stringify($(this).serializeJson()),verifycode:$("#verifycode").val()},
			dataType:'',
			success:function(msg){
				if(msg){
					codef5();
					return layer.msg(msg);
				}
				layer.msg("登录成功",{icon:1});
				window.location.href = path+"salesman/index.html";
			}
		})
		return false;
	})
	
	$("#verifycode").click(function(){
		/*var $this = this;
		$ajax({
			url:path+"salesman/login.html",
			data:{verifycode:$(this).val()},
			done:function(msg){
				if(msg == "验证码错误"){
					layer.tips("验证码错误", $this, {
					    tips: [3, "#3595CC"],
					    time: msgtime
					});
					codef5();
				}
			}
		},1)*/
		codef5();
	});
}

function $ajax(option,type){
	var defVal = {
		async:false,
		url:'',
		data:'',
		dataType:'json',
		done:function(json){
			
		}
	}
	var obj = $.extend({},defVal,option);
	obj.dataType = type=== undefined ? "json" : '';
	$.ajax({
		async:obj.async,
		type:'post',
		url:obj.url,
		data:obj.data,
		dataType:obj.dataType
	}).done(obj.done)
	.error(function(msg){
		layer.msg("网络异常");
	})
}