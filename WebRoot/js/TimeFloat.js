var TIMESELECTHTML='<div class="ti-panel">'+
				   '	<div class="ti-top">选择月份</div>'+
				   '	<div class="ti-con">'+
				   '		<div class="con-item">'+
				   '			<p><a class="ti-btn js-addyear" href="javascript:void(0)">+</a></p>'+
				   '			<p class="ti-time js-tiyear">${year}</p>'+
				   '			<p><a class="ti-btn js-desyear" href="javascript:void(0)">-</a></p>'+
				   '		</div>'+
				   '		<div class="con-item">'+
				   '			<p><a class="ti-btn js-addmon" href="javascript:void(0)">+</a></p>'+
				   '			<p class="ti-time js-timonth">${month}</p>'+
				   '			<p><a class="ti-btn js-desmon" href="javascript:void(0)">-</a></p>'+
				   '		</div>'+
				   '	</div>'+
				   '	<div class="ti-bot">'+
				   '		<a href="javascript:layer.closeAll()">取消</a>'+
				   '		<a class="js-confirm" href="javascript:void(0)">确定</a>'+
				   '	</div>'+
				   '</div>';
var _timeSelect=Class.create();

_timeSelect.prototype={
	"initialize":function(){this.bindAction(this);},
	"option":{
		"year":null,
		"month":null,
		"select":null
	},
	"init":function(obj){
		var myDate = new Date(); 
		this.option.year=obj.year==null?myDate.getFullYear():obj.year;
		var month=obj.month==null?(parseInt(myDate.getMonth())+parseInt(1)):obj.month;
		if(month<10)
			month="0"+month;
		this.option.month=month;
		this.option.select=obj.select;
		this.showTimeSelect();
	},
	"bindAction":function(target){
		$("body").on("click",".ti-btn.js-addyear",function(){
			target.year(1);
		});
		$("body").on("click",".ti-btn.js-desyear",function(){
			target.year(-1);
		});
		$("body").on("click",".ti-btn.js-addmon",function(){
			target.month(1);
		});
		$("body").on("click",".ti-btn.js-desmon",function(){
			target.month(-1);
		});
		$("body").on("click",".ti-bot .js-confirm",function(){
			target.confirmTi();
		});
	},
	"showTimeSelect":function(){
		layer.open({
		    type: 1,
		    title: false,
		    closeBtn: 0, //不显示关闭按钮
		    skin: 'layui-layer-rim', //加上边框
		    area: ['300px','227px'], //宽高
		    content: TIMESELECTHTML.replace("${year}", $.trim(this.option.year))
		    					   .replace("${month}", $.trim(this.option.month))
		});
	},
	"year":function(type){
		var time=$.trim($(".js-tiyear").text());
		$(".js-tiyear").text(parseInt(time) + parseInt(type));
	},
	"month":function(type){
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
	},
	"confirmTi":function(){
		var year=$.trim($(".js-tiyear").text());
		var time=$.trim($(".js-timonth").text());
		$(".js-year").text(year);
		$(".js-month").text(time);
		layer.closeAll();
		//账目查询
//		checkTime=year+"-"+time+"-01 00:00:00";
		checkTime=year+"-"+time;
		(this.option.select).val(checkTime);
	}
};