var html='<div class=""></div>'

$(function(){
	//page_init();
	layer.config({
	    extend: 'extend/layer.ext.js'
	});
	basis_init();
	delivery_scope(); 
	$('.timepicker').timepicki({show_meridian:false});
	
		
	
})

function delivery_scope(){
	var id = $("#hide_id").html();
	
	var load = function(){
		jQuery.getScript(path+"js/syl/jquery.form.js");
		jQuery.getScript(path+"js/syl/jquery.mypagin.js");
		jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){
			  $("#adress_table").mytable({
				  url:path+"shop/delivery/scopelist.html",
				  querydiv:$("#onequery"),
				  btns:[{
					 value:"",
					 name:"remove",
					 isclass : 'btn btn-danger',
					 icon:"fa fa-trash"
				  }]
			  })	
			
			  $("#all_adress_table").mytable({
				  url:path+"shop/adress/getAdresslist.html",
				  querydiv:$("#twoquery"),
				  btns:[{
						 value:"",
						 name:"add_adress",
						 isclass : 'btn btn-success',
						 icon:"fa fa-plus"
				  }]
			  })
			
			  $("body").on("click","[href='#profile2']",function(){
				  //var v = $("#adress_table tbody tr").length;
				  //if(v==0)$("#go_adress").click();
			  })
		})
		
	}
	
	function helpf5(table,table_id,parent){
		var temp = '<table class="table users-table table-condensed table-hover" id="'+table_id+'">\
						<thead>\
							<tr '+(table_id == "all_adress_table"?"data-check=true":"")+'>\
								<th data-hide="id"></th>\
								<th data-value="province">省份</th>\
								<th data-value="city">城市</th>\
								<th data-value="area">区</th>\
								<th data-value="detAdd">详细地址</th>\
								<th data-value="addName">小区名</th>\
								<th data-hide="lon"></th>\
								<th data-hide="lat"></th>\
								<th data-btns="btns">操作</th>\
							</tr>\
						</thead>\
					</table>';
					table.siblings("#pagina_div").remove();
					table.remove();
					parent.append(temp);
	}
	
	var bind = function (){
		$("#profile2").on("click","#go_adress",function(){
			$("#onetable,#twotable,#onequery,#twoquery,#go_back,#add_adress").toggle();
			$(this).hide();
		})

		$("#profile2").on("click","#go_back",function(){
			$("#onetable,#twotable,#onequery,#twoquery,#go_back,#add_adress").toggle();
			$("#go_adress").show();
			helpf5($("#adress_table"),"adress_table",$("#onetable"));
			$("#adress_table").mytable({
				  url:path+"shop/delivery/scopelist.html",
				  querydiv:$("#onequery"),
				  btns:[{
					 value:"",
					 name:"remove",
					 isclass : 'btn btn-danger',
					 icon:"fa fa-trash"
				  }]
			})
		})
		
		$("body").on("click","[name='remove']",function(){
			var adressID = $(this).parents("tr").find("#id").val();
			var tr = $(this).parents("tr");
			var index = layer.confirm('是否确定删除？', {
			    btn: ['取消','确定'] //按钮
			}, function(){
				layer.close(index);
			}, function(){
				 $ajax({
					   url:path+"shop/delivery/remove.html",
					   data:{id:id,adressID:adressID},
					   done:function(){
						   layer.msg("删除成功");
						   tr.remove();
						   helpf5($("#all_adress_table"),"all_adress_table",$("#twotable"));
						   $("#all_adress_table").mytable({
								  url:path+"shop/adress/getAdresslist.html",
								  querydiv:$("#twoquery"),
								  btns:[{
										 value:"",
										 name:"add_adress",
										 isclass : 'btn btn-success',
										 icon:"fa fa-plus"
								  }]
						   })
					   }
				   },1)
			});
		})
		
		$("#profile2").on("click","#add_adress,[name='add_adress']",function(){
			if($(this).is("[name='add_adress']"))$(this).parents("tr").find(".check").prop("checked",true);
			var temp = getCheck();
			var b = temp.b;
			var trs = temp.trs;
			if(b.length <= 0) return layer.msg("请至少选择一行");
			$ajax({
				url:path+"shop/delivery/add.html",
				data:{shopID:id,ids:JSON.stringify(b)},
				done:function(msg){
					if(msg == "不能为空") return layer.msg("请至少选择一行");
					//if(!isNaN(msg)) return layer.msg(temp.length-msg+"条记录添加成功 有"+msg+"条配送范围已经存在");
					layer.msg("添加成功");
					$.each(trs,function(i,val){
						val.remove();
					});
					location.href=path+"shop/dispatchedit.html";
				}
			},1)
		})
	}
	
	function getCheck(){
		var b = [],trs = [];
		$.each($(".check"),function(){
			var a = $(this).prop("checked")
			if(a){
				trs.push($(this).parents("tr"));
				b.push($(this).parents("tr").find("#id").val());
			}
		});
		return {
			b:b,
			trs:trs
		};
	}
	
	new load();
	new bind();
}

