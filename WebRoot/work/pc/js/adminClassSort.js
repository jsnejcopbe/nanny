var CONTAIR='<li class="list-group-item clearfix js-class">${html}</li>';




$(function(){
	btnInit();
	$("body").on("click",".it-rh .btn",function(){
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
});

/**
 * 元素移动操作
 * @param tar
 * @param pre
 * @param next
 * @param type
 * @param innerHtml
 */
function blockMove(tar,pre,next,type,innerHtml){
	switch (type) {
	case 0:
		tar.remove();
		$(".list-group .list-group-item:first-child").before(CONTAIR.replace("${html}", innerHtml));
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
		$(".btn-area").before(CONTAIR.replace("${html}", innerHtml));
	default:
		break;
	}
	btnInit();
}




/**
 * 按钮初始化
 */
function btnInit(){
	$(".it-rh .btn").removeClass("disabled");
	$(".list-group .list-group-item:first-child").find(".js-top").addClass("disabled");
	$(".list-group .list-group-item:first-child").find(".js-up").addClass("disabled");
	$(".btn-area").prev().find(".js-down").addClass("disabled");
	$(".btn-area").prev().find(".js-bottom").addClass("disabled");
}




/**
 * 创建数据
 */
function createData(){
	var index=$(".js-class").length;
	var arrayData=new Array();
	$(".js-class").each(function(){
		var param=new Object();
		param.classID=$(this).find(".classID").val();
		param.memo=index;
		index--;
		arrayData.push(param);
	});
	return arrayData;
}


/**
 * 保存排序
 */
function updateClassSor(type){
	var data;
	if(type=='0'){
		data=createData();
	}else{
		data=createCHData();
	}
			
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/admin/updatesort.json",
		"Data":"json="+JSON.stringify(data),
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			setTimeout('location.reload(true)', 1000);
		},
		"fnError":function(){
			layer.closeAll('loading');
			layer.msg("更新失败,请联系管理员");
		}
	};
	new g_fnAjaxUpload(param);
}

/*************************************************************************************************************************************************
 * 子类操作
 */


var CHILDHTML='<li class="list-group-item clearfix chclass">\
					<div class="col-xs-6 it-lf "><span>${chname}</span></div>\
					<div class="col-xs-6 it-rh">\
						<a href="javascript:void(0)" class="btn btn-danger js-top">\
							<i class="fa fa-angle-double-up"></i>\置顶</a>\
						<a href="javascript:void(0)" class="btn btn-danger js-up">\
							<i class="fa fa-angle-up"></i>上移</a>\
						<a href="javascript:void(0)" class="btn btn-info js-down">\
							<i class="fa fa-angle-down"></i>下移</a>\
						<a href="javascript:void(0)" class="btn btn-info js-bottom">\
							<i class="fa fa-angle-double-down"></i>置底</a>\
						</div>\
						<input class="chID" type="hidden" value="${chid}">\
					</li>';




/**
 * 子类展示排序
 */
function showChild(id){
	var html="";
	
	var param={
		"sURL":BASEPATH+"/admin/childsort.html",
		"Data":"parid="+id,
		"fnSuccess":function(data){
			//alert(data.msg);
			$(".par").css("display","none");
			 var array= data.msg;
			 
			for(var i=0;i<array.length;i++){
				 html+=CHILDHTML.replace("${chname}", array[i].name)
 				   				.replace("${chid}", array[i].id);
			}
			
			
			$(".child").prepend(html);
			//$(".par").insertBefore(html);
			$(".child").css("display","block");
			
			//layer.msg(data.msg);
			
			CONTAIR='<li class="list-group-item clearfix chclass">${html}</li>';
			
		},
		"fnError":function(){
			
			layer.msg("失败,请联系管理员");
		}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 创建子类数据
 */
function createCHData(){
	var index=$(".chclass").length;
	var arrayData=new Array();
	$(".chclass").each(function(){
		var param=new Object();
		param.classID=$(this).find(".chID").val();
		param.memo=index;
		index--;
		arrayData.push(param);
	});
	return arrayData;
}

/**
 * 返回父类列表
 */
function  goblack() {
	CONTAIR='<li class="list-group-item clearfix js-class">${html}</li>';
	$(".child").css("display","none");
	$(".par").removeAttr('style');
	$(".chclass").remove();
}

