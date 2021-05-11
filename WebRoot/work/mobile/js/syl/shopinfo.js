$(function(){
	layer.config({
	    extend: 'extend/layer.ext.js'
	});
	
	init();
	
	$ajax({
		url:path+"shop/adress/getAdress.html",
		done:function(json){
			data = json;
			/*$("#hide_search .list").append("<a class='blue'>没有找到所在的小区？去添加</a>");*/
		}
	})
})

var data;
var AreaLayer = function(){
	
	setData(data);
	console.log(data);
	
	$("body").on("click",".input-group-addon",function(){
		var v = $(".layui-layer-content .area_search").val();
		var arr=$.grep(data,function(n){
            return mat(n.addName);
		});
        
        function mat(item) {
            return ~(item+"").toLowerCase().indexOf(v);
        }
        
        $("#hide_search input").attr("value",v);
        setData(arr);
	})
	
	function setData(data){
		$("#hide_search .list a").remove();
		
		var index = layer.load(0, {shade: false}); 
		$.each(data,function(i,val){
			var div = '<a href="#" data-id="'+val.id+'" class="list-group-item">'+val.addName+'</a>';
			$("#hide_search .list").append(div);
		})
		
		//自定页
		//layer.closeAll();
		layer.open({
		    type: 1,
		    title:'',
		    skin: 'layui-layer-rim', //样式类名
		    closeBtn: 0, //不显示关闭按钮
		    shift: 2,
		    area: ['320px', '300px'], //宽高
		    shadeClose: true, //开启遮罩关闭
		    content: $("#hide_search").html(),
		    success: function(layero, index){
		    	layer.closeAll('loading');
		    }
		});
	}
}

