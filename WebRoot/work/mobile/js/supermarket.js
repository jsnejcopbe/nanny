var scrHeight=document.body.clientHeight;
var PROHTMLCON='<div id="wrap-right-${id}" class="col-xs-9 js-wrap">\				<div class="pro-con" id="pro-con-${id}">\					${conlist}\					<div class="pro-logo"><img src="/nanny/images/logo.jpg" style="width:100%;"></div>\				</div>\				</div>';
var PROHTML='<div class="pro-item clearfix" data-proid=${proID }>\				<a class="col-xs-4" href="javascript:toDetPage(${proID })">\					<img src="${cover }" width="45" height="45">\				</a>\				<div class="col-xs-8">\					<p class="pro-name">${name }</p>\					<p class="pro-sale">销量：${buyCount }</p>\					<p class="pro-price">${disPrice }\				        <span class="past">${price }</span>\					</p>\					${exc-btn}\					<div class="btn-area">\						<a class="btn-del" href="javascript:void(0)">\							<i class="icon-minus-sign"></i>\						</a>\						<span class="tip-procount">0</span>\						<a class="btn-add" href="javascript:void(0)">\							<i class="iconfont-gl">&#xe629;</i>\						</a>\					</div>\				</div>\		     </div>';

var totalCount=0;
var wrap1;
var wrap2;

$(function(){
	scorllerCount($("#firstClaID").val());
//	layer.load(1, {shade: [0.8,'#333']});
//	$(".menu-con>.menu-item:first").addClass("selected");
//	$(".menu-con>.menu-item:first").find("a").addClass("selected");
	
	var nowClassID=$("#firstClaID").val();
	$(".menu-item a[data-id='"+nowClassID+"']").addClass("selected");
	$(".menu-item a[data-id='"+nowClassID+"']").parents(".menu-item").addClass("selected");
	
	$("body").on("click",".btn-add",function(){
//		if($("#isClosed").val()=="" && $("#isOutArea").val()=="")
		if(ERRORMSG==null)
			addFun($(this));
		else
			layer.msg(ERRORMSG);
	});
	
	$("body").on("click",".btn-del",function(){
//		if($("#isClosed").val()=="" && $("#isOutArea").val()=="")
		if(ERRORMSG==null)
			delFun($(this));
		else
			layer.msg(ERRORMSG);
	});
	
	$(".menu-item>a").on("click",function(){
		var name=$(this).text();
		var id=$(this).attr("data-id");
		getpro(id, name);
	});
	
	$("body").on("click",".exc-btn>a",function(){
		pointExchange($(this).parents(".pro-item"));
	});
	
//	new g_fnImgCheck({
//		"call":function(){
//			layer.closeAll('loading');
//			scorllerCount($("#firstClaID").val());
//		}
//	});
	countInit();
	lazyLoadInit();
	
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
});



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
 * 获得一级分类下的商品
 * @param proID
 */
function getpro(classID,parName){
	clearProData();
	if(showProduct(classID))
	{
		layer.load(1, {shade: [0.8,'#333']});
		var param={
			"sURL" :BASEPATH+"/shopproajax.json",
			"Data" :"classID="+classID+"&shopID="+$("#shopID").val()+"&pageIndex=1",
			"fnSuccess" : function(data){
				layer.closeAll('loading');

				var html=createHtml(data,parName);
				html=PROHTMLCON.replace("${conlist}", html)
							   .replace(/(\${id})/g, classID);
				
				$(".page-con").append(html);
//				new g_fnImgCheck({"call":function(){layer.closeAll('loading');scorllerCount(classID);}});
				scorllerCount(classID);
				
				selectInit(classID);
				
				lazyLoadInit();
			},
			"fnError"   : function(){
				layer.closeAll('loading');
				layer.msg("查询失败");
			}
		};
		new g_fnAjaxUpload(param);
	}
}

/**
 * 商品展示
 * @param classID
 */
function showProduct(classID){
	$(".menu-con>.menu-item").removeClass("selected");
	$(".menu-con>.menu-item").find("a").removeClass("selected");
	
	$(".menu-con>.item-"+classID).addClass("selected");
	$(".menu-con>.item-"+classID).find("a").addClass("selected");
	
	if($("#wrap-right-"+classID).length!=0)
	{
		$("#wrap-right-"+classID).css("display","block");
		scorllerCount(classID);
		return false;
	}else
		return true;
}

