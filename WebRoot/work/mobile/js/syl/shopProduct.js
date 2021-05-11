var size = 20;//每页显示多少

$(function(){ 
	$.ajaxSetup({cache:false});
	 
	$("#onoffer_btn").click(function(){
		$("#onoffer .item").remove();
		sellAndsold_load(0,$("#onoffer"));
		checkBoxInit();
	});
	
	$("#soldout_btn").click(function(){
		$("#soldout .item").remove();
		sellAndsold_load(1,$("#soldout"));
		checkBoxInit();
	});
	
	$("#onoffer_btn").click();
	
	$("body").on("click",".all_check",function(){
		var target=$(this);
		checkBoxSel(target);
	});
	
	$(".js-firclass").on("change",function(){
		var type=$(this).val();
		if(type!=-1)
		{
			getSecClass(type);
		}else
			$(".js-secclass .op-item").remove();
		doQuery();
	});
	
	$(".js-secclass").on("change",function(){doQuery();});
});

function doQuery()
{
	$("#onoffer>.item").remove();
	$("#onoffer>.noStatebox").remove();
	var obj=new Object();
	obj.typeID1=$(".js-firclass").val();
	obj.typeID2=$(".js-secclass").val();
	obj.sit="aaaa";
	new getData(obj,$("#onoffer"));
}

/**
 * 获得二级分类
 */
