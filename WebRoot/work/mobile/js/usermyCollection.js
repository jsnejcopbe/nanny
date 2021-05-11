function delcollection(){
	var data="userID="+b+"&shopID="+d;
	$.ajax({
		type : "POST",
		url : BASEPATH+"/users/delcollection.json",
		data : data,
		success : function(msg) {
			var data = eval('(' + msg + ')');
			layer.msg(data.msg);
			if(data.hasOwnProperty("success"))
				window.location.href = BASEPATH+"/users/myCollection.html";
		}
	});
}