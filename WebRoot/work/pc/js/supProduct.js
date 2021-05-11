$(function(){


	var brand = function(){

	}

	//批量处理
	var bind_many_handle = function(){ 
		var up_down = function(ids,proCodes,isUse){
			 layer.load(1, {shade: [0.8,'#333']});

			$.ajax({
				type:'post',
				url:path+"supplier/batch_isUse.html",
				data:{ids:JSON.stringify(ids),isUse:isUse}
			}).done(function(msg){
				if(msg){
					layer.msg("系统异常");
					layer.closeAll("loading");
				}else{
					window.location.reload(true);
					layer.msg("操作成功")
				}
			}).error(function(){
				layer.msg("网络异常");
			})
		}

		$("#many_stock").click(function(){
			var obj = getCheck();
			if(obj.ids.length <=0){
				layer.msg("请至少选择一项")
				return;
			}
			$("#stock_modal form").html("");
			var proCodes = obj.proCodes;
			var inventorys = obj.inventorys;
			var names = obj.names;
			var proPrice=obj.proPrice;
			$.each(obj.ids,function(i,val){
				$("#stock_modal form").append
				('<input class="form-control" name="ids" value="'+val+'" type="hidden">\
				  <a class="input-group demo-input-group">\
						<span class="input-group-addon">'+names[i]+'</span>\
						<span class="input-group-addon">价格</span>\
						<input class="form-control" name="proPrice" data-verify="number" value="'+proPrice[i]+'" type="text">\
				  </a>');
			})
			$("#stock_modal").modal();
		})

		$("#stock_modal #save").click(function(){
			var obj = getCheck();
			var ids = obj.ids;
			
			var proPrice=[];
			var b = $("#stock_modal form").verifys({msg:["请输入正确的数字"]});
			if(!b)return;
			var form = $("#stock_modal form").serializeJson();
			if(form.ids.length == 1){
				$.each(form,function(i,val){
					form[i] = [val+""];
					//form[i] = ["+val+"];
				})
			}
			 $("#stock_modal input[name='proPrice']").each(function(i,val){
				 var dd=$(this).val();
					 proPrice[i] =dd;
			 })
			layer.load(1, {shade: [0.8,'#333']});
			$.ajax({
				type:'post',
				url:path+'supplier/batch_inven.html',
				data:{ids:JSON.stringify(ids),proPrice:JSON.stringify(proPrice)}
			}).done(function(msg){
				if(msg){
					layer.msg(msg);
					layer.closeAll("loading");
				}else {
					layer.msg("操作成功");
					$("#stock_modal .close").click();
					priceChangeDel(form);
					layer.closeAll("loading");
//					window.location.reload(true);
				}
			})
			console.log(JSON.stringify(form));
		})

		$("#many_up").click(function(){
			var obj = getCheck();
			var ids = obj.ids;
			var proCodes = obj.proCodes;
			if(ids.length <=0){
				layer.msg("请至少选择一项")
				return;
			}
			new up_down(ids,proCodes,0);
		})

		$("#many_down").click(function(){
			var obj = getCheck();
			var ids = obj.ids;
			var proCodes = obj.proCodes;
			if(ids.length <=0){
				layer.msg("请至少选择一项")
				return;
			}
			new up_down(ids,proCodes,1);
		})

		$("#many_remove").click(function(){
			var obj = getCheck();
			var ids = obj.ids;
			var proCodes = obj.proCodes;
			if(ids.length <=0){
				layer.msg("请至少选择一项")
				return;
			}
			layer.confirm('是否确定删除？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type:'post',
					url:path+"supplier/batch_delpro.html",
					data:{ids:JSON.stringify(ids),proCodes:JSON.stringify(proCodes)},
					dataType:''
				}).done(function(msg){
					if(msg)layer.msg("系统异常");
					else{
						layer.msg("操作成功");
						window.location.reload(true);
					}
				}).error(function(){
					layer.msg("网络异常");
				})
			}, function(){
				//layer.msg('取消');
			});
		})

	}
	// 获取选中值
	function getCheck(){
		var ids = [];
		var proCodes = [];
		var names = [];
		var inventorys = [];
		var proPrice = [];
		$.each($(".check"),function(){
			var b = $(this).prop("checked");
			if(b){
				var id = $(this).parents("tr").find("#id").val();
				var proCode = $(this).parents("tr").find("#proCode").val();
				var name = $(this).parents("tr").find("#name").html();
				var inventory = $(this).parents("tr").find("#inventory").html();
				var price=$(this).parents("tr").find("#price").text();
				ids.push(id);
				proCodes.push(proCode);
				names.push(name);
				inventorys.push(inventory);
				proPrice.push(price);
			}
		});
		return {
			ids:ids,
			proCodes:proCodes,
			names:names,
			inventorys:inventorys,
			proPrice:proPrice
		}
	}

	
	var query = $("#shop_query");
	var brands = "";
	//初始化加载所有品牌
	$.ajax({
		type:'get',
		url:path+'admin/init_brand.html',
		dataType:'json'
	}).done(function(json){
		var bs = query.find("#brands");
		bs.attr("data-id",1);
		var data = json_spilt(json,1,5);
		brand_add_html(bs,data);
		brands = json;
	})

	//点击加载更多后
	$("#brands").on("click","#load_more",function(){
		var bs =query.find("#brands");
		var i = parseInt(bs.attr("data-id"))+1;
		bs.attr("data-id",i);
		if(i >= brands.length){
			layer.msg("已无更多");
			return;
		}
		var data = json_spilt(brands,i,5);
		brand_add_html(bs,data);
	})

	//点击品牌后
	$("#brands").on("click",".brand",function(){
		var b = $(this).hasClass("active");
		$.each(query.find("#brands .brand"),function(){
			$(this).removeClass("active");
		})
		if(!b){
			query.find("#hide_brand").val($(this).attr("data-id"));
			$(this).addClass("active");
		}else query.find("#hide_brand").val("-1");
	})
	
	//联动类别
	var select_url = path+'admin/init_type.html';
	query.find("[name='typeid1']").select({url:select_url,data:0});
	query.on("change","[name='typeid1']",function(e){
		var type = $(this).val();
		query.find("[name='typeid2']").select({url:select_url,data:type});
	})
	
	
	query.find("[name='typeid1']").val(parid);
	query.find("[name='typeid1']").change();
	query.find("[name='typeid2']").val(s_value);
		
	load_table();
		new bind_many_handle();
	 
});

	function load_table(){
		//加载表格内容
		var a = function(bs){
			$.each($(".coverImg"),function(i){
				$(this).prop("src",bs[i].cover);
			})
		}
		
		var funs = function(bs){
			new a(bs);
		}
	
	}

	

	//添加品牌内容
	function brand_add_html(bs,data){
		$.each(data.rows,function(i,val){
			bs.prepend('<a class="list-group-item brand" data-id="'+val.id+'"> <img style="height: 50px;width: 50px;border: #fff 3px solid !important;" class="chat-user-avatar" src="'+val.icon+'" alt="">'+val.name+'</a>');
		})
	}

	//品牌的分页
	function json_spilt(json,now_page,row){
		var newjson = {rows:[]};
		var data = json;
		for(var i=0;i<data.length;i++){
			var start,end;
			start = row*(now_page-1);
			end = row*now_page;
			if(i >= start && i < end){
				newjson.rows.push(data[i]);
			}
		}
		return newjson;
	}
	
	/**
	 * 价格库存更新处理
	 */
	function priceChangeDel(form){
		for(var i=0;i<form.ids.length;i++)
		{
			var parents=$(".tr #id[value='"+form.ids[i]+"']").parents(".tr");
			parents.find("#price").text(form.proPrice[i]);
		}
	}

