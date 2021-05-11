	$(function(){
		page();
	
});

	$("#btn_serch").click(function() {
			$("#forobj").html("");
			$("#pageIndex").val("");
			page();

		});
		
	
function page(){
	
		var logmin = $("#logmin").val();
		var logmax = $("#logmax").val();
		var status = $("#state").val();
		var pain = $("#pageIndex").val();
		var pageSize = $("#pageSize").val();
			
	var pageIndex;
	 if(pain!=""){
		 pageIndex= parseInt(pain) + 1;
	 }else{
		 pageIndex=pain;
	 }

	 $.ajax({
			type : "post",
			data : "pageIndex="+pageIndex+"&logmin="+logmin+"&logmax="+logmax+"&memo="+status,
			url : path+"/users/ProductExchangeDetailFy.json",
			dataType : "text",
			success : function(msg) {
				var data = eval('(' + msg + ')');
				
				var array=data.arr;
				
				var pageIndex = data.obj.nowpage;
				var pageSize = data.obj.size;
				
				$("#pageIndex").val(pageIndex);
				$("#pageSize").val(pageSize);
				
				for(var i=0;i<array.length;i++){
					var html='	<div class="list-item clearfix">\
					<div class="col-xs-9 item-left type2">\
						<div class="col-xs-4 item-left" style="padding-left: 0px;"><img alt="" src="'+array[i].cover+'" style=" width: 90px;height: 100px;"></div>\
						<div  class="col-xs-7 item-left">\
								<p><span>'+array[i].name+'</span></p>\
								<p><span>数量:'+array[i].number+'</span></p>\
								<p class="item-time"><span style="padding-right: 20px;">'+array[i].createTime+'</span> </p>\
						</div>\
					</div>\
					<div class="col-xs-3 item-right type2">\
						<p class="item-money"><font color="red">'+array[i].point+'积分</font></p>\
						<p class="item-money">原价:¥'+array[i].price+'</p>';
					
						if(array[i].memo=='0'){
							html+='<p class=""><label class="label "  style="background-color: #F8AC59;">等待确认</label></p>';
						}
						if(array[i].memo=='1'){
							html+='<p class=""><label class="label" style="background-color:#1AB394;">兑换成功</label></p>';
						}
						html+='	</div></div>';
						
						$("#forobj").append(html);
				
				
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
	