var source = [];
var sga_id = "";//
function basis_init(){
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
	
	
	var load = function(){
		$ajax({
			url:path+'shop/baseinfo/init.html',
			done:function(json){
				json = json[0];
				$("#shop_name").html(json.shop_name);
				var address = json.detAdd === undefined ? '暂无详细地址   <a class="edit_address_btn">添加地址</a>' : json.detAdd+'  <a class="edit_address_btn">修改地址</a>';
				$("#shop_address").html("地址 :  "+address);
				$("#shop_img").prop("src",json.shop_icon);
				var tel= json.memo;
				$("#shop_phone").html(json.memo);
				 tell= tel.split(",");
				$("#shop_phone1").html(tell[0]);
				$("#shop_phone2").html(tell[1]);
				$("#price1").val(json.delivery_price);
				$("#price2").val(json.min_send_price);
				//var title = json.title === undefined ? "暂无" : json.title;
				//var con = json.con === undefined ? "暂无" : json.con;
				//var startTime = json.startTime === undefined ? "" : json.startTime;
				//var endTime = json.endTime === undefined ? "" : json.endTime;
				//$("#shop_notice p").html("店铺公告 :");
				//
				$("#hide_id").html(json.id);
				/*$("#timepicker1").val(startTime);
				$("#timepicker2").val(endTime);*/
				$("#two_code img").attr("src",path+"image/TwoCode.html?data="+json.shop_des);
			}
		})
		
		var id = $("#hide_id").html();
		
		$ajax({
			url:path+"shop/notice/init.html",
			data:{id:id},
			done:function(json){
				$.each(json,function(i,val){
					var n = (val.con === undefined || val.con.length == 0) ? "暂无公告 现在添加公告？" : val.con;
					$("#shop_notice textarea").val(n);
					return false;
				})
			}
		})
		
		$ajax({
		   url:path+"shop/runtime/init.html",
		   data:{id:id},
		   done:function(json){
			   $.each(json,function(i,val){
				   var time = $(".runtime:first").clone();
				   $(time).find("#time_id").val(val.id);
				   $(time).find("#timepicker1").val(val.startTime);
				   $(time).find("#timepicker2").val(val.endTime);
				   $("#times form").append(time);
			   })
			   if($(".runtime").length>1)$(".runtime:first").remove();
		   }
		})
		// 二级联动 
		$("#province").select({url:path+"shop/adress/getProvince.html"})
		$("#province").change(function(){
			$("#city").select({url:path+"shop/adress/getCity.html",data:$(this).val()})
		})
		
		$ajax({
			url:path+"shop/adress/getAdress.html",
			done:function(json){
				var lon,lat;
				$.each(json,function(i,val){
					 var temp = {};
					 temp.id = val.id;
					 temp.name = val.detAdd+val.addName;
					 lon = val.lon;
					 lat = val.lat;
					 source.push(temp);
				})
				
				$('.community_search').typeahead({
				      source:source,
				      itemSelected:function(item,val,text){
				    	  sga_id = val;
				    	  $('.community_search').prop("readonly",true);
					  }
				});
				
				
			}
		})
		
	}
	
	var bind = function(){
		$("#go_shop").click(function(){
			var id = $("#hide_id").html();
			window.location.href = path+"store-"+id+".html"
		})
		
		$("#edit_shop").click(function(){
			var id = $("#hide_id").html();
			var textarea = $(this).siblings("textarea").val();
			$ajax({
				url:path+'shop/notice/edit.html',
				data:{id:id,data:textarea},
				done:function(msg){
					//if(!msg)
					layer.msg("修改成功",{icon:1});
				}
			},1)
		})
		
		$("#times").on("click",".open_remove_shop",function(){
			var time_id = $(this).siblings("dl").find("#time_id").val();
			if(!time_id){
				var par = $(this).parents(".runtime");//layer.msg('', function(){});	
				if(par.index() > 0)par.remove();
				return;
			}
			layer.confirm('是否确定删除该营业时间？', {
			    btn: ['确定','取消'] //按钮
			}, function(){
				$ajax({
					url:path+"shop/runtime/remove.html",
					data:{time_id:time_id},
					done:function(msg){
						layer.msg("删除成功");
						window.location.reload(true);
					}
				},1)
			})
		})
		
		$("#times").on("click",".open_edit_shop",function(){
			var id = $("#hide_id").html();
			var time1 = $(this).siblings("dl").find("#timepicker1").val();
			var time2 = $(this).siblings("dl").find("#timepicker2").val();
			var time_id = $(this).siblings("dl").find("#time_id").val();
			if(!(time1 && time2)){
				layer.msg("请选择时间",{icon:0})
				return;
			}
			var temp1 = time1.split(":");
			var temp2 = time2.split(":");
			if(temp2[0]<temp1[0]){
				layer.msg("终止时间不能早于开始时间",{icon:0});
				return;
			}else if(temp2[0] == temp1[0]){
				if(temp2[1]<=temp1[1]){
					layer.msg("终止时间不能早于开始时间",{icon:0});
					return;
				}
			}
			$ajax({
				url:path+'shop/runtime/edit.html',
				data:{id:id,time1:time1,time2:time2,time_id:time_id},
				done:function(json){
					layer.msg("修改成功",{icon:1});
				}
			},1)
		})
		
		$("#times").on("click","#plus_time",function(){
			
			var time = $(".runtime:first").clone();
			$(time).find("input").val("");
			//$(this).parent().before(time);
			$("#times form").append(time);
			$('.timepicker').timepicki({show_meridian:false});
		}) 
		
		$("#map_add").click(function(){
			var id = $("#hide_id").html();
			var val = $('#delivery .community_search').val();
			openMap(id,"");
		})
		
		$(".edit_address_btn").click(function(){
			$("#Areass").toggle();
			$('#delivery .community_search').val("");
		})
		
		$("#again_btn").click(function(){
			$('#delivery .community_search').val("");
			sga_id = "";
			$('.community_search').prop("readonly",false);
		})
		
		$("#sava_add").click(function(){
			if(!sga_id)return layer.msg("请选择您所在的地址 或增加新地址");
			var id = $("#hide_id").html();
			$ajax({
				url:path+"shop/adress/edit.html",
				data:{adressID:sga_id,shopID:id},
				done:function(msg){
					layer.msg("保存成功");
					$("#Areass").hide();
					window.location.reload(true);
				}
			},1)
		})
		
		$("#two_code a").click(function(){
			window.location.href = $("#two_code img").attr("src");
		})
		
		var reg = /^([1-9][0-9]*|0)(\\.[0-9]+)?$/;
		// 配送费
		$("#price1btn").click(function(){
			//delivery_price
			var v = $("#price1").val();
			if(isNaN(v))return layer.msg("请输入正确的数字");
			$ajax({
				url:path+"shop/price/change.html",
				data:{type:"delivery_price",value:v},
				done:function(msg){
					if(msg)return layer.msg(msg);
					layer.msg("保存成功");
				}
			},1)
		})
		// 起送价
		$("#price2btn").click(function(){
			//min_send_price
			var v = $("#price2").val();
			if(isNaN(v))return layer.msg("请输入正确的数字");
			$ajax({
				url:path+"shop/price/change.html",
				data:{type:"min_send_price",value:v},
				done:function(msg){
					if(msg)return layer.msg(msg);
					layer.msg("保存成功");
				}
			},1)
		})
	}
	load();
	bind();
}  
//打开地图
function openMap(id,detAdd,lon_,lat_){
	layer.config({
	    extend: 'extend/layer.ext.js'
	});
	var map = $(document.getElementById('mapIframe').contentWindow.document.body);
	$(map).find(".search_t").val(detAdd);
	if(lon_ && lat_){
		lon = lon_;
		lat = lat_;
		createParamData();
	}
	
	$(map).find("#btn_search").click();
	var poi_cur = $(map).find("#poi_cur").val();
	var addr_cur = $(map).find("#addr_cur").val();
	
	$("#mapModal").modal();
	$("#mapSave").click(function(){
		if(add_det=="" || province=="" || city=="" || area=="" || lon=="" || lat=="")
		{
			layer.msg("请选择一个地址");
			return;
		}
		
		var name=prompt("请输入所在的商圈","");
		if(name){
			var params = {
					province:province,
					city:city,
					area:area,
					detAdd: add_det,
					addName:name,
					lon:lon,
					lat:lat
				};
				$ajax({
					url:path+"/shop/adress/add.html",
					data:{data:JSON.stringify(params),shop_id:id},
					done:function(msg){
						$("#mapModal .close").click();
						layer.msg("保存成功");
						$("#Areass").hide();
						window.location.reload(true);
					}
				},1) 
		}
		
	})
}

