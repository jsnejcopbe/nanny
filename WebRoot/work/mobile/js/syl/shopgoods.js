$(function(){
	type1_load();//加载类型1数据
	main_load();//商品主内容的加载
	bind_add();//绑定铺货按钮
	
	scollerWidth();//计算滚动菜单长度
})
var size = 6;
var typeID1 = -1;
var typeID2 = -1;
//加载类型1数据
function type1_load(){
	$("body").on("focus","a",function(){
		$(this).blur();
	})
	
	var setData = function(json){
		$("#scroller").find("ul").html("");
		$.each(json,function(i,val){
			var li = '<li><a data-id="'+val.s_value+'" href="javascript:void(0)">'+val.s_text+'</a></li>';
			$("#scroller").find("ul").append(li);
		})
//		var width = $("#scroller").find("ul").width();
//		$("#scroller").css("width",width+"px"); 
	}
	
	$ajax({
		url:base_url+'/admin/init_type.html',
		data:{type:0},
		done:function(json){
			new setData(json);
		}
	})
}
//加载类型2数据
function type2(id){  
	var setData = function(json){
		$("#type2").html("");
		$.each(json,function(i,val){
			var name= (val.s_text).length>6?(val.s_text).substring(0,5)+"...":val.s_text;
			var a = '<a class="col-xs-3 cla-item" data-id="'+val.s_value+'" href="javascript:void(0)">'+name+'</a>';
			$("#type2").append(a);
		});
	};
	
	$ajax({
		url:base_url+'/admin/init_type.html',
		data:{type:id},
		done:function(json){
			new setData(json);
		}
	});
}

//绑定铺货按钮
var b = false;
function bind_add(){
	var go_add = function(ids){
		if(ids.length <=0){
			layer.msg("请至少选择一个");
			return;
		}
		$ajax({
			url:base_url+'/shop/goods/add.html',
			data:{data:JSON.stringify(ids)},
			dataType:'text',
			done:function(json){
				layer.msg("选货成功");
				$.each(ids,function(i,val){
					$("#main_content").find("[data-id='"+val.id+"']").remove();
				})
			}
		})
	}
	
	$("#main_content").on("click",".canGoods",function(){
		var ids = [];
		var parent=$(this).parents(".pro-li");
		var param=new Object();
		
		param.id =parent.attr("data-id");
		param.price=parent.find("input[name='proPrice']").val();
		
		ids.push(param);
		new go_add(ids);
	})
	
	$("body").on("click","#all_check",function(){
		b = !b;
//		if(b)$(this).html("取消所有");
//		else $(this).html("选择所有");
		if(b){
			$("#all_check").addClass("all_checked");
			$("#all_check i").attr("class","icon-check");
		}else{
			$("#all_check").removeClass("all_checked");
			$("#all_check i").attr("class","icon-check-empty");
		}
		$("#main_content").find("[name='check']").prop("checked",b);
	})

	$("body").on("click","#more_add",function(){
		var ids = [];
		$.each($("#main_content .pro-li"),function(){
			var param=new Object();
			var b = $(this).find("[name='check']").prop("checked");
			if(b){
				param.id = $(this).attr("data-id");
				param.price=$(this).find("input[name='proPrice']").val();
				ids.push(param);
			};
		});
		new go_add(ids);
	});
}
//商品主内容的加载
//<p class="rh-des">销售量:'+val.buyCount+'</p>\
function main_load(){
	var setData = function(json){
		$.each(json.rows,function(i,val){
			var costPrice = val.costPrice === undefined ? "暂无" : "¥"+val.costPrice;
			var div = '<div class="pro-li clearfix" data-id="'+val.id+'">\
							<div class="col-xs-4 li-lf">\
								<img src="'+val.cover+'">\
							</div>\
							<div class="col-xs-7 li-rh">\
								<p class="rh-tit">'+val.name+'</p>\
								<p class="rh-des">成本价:'+costPrice+'</p>\
								<div class="rh-des">\
									<span>建议售价:</span><span class="money">¥ '+val.price+'</span>\
									<input name="proPrice" type="hidden" value="'+val.price+'">\
								</div>\
							</div>\
							<input style="float: right;" type="checkbox" name="check"/>\
							<a class="rh-addbtn canGoods" href="javascript:void(0)">\
								<i class="iconfont-gl">&#xe629;</i>\
							</a>\
					  </div>';
			$("#main_content .js-load").before(div);
		});
	};
	
	var total;
	var getData = function(json){
		$ajax({
			type:'post',
			url:base_url+'/shop/init_shop.html',
			data:{data:JSON.stringify(json)},
			done:function(json){
				total = json.total;
				new setData(json);
				if(total < size){
					$("#more_temp").hide();
					$("#load_more").hide();
					return;
				}
				if(total == 0){
					$("#more_temp").show();
					$("#load_more").hide();
				}else{
					$("#more_temp").hide();
					$("#load_more").show();
				}
			}
		})
	}
	$("#search_input").val("");
	new getData({now_page:1,row:size});
	//加载更多
	$("#load_more").click(function(){
		var temp = parseInt($("#load_more").attr("data-page"))+1;
		if(temp > Math.ceil(total/size)){
			layer.msg("已无更多");
			return;
		}
		$("#load_more").attr("data-page",temp);
		var paging=search_help();
		paging.typeID1 = typeID1;
		paging.typeID2 = typeID2;
		new getData(paging);
	});
	
	//全选择
	$("#thisf5").click(function(){
		$("#scroller").find("a").removeClass("selected");
		$("#type2").html("");
		init_check();
		$("#main_content .pro-li").remove();
		$("#search_input").val("");
		new getData(search_help(1));
		
		//页数初始化
		$("#load_more").attr("data-page","1");
	});
	
	//类别1选择
	$("#scroller").on("click","a",function(){
		$("#type2").html("");
		var b = $(this).hasClass("selected");
		$("#scroller").find("a").removeClass("selected");
		//paging.typeID2 = -1;
		init_check();
		var paging = search_help(1);
		if(!b){
			typeID1 = $(this).attr("data-id");
			paging.typeID1 = typeID1;
			
			type2($(this).attr("data-id"));
			$(this).addClass("selected");
		}
		else paging = search_help(1);
		$("#main_content>.pro-li").remove();
		new getData(paging);
		
		//页数初始化
		$("#load_more").attr("data-page","1");
	})
	//类别2选择
	$("#type2").on("click","a",function(){
		var b = $(this).hasClass("cla-selected");
		$("#type2").find("a").removeClass("cla-selected");
		init_check();
		var paging = search_help(1);
		if(!b){
			typeID2 = $(this).attr("data-id");
			paging.typeID1 = typeID1;
			paging.typeID2 = typeID2;
			
			$(this).addClass("cla-selected");
		}else paging = search_help(1,typeID1);
		$("#main_content>.pro-li").remove();
		new getData(paging);
		
		//页数初始化
		$("#load_more").attr("data-page","1");
	})
	//排序
	$("#orderUl").on("click","li",function(){
		var i = $(this).index();
		var order;
		$("#order_title").html($(this).find("a").html());
		switch (i) {
		case 0:
			order = "sp.createTime";
			break;
		case 1:
			order = "sp.viewCount";
			break;
		case 2:
			order = "sp.price - sp.disPrice";
			break; 
		}
		var paging = search_help(1);
		paging.typeID1 = typeID1;
		paging.typeID2 = typeID2;
		paging.order = order;
		paging.orderType = "DESC";
		$("#main_content>.pro-li").remove();
		new getData(paging);
	})
	//搜索
	$("#goods_search").click(function(){
		typeID1 = -1;
		typeID2 = -1;
		var paging = search_help(1);
		$("#type2").html("");
		$("#main_content .pro-li").remove();
		new getData(paging);
	})
}

