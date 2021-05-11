var toa;
		
$(function() {
	
	$(".add").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		t.val(parseInt(t.val()) + 1)
		
		setTotal();
	})
	$(".min").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		var oldVal=t.val()
		
		t.val(parseInt(t.val()) - 1)
		if (parseInt(t.val()) < 0) {
			t.val(0);
		}
		
		if(priceCheck($(this)))
			setTotal();
		else{
			layer.msg("您的消费金额低于起送价")
			t.val(oldVal);
		}
	})
	
	$("#tab .pr-item .vvc").each(function(){
	 var sl=$(this).find("select option:nth-child(2)").attr("value");
	 var mo=$(this).find("select option:nth-child(2)").attr("data-money"); 
		if(sl!=null){
			$("select").val(sl);
			$(this).find(".fm-txt span").html("-"+mo);
			//selcton($("select"),sl,mo);
		}
	})
	setTotal();

	toa=$("#total").val();
	


	loaddiv();
	
	var reda= $('input[name="Fruit"]:checked').val();
	
	
});

function setTotal() {
	var s = 0;
	var d=0;
	var m=0;
	$("#tab .pr-item").each(function(){
		var nowprice=0;
//		var delpr=$(this).find(".delpr").text();
		var delpr=0;
		var vcid=$(this).find(".vvc select[name='vcid']").val();
		var reda= $('input[name="Fruit"]:checked').val();
		var money=($(this).find("option[value='"+vcid+"']")).attr("data-money");
			if(money!=null&&reda=='0'){
				m+=parseFloat(money);
				
			}
		$(this).find(".item-con").each(function(){
			var count=$(this).find(".text_box").val();
			var price=$(this).find(".price").text();
			var ise= $(this).find("[name='isExchange']").val();
			if(ise==0){
				s += parseInt(count)* parseFloat(price);
			}
			nowprice=parseInt(count)* parseFloat(price);
		});
		if(nowprice>0)
			d+=parseFloat(delpr);
	});
	if(s!=0){
		s=s+d-m;
	}
	  if(s>0){
		  
		  $("#total").val(s.toFixed(2));
		  $("#total-tex").text("共 ¥ "+s.toFixed(2));
	  }else{
		  $("#total").val(0);
		  $("#total-tex").text("共 ¥ "+0);
	  }   
}
function vvhide(i) {
	$("#tab .pr-item").each(function() {
		if(i=='0'){
			$(this).find(".vvc").show();
			setTotal();
		}else{
			$(this).find(".vvc").hide();
			setTotal();
		}
		
	})
}

$("#tab .pr-item .vvc").on("change","select[name='vcid']",function(e){
	var id=	$(this).val();
	var money=$(this).find("option[value='"+id+"']").attr("data-money");
	setTotal();
	var pa=$(this);
	var tooa=  $("#total").val();
	 //var nowto=parseFloat(tooa)-parseFloat(money);
	
	pa.parent(".vvc").find(".fm-txt span").html("-"+money);
	/*$("#total").val(nowto);
	$("#total-tex").text("共 ¥ "+nowto);*/
	//selcton(pa,id,money);
	
})

/*function selcton(pa,id,money) {
	$.ajax({
		type : "post",
		url : path+"/users/shcarVoucher.html",
		data : "uid="+id,
		dataType : "text",
		async:false,
		success : function(msg) {
		
			if(msg.indexOf('0')!=-1){
				
				pa.parent(".vvc").find(".fm-txt span").html("-"+money);
				$("#total").val(nowto);
				$("#total-tex").text("共 ¥ "+nowto);
			}else{
				layer.msg('每天只能使用一次抵用卷！！');
				$("option").attr("data-money",0);
				$("option").attr("value",0);
			}
			
		},
		error : function() {
			layer.closeAll("loading");
			layer.msg('操作有误!');
		}
	});
}*/

