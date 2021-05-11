$(function(){
	
	init();
})

var size = 8;
var total = 0;
var setData = function(options){
	var class1 = "cr-success";
	var class3 = "cr-danger";
	var itbtn1 = '<div class="it-btn" >\
						<a href="javascript:;" data-value="1" class="cz_btn1">\
						<i class="iconfont-gl">&#xe637;</i>\
						<br>\
						<span>通过</span>\
						</a>\
						<a class="cr-danger cz_btn2" href="javascript:;" data-value="2" >\
						<i class="iconfont-gl">&#xe647;</i>\
						<br>\
						<span>不通过</span>\
						</a>\
				   </div>';
	
	$ajax({
		url:path+'salesman/auditlist.html',
		data:{data:JSON.stringify(options)},
		done:function(json){
			total = json.total;
			$.each(json.rows,function(i,val){
				var state = val.state;
				var itbtn2 = '<div class="it-btn">\
								   <span class="'+(state !="待审核" && state == "通过" ? class1 : class3 )+'" href="#">\
									    '+state+'\
								   </span>\
							  </div>';
				
				var temp = state == "待审核" ? itbtn1 : itbtn2;
				var div = '<div class="list-item clearfix"  data-id="'+val.id+'">\
								<div class="col-xs-9 it-des" style="margin-top: 20px;">\
									<p>'+val.name+'&nbsp;&nbsp;<span>店名：'+val.shopName+'</span></p>\
									<p>联系电话:<span class="js-userTel">'+val.tel+'</span>&nbsp;&nbsp;微信:'+val.weixinAcc+'</p>\
								</div>\
								<div class="col-xs-3">\
								 '+temp+'\
								</div>\
						  </div>';
				$(".ap-list").append(div);
			})
		}
	})
}

function init(){
	//取消奇怪虚线
	$("a,li").focus(function(){
		$(this).blur();
	})
	
	var load = function(){
		new setData({now_page:1,row:size})
		
	}
	
	var bind = function(){
		
		//店铺审核
		$(".ap-list").on("click",".cz_btn1 , .cz_btn2",function(){
			
			
			
			var type = $(this).attr("data-value");
			var parent=$(this).parents(".list-item");
			var id = parent.attr("data-id");
			var tel=$.trim(parent.find(".js-userTel").text())
			var $this = $(this);
			layer.load(1, {shade: [0.8,'#333']});
			$.ajax({
				type:'post',
				url:path+'admin/list/change.html',
				data:{type:type,id:id,tel:tel},
				dataType:'text'
			}).done(function(msg){
				if(msg == "exist1"){
					layer.closeAll('loading');
					return layer.msg("手机号码已存在");
				}
				//alert(msg);
				layer.msg("操作成功",{icon:1});
				
				$clear();
				new setData({now_page:1,row:size});
				layer.closeAll('loading');
			}).error(function(){
				layer.msg("网络异常");
				layer.closeAll('loading');
			})
		})
		
		//搜索
		$("body").on("input propertyChange","#many",function(){
			$clear();
			new setData({now_page:1,row:size,many:$("#many").val()});
		})
		
		//状态
		$("body").on("click","#stata_ul li",function(){
			var type = $(this).attr("data-type");
			$("#stata_a span").html($(this).find("a").html());
			$clear();
			new setData({now_page:1,row:size,state:type,many:$("#many").val()});
		})
		
		//加载更多
		$("body").on("click",".pagin-area a",function(){
			var par = $(this).parent();
			var page = parseInt(par.attr("data-page"))+1;
			if(page > Math.ceil(total/size))return layer.msg("已无更多");
			par.attr("data-page",page);
			new setData({now_page:page,row:size})
		})
	}
	
	new load();
	new bind();
}

function $clear(){
	$(".ap-list").html("");
	$(".pagin-area").attr("data-page","1");
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
		//console.log(msg)
		layer.msg("网络异常");
	})
}