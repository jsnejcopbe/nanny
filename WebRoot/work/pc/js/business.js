$(function() {
	layer.config({
		extend: 'extend/layer.ext.js'
	});
});

$(function(){
	
	$("body").on("change",".up_file",function(){
     layer.load(1, {shade: [0.8,'#333']});
     var oParam={
                "sURL":path+"/upload/images.json",
                "sID":"file1_"+shopid,
                "contentType":"",
                "fnSuccess":function(data){
                	layer.closeAll("loading");
                	$("#fi_"+shopid).val(data.path);
                	document.getElementById("img_"+shopid).src =data.path;
                	//$("#img"+shopid).attr("src",data.path);
                	$(".fileinput-remove-button").click();
                },
                "fnError":function(){  
                	layer.closeAll("loading");
                	alert("上传失败");
                 }
             };
                 new g_fnFileUpload(oParam);
                    });
	$(".js-setSta").on("click",function(){
		var ID=$(this).attr("data-id");
		setSubsidySta(ID,1,$(this).parents("td"));
	});
	
	$(".js-remveSta").on("click",function(){
		var ID=$(this).attr("data-id");
		setSubsidySta(ID,0,$(this).parents("td"));
	});
	
	$(".js-setExSta").on("click",function(){
		var ID=$(this).attr("data-id");
		setExchangeSta(ID,1,$(this).parents("td"));
	});
	
	$(".js-remveExSta").on("click",function(){
		var ID=$(this).attr("data-id");
		setExchangeSta(ID,0,$(this).parents("td"));
	});
	
	$(".js-setExIsVouvhers").on("click",function(){
		var shopID=$(this).attr("data-id");
		setIsVouchers(shopID,1,$(this).parents("td"));
	});
	$(".js-remveExIsVoucher").on("click",function(){
		var shopID=$(this).attr("data-id");
		setIsVouchers(shopID,0,$(this).parents("td"));
	});
	
	
                   
   });
$("body").on("click","#state_query a",function(){
			var id = $(this).attr("data-id");
			//console.log(id);
			$("#hide_type").val(id);
			$("#shop_query").find("form").submit();
		});
		

function updbusin(id) {
	
	$.ajax({
        type: "POST",
        url: path+"/admin/updbusi.html",
        data: "shopid="+id,
        dataType: "json",
        success: function(data){
        		 layer.msg("操作成功");
				location.reload(true);
        },
		error: function(){
				layer.msg("操作失败");
    	}
    });
}

function opp(id){
	shopid=id;
	layer.open({
	    type: 1,
	    skin: 'layui-layer-demo', //样式类名
	    //shade: true,
	    //closeBtn: 0, //不显示关闭按钮
	    shift: 2,
	    area: ['500px',''],
	    shadeClose: true, //开启遮罩关闭
	    title: false, //不显示标题
	    content: $('#sname_'+id), //捕获的元素
	    //cancel: function(index){
	        //layer.close(index);
	        //this.content.show();   
	    //}
	});
	}


var shopid;
function updshop(id) {
	
	
	var shname=$("#shname_"+id).val();
	var shcon=$("#shcon_"+id).val();
	var shmemo=$("#shmemo_"+id).val();
	var shicon=$("#fi_"+id).val();
	shmemo= shmemo.replace("，", ",");
		
	var tel= shmemo.split(",");
		
	$.ajax({
		 type: "POST",
		 url: path+"/admin/updshopdata.html",
		 data: "shopid="+id+"&shopname="+shname+"&shopcon="+shcon+"&shopicon="+shicon+"&shopmemo="+shmemo,
		 dataType: "json",
		 success: function(data){
				 layer.msg("操作成功");
				location.reload(true);
				 },
		 error: function(){layer.msg("操作失败");}
		});
}

/**
 * 设置商户返现状态
 * @param sta
 */
function setSubsidySta(shopID,sta,traget){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":path+"/admin/updatesubsidysta.json",
		"Data":"sta="+sta+"&shopID="+shopID,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			if(sta==1){
				traget.find(".js-remveSta").css("display","inline-block");
				traget.find(".js-setSta").css("display","none");
			}
			else{
				traget.find(".js-remveSta").css("display","none");
				traget.find(".js-setSta").css("display","inline-block");
			}
		},
		"fnError"  :function(){layer.msg("设置失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 设置商户优惠券状态
 * 
 */
function setIsVouchers(shopID,isVouchers,traget){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":path+"/admin/updateIsVoucher.json",
		"Data":"shopID="+shopID+"&isVouchers="+isVouchers,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			if(isVouchers==1){
				traget.find(".js-remveExIsVoucher").css("display","inline-block");
				traget.find(".js-setExIsVouvhers").css("display","none");
		
				
			}
			else{
			
				traget.find(".js-remveExIsVoucher").css("display","none");
				traget.find(".js-setExIsVouvhers").css("display","inline-block");
			}
			
		},
		"fnError"  :function(){layer.msg("设置失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 设置商户返现状态
 * @param sta
 */
function setExchangeSta(shopID,sta,traget){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":path+"/admin/updatetranbysta.json",
		"Data":"sta="+sta+"&shopID="+shopID,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			if(sta==1){
				
				traget.find(".js-remveExSta").css("display","inline-block");
				traget.find(".js-setExSta").css("display","none");
			}
			else{
				traget.find(".js-remveExSta").css("display","none");
				traget.find(".js-setExSta").css("display","inline-block");
			}
		},
		"fnError"  :function(){layer.msg("设置失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 电话检查
 * @param telList
 * @returns
 */
function telCheck(telList){
	if(telList.length==1)
		return (/^0?1[3|4|5|8][0-9]\d{8}$/).test(telList[0]);
	else if(telList.length==2)
		return (/^0?1[3|4|5|8][0-9]\d{8}$/).test(telList[0])&&(/^0?1[3|4|5|8][0-9]\d{8}$/).test(telList[1]);
	else
		return true;
}