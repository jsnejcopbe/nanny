$(function(){
	init();
	
})
var size = 6;
var total;
var setData = function(now_page,row,memo,more_search){
	var memo_ = memo === undefined ? "-1" : memo;
	var more_search_ = more_search === undefined ? "" : more_search.replace(/['\s\\[\\]]/g,"");
//	console.log(memo_+" "+more_search_)
	$ajax({
		url:path+"shop/fans/init.html",
		data:{data:JSON.stringify({memo:memo_,more_search:more_search_})},
		done:function(data){
			total = data.rows.length;
//			if(total <= size)$(".pagin-area").hide();
//			else $(".pagin-area").show();
			$.each(json_spilt(data,now_page,row).rows,function(i,val){'+val.+'
				var qq = val.qq === undefined ? "暂无" : val.qq;
				var mail = val.mail === undefined ? "暂无" : val.mail;
				var div1 = '<div class="list-item clearfix" data-id="'+val.id+'">\
								<div class="it-left col-xs-3">\
									<img src="'+val.headImg+'">\
								</div>\
								<div class="it-right col-xs-9">\
									<p class="ri-name">'+val.nickName+'</p>\
									<p class="ri-des">\
										<span><i class="iconfont-gl">&#xe633;</i> 电话:'+val.tel+'</span>\
									</p>\
									<p class="ri-des">\
										<span><i class="iconfont-gl">&#xe61e;</i> 邮箱:'+mail+'</span>\
									</p>\
									<p class="ri-des">\
										<span><i class="iconfont-gl">&#xe62c;</i> qq:'+qq+'</span>\
								</div>\
								<div class="it-icon">\
									<p>'+val.point+'</p>\
									<p>积分</p>\
								</div>\
							</div>';
				
				$("#fans_content").append(div1);
			})
		}
	})

	function json_spilt(json,now_page,row){
		var newjson = {rows:[]};
		var data = json.rows;
		for(var i=0;i<data.length;i++){
			var start,end;
			start = row*(now_page-1);
			end = row*now_page;
			if(i >= start && i < end){
				newjson.rows.push(data[i]);
			}
		}
		return newjson;
	}
}

function init(){
	var load = function(){
		//下拉
		var type = temp_shopID === "" ? shopID : temp_shopID;
		$("#address ul").select({
			url:path+"shop/fans/getArea.html",
			data:type,
			type:1
		})
		
		new setData(1,size);
	}
	
	var bind = function(){
		//下拉事件
		$("#address ul li a").click(function(){
			var more = $(this).attr("data-value");
			$("#address span:eq(0)").text($(this).text());
			$("#address span:eq(0)").attr("data-value",more);
			$clear();
			new setData(1,size,more);
		})
		
		//搜索
		$("body").on("input propertyChange",".address_query",function(){
			var more = $("#address span:eq(0)").attr("data-value");
			console.log(more+" "+$(this).val());
			$clear();
			new setData(1,size,more,$(this).val());
		})	
		
		//加载更多
		$(".pagin-area a").click(function(){
			var page = parseInt($(this).attr("data-page")) + 1;
			if(page > Math.ceil(total/size))return layer.msg("已无更多");
			$(this).attr("data-page",page);
			var ress = $(".address_query").val();
			var more = $("#address span:eq(0)").attr("data-value");
			var more_search = ress == "" ? "" : ress;
			
			new setData(page,size,more,more_search);
		})
	}
	
	new load();
	new bind();
	
	function $clear(){
		$("#fans_content").html("");
		$(".pagin-area a").attr("data-page","1");
	}
}

function $ajax(option,type){
	var defVal = {
			async:false,
			url:'',
			data:'',
			dataType:'json',
			done:function(json){

			}
	}
	var obj = $.extend({},defVal,option);
	obj.dataType = type=== undefined ? "json" : "text";
	$.ajax({
		async:obj.async,
		type:'post',
		url:obj.url,
		data:obj.data,
		dataType:obj.dataType
	}).done(obj.done)
	.error(function(msg){
		if(msg)layer.msg("网络异常");
	})
}