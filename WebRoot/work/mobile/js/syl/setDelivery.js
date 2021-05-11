var TOPHTML='<div class="list-item clearfix li-top-bar">\
				<div class="col-xs-9">\
					<span>地址名称</span>\
					<div>\
						<a class="btn btn-info btn-sm" href="javascript:confirmChoose()">确认选择</a>\
						<a class="btn btn-danger btn-sm" href="'+BASEPATH+'/shop/shopIndex.html">返回上级</a>\
					</div>\
				</div>\
				<div class="col-xs-3">\
					<a class="item_add item_all" href="javascript:void(0)">\
						<i class="icon-check-empty"></i>\
						<br>\
						<span>选中地址</span>\
					</a>\
				</div>\
	         </div>';

$(function(){
	$("body").on("focus","a",function(){
		$(this).blur();
	});
	
	list1_init();
	list2_init();
});
var size = 6;
var setData = function(now_page,row,address,$this){
	var area = address === undefined ? "" : address.replace(/['\s\\[\\]]/g,"");
	var total;
	$ajax({
		async:true,
		url:path+$this.find("#hide_url").val(),
		data:{data:JSON.stringify({address:area})},
		done:function(json){
			total = json.rows.length;
			if(total <= size)$this.find(".pagin-area").hide();
			else $this.find(".pagin-area").show();
			
			$.each(json_spilt(json, now_page, row).rows,function(i,val){
				var addName = val.addName === undefined ? "暂无小区名" : val.addName;
				var detAdd = val.detAdd === undefined ? "暂无地址" : val.detAdd;
				
				var temp1 = '<div class="col-xs-3">\
									<div class="it-btn">\
									<a class="item_remove" href="#" data-lon="'+val.lon+'" data-lat="'+val.lat+'" data-id="'+val.id+'">\
									<i class="iconfont-gl">&#xe635;</i>\
									<br>\
									<span>删除地址</span>\
									</a>\
								</div>\
							</div>';
				var temp2 = '<div class="col-xs-3">\
									<div class="it-btn">\
									<a class="item_add" href="#" data-lon="'+val.lon+'" data-lat="'+val.lat+'" data-id="'+val.id+'">\
									<i class="icon-check-empty"></i>\
									<br>\
									<span>选中地址</span>\
									</a>\
								</div>\
							</div>';
				
				var div = '<div class="list-item clearfix">\
								<div class="col-xs-9 it-des">\
									<p>'+addName+'</p>\
									<p>'+detAdd+'</p>\
								</div>\
								'+($this.attr("id") == "list1" ? temp1 : temp2 )+'\
						   </div>';
					$this.find(".ap-list").append(div);
			});
		}
	});
	
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
	return {total:total};
}

function list1_init(){
	var $this = $("#list1");
	var total;
	
	var load = function(){
		total = new setData(1,size,"",$this).total;
	}
	var bind = function(){
		//加载更多
		$this.find(".pagin-area").on("click","a",function(){
			var page = parseInt($(this).attr("data-page")) + 1;
			if(page > Math.ceil(total/size))return layer.msg("已无更多");
			$(this).attr("data-page",page);
			var ress = $this.find(".address").val()
			var address = ress == "" ? "" : ress;
			total = new setData(page,size,address,$this).total;
		})
		
		$("body").on("click",".item_remove",function(){
		//$this.on("click",".item_remove",function(){
			var id = $(this).attr("data-id");
			var item = $(this).parents(".list-item");
			var index = layer.confirm('是否确定删除？', {
			    btn: ['取消','确定'] //按钮
			}, function(){
				layer.close(index);
			}, function(){
				 $ajax({
					 url:path+"shop/delivery/remove.html",
					 data:{id:shopID,adressID:id},
					 done:function(){
						 layer.msg("删除成功");
						 setTimeout(function(){
							 window.location.reload(true);
						 }, 500);
					 }
				 },1)
			})
		})
		
		$this.on("input propertyChange",".address",function(){
			var v = $(this).val();
			clear($this);
			total = new setData(1,size,v,$this).total;
		})
		
		$this.find(".bot-btn").click(function(){
			$this.hide();
			$("#list2").show();
			$("#list2 .ap-list .list-item:first").before(TOPHTML);
		});
		
	}
	new load();
	new bind();
}

function list2_init(){
	var $this = $("#list2");
	var total;
	
	var load = function(){
		total = new setData(1,size,"",$this).total;
	};
	
	var bind = function(){
		//加载更多
		$this.find(".pagin-area").on("click","a",function(){
			var page = parseInt($(this).attr("data-page")) + 1;
			if(page > Math.ceil(total/size))return layer.msg("已无更多");
			$(this).attr("data-page",page);
			var ress = $this.find(".address").val();
			var address = ress == "" ? "" : ress;
			//alert(address)
			total = new setData(page,size,address,$this).total;
		});
		
		$("body").on("click",".item_add",function(){
			var className=$(this).attr("class");
			if(className.indexOf("selected")!=-1){
				$(this).removeClass("selected");
				$(this).find("i").attr("class","icon-check-empty");
				
				$(".item_all").removeClass("selected");
				$(".item_all").find("i").attr("class","icon-check-empty");
				
				
				if(className.indexOf("item_all")!=-1){
					$(".item_add").removeClass("selected");
					$(".item_add").find("i").attr("class","icon-check-empty");
				}
			}else{
				if(className.indexOf("item_all")!=-1){
					$(".item_add").addClass("selected");
					$(".item_add").find("i").attr("class","icon-check");
				}else{
					$(this).addClass("selected");
					$(this).find("i").attr("class","icon-check");
				}
				if($(".selected").length == ($(".ap-list .list-item").length - 1)){
					$(".item_all").addClass("selected");
					$(".item_all").find("i").attr("class","icon-check");
				}
			}
		});
		
		$this.on("input propertyChange",".address",function(){
			var v = $(this).val();
			clear($this);
			total = new setData(1,size,v,$this).total;
		})
		
		$this.find("#list1_return").click(function(){
			window.location.reload(true);
		})
		
	}
	new load();
	new bind();
}

function clear($this){
	$this.find(".ap-list").html("");
	$this.find("pagin-area a").attr("data-page",1);
	
	if($("#list2 .ap-list .list-item:first").length!=0)
		$("#list2 .ap-list .list-item:first").before(TOPHTML);
	else
		$("#list2 .ap-list").append(TOPHTML);
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

/**
 * 确认选择
 */
function confirmChoose(){
	if($(".selected").length==0){
		layer.msg("请至少选择一项");
	}
	
	var ids = Array();
	$(".selected").each(function(){
		var className=$(this).attr("class");
		if(className.indexOf("item_all")==-1)
			ids.push($(this).attr("data-id"));
		$(this).parents(".list-item").remove();
	});
	
	$ajax({
		url:path+"shop/delivery/add.html",
		data:{shopID:shopID,ids:JSON.stringify(ids)},
		done:function(msg){
			layer.msg("添加成功");
			location.href=path+"shop/dispatchedit.html";
		}
	},1);
}