function init(){
//	$("#two_code").prop("src",path+"image/TwoCode.html?data=store-"+shopID+".html");
	
	//alert($("#hide_time_select .time_hour").val())
	var load = function(){
		$ajax({
			url:path+"shop/baseinfo/init.html",
			done:function(json){
				json = json[0];
				$("#shopName p").html(json.shop_name);
				var address = json.addName === undefined ? '暂无详细地址   <a class="edit_address_btn blue pull-right">添加地址</a>' : json.addName+'  <a class="edit_address_btn blue pull-right">修改地址</a>';
				$("#shopArea p").html(address);
				$("#shopTel p").html(json.memo);
					
				$("#shop_img").prop("src",json.shop_icon);
				var delivery_price = json.delivery_price === undefined ? "0" : json.delivery_price;
				var min_send_price = json.min_send_price === undefined ? "0" : json.min_send_price;
				$("[name='delivery_price']").val(delivery_price);
				$("[name='min_send_price']").val(min_send_price);
				
				$("#two_code").attr("src",path+"image/TwoCode.html?data="+json.shop_des);
			}
		})
		
		$ajax({
		   url:path+"shop/runtime/init.html",
		   data:{id:shopID},
		   done:function(json){
			   if(json && json.length > 0)$("#times .fm-cont").remove();
			   $.each(json,function(i,val){
				   var div = '<div class="fm-cont" id="time" data-id="'+val.id+'">\
									<div class="li_cont" >\
										<label class="">起始时间</label>\
							  			<a class="blue startTime">'+val.startTime+'</a>\
									</div>\
									<div class="li_cont">\
										<label class="">终止时间</label>\
							  			<a class="blue endTime">'+val.endTime+'</a>\
									</div>\
									<a class="times_remove btn btn-danger"><i class="fa fa-trash-o"></i></a>\
							  </div>'
				   $("#times #time_btn").before(div);
			   })
		   }
		})
		
		$ajax({
			url:path+"shop/notice/init.html",
			data:{id:shopID},
			done:function(json){
				console.log(json)
				$("#shopNotice p").html("<span class='getNotice'>暂无公告</span> 现在<a class='blue addNotice pull-right'>添加公告</a>")
				$.each(json,function(i,val){
					var n;
					if(!val.con || val.con.length == 0) n = "<span class='getNotice'>暂无公告</span> 现在<a class='blue addNotice pull-right'>添加公告</a>";
					else n = "<span class='getNotice'>"+val.con+"</span>" +"&nbsp;&nbsp;<a class='blue addNotice pull-right'>修改公告</a>"
					$("#shopNotice p").html(n);
					return false;
				})
			}
		})
		
		//upload
		$("body").on("change","#head_file",function(){
			var file_url;
			$.ajaxFileUpload({
				url : path+'upload/shopimg.html',
				secureuri:false,
				fileElementId:"head_file",
				dataType:'json',
				success: function (data){
					var file_url = data.file_url;
					$("#shop_img").prop("src",file_url);
					
					$ajax({
						url:path+'shop/headimg.html',
						data:{url:file_url},
						done:function(msg){
							layer.msg("上传成功");
						}
					},1)
				},
				error  : function(data){		
					layer.msg("网络异常");
				}
			});
		})
		
	}
	
	var bind = function(){
		$("body").on("click",".addNotice",function(){
			layer.prompt({
			    formType: 2,
			    value: $(".getNotice").html(),
			    title: '请输入公告'
			}, function(value, index, elem){
			    var n = value.length == 0 ? "<span class='getNotice'>暂无公告</span> 现在<a class='blue addNotice pull-right'>添加公告</a>" : "<span class='getNotice'>"+value+"</span>" +"&nbsp;&nbsp;<a class='blue addNotice pull-right'>修改公告</a>";
			    $("#shopNotice p").html(n);
			    if(value){
			    	$ajax({
						url:path+'shop/notice/edit.html',
						data:{id:shopID,data:value},
						done:function(msg){
							layer.msg("保存成功");
						}
					},1)
			    }
			    layer.close(index);
			});
		})
		
		$("body").on("click",".edit_address_btn",function(){
			new AreaLayer();
		})
		
		$("body").on("click",".layui-layer-content .list-group-item",function(){
				$(this).addClass("active");
				var sga_id = $(this).attr("data-id");
				$("#shopArea p").html($(this).html()+'&nbsp;&nbsp;<a class="edit_address_btn blue pull-right">修改地址</a>');
				//保存地址
				if(sga_id){
					$ajax({
						url:path+"shop/adress/edit.html",
						data:{adressID:sga_id,shopID:shopID},
						done:function(msg){
							layer.msg("保存成功");
							$(".layui-layer-shade").click();
						}
					},2)
				}
		})
		//添加行
		$("#times").on("click",".times_add",function(){
			var div = '<div class="fm-cont" id="time">\
							<div class="li_cont" >\
								<label class="">起始时间</label>\
					  			<a class="blue startTime">请选择</a>\
							</div>\
							<div class="li_cont">\
								<label class="">终止时间</label>\
					  			<a class="blue endTime">请选择</a>\
							</div>\
							<a class="times_remove btn btn-danger"><i class="fa fa-trash-o"></i></a>\
					  </div>'
			$("#times #time_btn").before(div);
		})
		
		//删行除
		$("body").on("click",".times_remove",function(){
			var $this = $(this).parents("#time");
			var id = $this.attr("data-id");
			if(!id){
				if($("#times #time").length > 1)$this.remove();
				return
			}
			layer.confirm('是否确定删除该营业时间？', {
			    btn: ['确定','取消'] //按钮
			}, function(){
				$ajax({
					url:path+"shop/runtime/remove.html",
					data:{time_id:id},
					done:function(msg){
						layer.msg("删除成功");
						if($("#times #time").length > 1)$this.remove();
						else window.location.reload(true);
					}
				},1)
			})
		})
		
		$("body").on("blur",".price",function(){
			var name = $(this).attr("name");
			var v = $(this).val();
			
			var $this = $(this);
			var reg = /^(\d{0,3}?|0)?$/;
			if(isNaN(v)){
					blur_b = false;
					setTimeout(function(){
						$this.focus();
						layer.closeAll();
						layer.msg("请输入正确的数字");
					},200);
					return
			}
			help_save_price(name,v);
			layer.msg("保存成功");
		})
		
		//时间选择
		$("body").on("click",".startTime",function(){
			var $this = $(this);
			help_time($this.html(),$this);
			
		})
		
		$("body").on("click",".endTime",function(){
			 var $this = $(this);
			 help_time($this.html(),$this)	
		})
		
	}
	new load();
	new bind();
}

