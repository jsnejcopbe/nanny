var ITEMCONHTML='<div class="row pro-it">\
					<div class="col-xs-12 it-tit clearfix">\
						<span class="tit-code">订单号:${orderCode }</span>\
						<span class="tit-sta">${staName }</span>\
					</div>\
					${proList}\
					<div class="it-opt col-xs-12">\
						<div class="opt-msg"> 合计<span>¥${totalPrice }</span>元(含配送费${fee }元)</div>\
					</div>\
					<div class="it-btn col-xs-12">${btnList}</div>\
			  </div>';
var PROITEMHTML='<div class="col-xs-12 it-con">\
					<div class="col-xs-3 con-img">\
						<img src="${cover }">\
					</div>\
					<div class="col-xs-7 con-des">\
						${name }\
					</div>\
					<div class="col-xs-2 con-pri">\
						<p>¥${price }</p>\
						<p>X${count }</p>\
					</div>\
				 </div>';



$(function() {
	layer.config({
	    extend: 'extend/layer.ext.js'
	});
})


/**
 * 翻页函数
 */
function pagin(){
	layer.load(1, {shade: [0.8,'#333']});
	nowPage=nowPage+1;
	var param={
		"sURL":	BASEPATH+"/users/userOder.html&"+nowPage,
		"Data": "sta="+$("#status").val(),
		"fnSuccess":function(data){
			$(".pro-it").remove();
			layer.closeAll('loading');
			var array=data.data;
			var outHtml="";
			for(var i=0;i<array.length;i++)//
			{
				outHtml+=ITEMCONHTML.replace("${orderCode }", array[i].orderCode)
									.replace("${staName }", array[i].staName)
									.replace("${proList}", createInnerHtml(array[i].detList))
									.replace("${totalPrice }", array[i].totalPrice)
									.replace("${fee }", array[i].fee)
									.replace("${btnList}", createBtnHtml(array[i].status, array[i].id,array[i].totalPrice));
			}
			$(".js-more").before(outHtml);
			if(array.length<20)
				$(".js-more").css("display","none");
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("查询失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 订单付款
 * @param orderID
 * @param sta
 */
function orderpayment(orderID){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/users/orPayment.html",
		"Data":"orderID="+orderID,
		"fnSuccess":function(data){
			//layer.closeAll('loading');
			//layer.msg(data.msg);
			if(data.msg=='余额不足'){
				setTimeout(function() {
					location.href="/nanny/pay/weixinpay.html";
				}, 2000);
				
			}else{
				setTimeout("location.reload(true)", 2000);
			}
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
	};
	new g_fnAjaxUpload(param);
}


/**
 * 确认收货
 * @param orderID
 * @param sta
 */
function orderConfirm(orderID){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/users/confirm.html",
		"Data":"orderID="+orderID,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			setTimeout("location.reload(true)", 2000);
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 用户退款
 * @param orderID
 * @param sta
 */

function orderRefund(orderID){
	
	layer.prompt({title: '取消订单原因', formType: 2}, function(text){
		var memo=text;
	        var param={
	        		"sURL":BASEPATH+"/users/orRefund.html",
	        		"Data":"orderID="+orderID+"&memo="+memo,
	        		"fnSuccess":function(data){
	        			layer.closeAll('loading');
	        			layer.msg(data.msg);
	        			if(!data.hasOwnProperty("error"))
	        				setTimeout("location.reload(true)", 2000);
	        		},
	        		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
	        	};
	        	new g_fnAjaxUpload(param);
	        
	    });
	
	
	
}

/**
 * 展示搜索框
 */
function showQuery(){
	$(".sh-cen").css("display","none");
	$(".sh-icon-right").css("display","none");
	$(".con-sec").fadeIn();
}
function closeQuery(){
	document.getElementById("query").value="";
	$(".con-sec").css("display","none");
	$(".sh-cen").css("display","block");
	$(".sh-icon-right").css("display","block");
}
function doQuery(){
	nowPage=0;
	pagin();
}
/********************************************************************************************************************/
/**
 * 拼接商品html
 * @param array
 */
function createInnerHtml(array){
	var html="";
	for(var i=0;i<array.length;i++)
	{
		html+=PROITEMHTML.replace("${cover }", array[i].cover)
						 .replace("${name }", array[i].name)
						 .replace("${price }", array[i].price)
						 .replace("${count }", array[i].count);
	}
	return html;
}

/**
 * 创建按钮btn
 * @param status
 */
function createBtnHtml(status,orderID,totalprice){
	var html='<a href="javascript:void('+orderID+')">查看详情</a>';
	if(status==1)
		html+='<a class="red-btn" href="javascript:orderConfirm('+orderID+',2)">确认订单</a>';
	if(status==1 || status==2)
		html+='<a href="javascript:orderRefund('+orderID+',4,'+totalprice+')">退款</a>';
	return html;
}