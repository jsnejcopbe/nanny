var page=1;
var PROITEMHTML='<div class="col-xs-6">\
					<div class="item-con" data-proid="${proID}">\
						<p class="con-cover">\
							<img src="${img}">\
						</p>\
						<p class="con-title">${name}</p>\
						<div>\
							<span class="con-disprice">${disprice}</span>\
							<span class="con-orgprice">${orgprice}</span>\
						</div>\
						<div class="btn-area">\
							<a class="btn-del" href="javascript:void(0)">\
								<i class="icon-minus-sign"></i>\
							</a>\
							<span class="tip-procount">0</span>\
							<a class="btn-add" href="javascript:void(0)">\
								<i class="iconfont-gl">&#xe629;</i>\
							</a>\
						</div>\
					</div>\
				 </div>';
var classID="";
var totalCount=0;

$(function(){
	//购物车初始化
	countInit();
	$("body").on("click",".btn-add",function(){addFun($(this));});
	$("body").on("click",".btn-del",function(){delFun($(this));});
});

/**
 * 分页
 */
function pagin(){
	page++;
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/proquery.json",
		"Data":"query="+$("#query").val()+"&classID="+classID+
			   "&shopID="+$("#shopID").val()+"&page="+page+"&pageSize="+$("#pageSize").val(),
		"fnSuccess" : function(rtnData){
			layer.closeAll('loading');
			var html="";
			for(var i=0;i<rtnData.length;i++){
				html+=PROITEMHTML.replace("${proID}", rtnData[i].id)
								 .replace("${img}", rtnData[i].cover)
								 .replace("${name}", rtnData[i].name);
				if(rtnData[i].disPrice==0)
					html=html.replace("${disprice}", "¥ "+rtnData[i].price)
							 .replace("${orgprice}", "");
				else
					html=html.replace("${disprice}", "¥ "+rtnData[i].disPrice)
							 .replace("${orgprice}", "¥ "+rtnData[i].price);
			}
			$(".reu-item").append(html);
			queryTip(rtnData);
			selectInit();
			new g_fnImgCheck();
		},
		"fnError"   : function(){layer.closeAll('loading');layer.msg("查询失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 查询操作
 * @param ID
 */
function doQuery(ID){
	$(".sec-tab").css("display","none");
	$(".reu-item").find(".col-xs-6").remove();
	page=0;
	if(ID!=null){
		classID=ID;
		var className=$("#st-seccla-"+ID).attr("data-truename");
		$("#query").val($.trim(className));
	}
	$(".js-clear").css("display","block");
	pagin();
	$(".sec-reu").fadeIn();
}

/**
 * 查询结果提示
 */
function queryTip(rtnData){
	$(".sec-pagin").css("display","none");
	var length=rtnData.length;
	if($(".item-con").length==0 && length==0)
		layer.msg("此条件下查无商品");
	else if($(".item-con").length!=0 && length==0)
		layer.msg("已是最后一页");
	else if(length==$("#pageSize").val())
		$(".sec-pagin").css("display","block");
}

/**
 * 输入框清空
 */
function queryClear(){
	document.getElementById("query").value="";
	$(".reu-item").find(".col-xs-6").remove();
	$(".sec-reu").css("display","none");
	$(".sec-tab").fadeIn();
}

/**
 * 价格计算
 */
function priceCount(target,type){
	var parent=target.parents(".item-con");
	var singlePrice=(parent.find(".con-disprice").text()).replace("¥ ","");
	var price=$(".js-price").text();
	
	if(type=="+")
		price=parseFloat(price)+parseFloat(singlePrice);
	else
		price=price-singlePrice;
	
	if(price>0)
	{
		$(".js-price").text(changeTwoDecimal(price));
		$(".js-procount").text(totalCount);
	}else{
		$(".js-price").text(0);
		$(".js-procount").text(0);
	}
	
}

/**
 * 拼接数据
 */
function createData(target){
	var parent=target.parents(".item-con");
	var price=parent.find(".con-disprice").text();
	var proID=parent.attr("data-proid");
	var count=parent.find(".tip-procount").text();
	//查询是否在数组中已存在，存在则删除
	for(var i=0;i<shopCarPro.length;i++)
	{
		if(shopCarPro[i].proID==proID && !shopCarPro[i].hasOwnProperty("ise")){
			shopCarPro.remove(i);
			break;
		}
	}
	//当数量大于0时，添加s
	if(count>0){
		var param=new Object();
		param.price=price.replace("¥ ","");
		param.count=count;
		param.proID=proID;
		shopCarPro.push(param);
	}
}

/**
 * 返回店铺主页
 */
function backToStore(){
	var strData=JSON.stringify(shopCarPro);
	$("#proCarList").val(strData);
	$("#storeJumpForm").submit();
}

/*******************************************************************购物车部分************************************************/

/**
 * 增加处理
 * @param target
 */
function addFun(target){
	var parent=target.parents(".btn-area");
	var count=parent.find(".tip-procount").text();
	if(count==0)
	{
		parent.find(".btn-del").css("display","inline-block");
		parent.find(".tip-procount").css("display","inline-block");
	}
	if(totalCount==0)
		$(".js-shopcar").append('<span class="float-tip">'+totalCount+'</span>');
	totalCount++;
	$(".float-tip").text(totalCount);
	parent.find(".tip-procount").text(parseInt(count) + parseInt(1));
	
	priceCount(target,"+");
	
	createData(target);
	
	canSend(target, "+");
}

/**
 * 减去处理
 * @param target
 */
function delFun(target){
	var parent=target.parents(".btn-area");
	var count=parent.find(".tip-procount").text() - 1;
	
	//0.减去选择商品的数量
	parent.find(".tip-procount").text(count);
	if(count==0)
	{
		target.removeAttr("style");
		parent.find(".tip-procount").removeAttr("style");
	}
	
	//1.减去总的数量
	totalCount--;
	if(totalCount>0)
		$(".js-shopcar").append('<span class="float-tip">'+totalCount+'</span>');
	else
		$(".float-tip").remove();
	
	priceCount(target,"-");
	
	createData(target);
	
	canSend(target, "-");
}

/**
 * 计算购物价格是否能够满足起送价
 * @param target
 * @param type
 */
function canSend(target,type){
//	var price=min_send_price - $(".js-price").text();
//	if(price>0){
//		$(".js-send").text(price);
//		$(".bot-pay .right>a").addClass("useless");
//	}else if(price==0){
//		$(".js-send").text(0);
//		$(".bot-pay .right>a").addClass("useless");
//	}else
//		$(".bot-pay .right>a").removeClass("useless");
	var price=min_send_price - $(".js-price").text();
	if(price>0){
		$(".js-send").text(price);
		$(".bot-pay .right>a").addClass("useless");
	}else{
		$(".js-send").text(0);
		$(".bot-pay .right>a").removeClass("useless");
	}
}

/**
 * 购物车初始化
 */
function countInit(){
	if(shopCarPro!="")
	{
		var totalPrice=0;
		var sendPrice=$(".js-send").text();
		for(var i=0;i<shopCarPro.length;i++)
		{
			if(!shopCarPro[i].hasOwnProperty("ise")){
				var singlePrice=shopCarPro[i].price;
				var singleCount=shopCarPro[i].count;
				totalPrice=parseFloat(totalPrice)+parseFloat(singlePrice*singleCount);
				totalCount=parseInt(totalCount)+parseInt(singleCount);
			}
		}
		$(".js-price").text(totalPrice);
		$(".js-procount").text(totalCount);
		if(totalPrice>0){
			$(".bot-pay").css("display","block");
			$(".js-shopcar").append('<span class="float-tip">'+totalCount+'</span>');
		}
		
		if(sendPrice-totalPrice>0){
			$(".bot-pay .right>a").addClass("useless");
			$(".js-send").text(sendPrice-totalPrice);
		}
		else{
			$(".bot-pay .right>a").removeClass("useless");
			$(".js-send").text(0);
		}
	}
	
	selectInit();
}

/**
 * 商品选择初始化
 * @param classID
 */
function selectInit()
{
	$(".reu-item .item-con").each(function(){
		var id=$(this).attr("data-proid");
		for(var i=0;i<shopCarPro.length;i++){
			if(id==shopCarPro[i].proID && !shopCarPro[i].hasOwnProperty("ise"))
			{
				$(this).find(".tip-procount").text(shopCarPro[i].count);
				$(this).find(".tip-procount").css("display","inline-block");
				$(this).find(".btn-del").css("display","inline-block");
			}
		}
	});
}



/**
 * 组织商品购物车数据
 */
function addToShop(){
	if($(".useless").length!=0)
		layer.msg("您购买的金额低于起送价");
	else if($("#userID").val()=="")
		showLogin();
	else{
		var array=getShopCarData();
		if(array==null)
			return;
		layer.load(1, {shade: [0.8,'#333']});
		var param={
			"sURL" :BASEPATH+"/users/addprotocar.json",
			"Data" :"arrayData="+JSON.stringify(array)+"&shopID="+$("#shopID").val()+"&userID="+$("#userID").val(),
			"fnSuccess" :function(data){
				layer.closeAll('loading');
				if(data.hasOwnProperty("jump"))
					location.href=BASEPATH+data.jump;
				else
					layer.msg(data.msg);
//				location.href=BASEPATH+"/users/shopcar.html";
			},
			"fnError" :function(){layer.closeAll('loading');layer.msg("添加失败,请联系管理员");}
		};
		new g_fnAjaxUpload(param);
	}	
}

/**
 * 组织购物车数据
 * @returns {Array}
 */
function getShopCarData(){
	var canPass=true;
	for(var i=0;i<shopCarPro.length;i++)
	{
		if(shopCarPro[i].hasOwnProperty("ise") && shopCarPro[i].price>$("#userInter").val()){
			canPass=false;
			break;
		}else{
			shopCarPro[i].shopID=$("#shopID").val();
			shopCarPro[i].userID=$("#userID").val();
		}
	}
	if(canPass==true)
		return shopCarPro;
	else{
		layer.msg("您的积分不足");
		return null;
	} 
}