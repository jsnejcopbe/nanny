$(function(){
	 init();
})

function init(){
	var load = function(){
		jQuery.getScript(path+"js/syl/verify.js");
		jQuery.getScript(path+"js/syl/basis.js");
		jQuery.getScript(path+"js/syl/jquery.form.js");
		jQuery.getScript(path+"js/syl/jquery.mypagin.js");
		jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){
		
			var a = function(){
				remove_btn();
			}
			
			function remove_btn(){
				$.each($("#refund_table tbody tr"),function(){
					var temp = $(this).find("#status").text();
					var sta= $(this).find("#refund_status").val();
					if(temp.indexOf("订单取消")!=-1 || temp.indexOf("交易完成")!=-1 ||
					   sta==1 || sta==2){
						$(this).find(".go_refund").remove();
					}
					
					if(sta==1 || sta==2 || temp.indexOf("订单取消")!=-1){
						$(this).find(".refuse_refund").remove();
					}
				})
			}
		
			$("#refund_table").mytable({
				url:path+"shop/refund/init.html",
				querydiv:$("#refund_query"),
				btns:[{
					 value:"取消订单",
					 name:"",
					 isclass : 'btn btn-primary go_refund',
					 icon:""
				},{
					 value:"退回申请",
					 name:"",
					 isclass : 'btn btn-primary refuse_refund',
					 icon:""
				},{
					 value:"申请详情",
					 name:"",
					 isclass : 'btn btn-primary myorderbtns',
					 icon:""
				
				}],
				
				funs:a
			})
			
			
			
			//var type = temp_shopID === undefined ? shopID : temp_shopID;
			//$("#plot_select").select({url:path+"shop/fans/getArea.html",data:type});
		})
	}
	
	var bind = function(){
		 $("body").on("click",".go_refund",function(){
			 var id = $(this).parents("tr").find("#id").val();
			 var totalprice = $(this).parents("tr").find("#totalPrice").html();
			 $.ajax({
				 url:path+'shop/orderrefund.json',
				 data:"orderID="+id+"&sta="+4+"&cost="+totalprice
			 }).done(function(msg){
				 if(msg){
					 layer.msg("操作成功");
					 window.location.reload(true);
				 }
				 
			 });
		 });
		 
		 $("body").on("click",".refuse_refund",function(){
			 var id = $(this).parents("tr").find("#id").val();
			 $.ajax({
				 url:path+'shop/refuserefundapp.json',
				 data:"orderID="+id
			 }).done(function(msg){
				 if(msg){
					 layer.msg("操作成功");
					 window.location.reload(true);
				 }
				 
			 });
		 });
	};
	
	var check = function(){
		$("body").on("click",".myorderbtns",function(){
			var parent=$(this).parents(".tr");
			var order=parent.find("#orderCode").text();
			$("#bg").css({
	            display: "block", height: $(document).height()
	        });
			 var $box = $('#liyou_'+order);
			 $box.css({
		            //设置弹出层距离左边的位置
		            left: ($("body").width() - $box.width()) / 2 - 125+ "px",
		            //设置弹出层距离上面的位置
		            top: ($(window).height() - $box.height()) / 2 - 120 + "px",
		            display: "block"
		        });
		});
		//点击关闭按钮的时候，遮罩层关闭
	    $(".close").click(function () {
	        $("#bg,.box").css("display", "none");
	    });
	}
	
	new load();
	new bind();
	new check();
}