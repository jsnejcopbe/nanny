var nowPage=1;
var CASHAPPHTML='<div class="list-item clearfix">'+
				'	 <div class="col-xs-9 item-left type2">'+
				'		<p>提现卡号 <span class="js-acc">${des}</span></p>'+
				'		<p class="item-time">${time}</p>'+
				'    </div>'+
				'	 <div class="col-xs-3 item-right type2">'+
				'	 	<p class="item-money">${money}</p>'+
				'		<p class="item-sta">${sta}</p>'+
				'	 </div>'+
				'</div>';


$(function(){
	new _timeSelect();
	$(".list-item").each(function(){
		var target=$(this).find(".js-acc");
		var str=target.text();
		target.text(mask(str, 4, 16, "*****"));
	});
	
	$(".time_query").on("focus",function(){
		_timeSelect.prototype.init({"select":$(this)});
	});
});

function doQuery(){
	nowPage=0;
	$(".fa-list .list-item").remove();
	paginAjax();
}

/**
 * 分页
 */
function paginAjax(){
	var time1=$("#start").val()+"-01";
	var time2=$("#end").val()+"-01";
	if(dateCompair(time1, time2)==false){
		layer.msg("开始时间不能大于结束时间");
		return;
	}
	
	layer.load(1, {shade: [0.8,'#333']});
	nowPage=nowPage+1;
	var data="start="+time1+"&end="+time2+"&page="+nowPage+"&type=json";
	var param={
		"sURL":BASEPATH+"/users/transrec.json",
		"Data":data,
		"fnSuccess":function(rtnData){
			var array=rtnData.data;
			if(array.length==0)
				layer.msg("没有更多数据");
			var html="";
			for(var i=0;i<array.length;i++)
			{
				var acc=mask(array[i].account, 4, 16, "*****");
				html+=CASHAPPHTML.replace("${des}", acc)
								 .replace("${money}", array[i].money)
								 .replace("${sta}", array[i].statuTxt)
								 .replace("${time}", array[i].createTime);
			}
			$(".pagin-btn").before(html);
			
			if(array.length<20)
				$(".pagin-btn").css("display","none");
			else
				$(".pagin-btn").css("display","block");
			
			layer.closeAll('loading');
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("查询失败");}
	};
	new g_fnAjaxUpload(param);
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

/**
 * 时间比较
 * @param time1
 * @param time2
 * @returns
 */
function dateCompair(time1,time2){
	if(time1=="" || time2=="")
		return true;
	else
		return g_fnTimeFunction.dateCompare(time1, time2);
}