/**
 * 获得地图上的参数
 * @param param
 */
var add_det="";
var province="";
var city="";
var area="";
var lon="";
var lat="";
var nowId="";
function getPos(param){
	add_det=param.add_det;
	province=param.province;
	city=param.city;
	area=param.area;
	lon=param.lon;
	lat=param.lat;
}
/**
 * 初始化地图上参数
 * @param param
 */
function createParamData(){
	var param=new Object();
	param.add_det=add_det;
	param.province=province;
	param.city=city;
	param.area=area;
	param.lon=lon;
	param.lat=lat;
	document.getElementById("mapIframe").contentWindow.setData(param);
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
		layer.msg("网络异常");
	})
}



function edit_tel() {
	/*layer.load(1, {shade: [0.8,'#333']});
	layer.prompt({title: '店铺电话修改', formType: 3}, function(text){
		var memo=text;
       	 var param={
        		"sURL":path+"shop/updshoptel.html",
        		"Data":"&memo="+memo,
        		"fnSuccess":function(data){
        			layer.closeAll('loading');
        			layer.msg("修改成功");
        			setTimeout("location.reload(true)", 2000);
        		},
        		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
        	};
        	new g_fnAjaxUpload(param);
        
    });*/
	
	$("#tel1").val($("#shop_phone1").html());
	$("#tel2").val($("#shop_phone2").html());
	layer.open({
	    type: 1,
	    //shade: false,
	    title :"编辑店铺电话",
	    shadeClose: true,
	    content: $('#shop_tel'), //捕获的元素
	    cancel: function(index){
	    	
	        //layer.close(index);
	        //this.content.show(); 
	    }
	});
	
}

function upd_tel() {
	var memo=$("#tel1").val()+","+$("#tel2").val();
	 var param={
     		"sURL":path+"shop/updshoptel.html",
     		"Data":"&memo="+memo,
     		"fnSuccess":function(data){
     			layer.closeAll('loading');
     			layer.msg("修改成功");
     			setTimeout("location.reload(true)", 2000);
     		},
     		"fnError":function(){layer.closeAll('loading');layer.msg("操作有误");}
     	};
     	new g_fnAjaxUpload(param);
     
 }
	

