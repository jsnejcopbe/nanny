function closeshop(){

	// 组织ajax参数
	$.ajax({
		type : "POST",
		url : "/nanny/closeshop.json",
		success : function(msg) {
			var data = eval('(' + msg + ')');
			layer.msg(data.msg);
			if(data.msg=="店铺关闭了"){
				location.reload();
			}			
			
			}
	});
	
}
function openshop(){

	// 组织ajax参数
	$.ajax({
		type : "POST",
		url : "/nanny/openshop.json",
		success : function(msg) {
			var data = eval('(' + msg + ')');
			layer.msg(data.msg);
			if(data.msg=="店铺打开了"){
				location.reload();
			}			
		
			}
	});
	
}