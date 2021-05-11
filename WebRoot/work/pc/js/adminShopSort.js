var CONTAIR='<li class="list-group-item" data-pat="${dispaID}">${html}</li>';

var BLOCKHTML='<div class="add-block">\
					<h2>${addName}</h2>\
					<p>${addDet}</p>\
					<ul class="list-group">\
						${shopList}\
					</ul>\
			   </div>';
var LISTHTML='<li class="list-group-item" data-pat="${dispaID}">\
					<span>${shopName}</span>\
					<div class="btn-area">\
					<a href="javascript:void(0)" class="btn btn-danger btn-sm js-top">\
						<i class="fa fa-angle-double-up"></i>\
						置顶\
					</a>\
					<a href="javascript:void(0)" class="btn btn-danger btn-sm js-up">\
						<i class="fa fa-angle-up"></i>上移\
					</a>\
					<a href="javascript:void(0)" class="btn btn-info btn-sm js-down">\
						<i class="fa fa-angle-down"></i>下移\
					</a>\
					<a href="javascript:void(0)" class="btn btn-info btn-sm js-bottom">\
						<i class="fa fa-angle-double-down"></i>\
						置底\
					</a>\
					</div>\
			  </li>';

$(function(){
	//绑定事件
	$(".tab-pane-btn").on("click",function(){
		var id=$(this).attr("id");
		if($.trim(id)=="tab-add")
			$("#queryForm").attr("action",BASEPATH+"/admin/shopsort.html?type=1");
		else
			$("#queryForm").attr("action",BASEPATH+"/admin/shopsort.html");
		$("#queryForm").submit();
	});
	
	$(".js-doquery").on("click",function(){
		$(this).find("input[name='page']").remove();
		$("#queryForm").submit();
	});
	
	$(".js-checkshop").on("click",function(){createHtml($(this).attr("data-shop"), $(this).attr("data-add"));});
	
	$("body").on("click",".list-group-item .btn",function(){
		//0.查询相邻元素
		var item=$(this).parents(".list-group-item");
		var preEl=item.prev();//前一个元素
		var nextEl=item.next();//后一个元素
		var innerHtml=item.html();//内部html
		
		//1.根据类型进行对应操作
		var className=$(this).attr("class");
		if(className.indexOf("js-top")!=-1)
			blockMove(item, preEl, nextEl, 0, innerHtml);
		else if(className.indexOf("js-up")!=-1)
			blockMove(item, preEl, nextEl, 1, innerHtml);
		else if(className.indexOf("js-down")!=-1)
			blockMove(item, preEl, nextEl, 2, innerHtml);
		else if(className.indexOf("js-bottom")!=-1)
			blockMove(item, preEl, nextEl, 3, innerHtml);
	});
	
	//初始化操作
	var panelHeigh=$(".col-xs-8 .panel-archon").height();
	$(".col-xs-4 .panel-archon").css("min-height",panelHeigh+"px");
	
	$("tbody tr:first").find(".js-checkshop").click();
});

/**
 * 拼接右侧地址列表排序html
 * @param shopID
 * @param addID
 */
function createHtml(shopID,addID){
	$(".col-xs-4 .panel-archon .add-block").remove();
	var data=shopID==null?("addID="+addID):("shopID="+shopID);
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/admin/shopaddlist.json",
		"Data":data,
		"fnSuccess":function(data){
			var html="";
			$.each(data,function(i,val){
				html+=BLOCKHTML.replace("${addName}", val.addName)
							   .replace("${addDet}", val.addDet)
							   .replace("${shopList}", createList(val.addArray));
			});
			$(".col-xs-4 .panel-body").append(html);
			layer.closeAll('loading');
			btnInit();
		},
		"fnError":function(){
			layer.closeAll('loading');
			layer.msg("查询失败");
		}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 创建商户列表下拉框
 * @param array
 * @returns {String}
 */
function createList(array){
	var html="";
	$.each(array,function(i,val){
		html+=LISTHTML.replace("${shopName}", val.shop_name)
					  .replace("${dispaID}", val.dispaID);
	});
	return html;
}

/**
 * 按钮初始化
 */
function btnInit(){
	$(".col-xs-4 .btn").removeClass("disabled");
	$(".add-block").each(function(){
		$(this).find(".list-group-item:first").find(".js-top").addClass("disabled");
		$(this).find(".list-group-item:first").find(".js-up").addClass("disabled");
		$(this).find(".list-group-item:last").find(".js-down").addClass("disabled");
		$(this).find(".list-group-item:last").find(".js-bottom").addClass("disabled");
	});
}

/**
 * 元素移动操作
 * @param tar
 * @param pre
 * @param next
 * @param type
 * @param innerHtml
 */
function blockMove(tar,pre,next,type,innerHtml){
	var parent=tar.parents(".list-group");
	switch (type) {
	case 0:
		tar.remove();
		parent.find(".list-group-item:first").before(CONTAIR.replace("${html}", innerHtml)
															.replace("${dispaID}", tar.attr("data-pat")));
		break;
	case 1:
		tar.remove();
		pre.before(CONTAIR.replace("${html}", innerHtml));
		break;
	case 2:
		tar.remove();
		next.after(CONTAIR.replace("${html}", innerHtml));
		break;
	case 3:
		tar.remove();
		parent.find(".list-group-item:last").after(CONTAIR.replace("${html}", innerHtml)
														  .replace("${dispaID}", tar.attr("data-pat")));
	default:
		break;
	}
	btnInit();
}

/**
 * 保存排序设置
 */
function saveOrderSet(){
	layer.load(1, {shade: [0.8,'#333']});
	var data=createOrderData();
	var param={
		"sURL":BASEPATH+"/admin/shopareasort.json",
		"Data":"dataArray="+JSON.stringify(data),
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("保存失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 拼接顺序数据
 */
function createOrderData(){
	var array=new Array();
	$(".add-block").each(function(){
		$.each($(this).find(".list-group-item"),function(i,val){
			var param=new Object();
			param.dispaID=$(val).attr("data-pat");
			param.index=i;
			array.push(param);
		});
	});
	return array;
}
