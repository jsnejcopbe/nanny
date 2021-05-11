$(function(){
	page();
	var arrsize=$("#arrsize").val();
	if(arrsize>10){
	
	$(".pancel").css('display','block');
	}
});

	
function page(){
	
	var sta=$("#sta").val();
	var pain=$("#pageIndex").val();
	var pageSize=$("#pageSize").val();
	
	var pageIndex;
	 if(pain!=""){
		 pageIndex= parseInt(pain) + 1;
	 }else{
		 pageIndex=pain;
	 }

	 $.ajax({
			type : "post",
			data : "pageIndex="+pageIndex+"&pageSize="+pageSize+"&sta="+sta,
			url : path+"/users/userCouponFlip.html",
			dataType : "text",
			success : function(msg) {
				var data = eval('(' + msg + ')');
				var pageIndex=data.pageIndex;
				
				var pageSize=data.pageSize;
				
				var array=data.arr;
				
				$("#pageIndex").val(pageIndex);
				$("#pageSize").val(pageSize);
				
				for(var i=0;i<array.length;i++){
				var div='<div class="row pro-it" style="width: 98%;margin: 10px auto;border-radius:7px;">\
			<div style="width: 100%;background-color: #CF3E36;height: 100px;text-align: center;line-height: 100px;border-top-left-radius:7px;border-top-right-radius:7px;">\
					<div ><font size="5px" color="white">专享抵用券立减'+array[i].money+'元</font></div>\
				</div>\
					<div style="position: relative;left: 5px;top: 5px;"><div style="float: right;">\
					过期时间：'+array[i].endTime+' &nbsp; &nbsp; </div>\
				</div>\
			<div class="it-opt col-xs-12"></div>\
			<div class="it-btn col-xs-12"></div>\
			</div>';
					
					$(".myclass").append(div);
					
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