function getSecClass(type){
	var param={
		"sURL":base_url+"/shop/getsecclassajax.json",
		"Data":"type="+type,
		"fnSuccess":function(data){
			var html="";
			for(var i=0;i<data.length;i++)
			{
				html+='<option class="op-item" value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			$(".js-secclass").append(html);
		},
		"fnError" : function(){layer.msg("查询二级分类失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 商品上下架
 */
function proUpAndRemove(){
	if($(".btn-canuse").length==0){
		layer.msg("请至少选择一项");
		return;
	}
	layer.load(1, {shade: [0.8,'#333']});
	var dataArray=getSelectedData();
	var prosta;
	if($("#onoffer").attr("style")==null || ($("#onoffer").attr("style")).indexOf("block")!=-1)
		prosta="reomve";
	else
		prosta="up";
	$.ajax({
		type : "POST",
		url  : base_url+"/shop/proupandremove.json",
		data : "jsonData="+JSON.stringify(dataArray)+"&proSta="+prosta,
		success : function(rtnData){
			layer.closeAll('loading');
			var data=eval('(' + rtnData + ')');
			layer.msg(data.msg);
			location.reload(true);
		},
		error : function(){layer.closeAll('loading');layer.msg("提交失败");}
	});
}

/**
 * 跳转至价格库存修改页
 */
function jumpToProDataEdit(){
	if($(".btn-canuse").length==0)
		layer.msg("请至少选择一项");
	else{
		$("#jsonData").val(JSON.stringify(getSelectedData()));
		$("#jumpForm").submit();
	}
}

/**
 * 获得选择的商品数据
 */
function getSelectedData(){
	var dataArray=new Array();
	$(".all_checked").each(function(){
		if(($(this).attr("class")).indexOf("js-selall")==-1)
		{
			var parent=$(this).parents(".item");
			var param=new Object();
			param.id=parent.attr("data-id");
			param.price=(parent.find(".curPrice").text()).replace("￥","");
			param.inventory=parent.find(".inventory").text();
			param.proName=parent.find("h1").text();
			param.img=parent.find(".imgbox").find("img").attr("src");
			
			dataArray.push(param);
		}
	});
	return dataArray;
}

/**
 * 复选框与按钮初始化
 */
function checkBoxInit(){
	$(".all_check").removeClass("all_checked");
	$(".all_check>i").attr("class","icon-check-empty");
	$(".blc-fl a").removeClass("btn-canuse");
}

/**
 * 复选框选中检查
 * @param target
 */
function checkBoxSel(target){
	var className=target.attr("class");
	$(".all_check>i").attr("class","icon-check-empty");
	$(".blc-fl a").removeClass("btn-canuse");
	
	if(className.indexOf("js-selall")!=-1)
	{
		if(className.indexOf("all_checked")!=-1)
			$(".all_check").removeClass("all_checked");
		else
			$(".all_check").addClass("all_checked");
	}else{
		if(className.indexOf("all_checked")!=-1){
			$(".js-selall").removeClass("all_checked");
			target.removeClass("all_checked");
		}
		else{
			target.addClass("all_checked");
			if($(".all_checked").length==($(".all_check").length - 1))
				$(".js-selall").addClass("all_checked");
		}
	}
	$(".all_checked>i").attr("class","icon-check");
	
	if($(".all_checked").length!=0)
		$(".blc-fl a").addClass("btn-canuse");
}

/********************************************************************************************************************************/
//设置数据
var setData = function(json,obj){
		var null_div = '<div class="noStatebox flex-center">\
							<div class="box">\
								<p>\
									<i class="iconfont"></i>\
								</p>\
								<p class="txt">没有记录</p>\
							</div>\
						</div>';
		if(json.total > 0){
			if(json.rows==0)
			{
				layer.msg("已无更多");
				return;
			}else{
				$.each(json.rows,function(i,val){
					var buyCount = val.buyCount === undefined ? "0" : val.buyCount;
					var div = '<div class="item" data-id="'+val.id+'">\
					<div class="itemInfo fixed" data-info="76052">\
					<div class="imgbox">\
					<img src="'+val.cover+'">\
					</div>\
					<div class="infobox">\
					<h1>'+val.name+'</h1>\
					<p class="price"><span class="curPrice">￥'+val.price+'</span></p>	\
					<p><span>库存：<span class="inventory">'+val.inventory+'</span></span></p> \
					</div>\
					</div>\
					<p class="itemOperat">\
					<span class="view"><i class="iconfont"></i></span>\
					<span class="goods" data-src="http://7xofwb.com2.z0.glb.qiniucdn.com/VDPRODUCTCODE_76052.png"><i class="iconfont"></i></span>\
					</p>\
					<a class="all_check" href="javascript:void(0)">\
					<i class="icon-check-empty"></i>\
					</a>\
					</div>';
					obj.append(div);
				});
				return;
			}
		}
		obj.html(null_div);
		
//<p><span>总销量：'+buyCount+'</span><span>库存：'+val.inventory+'</span></p>		
}
//获取数据
var getData = function(data,obj){
	var total;
	$ajax({
		url:base_url+'/admin/init_shop.html',
		data:{data:JSON.stringify(data)},
		done:function(json){
			new setData(json,obj);
			total = json.total;
		}
	});
	if(data.row * total <=size)$("#load_more").hide();
	else $("#load_more").show();
	return {
		total:total
	}
}
// 出售/下架 数据加载
function sellAndsold_load(isuse,obj){
	var more = $("#load_more");
		more.attr("data-page",1);
		
	var option = {
			now_page:more.attr("data-page"),
			row:size,
			isUse:isuse,
			typeID1:$(".js-firclass").val(),
			typeID2:$(".js-secclass").val()
	};
	
	bind_subMenu(option,obj);
	
	new getData(option,obj);
	$("#load_more").click(function(){
		var temp = parseInt(more.attr("data-page"))+1;
//		if(temp > Math.ceil(data.total/size)){
//			layer.msg("已无更多");
//			return;
//		}
		more.attr("data-page",temp);
		var option = {
				now_page:more.attr("data-page"),
				row:size,
				isUse:isuse,
				typeID1:$(".js-firclass").val(),
				typeID2:$(".js-secclass").val()
		};
		new getData(option,obj);
	});
}

//绑定子菜单 排序
function bind_subMenu(option,obj){
	$(".subMenu").unbind('click');
	$(".subMenu").on("click","li",function(){
		$("#onoffer .item").remove();
		$("#soldout .item").remove();
		var b = $(this).attr("data-flag");
		$(".subMenu li").removeClass("cur");
		$(".subMenu li i").removeClass("cur");
		$(".subMenu li").attr("data-flag",false);
		if(b!="true"){
			$(this).attr("data-flag",true);
			$(this).addClass("cur");
			$(this).find(".down").addClass("cur");
			
			option.order = $(this).attr("data-info");
			option.orderType = "desc";
			new getData(option,obj)
			
		}else{
			$(this).addClass("cur");
			$(this).find(".up").addClass("cur");
			
			option.order = $(this).attr("data-info");
			option.orderType = "asc";
			new getData(option,obj)
		}
	})
}

function $ajax(option){
	var defVal = {
		url:'',
		data:'',
		dataType:'json',
		done:function(json){
			
		}
	}
	var obj = $.extend({},defVal,option);
	$.ajax({
		async:false,
		type:'post',
		url:obj.url,
		data:obj.data,
		dataType:obj.dataType
	}).done(obj.done)
	.error(function(msg){
		if(!msg)alert("提交失败")
	})
}