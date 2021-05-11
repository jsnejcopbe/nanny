var LISTHTML='  <li class="list-group-item clearfix">\
					<div class="col-xs-3" style="text-align: center;">\
						<img src="${src}" style="height: 100px;max-width: 100%;">\
					</div>\
					<div class="col-xs-9">\
						<label>${proName}</label>\
						<input class="form-control" type="text" value="${price}" placeholder="请输入价格" name="price" style="margin-top: 10px;">\
						<input type="hidden" value="${proID}" name="proID">\
					</div>\
				</li>';

$(function(){
	var query = $("#shop_query1");
	var brands = "";
	//初始化加载所有品牌
	$.ajax({
		type:'get',
		url:path+'admin/init_brand.html',
		dataType:'json'
	}).done(function(json){
		var bs = query.find("#brands");
		bs.attr("data-id",1);
		var data = json_spilt(json,1,5);
		brand_add_html(bs,data);
		brands = json;
	})

	//点击加载更多后
	$("#brands").on("click","#load_more",function(){
		var bs =query.find("#brands");
		var i = parseInt(bs.attr("data-id"))+1;
		bs.attr("data-id",i);
		if(i >= brands.length){
			layer.msg("已无更多");
			return;
		}
		var data = json_spilt(brands,i,5);
		brand_add_html(bs,data);
	})

	//点击品牌后
	$("#brands").on("click",".brand",function(){
		var b = $(this).hasClass("active");
		$.each(query.find("#brands .brand"),function(){
			$(this).removeClass("active");
		})
		if(!b){
			query.find("#hide_brand").val($(this).attr("data-id"));
			$(this).addClass("active");
		}else query.find("#hide_brand").val("-1");
	})
	
	//联动类别
	var query = $("#shop_query1");
	var select_url = path+'admin/init_type.html';
	query.find("[name='typeid1']").select({url:select_url,data:0});
	query.on("change","[name='typeid1']",function(e){
		var type = $(this).val();
		query.find("[name='typeid2']").select({url:select_url,data:type});
	})
	
	
	query.find("[name='typeid1']").val(parid);
	query.find("[name='typeid1']").change();
	query.find("[name='typeid2']").val(s_value);
	
	load_table();
	
	$("#newpro_table").on("click",".shop_good",function(){
		$(this).parents("tr").find(".check").prop("checked",true);
//		var id = $(this).parents("tr").find("#id").val();
//		var temp = [];
//		temp.push(id);
//		shop_ajax(temp);
		createProHtml();
	})
	
	$("#goods_add").click(function(){
//		var tab = $("#newpro_table");
//		var id = [];
//		$.each(tab.find("tr"),function(){
//			var b = $(this).find(".check").prop("checked");
//			
//			if(b)id.push($(this).find("#id").val());
//		})
//		shop_ajax(id);
		createProHtml();
	})
})


function load_table(){
	var a = function(bs){
		$.each($(".coverImg"),function(i){
			$(this).prop("src",bs[i].cover);
		})
	}
	
	var funs = function(bs){
		new a(bs);
	}
	
	//加载表格内容
	
}

function shop_ajax(id){
	if(id.length <=0){
		layer.msg("请至少勾选一行")
		return;
	}
	$.ajax({
		type:'post',
		url: path+"shop/goods/add.html",
		data:{data:JSON.stringify(id)}
	}).done(function(msg){
		var checkList=$("#newpro_table tbody .check:checked");
		
		if(checkList.length==10)
			location.reload(true);
		else{
			$("#newpro_table .tr").each(function(){
				//var b=$(this).find(".check").value();
				var b=$(this).find(".check").prop("checked");
				if(b){
					$(this).css("display","none");
				}	
			});
			
			$("#priceModal").modal("hide");
			
			layer.msg("铺货成功");
			//window.location.reload(true);
		}
	})
}

//添加品牌内容
function brand_add_html(bs,data){
	$.each(data.rows,function(i,val){
		
		if(brid==val.id){
				bs.prepend('<a class="list-group-item brand active" data-id="'+val.id+'"> <img style="height: 50px;width: 50px;border: #fff 3px solid !important;" class="chat-user-avatar" src="'+val.icon+'" alt="">'+val.name+'</a>');

		}else{
			
			bs.prepend('<a class="list-group-item brand" data-id="'+val.id+'"> <img style="height: 50px;width: 50px;border: #fff 3px solid !important;" class="chat-user-avatar" src="'+val.icon+'" alt="">'+val.name+'</a>');
		}
	})
}

//品牌的分页
function json_spilt(json,now_page,row){
	var newjson = {rows:[]};
	var data = json;
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

function todays(num) {
	var days=$("#days").val();
	
	if(num==0){
		$("#days").val(parseInt(days)+1);
	}if(num==1){
		$("#days").val(parseInt(days)-1);
	}
	$("#sform").submit();
	
}

/**
 * 拼接价格修改html
 */
function createProHtml(){
	$("#priceModal").find(".list-group").remove();
	var html="";
	$("#newpro_table .tr").each(function(){
		if($(this).find(".check").prop("checked") && ($(this).css("display"))!="none")
			html+=LISTHTML.replace("${src}", $(this).find(".coverImg").attr("src"))
						  .replace("${price}", $(this).find("#price").text())
						  .replace("${proID}", $(this).find("#id").val())
						  .replace("${proName}", $(this).find("#name").text())
	});
	$("#priceModal").find(".modal-body").append('<ul class="list-group">'+html+"</ul>");
	$("#priceModal").modal();
}	

/**
 * 保存修改的价格
 */
function savePrice(){
	var data=new Array();
	var canPass=true;
	$("#priceModal .list-group-item").each(function(){
		var ID=$(this).find("input[name='proID']").val();
		var price=$(this).find("input[name='price']").val();
		var param=new Object();
		param.id=ID;
		param.price=price;
		data.push(param);
		if(price=="" && !(/^\d+(\.\d+)?$/).test(price)){
			layer.msg("请输入数字类型的价格");
			canPass=false;
		}
	});
	if(canPass==true)
		shop_ajax(data);
}