function addcollection(shopID){
	var data = "shopID="+shopID;
	// 组织ajax参数
	$.ajax({
		type : "POST",
		url : "/nanny/addcollection.json",
		data:data,
		success : function(msg) {
			var data = eval('(' + msg + ')');
			layer.msg(data.msg);
			if(data.msg=="取消收藏"){
				$(".color").css("color",""); 	
			}			
			else if (data.hasOwnProperty("success")){
				$(".color").css("color","#F44336"); 			
			}
			}
	});
	
}

function showQrCode(){
	var src=$(".js-sQrCode").attr("data-src");
	layer.open({
	    type: 1,
	    title: "店铺二维码",
	    skin: 'layui-layer-rim', //加上边框
	    area: ['300px','300px'], //宽高
	    content: "<div style='text-align:center'><img src='"+BASEPATH+"/image/TwoCode.html?data="+src+"'></div>"
	});
}