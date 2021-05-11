$(function(){
	page();
})


function page(){
	
	var pain=$("#pageIndex").val();
	var pasi=$("#pageSize").val();
	var pa;
	 if(pain!=""){
		 pa= parseInt(pain) + 1;
	 }else{
		 pa=pain;
	 }
	
	 $.ajax({
			type : "post",
			data : "pageIndex="+pa+"&pageSize="+pasi,
			url : path+"/users/signlist.html",
			dataType : "text",
			success : function(msg) {
				var data = eval('(' + msg + ')');
				var pageIndex=data.pageIndex;
				var pageSize=data.pageSize;
				var array=data.usign;
				$("#pageIndex").val(pageIndex);
				$("#pageSize").val(pageSize);
				for(var i=0;i<array.length;i++){
					var div = '<div class="panel-body">\
						<img src="'+array[i].headImage+'" style="width:40px;height:40px;" class="img-circle">\
						'+array[i].nickName+'&nbsp &nbsp &nbsp\
						<span class="spanTime"> ' +array[i].score+'&nbsp'+array[i].signTime+'</span>\
						</div>';
					$(".page").append(div);
				}
				
				if(array.length==0){
					layer.msg('已无更多!');
				}
			},
			error : function() {
				layer.closeAll("loading");
				layer.msg('网络有误!');
			}
		});
	}


