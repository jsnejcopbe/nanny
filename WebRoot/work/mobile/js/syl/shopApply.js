var HTML='<div class="layer-con">\
			 <p class="clearfix">\
				 <i class="iconfont-gl">&#xe61e;</i>\
				 <span>恭喜您，您的申请已成功提交，请耐心等待审核</span>\
			 </p>\
			 <p>\
				 <a class="btn btn-block btn-info" href="weixin://contacts/profile/zsbomu">点我前往关注公众号</a>\
				 <a class="btn btn-block btn-danger" href="'+path+'login.html">暂时不关注公众号</a>\
			 </p>\
		  </div>';


$(function(){
	jQuery.getScript(path+"js/syl/jquery.form.js");
	jQuery.getScript(path+"js/syl/verify.js");
	jQuery.getScript(path+"js/syl/basis.js");

	$("#apply_form").submit(function(){
		var b = $(this).verifys({
			msg:["姓名不能为空","手机号码不正确","商店名不能为空"],
			type:1,
			bg:"#3595CC"
		});
		if(!b)return false;
		layer.load(1, {shade: [0.8,'#333']});
		$(this).formSubmit({
			url:path+'shop/add.html?type=0',
			dataType:'text',
			success:function(msg){
				if(msg == 'exist'){
					layer.msg("该店铺名已被申请");
					layer.closeAll('loading');
					return;
				}
				if(msg == 'exist1'){
					layer.msg("该手机号已被申请");
					layer.closeAll('loading');
					return;
				}
				if(msg == "不能为空"){
					layer.msg(msg);
					layer.closeAll('loading');
					return;
				} 
				layer.msg('申请成功', {
				    icon: 1,
				    time: 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function(){
					showFocusLayer();
				}); 
			}
		});
		return false;
	}); 
});

function showFocusLayer(){
//	layer.open({
//	    type: 1,
//	    title: false,
//	    skin: 'layui-layer-rim', //加上边框
//	    area: ['200px','150px'], //宽高
//	    content: HTML
//	});
	location.href=path+"login.html";
}