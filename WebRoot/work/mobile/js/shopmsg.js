function msgview(id){
	var data="uid="+id+"&con="+$("#content").val();
	
	//拼装
	$.ajax({
		type : "POST",
		url : "/nanny/shop/saveMessage.html",
		data : data,
		success: function(msg) {
			var data = eval('(' + msg + ')');
			layer.msg(data.msg);
		    window.location.href="/nanny/shop/shopMessage.html";
		}
		
   });
	
	
}