//获得查询json
function search_help(page,typeID1,typeID2){
	var search = $("#search_input").val();
	var page = page == 1 ? "1":$("#load_more").attr("data-page");
	var typeID1 = typeID1 === undefined ?"-1":typeID1;
	var typeID2 = typeID2 === undefined ?"-1":typeID2;
	return {
		more:search,
		now_page:page,
		row:size,
		typeID1:typeID1,
		typeID2:typeID2
	}
}

function init_check(){
	$("#all_check").removeClass("all_checked");
	$("#all_check").find("i").removeClass("icon-check");
	$("#all_check").find("i").addClass("icon-check-empty");
	b = false;
}

// 简化的ajax
function $ajax(option){
	var defVal = {
		async:false,
		type:'post',
		url:'',
		data:'',
		dataType:'json',
		done:function(json){}
	}
	var obj = $.extend({},defVal,option);
	$.ajax({
		async: obj.async,
		type: obj.type,
		url: obj.url,
		data: obj.data,
		dataType: obj.dataType
	}).done(obj.done)
	.error(function(msg){
		if(!msg)alert("提交失败");
	})
}

/**
 * 计算滚动菜单长度
 */
function scollerWidth(){
	var itemWidth=$("#scroller ul li").width();
	var totalCount=$("#scroller ul li").length;
	var barWidth=parseInt(itemWidth*totalCount)+parseInt(30);
//	var barWidth=itemWidth*totalCount;
	$("#scroller").css("width",barWidth+"px");
}

function showQuery(){
	$(".top-title").css("display","none");
	$(".dt-serarea").fadeIn();
	/*$(".serch-int input").focus();
	$(".serch-int input").one("blur",function(){
		$(".top-title").fadeIn();
		$(".dt-serarea").css("display","none");
	});*/
}