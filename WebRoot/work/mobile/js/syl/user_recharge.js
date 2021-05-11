$(function(){
	init();
})
var size = 6;
var total;
var setData = function(now_page,row){
	$.myajax({
		url:path+"users/recharge/init.html",
		done:function(data){
			total = data.rows.length;
//			if(total <= size)$(".pagin-area").hide();
//			else $(".pagin-area").show();
			$.each($.jsonSplit(data,now_page,row).rows,function(i,val){
				var div = '<div class="list-item clearfix">\
								<div class="col-xs-3 item-left">\
									<img src="'+path+'images/tag-icon.png">\
								</div>\
								<div class="col-xs-6 item-center">\
									<p class="des">充值'+val.money+'元</p>\
									<p class="time">'+val.createTime+'</p>\
								</div>\
								<div class="col-xs-3 item-right">'+val.status+'</div>\
						  </div>';
				$(".charge-list").append(div);
			})
		}
	})
	
}

function init(){
	var load = function(){
		new setData(1,size);
	}
	
	var bind = function(){
		//加载更多 pagin-area
		$(".pagin-area a").click(function(){
			var page = parseInt($(this).attr("data-page")) + 1;
			if(page > Math.ceil(total/size))return layer.msg("已无更多");
			$(this).attr("data-page",page);
			new setData(page,size);
		});
		
		//充值
		$("#charge span").click(function(){
			var money = $("#charge input").val();
			if(isNaN(money))return layer.msg("请输入正确的金额");
			window.location.href = path+"users/recharge/mo_"+money+".html";
		})
		
		//快速充值
		$(".js-charge").click(function(){
			var money = $(this).attr("data-money");
			window.location.href = path+"users/recharge/mo_"+money+".html";
		})
	}
	
	function $clear(){
		
	}
	
	new load();
	new bind();
}