function clear(id) {
			$.ajax({
				type : "post",
				url : path+"/users/delShopcar.html",
				data : "uid="+id,
				dataType : "text",
				success : function(msg) {
					location.href = path+"/supermarket.html";
				},
				error : function() {
					layer.closeAll("loading");
					layer.msg('操作有误!');
				}
			});
		}
		
		
	
		function updateshop() {
			layer.load(1, {
				shade : [ 0.8, '#333' ]
			});
			var reda= $('input[name="Fruit"]:checked').val();
			
			
	
			var shopid = new Array();//传递参数
			$("#tab .pr-item").each(function() {
				var par = new Object();//凭借参数
				par.id = $(this).find("[name='shopId']").val();
				var vid= $(this).find(".vvc").find("select[name='vcid']").val();
				if(vid!=undefined){
					par.vid=vid;
				}else{
					par.vid=0;
				}
				/*if(reda=='0'){
				
					  
				}*/
				var arryData = new Array();//传递参数
				var tolal=0;
				($(this).find(".pro")).each(function() {
					var param = new Object();//凭借参数
					param.id = $(this).find("[name='proID']").val();
					param.sid = $(this).find("[name='shop']").val();
					param.price = $(this).find(".price").text();
					param.count = $(this).find(".text_box").val();
					var ise=$(this).find("[name='isExchange']").val()
					param.isex = ise;
					if(ise==0){
						tolal += parseInt($(this).find(".text_box").val())* parseFloat($(this).find(".price").text());
					}
					
					arryData.push(param);
				})
				par.arryData=arryData;
				par.tolal=tolal;
				
				shopid.push(par);
				
				
			})
			var mesg=$("#mesg").val();
			
			var data = $("#pro").serialize() +"&shopid="+JSON.stringify(shopid)+"&mesg="+mesg;
			
			if($("#total").val()!=0.00){
			$.ajax({
				type : "post",
				url : path+"/users/updShopcar.html",
				data : data,
				dataType : "text",
				success : function(msg) {
					
					var data = eval('(' + msg + ')');
					if(reda=='0'){
						location.href = path+"/users/Payment.html";
					}else{
						location.href = path+"/users/cod.html";
					}
					//layer.msg("保存成功");
				},
				error : function() {
					layer.closeAll("loading");
					layer.msg('操作有误!');
				}
			});
		  }else{
			  layer.closeAll("loading");
			  layer.msg("您没有选购任何商品");
		  }
		}
		
		
	function loaddiv(){
			var arryData = new Array();//传递参数
			$("#tab .pr-item ").each(function() {
				var param = new Object();//凭借参数
							
				param.shopid = $(this).find("[name='shopId']").val();
							//alert($(this).find("[name='shopId']").val());
				arryData.push(param);
			})
			var data= "arryData="+JSON.stringify(arryData);
							//alert(data);
			 $.ajax({
					type : "post",
					url : path+"/users/pointgoods.html",
					data : data,
					dataType : "json",
					success : function(msg) {
					      //var data1 = eval('(' + msg + ')');
						var arry=msg.arryData;
						    // var data=eval('(' + arry + ')');
							if(arry.length>0){
							      layer.confirm('您有积分兑换商品，是否合并在此单内进行送货?', {
			 								title: '',
			 								closeBtn: 0,
			 			 					btn: ['嗯嗯','不要'] //按钮
										}, function(){
											/*for ( var j = 0; j < arry.length; j++) {
													
												}*/
												$.each(arry,function(i,val) {
													addhtml(val.shopid,val.data);
														});
												layer.closeAll('dialog');
										}, function(){
							 	 			 layer.msg('谨遵法旨');
													});
							}
								},
					error : function() {
						layer.closeAll("loading");
						layer.msg('操作有误!');
								}
					});
					
	}	
	
	
	
	
	
	function addhtml(shid,arryData) {
		var qq=$(".shid[value='"+shid+"']").parents(".pr-item").find(".vvc");
		
		for ( var i = 0; i < arryData.length; i++) {
			var name1=arryData[i].name;
			var name2=name1.substring(0, 6);
			$(qq).before('	<div class="item-con clearfix"><div class="col-xs-5 con-left">\
			<span>'+name2+'...</span>\
			</div>\
			<div class="col-xs-7 con-right fm-txt pro">\
					<span style="font-size: 18px;" >(需积分:'+arryData[i].point+')¥</span><span class="price">'+arryData[i].price+'</span> \
					 <a class="btn-del" href="javascript:void(0)">\
						<i class=" icon-ban-circle"></i>\
					</a> \
					<input class="con-cou text_box" readonly="readonly" type="text" value='+arryData[i].number+'>\
					 <a class="btn-add" href="javacript:void(0)">\
						<i class=" icon-ban-circle"></i>\
					</a>\
				<input  type="hidden" name="isExchange" value="1">\
				<input  type="hidden" name="shop" value='+arryData[i].shopID+'>\
				<input  type="hidden" name="proID" value='+arryData[i].proID+'>\
			</div></div>')
			
		}
		
		
	}
	
	/**
	 * 价格检查
	 * @param tar
	 */
	function priceCheck(tar){
		var parents=tar.parents(".pr-item");
		var price=parseFloat(0);
		$.each(parents.find(".item-con"),function(i,val){
			price=price + (parseFloat($(val).find(".price").text()) * $(val).find(".con-cou").val());
		});
		if(price<parents.find(".shfee").val())
			return false;
		else
			return true;
	}