/**
 * 拼接html
 * @param data
 * @returns {String}
 */
function createHtml(data,parName){
	var html="";
	for(var i=0;i<data.length;i++)
	{
		var className=data[i].className;//二级分类名
		var proList=data[i].data;//二级分类下的商品
		
		if(className!=parName && proList.length>0)
			html+='<div class="pro-span">'+className+'</div>';
		else if(className==parName && proList.length>0)
			html+='<div class="pro-span">其它</div>';
		
		for(var k=0;k<proList.length;k++){
			html+=PROHTML.replace(/(\${proID })/g, proList[k].id)
						 .replace("${cover }", proList[k].cover)
						 .replace("${name }", proList[k].name)
						 .replace("${buyCount }", proList[k].buyCount);
			if(proList[k].hasOwnProperty("isExchange"))
			{
				html=html.replace("${disPrice }", "<span class='point-txt'>"+proList[k].point+"积分</span>" +
												  "<span class='now hide'>"+proList[k].price+"</span>" +
												  "<input type='hidden' name='isex' value='"+proList[k].isExchange+"'>")
						 .replace("${price }", "")
						 .replace("${exc-btn}", '<p class="exc-btn"><a href="javascript:void(0)">立即兑换</a></p>');
			}else{
				html=html.replace("${exc-btn}", "");
				if(proList[k].disPrice==0)
					html=html.replace("${disPrice }", "<span class='now'>¥ "+proList[k].price+"</span>")
							 .replace("${price }", "");
				else
					html=html.replace("${disPrice }", "<span class='now'>¥ "+proList[k].disPrice+"</span>")
							 .replace("${price }", "<span class='past'>¥ "+proList[k].price+"</span>");
			}
		}
	}
	return html;
}

/**
 * 清除商品数据
 */
function clearProData(){
	$(".pro-con").removeAttr("style");
//	$(".pro-con").css("display","none");
	$(".js-wrap").css("display","none");
}


function lazyLoadInit(){
	$(".pro-item a>img").scrollLoading();
}

/**
 * 计算滚动条长度
 */
function scorllerCount(classID){
	 $("#wrap-right-"+classID).css("height",scrHeight+"px");
	 $("#wrap-left").css("height",scrHeight+"px");
	 var height=0;
	 var itemHeight=0;
	 $(".menu-con .menu-item").each(function(){
		 height=parseInt(height)+parseInt($(this).outerHeight());
	 });
	 height=parseInt(height)+parseInt(166);
	 $(".menu-con").css("height",height+"px");
	 
	 itemHeight=$("#pro-con-"+classID).height();
	 $("#pro-con-"+classID).css("height",parseInt(itemHeight)+parseInt(90*4)+"px");
	 
	 //0.计算商品列表滚动条
	 if(wrap1!=null)
		 wrap1.destroy();
	 wrap1=new IScroll('#wrap-right-'+classID, { disableMouse:true,HWCompositing:false, click:true});

	 //1.计算类别列表滚动条
//	 if(wrap2!=null)
//		 wrap2.destroy();
//	 else
//		 wrap2=new IScroll('#wrap-left', { scrollX: false, scrollY: true, mouseWheel: true,click:true});
	 if(wrap2==null)
		 wrap2=new IScroll('#wrap-left', { disableMouse:true,HWCompositing:false, click:true});
	 
//	 wrap1.on('scrollEnd', function(){wrap2.scrollTo(0, 0);});
}

/**
 * 价格计算
 */
function priceCount(target,type){
	var parent=target.parents(".pro-item");
	var singlePrice=(parent.find(".now").text()).replace("¥ ","");
	var price=$(".js-price").text();
	
	if(parent.find("input[name='isex']").val()==null){
	
		if(type=="+")
			price=parseFloat(price)+parseFloat(singlePrice);
		else
			price=price-singlePrice;
		
		if(price>0)
		{
			$(".js-price").text(changeTwoDecimal(price));
			$(".js-procount").text(totalCount);
			$(".bot-pay").css("display","block");
		}else{
			$(".js-price").text(0);
			$(".js-procount").text(0);
			$(".bot-pay").css("display","none");
		}
	
	}
}

