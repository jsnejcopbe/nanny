// JavaScript Document
path = "http://mp.soqi.cn";
wap = "http://m.soqi.cn";
twoCodepath = "http://two.soqi.cn";
var pic = "http://pic.soqi.cn";
var vd_bak = "http://mp.soqi.cn";
var domain = ".soqi.cn";
var statics="http://7xiobb.com2.z0.glb.clouddn.com";
var portraitPath="http://7xitth.com2.z0.glb.qiniucdn.com";
var background="http://7xixdl.com2.z0.glb.qiniucdn.com";
var mpqrcode="http://7xjoax.com2.z0.glb.qiniucdn.com";
var contentqr="http://7xk39h.com2.z0.glb.qiniucdn.com";
var qqqr = "http://7xkt0q.com2.z0.glb.qiniucdn.com"
var weixinqr = "http://7xkt0p.com2.z0.glb.qiniucdn.com"
var weiboqr = "http://7xkt0r.com2.z0.glb.qiniucdn.com"
var minipageimgs = "http://7xliyi.com2.z0.glb.qiniucdn.com"
var mpmusic="http://7xonwm.com2.z0.glb.qiniucdn.com";
var vdimg="http://7xofwb.com2.z0.glb.qiniucdn.com";

//设置返回按钮链接
$(document).ready(function(){
	$('body').on('click','#j-m-moremenu',function(){
		$('.m-menubox').toggleClass('active');
		if(!($('.m-menuboxbg').length > 0)){
			if($('[data-page="pa"]').length > 0){ 
				$('[data-page="pa"]').append("<div class='m-menuboxbg'></div>");
				$('[data-page="pb"]').append("<div class='m-menuboxbg'></div>");
			}else{ 
				$('body').append("<div class='m-menuboxbg'></div>");
			}
		};
		$('.m-menuboxbg').toggleClass('active');
		//获取消息
//		$.ajax({
//				url : path,
//				type : 'POST',
//                dataType:'json',
//				async : true,
//				cache : false,
//				success : function(msg){
//					if(msg.msg > 0){
//						$('.badge').html(msg.msg);
//					}else{
//						$('.badge').hide();
//					}
//				}
//			});
	});
	$('body').on({
			'touchmove':function() {
				$('.m-menuboxbg').remove();
				$(".m-menubox").removeClass('active');
			},
			'touchstart':function(e) {
				event.preventDefault();
				$('.m-menuboxbg').remove();
				$(".m-menubox").removeClass('active');
			},
			'touchstart':function(e) {
				event.preventDefault();
				$('.m-menuboxbg').remove();
				$(".m-menubox").removeClass('active');
			}
	},'.m-menuboxbg');
	
	//topbar更多标签
	$('#j-m-moremenu-vd').bind({'click':function(){
		$('.m-menubox').toggleClass('active');
		if(!($('.m-menuboxbg').length > 0)){
			$('.ui-pageswitch').append("<div class='m-menuboxbg'></div>");
		};
		$('.m-menuboxbg').toggleClass('active');
		setTimeout(function(){$('.m-menubox').removeClass('active');},3000);//登录/已登录框自动关闭
	}
	});
	
	//判断浏览器
	getOs = function(){
	   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
			return "Firefox";  
	   }else if(isSafari=navigator.userAgent.indexOf("Safari")>0) {  
			return "Safari";  
	   }else if(isCamino=navigator.userAgent.indexOf("Camino")>0){  
			return "Camino";  
	   }else if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){  
			return "Gecko";  
	   }else if(navigator.userAgent.indexOf("MSIE")>0 || navigator.userAgent.indexOf("rv")>0){ 
			return "MSIE";  
	   }
	}
	
	//添加时间函数
	SetCookie = function(name, value){
		var Days = 30;
	   	var exp = new Date();
	   	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	   	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/;domain="+domain;
	}
	
	//获取时间函数
	GetCookie = function(name){
	    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	    if (arr != null) return unescape(arr[2]); return null;
	}
	
	//删除时间函数
	DelCookie = function(name){
	    var exp = new Date();
	    exp.setTime(exp.getTime() - 1);
	    var cval = GetCookie(name);
	    if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/;domain="+domain;
	}
	
	if(getOs()== "MSIE"){
		window.location.href = "/imgDeal/downbrowser.jsp";
	}
});

