$(function(){
	createBtnHtml();
});

/**
 * 创建按钮btn
 * @param status
 */
function createBtnHtml(){
	var html="";
	var status=$("#status").val();
	if(status==1)
		html+='<a class="nor-btn" href="javascript:orderConfirm('+$("#orderID").val()+',2)">确认并发货</a>';
	if(status==1 || status==2)
		html+='<a class="dan-btn" href="javascript:shopmsg('+$("#orderID").val()+')">取消订单</a>';
	$(".or-opt").append(html);
}

/**
 * 订单确认
 * @param orderID
 * @param sta
 */
function orderConfirm(orderID,sta,isForce){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/shop/orderconfirm.json",
		"Data":"orderID="+orderID+"&sta="+sta+"&isForce="+(isForce==null?"":isForce),
		"fnSuccess":function(data){
			layer.closeAll('loading');
			
			if(data.hasOwnProperty("isRefund"))
				forceToPass(orderID, sta);
			else{
				layer.msg(data.msg);
				setTimeout("location.href='"+BASEPATH+"/shop/order-1.html'", 2000);
			}
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 强制执行发货
 * @param orderID
 * @param sta
 */
function forceToPass(orderID,sta){
	layer.confirm('该订单的下单用户已提交了订单取消申请，是否仍要送货？',
				  {btn: ['确定','取消']},
				  function(){
					  orderConfirm(orderID,sta,"force");
					  return;
				  },
				  function(){return;});
}

/**
 * 退款理由
 */
function shopmsg(id) {
	$('#shmsg_'+id).css("display","block");
	$('#top').css("display","block");
	$('.or-opt').css("display","none");
	$('.dtop').css("display","none");
	$('.container').css("display","none");
}


/**
 * 退款理由取消
 */
function clearmsg(id) {
	$('#shmsg_'+id).css("display","none");
	$('.or-opt').removeAttr('style');
	$('.dtop').removeAttr('style');
	$('#top').css("display","none");
	$('.container').removeAttr('style');
}


/**
 * 用户退款
 * @param orderID
 * @param sta
 */
function orderRefund(orderID,sta,totalprice){
	layer.load(1, {shade: [0.8,'#333']});
	
	var shmsg=$("input[name='smsg_"+orderID+"']:checked").val();
	var inp=$("textarea[name='inp_"+orderID+"']").val();
	var shmasg;
	if(shmsg=='1'){
		shmasg=inp;
	}else{
		shmasg=shmsg;
	}
	if(shmasg!=undefined){
	var param={
		"sURL":BASEPATH+"/shop/orderrefund.json",
		"Data":"orderID="+orderID+"&sta="+sta+"&cost="+totalprice+"&shopmsg="+shmasg,
		"fnSuccess":function(data){
//			layer.closeAll('loading');
			layer.msg(data.msg);
			if(!data.hasOwnProperty("error"))
				location.href=BASEPATH+'/shop/order-1.html';;
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
	};
	new g_fnAjaxUpload(param);
	}else{
		layer.closeAll('loading');
		layer.msg("请填写或选择退款理由！！");
	}
}