/**
 * 计算购物价格是否能够满足起送价
 * @param target
 * @param type
 */
function canSend(target,type){
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
 * 组织商品购物车数据
 */
function addToShop(){
	if($(".useless").length!=0){
		layer.msg("您购买的金额低于起送价");
		return;
	}		
   	else if($("#userID").val()==""){
//		showLogin();
//		return;
   		$("#userID").val("none");
	}		
	else if($("#isClosed").val()!=""){
		layer.msg("店铺还未开张");
		return;
	}
	else if($("#isOutArea").val()!=""){
		layer.msg("您不在本店的配送范围内");
		return;
	}
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
			$(".js-send").text(sendPrice-totalPrice);
			$(".bot-pay .right>a").addClass("useless");
		}
		else{
			$(".js-send").text(0);
			$(".bot-pay .right>a").removeClass("useless");
		}
	}
	
	selectInit($("#firstClaID").val());
}

/**
 * 商品选择初始化
 * @param classID
 */
function selectInit(classID)
{
	$("#pro-con-"+classID+" .pro-item").each(function(){
		var id=$(this).attr("data-proid");
		for(var i=0;i<shopCarPro.length;i++){
			if(id==shopCarPro[i].proID)
			{
				if((classID==0 && shopCarPro[i].hasOwnProperty("ise")) || classID!=0)
				{
					$(this).find(".tip-procount").text(shopCarPro[i].count);
					$(this).find(".tip-procount").css("display","inline-block");
					$(this).find(".btn-del").css("display","inline-block");
				}
			}
		}
	});
}

/**
 * 前往商品查询页面
 */
function toQueryPage(){
	if($("#isClosed").val()!="")
		layer.msg("店铺尚未开张");
	else if($("#isOutArea").val()!=""){
		layer.msg("您不在本店的配送范围内");
	}
	else{
		var strData=JSON.stringify(shopCarPro);
		$("#proCarList").val(strData);
		$("#queryJumpForm").submit();
	}
}

/**
 * 前往详情页面
 */
function toDetPage(proID){
	var strData=JSON.stringify(shopCarPro);
	$("#proCarList").val(strData);
	$("#queryJumpForm").attr("action",BASEPATH+"/pro-det-"+proID+".html");
	$("#queryJumpForm").submit();
}

/**
 * 拼接数据
 */
function createData(target){
	var parent=target.parents(".pro-item");
	var price=parent.find(".now").text();
	var proID=parent.attr("data-proid");
	var count=parent.find(".tip-procount").text();
	var ise=parent.find("input[name='isex']").val();
	
	//查询是否在数组中已存在，存在则删除
	if(ise==null){
		for(var i=0;i<shopCarPro.length;i++)
		{
			if(shopCarPro[i].proID==proID){
				shopCarPro.remove(i);
				break;
			}
		}
		//当数量大于0时，添加s
		if(count>0){
			var param=new Object();
			param.price=price.replace("¥ ","").replace("积分","");
			param.count=count;
			param.proID=proID;
//			if(ise!=null){
//				param.ise=ise;
//			}
			shopCarPro.push(param);
		}
	}
}

/**
 * 积分兑换
 * @param target
 */
function pointExchange(target){
	var data=new Object();
	var point=(target.find(".point-txt").text()).replace("积分","");
	var count=target.find(".tip-procount").text();
	data.shopID=$("#shopID").val();
	data.proID=target.attr("data-proid");
	data.count=$.trim(count);
	data.point=$.trim(count) * point;
	data.des="用户积分兑换商品";
	
//	if(data.point>$("#userInter").val())
//	{
//		layer.msg("您的积分不足");
//		return;
//	}
	
	if(count=="0" || count=="")
	{
		layer.msg("请选择要兑换的数量");
		return;
	}
	
	layer.load(1, {shade: [0.8,'#333']});
	
	var param={
		"sURL":BASEPATH+"/users/pointexchange.json",
		"Data":"dataObj="+JSON.stringify(data),
		"fnSuccess" : function(data){
			layer.msg(data.msg);
			layer.closeAll('loading');
		},
		"fnError" : function(){layer.closeAll('loading');layer.msg("添加失败,请联系管理员");}
	};
	new g_fnAjaxUpload(param);
}