	$(function(){
		page();
	
});

		
		function page(){
	
	var sta=$("#status").val();
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
			data : "pageIndex="+pageIndex+"&sta="+sta,
			url : BASEPATH+"/users/usersOrderFy.json",
			dataType : "text",
			success : function(msg) {
				var data = eval('(' + msg + ')');
				
				var pageIndex=data.order.nowPage;
				
				var pageSize=data.order.size;
				
				var array=data.order.order;
				
				$("#pageIndex").val(pageIndex);
				$("#pageSize").val(pageSize);
				
				
				for(var i=0;i<array.length;i++){
				
		
				var html='<div class="row pro-it">\
					<div class="col-xs-12 it-tit clearfix">\
						<span class="tit-code">订单号:'+array[i].orderCode+'</span>\
						<span class="tit-sta">'+array[i].staName+' </span>\
					</div>\
					<div id="second_'+array[i].id+'"></div>\
					<div class="it-opt col-xs-12" id="three_'+array[i].id+'"></div>\
					<div class="it-btn col-xs-12" id="four_'+array[i].id+'">\
						<a href="'+BASEPATH+'/users/orDetails.html?orid='+array[i].id+'&sta='+sta+'">查看详情</a>\
					</div>\
				</div>';
				
				$(".container").append(html);
				
				
				
				 var company=array[i].company;
				for(var j=0;j<company.length;j++){
				html='<div class="col-xs-12 it-con heheda" >\
				<div class="col-xs-3 con-img">\
					<img src="'+company[j].cover+'">\
				</div></div>';
				
				$("#second_"+array[i].id).append(html);
				
					
	}//内层循环 */
	
				 var k=0; 
				$("#second_"+array[i].id).find(".heheda").each(function(){
					
					if(company[k].memo=='0'){
					 html='<div class="col-xs-7 con-des">\
							'+company[k].name +'\
						</div>\
						<div class="col-xs-2 con-pri">\
							<p>¥'+company[k].price+'</p>\
							<p>X'+company[k].count+'</p>\
						</div>';
		
						$(this).append(html);
				
				}
				if(company[k].memo!='0'){
				html='<div class="col-xs-7 con-des">\
							'+company[k].name+'\
							<p style="color:#E70012">此商品为积分兑换</p>\
						</div>\
						<div class="col-xs-2 con-pri">\
							<p>'+company[k].point+'</p>\
							<p>X'+company[k].count+'</p>\
						</div>';
				
				$(this).append(html);
			}
			k++;
		}); 
				
				if(array[i].vcID!='0'){
				html='<div class="opt-msg"> '+array[i].svName+'<span>-¥'+array[i].svMoney+'</span>元</div>';
				$("#three_"+array[i].id).append(html);
				}
				
				html='<div class="opt-msg"> 实付<span>¥'+array[i].totalPrice+'</span>元(含配送费'+array[i].fee+'元)</div>';
				$("#three_"+array[i].id).append(html);	
				
				if(array[i].status=='0'){
				html='<a class="red-btn" href="javascript:orderpayment('+array[i].id +')">付款</a>';
				$("#four_"+array[i].id).append(html);
				}
				if(array[i].status=='2'){
				html='<a class="red-btn" href="javascript:orderConfirm('+array[i].id+')">确认收货</a>';
				$("#four_"+array[i].id).append(html);
				}
				if(array[i].status=='1'){
				html='<a href="javascript:orderRefund('+array[i].id+')">取消订单</a>';
				$("#four_"+array[i].id).append(html);
				} 
				
}//外层循环
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