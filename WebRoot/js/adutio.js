var ADUTIOHTML='<audio id="orderaudio" src="${src1}" controls="controls" hidden="true"></audio>\
				<audio id="appraudio" src="${src2}" controls="controls" hidden="true"></audio>';
var ADUTIOTIME;
var ADUTIOROOPCHECK;

$(function(){
	//0.初始化
	$("body").append(ADUTIOHTML.replace("${src1}", "/nanny/mp3/audiotip.mp3")
							   .replace("${src2}", "/nanny/mp3/resappridao.mp3?v=0.01"));//打出h5mp3播放
	ADUTIOTIME=getNowFormatDate();//初始化时间
	
	//1.设置定时
	ADUTIOROOPCHECK=setInterval("adutioOrderCheck()", 20 * 1000);//20秒查询一次 循环
	ADUTIOAPPROOPCHECK=setInterval("adutioResAppCheck()", 25 * 1000);
});

/**
 * 订单查询
 */
function adutioOrderCheck(){
	$.ajax({
		"type" : "POST",
		"url"  : "/nanny/shop/ordercheckbytime.json",
		"data" : "time="+ADUTIOTIME,
		"dataType" : "json",
		"success" : function(rtnData){
			//0.计算订单数量
			var count=$.trim($(".js-menuodcount").text());
			if(rtnData.hasOwnProperty("count")){
//				count=parseInt(count)+parseInt(rtnData.count);
				$(".js-menuodcount").text(rtnData.count);
				$(".js-menuodcount-sec").text(rtnData.count);
				
				//1.播放语音
				if(rtnData.count>0)
					document.getElementById("orderaudio").play();
			}
			else if(rtnData.hasOwnProperty("stop"))
				clearInterval(ADUTIOROOPCHECK);
			
			//2.更新时间
			ADUTIOTIME=getNowFormatDate();
		}
	});
}

/**
 * 退款申请查询
 */
function adutioResAppCheck(){
	$.ajax({
		"type" : "POST",
		"url"  : "/nanny/shop/resappcheckbytime.json",
		"data" : "time="+ADUTIOTIME,
		"dataType" : "json",
		"success" : function(rtnData){
			//0.计算订单数量
			if(rtnData.hasOwnProperty("count")){
				//1.播放语音
				if(rtnData.count>0)
					document.getElementById("appraudio").play();
			}
			else if(rtnData.hasOwnProperty("stop"))
				clearInterval(ADUTIOROOPCHECK);
			
			//2.更新时间
			ADUTIOTIME=getNowFormatDate();
		}
	});
}

/**
 * 获得当前时间
 * @returns {String}
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
} 