$(document).ready(function(){
	$("#ajax_exchange .img_lazy img").lazyload({effect: "fadeIn"});
	$("#tp_images .img_lazy img").lazyload({
		effect: "fadeIn"
	}).on('load', function() {
		var navNum = $("#content_tab .tabs a.on").index();
		var hei = $("#content_tab .main_li ul li").eq(navNum).height();
		$("#content_tab").css("min-height",hei+"px");
		$("#content_tab .main_li").css("min-height",hei+"px");
    });
	$(".top_lazy").lazyload({effect: "fadeIn"});
	slidsInit();
	//选项卡操作
	$("#content_tab .main_li").touchSlider({
		flexible : true,
		speed : 150,
		btn_prev : $("#btn_li_prev"),
		btn_next : $("#btn_li_next"),
		paging : $("#content_tab .tabs a"),
		counter : function (e){
			$("#content_tab .tabs a").removeClass("on").eq(e.current-1).addClass("on");
			var hei = $("#content_tab .main_li ul li").eq(e.current-1).height();
			$("#content_tab").css("min-height",hei+"px");
			$("#content_tab .main_li").css("min-height",hei+"px");
		}
	});
	$("#open_down").click(function(){
		$(".down_bg").show();
		$(".down_ul").slideDown(500);
	});
	$("#open_share").click(function(){
		$(".in_ul").slideDown(500);
		$(".down_ul").hide();
	});
	$(".close_tc").click(function(){
		hiddenDiv();
	});
});
var wiHei=0.8;//宽高比例		
$(function() {
	var index = 0;
	var picTimer;
	picTimer = setInterval(function(){
		if($("#content_tab .tabs a").eq(0).hasClass("on")){
			var heis = $("#content_tab .main_li ul li").eq(0).height();
			$("#content_tab").css("min-height",heis+"px");
			$("#content_tab .main_li").css("min-height",heis+"px");
		}
		if($(".main_image li").eq(0).find("img").attr("src") != null){
			imgReady($(".main_image li").eq(0).find("img").attr("src"), function () {
				var w = $(".main_image").width();
				var h = this.height;
				var imw = this.width;
				if(imw/h <= wiHei){
					$(this).css("width","80%");
					h = w*h*wiHei/imw;
				}else{
					h = w*h/imw;
				}
				//wiHei = w/h;
				$("#pro_slider .main_image").css("min-height",h+"px");
				$("#pro_slider").css("min-height",h+"px");
				$("#pro_slider ul li").css({"height":h+"px","line-height":h+"px"});
			});
		}
		if($(".top_lazy").attr("data-original") != null){
			imgReady($(".top_lazy").attr("data-original"), function () {
				var h = this.height;
				var imw = this.width;
				if(imw/h <= wiHei){
					$(".top_lazy").css("width","80%");
				}else{
					$(".top_lazy").css("width","100%");
				}
			});
		}
	},50);
});

function slidsInit(){
	
	$dragBln = false;
	//轮播插件
	$(".main_image").touchSlider({
		flexible : true,
		speed : 150,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e){
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
			if(!$(".main_visual ul li").eq(e.current-1).hasClass("overload")){
				$(".main_visual ul li").eq(e.current-1).find("img.lazy").lazyload({effect: "fadeIn"});
			}
			var w = $(".main_image").width();
			var imgSrc = $(".main_image li").eq(e.current-1).find("img").attr("data-original");
			if(imgSrc != null){
				imgReady(imgSrc, function () {
					var h = this.height;
					var imw = this.width;
					if(imw/h <= wiHei){
						$("#pro_slider ul li").eq(e.current-1).find("img").css({"height":"100%","width":"auto"});
					}else{
						$("#pro_slider ul li").eq(e.current-1).find("img").css({"width":"100%","height":"auto"});
					}
				})
			}
			$(".main_visual ul li").eq(e.current-1).addClass("overload");
		}
	});
	
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	});
	
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	});
	
	$(".main_image a").click(function(){
		if($dragBln) {
			return false;
		}
	});
	//5s执行一次（自动轮播）
	var timer = setInterval(function(){
		$("#btn_next").click();
	}, 5000);
	
	$(".main_visual").hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		},5000);
	});
	
	$(".main_image").bind("touchstart",function(){
		clearInterval(timer);
	}).bind("touchend", function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		}, 5000);
	});
}

