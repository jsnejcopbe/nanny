function cashapply(id){
var data="status="+id+"&logmin="+$("#logmin").val()+"&logmax="+$("#logmax").val()+
               "&tel="+$("#tel").val()+"&logmax="+$("#logmax").val();	
    // alert(data);
	$.ajax({
		type : "POST",
		url :"/nanny/cashapply.html",
		data : data,
		success : function(msg) {
	    var data = eval('(' + msg + ')');
	    alert(data.msg);
	    location.reload();
	   }
   });	
}

function pass(id){	
	var data ="appID="+id ;
	$.ajax({
		type:"POST",
		url:"/nanny/admin/passtransfer.json",
		data:data,
		success:function(msg){
	    var data = eval('(' + msg + ')');
	    alert(data.msg);
	    location.reload();
		}
	});
}
function notpass(id){	
	var data ="appID="+id ;
	$.ajax({
		type:"POST",
		url:"/nanny/admin/refusetransfer.json",
		data:data,
		success:function(msg){
	    var data = eval('(' + msg + ')');
	    alert(data.msg);
	    location.reload();
		}
	});
}