var Popup = {
	/**
	 * 普通弹出 3秒提示
	 * @param {Object} content
	 */
	noticeTis : function(content){
		var id = parseInt(Math.random() * 100);
		var html = '';
		html += '<style type="text/css">@-webkit-keyframes fadeInOut { 0% { opacity: 0;} 10% { opacity: 1;} 90% { opacity: 1; -webkit-transform: translateY(0px);} 99% { opacity: 0; -webkit-transform: translateY(-30px);} 100% { opacity: 0; }		}	</style>';
		html += '<div class="ui-toast" style="position: absolute; z-index: 10003;-webkit-animation: fadeInOut 3s linear forwards;  text-align: center; text-align: center; top: 70%;  width: 100%; left: 0; display: block; font-size: 1.6rem; line-height: 1.2;" id="j-toast-default'+id+'">';
		html += '<div class="toast-cont" style=" background: rgba(0,0,0,0.8); border: solid 1px #000;-webkit-animation: fadeInOut 3s linear forwards; border-radius: 4px; color: #fff; display: inline-block; padding: 10px 20px; -webkit-box-shadow:0 0 12px rgba(0,0,0,.3); box-shadow:0 0 12px rgba(0,0,0,.3); text-align: left; max-width: 90%;">默认的Toast通知</div>';
		html += '</div>';
		$('body').append(html);
	   	$("#j-toast-default"+id).children().html(content);
	    setTimeout(function(){$("#j-toast-default"+id).remove();},3000);
	},
	/**
	 * 弹出但确定,取消按钮的提示
	 * @param {Object} content 设置弹出提示框的内容
	 * @param {Object} confirm 设置点击按钮的触发函数
	 * @param {Object} data  调用confirm时带过去的参数，confirm(data);
	 */
	confirm : function(content,confirm,data){
		var id = parseInt(Math.random() * 1000);
		var html = '';
		html += '<div class="" style="box-shadow: 0 0 10px rgba(0,0,0,0.2);z-index: 501; border-radius: 8px; background: #f8f8f8; width: 80%;position: fixed; top: 50%; margin-top: -75px;  left: 50%;  margin-left: -40%;" id="j-toast-default'+id+'">';
		html += '<div class="toast-cont'+id+'" style="line-height: 1.8;color:#4f4f4f;box-sizing:border-box;width:100%;display: inline-block; padding: 20px 20px;text-align: center;">默认的Toast通知</div>';
		html += '<div style="border-top: 1px solid #e1e1e1;"><input type="button" style="outline:0;width:50%;height:40px;color:#0079ff;text-align: center;padding: 12px 0;font-size: 16px;border-right:1px solid #e1e1e1;border-top:0;border-bottom:0;border-left:0;border-radius:8px 0 0 8px;background-color:#fff;" id="cancel'+id+'" value="取消"><input type="button" id="confirm'+id+'" value="确定" style="outline:0;width:50%;height:40px;color:#0079ff;text-align: center;padding: 12px 0;font-size: 16px;border:none;border-radius:8px;background-color:#fff;"></div></div>'; 
		html += '</div>';
		html += '<div id="ShieldingLayer'+id+'" style="width:100%;height:100%;background: rgba(21, 20, 20, 0.3);z-index: 500;position: fixed;"></div>';
		$('body').append(html);
	   	$("#j-toast-default"+id+" .toast-cont"+id).html(content);
	   	$('#cancel'+id).on('click',function(){
	   		if(typeof cancel != undefined){
	   			$("#j-toast-default"+id).remove();
				$("#ShieldingLayer"+id).remove();
	   		}
	   	});
	   	$('#confirm'+id).on('click',function(){
	   		if(typeof cancel != undefined){
	   			confirm(data);
	   			$("#j-toast-default"+id).remove();
				$("#ShieldingLayer"+id).remove();
	   		}
	   	});
	},
	/**
	 * 弹出带确定按钮的长时间的对话框提示
	 * @param {Object} content 设置弹出提示框的内容
	 */
	longtimetips : function(content){
		var id = parseInt(Math.random() * 1000);
		var html = '';
		html += '<div class="" style="box-shadow: 0 0 10px rgba(0,0,0,0.2);z-index: 501; border-radius: 8px; background: #f8f8f8; width: 80%;position: fixed; top: 50%; margin-top: -75px;  left: 50%;  margin-left: -40%;" id="j-toast-default'+id+'">';
		html += '<div class="toast-cont'+id+'" style="line-height: 1.8;color:#4f4f4f;box-sizing:border-box;width:100%;  display: inline-block; padding: 20px 20px;text-align: center;">默认的Toast通知</div>';
		html += '<div style="border-top: 1px solid #e1e1e1;"><input type="button" style="outline:0;width:100%;height:40px;color:#0079ff;text-align: center;padding: 12px 0;font-size: 16px;border-right:1px solid #e1e1e1;border-top:0;border-bottom:0;border-left:0;border-radius:8px;background-color:#fff;" id="cancel'+id+'" value="确定"></div></div>'; 
		html += '</div>';
		html += '<div id="ShieldingLayer'+id+'" style="width:100%;height:100%;background: rgba(21, 20, 20, 0.3);z-index: 500;position: fixed;"></div>';
		$('body').append(html);
	   	$("#j-toast-default"+id+" .toast-cont"+id).html(content);
	   	$('#cancel'+id).on('click',function(){
	   		if(typeof cancel != undefined){
	   			$("#j-toast-default"+id).remove();
				$("#ShieldingLayer"+id).remove();
	   		}
	   	});
	},
	/**
	 * 弹出全屏覆盖的提示 注意：提示内容在屏幕中央
	 * @param {Object} content   内容
	 * @param {Object} fun		超时回调函数
	 * @param  outTime 	超时时间 设置0或者小于零时超时无效
	 * @return 返回当前DIV节点
	 */
	noticeFullScreen : function(content,fun,outTime){
			var id = parseInt(Math.random() * 1000);
			var html = '';
			html += '<div class="flex-center_loading" id="loading' + id + '" style=" position: absolute;width: 100%;height: 100%;z-index: 9999;top: 0;background-color: rgba(0,0,0,0.5)">';
			html += '<div class="" style="margin-top: 80%;width:auto;height:auto;"></div><div class="ui-load-txt" style="color: #ccc;text-align: center;margin: 10px;font-size: 18px;"></div></div>';
			html += '</div>';
			$('body').append(html);
		   	$("#loading"+id+" .ui-load-txt").html(content);
			if(typeof fun != undefined && outTime > 0){
				setTimeout(function(){
					fun();
					$('#loading' + id).remove();
				},outTime);
			}
			return $('#loading' + id);
	},
	/**
	 * 弹出满屏可设置进度的提示
	 * @return 返回一个对象，带setArc(arc),arc进度值 1-100 百分比的形式设置
	 */
	ProgressBar : function(){
		var id = parseInt(Math.random() * 1000);
		var html = '<div style="z-index: 9999; background-color: rgba(108,108,108,0.8);padding:0;position: absolute;width:100%;height:100%;top: 0;left: 0;">';
		html += '<div id="divtext1'+id+'" style="position: absolute;left:50%;margin-left: -150px;width:300px;height:300px"><p style="color: #fff;text-align: center; margin-top: 60%; font-size: 12px;">上传中...</p></div>';
		html += '<div id="divtext2'+id+'" style="position: absolute;left:50%;margin-left: -150px;width:300px;height:300px"><p style="color: #fff;text-align: center; margin-top: 48%; font-size: 12px;">0%</p></div>';
		html += '<canvas id = "canvas'+id+'" style="position: absolute;left:50%;margin-left: -150px;" ></canvas></div>';
		$('body').append(html);
		var canvas = document.getElementById('canvas'+id);
		var context = null;
		if (typeof canvas != undefined) {
			context = canvas.getContext('2d');
			canvas.width = 300;
			canvas.height = 300;
		}
		this.setArc = function(arc){
			context.clearRect(0,0,300,300);
			context.fillStyle="rgba(108,108,108,0)";
			context.fillRect(0,0,300,300);
			context.strokeStyle="#565656";
			context.lineWidth=3;
			context.beginPath();
			context.arc(150,150,20,0,2*Math.PI);
			context.stroke();
			context.strokeStyle="#31C330";
			context.lineWidth=3;
			context.beginPath();
			context.arc(150,150,20,0,2*Math.PI*(arc/100));
			context.stroke();
			$('#divtext2'+id).find('p').text(arc + "%");
			$('#divtext1'+id).find('p').text("上传中...");
		};
		return this;
	}
}
var checkMobile = function (m){ 
	var t = /^1[3|4|5|7|8][0-9]\d{8}$/;
    return t.test(m); 
} 