function initError(obj){
	$("#error_infors span").text(obj); 
	$("#error_infors").fadeIn(800).delay(1000).fadeOut(800); 
}
/* 异步请求更换图片  */
function ajaxImage(productId){
	var myUrl=touchUrl+"/touch/product.do?a=1&id="+productId;
	$.ajax({ url:myUrl,success: function(data){
	     var res= $.parseJSON(data);
	     if(res.data.length > 0){
	    	 $("#ajax_exchange").empty();
	    	 for(var i=0;i<res.data.length;i++){
		    	 var container=$("#ajax_exchange");
		    	 container.append(
				    		 '<p>'+
				    		    res.data[i].introduction+'</p><p class="img_lazy">'+
				    		    '<img data-original="'+waphre+res.data[i].uploadPath+'" src="'+rep+'/upload/touch/newWap/icon/load.gif"/>'+
				    		    '</p>'
				    );
		     }
	    	 $("#ajax_exchange .img_lazy img").lazyload({effect: "fadeIn"});
	     }
	     if(res.images.length>0){
	    	 var xy_htm = $(".user_xyd").prop("outerHTML");
		     $("#pro_slider").empty();
		     var container=$("#pro_slider");
		     var html='<div class="main_image"><ul>';
		     var nums = 0;
		     $.each(arr, function(key, val) {
				if(val-1 < res.images.length){
					nums++;
					 html+='<li>'+
		    		    '<img data-original="'+waphre+res.images[val-1]+'" alt="正在加载图片...." class="lazy" src="'+rep+'/upload/touch/newWap/icon/load.gif"/>'+
		    		    '</li>';
				}
		     });
		     html +="</ul></div>";
		     html +='<div class="flicking_con">';
		     for(var ii=0;ii<nums;ii++){
		    	 html +='<a href="#">'+(ii+1)+'</a>&nbsp;';
		     }
		     html +="</div>";
		     if(xy_htm != undefined){
		     html +=xy_htm;
		     }
		     container.append(html);
		     slidsInit();
	     }
	}});	
}


function addProductCollection(url,productId) {
		$.ajax({
			type:"POST", 
			url:url+"/product/productCollection.do?type=1&productId="+productId, 
			success:function (date){
				if(date=="noLogin"){
					//未登录
					_obj_shows('收藏商品要先登录哦~',2,0)
				}else if(date=="hasCollect"){
					//已收藏
					$("#old_coll").text('已收藏');
					$(".succ_procoll").attr("src",rep+"/upload/touch/newWap/icon/shopPro30.png");
					$(".add_procoll").hide();
					$(".succ_procoll").show();
					_obj_shows('您已收藏过此商品',0,0);
				}else if(date=="error"){
					//错误
					_obj_shows('收藏商品出现异常,请重新操作',1,0);
				}else if(date=="success"){
					//收藏成功
					$("#old_coll").text('已收藏');
					$(".succ_procoll").attr("src",rep+"/upload/touch/newWap/icon/shopPro30.png");
					$(".add_procoll").hide();
					$(".succ_procoll").show();
					hiddenDiv();
					initError('收藏成功');
				}
			}
		});
		return true;
}

/* 添加心愿单  */
function addWishOrder(url,productId) {
	$.ajax({
		type:"POST", 
		url:url+"/wish/wishOrder.do?method=addAsync&productId="+productId, 
		dataType : "json",
		success:function (data){
			if(data.type =="noLogin"){
				_obj_shows(data.msg ,4,1);
			}else if(data.type =="hasWish"){
				$("#wish_hasadd").show();
				_obj_shows(data.msg ,3,1);
			}else if(data.type =="threeLim"){
				$("#wish_full").show();
				_obj_shows(data.msg ,3,1);
			}else if(data.type=="error"){
				$("#wish_error").show();
				_obj_shows(data.msg ,1,1);
			}else if(data.type=="timeErr"){
				$("#wish_timeErr").show();
				_obj_shows(data.msg ,1,1);
			}else if(data.type=="success"){
				window.location.href = url+"/wish/wishOrder.do?method=view";
			}
		}
	});
	return true;
}
function hiddenDiv(){
	 $("#loginMsg").hide();
	 $(".down_bg").hide();
	 $(".down_ul").hide();
	 $(".in_ul").hide();
	 $(".shop_down_bg").hide();
	 $(".shop_down_ul").hide();
	 $(".shop_in_ul").hide();
}
/* 分享和收藏弹出框 */
function _obj_shows(_obj,_col_index,_type){
	 $("#loginMsg .colle_to").html(_obj);
	 $("#loginMsg .collection").eq(_col_index).show().siblings().hide();
	 if(_type == 0){
		 $(".down_ul").hide();
	 }	
	 $(".shop_down_bg").show();
     $("#loginMsg").slideDown(500);
     $(".shop_down_ul").hide();
}
/* 分享功能 */
var shareURL = {"sina":"http://v.t.sina.com.cn/share/share.php?&url={url}&title={title}&content=gb2312",
		"qqt":"http://v.t.qq.com/share/share.php?title={title}&url={url}&pic={pic:|}",
				"qzone":"http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url={url}&title={title}"};
function share_product(obj){
	var link = shareURL[obj];
	var pics = new Array();
	link = link.replace('{title}', '再网购，就剁手！但买卖宝这个'+pro_names+' 实在忍不住想败，亲们看看怎么样？');
	link = link.replace('{url}', encodeURIComponent(window.location.href));	
	link = link.replace('{pic:|}', pics.join('|'));
	window.open(link);
}
/*异步请求子商品优惠价和促销提示信息*/
function ajaxPromotionInfo(productId,isWish){
	if(productId == null || productId == 'undefined' || parseInt(productId) <= 0){
		return false;
	}
	$.ajax({
		   type: "POST",
		   async:false,
		   url: touchUrl+"/touch/product.do",
		   data: "a=2&isWish="+isWish+"&subPId="+productId,
		   dataType:"json",
		   success: function(data){
			   $(".jdGoods").find("h2").remove();
			   if(data.promotionInfo!=null && data.promotionInfo.length>0){
				   $(".jdGoods h1").after("<h2>"+data.promotionInfo+"</h2>");
			   }
			   $("#product_price").html(data.price);
		   }
		 });
}

