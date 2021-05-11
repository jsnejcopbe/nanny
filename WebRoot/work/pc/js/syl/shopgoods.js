var LISTHTML='  <li class="list-group-item clearfix">\
					<div class="col-xs-3" style="text-align: center;">\
						<img src="${src}" style="height: 100px;">\
					</div>\
					<div class="col-xs-9">\
						<label>${proName}</label>\
						<input class="form-control" type="text" value="${price}" placeholder="请输入价格" name="price" style="margin-top: 10px;">\
						<input type="hidden" value="${proID}" name="proID">\
					</div>\
				</li>';

var JUMPBLOCKHTML='<div class="jump-pagin">\
						<div>\
							<span>跳转到</span>\
							<input class="jump-page" value="1" type="text">\
							<span>页</span>\
						</div>\
					</div>';

var query = $("#shop_query");
var size = 5;
var shop_url = path+"shop/goods/add.html";

$(function(){
	jQuery.getScript(path+"js/syl/verify.js");
	jQuery.getScript(path+"js/syl/basis.js");
	jQuery.getScript(path+"js/syl/jquery.form.js");
	jQuery.getScript(path+"js/syl/jquery.mypagin.js");
	jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){
		var brands = "";
		//初始化加载所有品牌
		$.ajax({
			type:'get',
			url:path+'admin/init_brand.html',
			dataType:'json'
		}).done(function(json){
			var bs = query.find("#brands");
			bs.attr("data-id",1);
			var data = json_spilt(json,1,size);
			brand_add_html(bs,data);
			brands = json;
		})

		//点击加载更多后
		$("#brands").on("click","#load_more",function(){
			var bs = query.find("#brands");
			var i = parseInt(bs.attr("data-id"))+1;
			bs.attr("data-id",i);
			if(i >= brands.length){
				layer.msg("已无更多");
				return;
			}
			var data = json_spilt(brands,i,size);
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
		var select_url = path+'admin/init_type.html';
		query.find("[name='typeID1']").select({url:select_url,data:0});
		query.on("change","[name='typeID1']",function(e){
			var type = $(this).val();
			query.find("[name='typeID2']").select({url:select_url,data:type})
		})
		
		load_table();
		
		$("#goods_table").on("click",".shop_good",function(){
			$(this).parents("tr").find(".check").prop("checked",true);
//			var id = $(this).parents("tr").find("#id").val();
//			var temp = [];
//			temp.push(id);
//			shop_ajax(temp);
			createProHtml();
		})
		
		$("#goods_add").click(function(){
//			var tab = $("#goods_table");
//			var id = [];
//			$.each(tab.find("tr"),function(){
//				var b = $(this).find(".check").prop("checked");
//				
//				if(b)id.push($(this).find("#id").val());
//			})
//			shop_ajax(id);
			createProHtml();
		})
	 
	})
});

function load_table(){
	var a = function(bs){
		$.each($(".coverImg"),function(i){
			$(this).prop("src",bs[i].cover);
		})
	}
	
	var funs = function(bs){
		new a(bs);
		document.getElementById("checkType").value="";
	}
	
	//加载表格内容
	var v = {
			url:path+'shop/init_shop.html?cs='+$("#checkType").val(),
			querydiv:query,
			autopagin:false,
			row:20,
			funs:funs,
			diys:['<img src="" class="coverImg"></img>',"<a class='shop_good btn btn-primary' href='javascript:;'><i class='fa fa-plus'></i>铺货</a>"]
	};
	$("#goods_table").mytable(v);
	//<a class='btn btn-info shop_sup'  style='display: table-caption; margin-top: 5px;' href='javascript:;'><i class='fa fa-search'></i>供应商</a>
	sessionCallBackFun();
	
	//翻页跳转html&事件绑定
	addJumpBlock();
}






function sessionCallBackFun(){
	if($("#checkType").val()!="" && $("#seNowPage").length){
		var select_url = path+'admin/init_type.html';
		var index=$("#seNowPage").val();
		$("#more").val($("#seMore").val());
		$("select[name='typeID1']").val($("#seType1").val());
		if($("#seType1").val()!=""){
			query.find("[name='typeID2']").select({url:select_url,data:$("#seType1").val()});
			$("select[name='typeID2']").val($("#seTypeID2").val());
		}
		$("#hide_brand").val($("#seBrandID").val());
		$(".pagination .page[data-index='"+index+"']").click();
	}
	document.getElementById("checkType").value="";
}

function shop_ajax(id){
	if(id.length <=0){
		layer.msg("请至少勾选一行");
		return;
	}
	
	layer.load(1, {shade: [0.8,'#333']});
	
	$.ajax({
		type:'post',
		url:shop_url,
		data:{data:JSON.stringify(id)}
	}).done(function(msg){
		
		var checkList=$("#goods_table tbody .check:checked");
		
		if(checkList.length==20)
			location.reload(true);
		else{
			layer.closeAll("loading");
			$("#goods_table .tr").each(function(){
				//var b=$(this).find(".check").value();
				var b=$(this).find(".check").prop("checked");
				if(b){
					$(this).css("display","none");
				}	
			});
			
			$("#priceModal").modal("hide");
			
			layer.msg("铺货成功");
		}
	})
}

//添加品牌内容
function brand_add_html(bs,data){
	$.each(data.rows,function(i,val){
		bs.prepend('<a class="list-group-item brand" data-id="'+val.id+'"> <img style="height: 50px;width: 50px;border: #fff 3px solid !important;" class="chat-user-avatar" src="'+val.icon+'" alt="">'+val.name+'</a>');
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

/**
 * 简单查询
 */
function doEasyQuery(){
	var proName=$("#inputQuery").val();
	$("#more").val(proName);
	$("#shop_query button").click();
}

/**
 * 拼接价格修改html
 */
function createProHtml(){
	$("#priceModal").find(".list-group").remove();
	var html="";
	$("#goods_table .tr").each(function(){
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

/**
 * 页数跳转
 */
function addJumpBlock(){
	$("#pagina_div").append(JUMPBLOCKHTML);
	$(".jump-pagin").on("keypress",".jump-page",function(event){
		if(event.keyCode == "13"){
			var value=$(this).val();
			$("#pagination>.pagination").append('<li data-index="'+value+'" class="page" style="display:none;"><a href="javascript:void(0);">'+value+'</a></li>');
			$(".page[data-index='"+value+"']").find("a").click();
		}  
	});
}