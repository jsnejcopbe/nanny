var MENUHTML='<ul class="fl-con" data-limit="5">${list}</ul>\
			  <i class="arrow arrow_out"></i>\
			  <i class="arrow arrow_in"></i>';
var NULLLISTHTML='<div class="bot-fl">\
					  <ul class="fl-con" data-limit="5">\
						  <li class="fl-con-it js-menu add-btn">\
								<a href="javascript:void(0)">\
									<i class="icon-plus icon-2x"></i>\
								</a>\
						  </li>\
					  </ul>\
					  <i class="arrow arrow_out"></i>\
					  <i class="arrow arrow_in"></i>\
				  </div>';
var NOWEDITMENU;//当前目标选择器

$(function(){
	//事件绑定
	$("body").on("click",".js-menu",function(){
		NOWEDITMENU=$(this);
		selectItem($(this));
		return false;
	});
	$(".js-menuNameInp").on("input propertychange",function(){
		var inputName=$(this).val()==""?"菜单名称":$(this).val();
		NOWEDITMENU.children("a").text(inputName);
		$(".js-menuName").text(inputName);
	});
	$(".js-menuUrl").on("input propertychange",function(){
		NOWEDITMENU.attr("data-url",$(this).val());
	});
	
	//初始化高度
	var panelHeigh=$(".fun-ma .col-xs-4").height();
	$(".fun-ma .ma-set").css("min-height",panelHeigh+"px");
	$(".pre-bot .js-menu:first").click();
});

/**
 * 菜单按钮选择
 */
function selectItem(target){
	//0.清空样式
	$(".js-menu").removeClass("bot-sl");
	target.addClass("bot-sl");
	target.parents(".js-menu").addClass("bot-sl");
	
	var className=target.attr("class");
	if(className.indexOf("add-btn")!=-1)//场景1：点击增加按钮
		addNewMenu(target);
	else{
		if(className.indexOf("col-xs-4")!=-1)//场景2：点击一级菜单按钮
			showChildMenu(target);
		
		selectedMenu(target)
	}
}

/**
 * 添加新菜单
 * @param target
 */
function addNewMenu(target){
	var html="";
	if((target.attr("class")).indexOf("col-xs-4")!=-1)
		html='<div class="js-menu col-xs-4" data-url=""><a href="javascript:void(0)">菜单名称</a>'+NULLLISTHTML+'</div>';
	else
		html='<li class="fl-con-it js-menu" data-url=""><a href="javascript:void(0)">菜单名称</a></li>';
	var parent=(target.parent(".pre-bot").length==0) ? target.parent(".fl-con"):target.parent(".pre-bot");
	parent.children(".add-btn").before(html);
	parent.children(".add-btn").prev(".js-menu").click();
	if((parent.children(".js-menu").length) > parent.attr("data-limit"))
		parent.children(".add-btn").remove();
}

/**
 * 显示子菜单
 */
function showChildMenu(target){
	$(".bot-fl").css("display","none");
	var floatMenu=target.find(".bot-fl");
	floatMenu.removeAttr("style");
}

/**
 * 二级目录检查
 */
function createMenuHtml(parent){
	if(parent.find(".js-menu").length<5 && parent.find(".add-btn").length==0)
		parent.append('<li class="fl-con-it js-menu add-btn"><a href="javascript:void(0)"><i class="icon-plus icon-2x"></i></a></li>');
}

/**
 * 删除菜单
 */
function deleteMenu()
{
	var parent=(NOWEDITMENU.parent(".pre-bot").length==0) ? NOWEDITMENU.parent(".fl-con"):NOWEDITMENU.parent(".pre-bot");
	layer.confirm('删除一级菜单将同时删除子菜单内容，是否继续?', function(index){
		 NOWEDITMENU.remove();
		 
		 if((parent.attr("class")).indexOf("fl-con")!=-1)
			 createMenuHtml(parent);
		 
		 if($(".pre-bot>.js-menu").length<3 && $(".pre-bot>.add-btn").length==0)
			 $(".pre-bot").append(' <div class="js-menu col-xs-4 add-btn">\
											<a href="javascript:void(0)">\
											<i class="icon-plus"></i>\
										</a>\
									</div>');
		 layer.closeAll();
	});  
}

/**
 * 选中菜单
 * @param target
 */
function selectedMenu(target){
	var menuName=target.children("a").text();
	$(".js-menuName").text($.trim(menuName));
	$(".js-menuNameInp").val($.trim(menuName));
	
	if(target.find(".fl-con .js-menu").length>1){
		$(".js-errortip").css("display","block");
		$(".set-con").css("display","none");
		$(".js-menuUrl").val("");
	}else{
		$(".js-errortip").css("display","none");
		$(".set-con").css("display","block");
		$(".js-menuUrl").val(target.attr("data-url"));
	}
}

/**
 * 组织菜单数据
 * @param select
 * @returns {Array}
 */
function createMenuData(select){
	var data=new Array();
	var isRoop=false;
	if(select==null){
		select=$(".pre-bot>.js-menu")
		isRoop=true;
	}
		
	$.each(select,function(i,val){
		if($(val).attr("class").indexOf("add-btn")==-1)
		{
			var param=new Object();
			param.name=$.trim($(val).children("a").text());
			param.type="view";
			param.url=encodeURI($(val).attr("data-url"));
			
			if(isRoop==true)//递归查询二级菜单集合
				param.sub_button=createMenuData($(val).find(".fl-con>.js-menu"))
			
			data.push(param);
		}
	});
	return data;
}

/**
 * 更新微信菜单
 */
function updateWeiXinMenu(){
	//0.组织数据
	layer.load(1, {shade: [0.8,'#333']});
	var jsonStr=JSON.stringify(createMenuData());
	var param={
		"sURL":BASEPATH+"/admin/updatewxmenu.json",
		"Data":{"json":jsonStr},
		"fnSuccess":function(data){
			layer.closeAll('loading');
			if(data.errcode==0)
				layer.msg("修改成功");
			else
				layer.msg("微信服务器报错，错误码："+data.errcode);
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("修改失败");}
	}
	new g_fnAjaxUpload(param);
}