function help_time(time,$this){
	if(time == "请选择"){
		var today=new Date()
		var h=today.getHours()
		var m=today.getMinutes()
		$(".time_hour").attr("value",h);
		$(".time_min").attr("value",m);
	}else{
		var h = time;
		var temp1 = h.split(":");
		$(".time_hour").attr("value",temp1[0]);
		$(".time_min").attr("value",temp1[1]);
	}
	
	var index = layer.open({
	    type: 1,
	    title:'',
	    skin: 'layui-layer-nobg', //样式类名
	    closeBtn: 0, //不显示关闭按钮
	    shift: 2,
	    area: ['182px', '148px'], //宽高
	    shadeClose: false, //开启遮罩关闭
	    content: $("#hide_time_select").html()
	});
	
	jQuery.getScript(path+"js/mytimepicki.js");
	
	$(".layui-layer-shade").unbind("click");
	$(".layui-layer-shade").click(function(){
		var h = $(".layui-layer .time_hour").val();
		var m = $(".layui-layer .time_min").val();
		var reg = /^([0-9][0-9])?$/;
		if(!reg.test(h) && !reg.test(m))return layer.msg("请输入正确数字");
		if(h>23 || m >59)return layer.msg("时间格式错误");
		var time = h+":"+m;
		//console.log($this)
		var b = $this.hasClass("endTime");
		if(b){
			var parent =  $this.parents("#time");
			var time1 = parent.find(".startTime").html();
			var time2 = time;
			var time_id = parent.attr("data-id");
			if(help_save_time(shopID,time1,time2,time_id)) $this.html(time);
		}
		else $this.html(time);
		layer.close(index);
	})
}

function help_save_time(shopID,time1,time2,time_id){
	if(time1 == "请选择"){
		layer.msg("请先选择起始时间",{icon:0})
		return false;
	}
	var temp1 = time1.split(":");
	var temp2 = time2.split(":");
	if(temp2[0]<temp1[0]){
		layer.msg("终止时间不能早于开始时间",{icon:0});
		return false;
	}else if(temp2[0] == temp1[0]){
		if(temp2[1]<=temp1[1]){
			layer.msg("终止时间不能早于开始时间",{icon:0});
			return false;
		}
	}
	time_id = time_id === undefined ? "" : time_id;
	$ajax({
		url:path+'shop/runtime/edit.html',
		data:{id:shopID,time1:time1,time2:time2,time_id:time_id},
		done:function(json){
			layer.msg("营业时间修改成功",{icon:1});
		}
	},1)
	return true;
}

function help_save_price(name,v){
	$ajax({
		url:path+"shop/price/change.html",
		data:{type:name,value:v},
		done:function(msg){
			//if(msg)return layer.msg(msg);
			//layer.msg("保存成功");
		}
	},2)
}

function $ajax(option,type){
	var defVal = {
		async:false,
		url:'',
		data:'',
		dataType:'json',
		done:function(json){
			
		}
	}
	var obj = $.extend({},defVal,option);
	obj.dataType = type=== undefined ? "json" : "text";
	$.ajax({
		async:obj.async,
		type:'post',
		url:obj.url,
		data:obj.data,
		dataType:obj.dataType
	}).done(obj.done)
	.error(function(msg){
		//console.log(msg)
		if(type)layer.msg("网络异常");
	})
}


/*function edit_tel() {
	var tel =$("#shopTel p").html();
	
	tell= tel.split(",");
	$("#tel1").val(tell[0]);
	$("#tel2").val(tell[1]);
	layer.open({
	    type: 1,
	    shade: false,
	    title :"编辑店铺电话",
	    //shade: 0.3,
	    //shadeClose: true,
	    content: $('#shop_tel'), //捕获的元素
	    cancel: function(index){
	    	
	        //layer.close(index);
	        //this.content.show(); 
	    }
	});
	
}

function upd_tel() {
	var memo=$("#tel1").val()+","+$("#tel2").val();
	$ajax({
			type:"post",
     		url:path+"shop/updshoptel.html",
     		data:"&memo="+memo,
     		dataType: "text",
     		done: function(data){
     			layer.msg("修改成功");
     			setTimeout("location.reload(true)", 2000);
         	  },
         	
     	});
     	
     
 }*/
	