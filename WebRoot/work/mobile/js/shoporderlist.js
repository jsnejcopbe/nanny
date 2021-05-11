var ITEMCONHTML='<div class="row pro-it">\
					<div class="col-xs-12 it-tit clearfix">\
						<span class="tit-code">订单号:${orderCode }</span>\
						<span class="tit-sta">${staName }</span>\
					</div>\
					${proList}\
					<div class="it-opt col-xs-12">\
						<div class="opt-msg">共2件商品 合计<span>¥${totalPrice }</span>元(含配送费${fee }元)</div>\
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
var nowPage=1;


/**
 * 翻页函数
 */
function pagin(){
	layer.load(1, {shade: [0.8,'#333']});
	nowPage=nowPage+1;
	var param={
		"sURL":	BASEPATH+"/shop/order-"+nowPage+".json",
		"Data": "type=json&status="+$("#status").val()+"&query="+$("#query").val(),
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
									.replace("${btnList}", createBtnHtml(array[i].status, array[i].id,array[i].totalPrice,array[i].orderCode));
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
				setTimeout("location.reload(true)", 2000);
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
	$('.pro-it').css("display","none");
	$('.dtop').css("display","none");
	$('.top-tab').css("display","none");
}

/**
 * 退款理由取消
 */
function clearmsg(id) {
	$('#shmsg_'+id).css("display","none");
	$('#top').css("display","none");
	$('.pro-it').removeAttr('style');
	$('.dtop').removeAttr('style');
	$('.top-tab').removeAttr('style');
}

/**
 * 用户退款
 * @param orderID
 * @param sta
 */
function orderRefund(orderID,sta,totalprice){
	
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
				layer.closeAll('loading');
				layer.msg(data.msg);
				if(!data.hasOwnProperty("error"))
					setTimeout("location.reload(true)", 2000);
			},
			"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
		};
		new g_fnAjaxUpload(param);
	}else{
		layer.msg("请填写或选择退款理由！！");
	}
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
function createBtnHtml(status,orderID,totalprice,orderCode){
	var html='<a href="'+BASEPATH+'/shop/order-det-'+orderCode+'.html">查看详情</a>';
	if(status==1)
		html+='<a class="red-btn" href="javascript:orderConfirm('+orderID+',2)">确认订单</a>';
	if(status==1 || status==2)
		html+='<a href="javascript:orderRefund('+orderID+',4,'+totalprice+')">退款</a>';
	return html;
}