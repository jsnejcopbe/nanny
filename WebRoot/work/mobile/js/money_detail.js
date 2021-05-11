var nowPage=1;
var checkTime="";
var nowType=2;
var COST;
var wait=60;
var CASHLISTHTML='<div class="list-item clearfix" data-id="${id}">'+
				 '	 <p class="item-time col-xs-12">${date}-${day}</p>'+
				 '	 <div class="col-xs-9 item-left">'+
				 '		<img src="${src}"> ${des}'+
				 '   </div>'+
				 '	 <div class="col-xs-3 item-right">${money}</div>'+
				 '</div>';

var CASHAPPHTML='<div class="list-item clearfix">'+
				'	 <div class="col-xs-9 item-left type2">'+
				'		<p> ${des}</p>'+
				'		<p class="item-time">${date}-${day}</p>'+
				'    </div>'+
				'	 <div class="col-xs-3 item-right type2">'+
				'	 	<p class="item-money">${money}</p>'+
				'		<p class="item-sta">${sta}</p>'+
				'	 </div>'+
				'</div>';

var TIMESELECTHTML='<div class="ti-panel">'+
				   '	<div class="ti-top">选择月份</div>'+
				   '	<div class="ti-con">'+
				   '		<div class="con-item">'+
				   '			<p><a class="ti-btn" href="javascript:year(1)">+</a></p>'+
				   '			<p class="ti-time js-tiyear">${year}</p>'+
				   '			<p><a class="ti-btn" href="javascript:year(-1)">-</a></p>'+
				   '		</div>'+
				   '		<div class="con-item">'+
				   '			<p><a class="ti-btn" href="javascript:month(1)">+</a></p>'+
				   '			<p class="ti-time js-timonth">${month}</p>'+
				   '			<p><a class="ti-btn" href="javascript:month(-1)">-</a></p>'+
				   '		</div>'+
				   '	</div>'+
				   '	<div class="ti-bot">'+
				   '		<a href="javascript:layer.closeAll()">取消</a>'+
				   '		<a href="javascript:confirmTi()">确定</a>'+
				   '	</div>'+
				   '</div>';

var CASHDETAILHTML='<div>\
						<p>\
							<span>交易时间：</span>\
							<span>${time}</span>\
						</p>\
						<p>\
							<span>交易金额：</span>\
							<span>${money}</span>\
						</p>\
						<p>\
							<span>支付/收款用户：</span>\
							<span>${name}</span>\
						</p>\
						<p>\
							<span>备注：</span>\
							<span>${memo}</span>\
						</p>\
					</div>';

$(function(){
	//paginAjax();
	$(".js-cashtime").bind("click",function(){showTimeSelect();});
	botMenuChange();
	/*$("body").on("click",".list-item",function(e){getCashDetail(e.currentTarget||e.traget);});
	$(".cash-tab").bind("click",function(e){
		var target=e.currentTarget||e.traget;
		
		$(".cash-tab").removeClass("selected");
		$("#"+target.id).addClass("selected");
		
		nowType=$("#"+target.id).attr("data-type");
		nowPage=1;
		$(".list-item").remove();
		
		
		
		paginAjax();
	});*/
});

/**
 * 分页
 */
function paginAjax(){
	layer.load(1, {shade: [0.8,'#333']});
	//var html=nowType==2?"/money/detail/init.json":"/getcashappajax.json";
	var param={
		"sURL":html,
		"Data":"page="+nowPage+"&startTime="+checkTime+"&type="+nowType,
		"fnSuccess" : function(data){
			if(nowType==2)
				dataDelType1(data);	
			else
				dataDelType2(data);
			nowPage++;
			layer.closeAll('loading');

			if(data.length<20 || data.detail.length<20)
				$(".pagin-btn").css("display","none");
			else
				$(".pagin-btn").css("display","block");
		},
		"fnError" :function(){layer.closeAll('loading');layer.msg("查询失败 请联系管理员");}
	};
	new g_fnAjaxUpload(param);
}


/**
 * 显示时间选择
 */
function showTimeSelect(){
	layer.open({
	    type: 1,
	    title: false,
	    closeBtn: 0, //不显示关闭按钮
	    skin: 'layui-layer-rim', //加上边框
	    area: ['300px','227px'], //宽高
	    content: TIMESELECTHTML.replace("${year}", $.trim($(".js-year").text()))
	    					   .replace("${month}", $.trim($(".js-month").text()))
	});
}

/**
 * 确认选择时间
 */
function confirmTi(){
	var year=$.trim($(".js-tiyear").text());
	var time=$.trim($(".js-timonth").text());
	$(".js-year").text(year);
	$(".js-month").text(time);
	layer.closeAll();
	//账目查询
	checkTime=year+"-"+time+"-01 00:00:00";
	nowPage=1;
	$(".list-item").remove();
	paginAjax();
}


/***********************************************************************************************************/

/**
 * 时间加减
 * @param type
 */
function year(type){
	var time=$.trim($(".js-tiyear").text());
	$(".js-tiyear").text(parseInt(time) + parseInt(type));
}

function month(type){
	var time=$.trim($(".js-timonth").text());
	time=parseInt(time) + parseInt(type);
	if(time>12)
	{
		var year=$.trim($(".js-tiyear").text());
		$(".js-tiyear").text(parseInt(year) + parseInt(1));
		$(".js-timonth").text("01");
	}
	else if(time<1)
	{
		var year=$.trim($(".js-tiyear").text());
		$(".js-tiyear").text(parseInt(year) - parseInt(1));
		$(".js-timonth").text("12");
	}
	else{
		time=time<10?"0"+time:time;
		$(".js-timonth").text(time);
	}
}

/**
 * 底部菜单切换
 */
function botMenuChange(){
	$(".rec-bot").css("display","none");
	switch (parseInt(nowType)) {
	case 0:
		$(".js-trans-bot").css("display","block");
		break;
	case 1:
		$(".js-cash-bot").css("display","block");
		break;
	default:
		$(".js-rec-bot").css("display","block");
		break;
	}
}

/**
 * 位数隐藏
 * @param str
 * @param begin
 * @param end
 * @param char
 * @returns
 */
function mask(str,begin,end,char){
	 var scdStr =str.substring(begin,end);
	 return str.replace(scdStr,char);
}

