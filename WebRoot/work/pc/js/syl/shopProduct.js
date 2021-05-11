$(function(){
	jQuery.getScript(path+"js/syl/verify.js");
	jQuery.getScript(path+"js/syl/jquery.form.js");
	jQuery.getScript(path+"js/syl/basis.js");

	var brand = function(){};

	//批量处理
	var bind_many_handle = function(){ 
		var up_down = function(ids,proCodes,isUse){
			 layer.load(1, {shade: [0.8,'#333']});

			$.ajax({
				type:'post',
				url:path+"product/batch_isUse.html",
				data:{ids:JSON.stringify(ids),proCodes:JSON.stringify(proCodes),isUse:isUse}
			}).done(function(msg){
				if(msg){
					layer.msg("系统异常");
					layer.closeAll("loading");
				}else{
//					window.location.reload(true);
					layer.closeAll("loading");
					staChangeDel(isUse);
					layer.msg("操作成功");
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
				  <input class="form-control" name="proCodes" value="'+proCodes[i]+'" type="hidden">\
				  <a class="input-group demo-input-group">\
						<span class="input-group-addon">'+names[i]+'</span>\
						<input class="form-control" name="inventorys" data-verify="number" value="'+inventorys[i]+'" type="text">\
						<span class="input-group-addon">价格</span>\
						<input class="form-control" name="proPrice" data-verify="number" value="'+proPrice[i]+'" type="text">\
				  </a>');
			})
			$("#stock_modal").modal();
		})

		$("#stock_modal #save").click(function(){
			var b = $("#stock_modal form").verifys({msg:["请输入正确的数字"]});
			if(!b)return;
			var form = $("#stock_modal form").serializeJson();
			if(form.ids.constructor != Array){
				$.each(form,function(i,val){
					form[i] = [val+""];
					//form[i] = ["+val+"];
				})
			}
			layer.load(1, {shade: [0.8,'#333']});
			$.ajax({
				type:'post',
				url:path+'product/batch_inven.html',
				data:{data:JSON.stringify(form)}
			}).done(function(msg){
				if(msg){
					layer.msg(msg);
					layer.closeAll("loading");
				}else {
					layer.closeAll("loading");
					layer.msg("操作成功");
					$("#stock_modal .close").click();
					priceChangeDel(form);
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
					url:path+"product/batch_delpro.html",
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

	//一次显示的品牌数
	var size = 20;
	if(isAdmin == 1){
		$("#span_title").html("商品列表");
		$("#goods").remove();
	}else $("#span_title").html("我的商品");

	var query = $("#shop_query");

	jQuery.getScript(path+"js/syl/jquery.mypagin.js");
	jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){

		var brands = "";
		//初始化加载所有品牌
		$.ajax({
			async:false,
			type:'get',
			url:path+'admin/init_brand.html',
			dataType:'json'
		}).done(function(json){
			var bs = query.find("#brands");
			bs.attr("data-id",1);
			var data = json_spilt(json,1,size);
			brand_add_html(bs,data);
			brands = json;
		})

		//点击加载更多后
		$("#brands").on("click","#load_more",function(){
			var bs = query.find("#brands");
			var i = parseInt(bs.attr("data-id"))+1;
			bs.attr("data-id",i);
			if(i >= brands.length){
				layer.msg("已无更多");
				return;
			}
			var data = json_spilt(brands,i,size);
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
		//query.find("#brands").select({url:path+'admin/init_brand.html'});

		//联动类别
		var select_url = path+'admin/init_type.html';
		query.find("[name='typeID1']").select({url:select_url,data:0});
		query.on("change","[name='typeID1']",function(e){
			var type = $(this).val();
			query.find("[name='typeID2']").select({url:select_url,data:type})
		})

		$("#shop_table").on("click",".shop_edit",function(){
			var id = $(this).parents("tr").find("#id").val();
			window.location.href = path+"admin/edit_product.html?id="+id;
		})
		load_table();
		new bind_many_handle();
		
		bindTdClick(["name","price","inventory"]);
	});  

	//上下架展示切换
	$("input[name='showDown']").on("click",function(){
		$("#showDown").val($("input[name='showDown']:checked").val());
		$("#shop_query button").click();
	});
	//上下架状态改变
	$("body").on("click",".js-upanddown",function(){proStaChang($(this));});

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
		if($("#isExchange").val()==""){
		var v = {
				
			url:path+'admin/init_shop.html?cs='+$("#checkType").val()+"&showDown="+$("input[name='showDown']:checked").val(),
			querydiv:query,
			autopagin:false,
			row:20,
			funs:funs,
			diys:['<img src="" class="coverImg"></img>',"<a class='shop_edit btn btn-primary' href='javascript:;'><i class='fa fa-edit'></i> 编辑</a><a class='btn btn-danger js-upanddown' href='javascript:void();'><i class='fa fa-edit'></i>上/下架</a><a class='shop_ischange btn btn-info' href='javascript:;'><i class='fa fa-edit'></i>可/不可兑换</a>"]
		};
		}else{
			
			var v = {
					
					url:path+'admin/init_shop.html?cs='+$("#checkType").val()+"&showDown="+$("input[name='showDown']:checked").val(),
					querydiv:query,
					autopagin:false,
					row:20,
					funs:funs,
					diys:['<img src="" class="coverImg"></img>',"<a class='shop_edit btn btn-primary' href='javascript:;'><i class='fa fa-edit'></i> 编辑</a><a class='btn btn-danger js-upanddown' href='javascript:void();'><i class='fa fa-edit'></i>上/下架</a>"]
					
				};
		}
	
		$("#shop_table").mytable(v);
	
		sessionCallBackFun();
		
	}
	
	
		
	

	function sessionCallBackFun(){
		if($("#checkType").val()!="" && $("#seNowPage").length){
			var select_url = path+'admin/init_type.html';
			var index=$("#seNowPage").val();
			$("#more").val($("#seMore").val());
			$("select[name='typeID1']").val($("#seType1").val());
			if($("#seType1").val()!=""){
				query.find("[name='typeID2']").select({url:select_url,data:$("#seType1").val()});
				$("select[name='typeID2']").val($("#seTypeID2").val());
			}
			$("#hide_brand").val($("#seBrandID").val());
			$(".pagination .page[data-index='"+index+"']").click();
		}
		document.getElementById("checkType").value="";
	}

	//添加品牌内容
	function brand_add_html(bs,data){
		$.each(data.rows,function(i,val){
			bs.prepend('<a class="list-group-item brand" data-id="'+val.id+'"> <img style="height: 50px;width: 50px;border: #fff 3px solid !important;" class="chat-user-avatar" src="'+val.icon+'" alt="">'+val.name+'</a>');
		});
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
			parents.find("#inventory").text(form.inventorys[i]);
		}
	}
	
	/**
	 * 上下架状态改变
	 */
	function staChangeDel(type){
		$(".tr").each(function(){
			var checkValue = $(this).find(".check").prop("checked");
			if(checkValue){
				if((type==0 && $("#showDown").val()==1) || (type==1 && $("#showDown").val()==0))
					$(this).remove();
//				if(type==0)
//					$(this).find("#isUse").text("上架");
//				else
//					$(this).find("#isUse").text("下架");
			}
		});
	}
});


/***********************************************2016.02.29更新 设置商品为推荐商品 wph********************************/

/**
 * 设置商品为推荐商品
 */
function setProAsRec(){
	if($(".check:checked").length==0){
		layer.msg("请至少选择一项");
		return;
	}
	
	var dataArray=new Array();
	$(".tr").each(function(){
		var checkValue = $(this).find(".check").prop("checked");
		if(checkValue)
		{
			var ID=$(this).find("#id").val();
			var param=new Object();
			param.ID=ID;
			param.isRec=$(this).find("#isRecInt").val();
			dataArray.push(param);
		}
	});
	
	postSelectedData(dataArray);
}

/**
 * 提交商品设置
 * @param data
 */
function postSelectedData(data){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":path+"admin/updateprotorecommond.json",
		"Data":"jsonData="+JSON.stringify(data),
		"fnSuccess" : function(data){
			staChange();
			layer.closeAll('loading');
			layer.msg(data.msg);
		},
		"fnError" : function(){
			layer.closeAll('loading');
			layer.msg("提交失败，请联系管理员");
		}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 更改商品状态
 */
function staChange(){
	$(".tr").each(function(){
		var parent=$(this);
		var checkValue = $(this).find(".check").prop("checked");
		if(checkValue){
			if(parent.find("#isRecInt").val()==0)
				parent.find("#isRecInt").val(1);
			else
				parent.find("#isRecInt").val(0);
			
			if(parent.find("#isRecommond").text()=="非推荐商品")
				parent.find("#isRecommond").text("推荐商品");
			else
				parent.find("#isRecommond").text("非推荐商品");
		}
	});
}

/**
 * 简单查询
 */
function doEasyQuery(){
	var proName=$("#inputQuery").val();
	$("#more").val(proName);
	$("#shop_query button").click();
}

/**
 * 状态改变
 */
function proStaChang(target){
	var parents=target.parents(".tr");
	var sta=parents.find("#isUse").text();
	parents.find(".check").prop("checked",true);
	if(sta=="上架")
		$("#many_down").click();
	else
		$("#many_up").click();
}

/**
 * 事件绑定列
 * @param bindList
 */
function bindTdClick(bindList){
	for(var i=0;i<bindList.length;i++)
	{
		$("body").on("click","td>span[id='"+bindList[i]+"']",function(){
			var parent=$(this).parents("td");
			var id=$(this).attr("id");
			var html;
			if((/^\d+(\.\d+)?$/).test($.trim($(this).text())))
				html='<input style="font-size:26px;" id="'+id+'" type="text" placeholder="请输入修改的值" value="'+$(this).text()+'" data-type="num">';
			else
				html='<input style="font-size:26px;" id="'+id+'" type="text" placeholder="请输入修改的值" value="'+$(this).text()+'" data-type="str">';
			$(this).remove();
			parent.append(html);
			parent.find("input").focus();
		});
		
		$("body").on("blur","input[id='"+bindList[i]+"']",function(){
			if($(this).val()=="")
			{
				layer.msg("请输入修改内容");
				$(this).focus();
				return;
			}
			if($(this).attr("data-type")=="num" && !(/^\d+(\.\d+)?$/).test($(this).val()))
			{
				layer.msg("请输入数字");
				$(this).focus();
				return;
			}	
			var parents=$(this).parents(".tr");
			var tdParents=$(this).parents("td");
			var param="colName="+$(this).attr("id")+"&colValue="+$(this).val()+"&proID="+parents.find("#id").val()+
					  "&proCode="+parents.find("#proCode").val();
			
			var target=$(this);
			
			var posAjax={
				"sURL":path+"product/updateprobycloum.json",
				"Data":param,
				"async":false,
				"fnSuccess" : function(data){
					if(data.msg=="success"){
						var html='<span id="'+target.attr("id")+'">'+target.val()+'</span>';
						target.remove();
						tdParents.append(html);
					}else
						layer.msg(data.msg);
				},
				"fnError"   : function(){layer.msg("修改失败");}
			};
			new g_fnAjaxUpload(posAjax);
		});
	}
}

$("#shop_table").on("click",".shop_ischange",function(){
	var id = $(this).parents("tr").find("#id").val();
	

	$.ajax({
		 type: "POST",
		 url: path+"admin/init_isExChange.html",
		 data: "productId="+id,
		 dataType: "json",
		 success: function(data){

				// layer.msg(data.msg);
				//location.reload(true);
				 

				// layer.msg(data.msg);
				//location.reload(true);

				 },
		 error: function(){layer.msg("操作失败");}
		});
	$(this).parents("tr").find("#isExchange").text()=="不可兑换"?$(this).parents("tr").find("#isExchange").text("可兑换"):$(this).parents("tr").find("#isExchange").text("不可兑换");
	
})
