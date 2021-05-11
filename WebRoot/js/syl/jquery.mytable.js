/**
 * @author Zombie
 * 2015-11-13
 * 表格插件 v4.0 添加排序完善bug
 *   
 *   tr 标签
 *   <tr data-order="true"> true开启排序
 * 	
 *   各种 th 标签用法  width 不是 必须的 默认 auto
 *   <th data-auto="id" width="60px;">自增长</th> 只能在第一个
 *   
 *   <th data-check="id" width="60px;">
 *   
 *   <th data-hide="id" width="60px;">隐藏</th>
 *	 <th data-value="name" width="60px;">普通text</th>
 *	 <th data-select="age:get_select_data.html" width="100px;">下拉选择</th>
 *	 <th data-btns="btns">操作</th>按钮</th>
 *	 			  btns:[{
					 value:"",
					 name:"remove",
					 isclass : 'btn btn-danger',
					 icon:"fa fa-trash"
				  }]
 *	 <th data-diy="true">自定义1</th>  自定义的单元格 
 *		 两种情况 1:true diys:["html"]
 *				 2:字段 diys:{字段1:"html",字段2:"html"}
 *
 *	 两种下拉框的 data-select 决定生成的select name和id是什么即对应数据库列名
 *				data-on="xx" 下拉框的依赖 即要想要选中的项 可以为空
 *	 第一种 下拉框的使用 后台返回的json格式 必须包含 value 和 text 这两个键值对 分别是 option 的value 和 html内容
 * 	 <th data-select="state" data-url="get_select_data1.html" width="80px;">状态2</th>
 *   第二种 
 *   <th data-select="state" width="80px;">状态2</th> 下拉框的使用 数据不由后台生成 在界面上写死状态 
 *   json格式 例： [ 
 *					 [
 *  					{"value":1,"text":"例子1"},
 *						{"value":2,"text":"例子2"}
 *					 ],
 *					 [
 *						{"value":1,"text":"状态1"},
 *						{"value":2,"text":"状态2"}
 *					 ]
 *				  ]
 *	<th data-btn="btns" width="auto">操作</th> 只能出现在最后一列 
 */
;(function($){
	$.fn.mytable = function(options,callback){
		var defaultVal = {
			url:'',
			dataType:'json',
			querydiv:'', //查询form所在的div
			diys:[], // 自定义的列 多个按照先后顺序绑定到表格上
			btns:[], // 最后一列的 按钮组  
			select:{ // 下拉框的配置
				type:1, //下拉框的类型 默认 1 第一个项为请选择 值为-1 关闭 0 
				json:[] // 用来配置下拉框的option （写死情况下）
			},
			funs:'',
			autopagin:true,//前台分页
			type:0,//0 不开启可改变行数的分页  1 开启
			row:8, // 初始化时表格每页显示几行
			rowhomes:[], //可选的一页几行 例: [2,5,10]
			mypagin_config:{}, // 分页控件的配置,
			f5btn:false, //是否有刷新按钮
			showMsg:true, //查询成功后是否显示消息
			tr:'<tr class="tr"></tr>',
			pagination:'<div class="pagination pagination-centered" id="pagination"></div>'
		}
		var $this = this;
		var obj = $.extend({},defaultVal,options);
		obj.mypagin_config.type = obj.type;
		obj.mypagin_config.row = obj.row;
		obj.mypagin_config.rowhomes = obj.rowhomes;
		
		$this.nextAll("#pagina_div").remove();
//		$this.find(".order").parent().remove();
//		$this.find("#all_check").remove();
		init($this, obj);

		if(!obj.autopagin){
			var temp = {now_page:1,row:obj.row};
			var json = init_load($this,obj,{data:JSON.stringify(temp)});
			if(!json) return this;
			if(!json.total){alert("json格式不对 请检测json是否包含 total键值"); return this}
			init_search($this,obj,temp);// bind query
			start_pagin(json,$this,obj,json.total);
		}else if(obj.autopagin){
			var json = init_load($this,obj);
			if(!json) return this;
			init_search($this,obj);// bind query
			start_pagin(json,$this,obj);
		}
		
		if(callback)callback();
	}
	
	function f5($this){
		$this.siblings("#pagina_div").find(".active").click();
	}
	
	function init($this,obj){
		var tr = $this.find("thead tr");
		var order = tr.attr("data-order");
		var check = tr.attr("data-check");
		 
		if(check){
			tr.prepend('<th data-check="check"><input type="checkbox" id="all_check" name="all_check" /></th>');
			$this.on("click","#all_check",function(){
				var temp = $(this).prop("checked");
				$(".check").prop("checked",temp);
			})
		}
		
		var down = "fa-caret-down";
		var up = "fa-caret-up";
		if(order){
			$this.before("<input type='hidden' id='order' value=''>")
			var length = $this.find("thead tr th").length;
			$.each($this.find("thead tr th"),function(i){
				if($(this).attr("data-value"))$(this).append("<a><i class='fa "+down+" order'></i></a>");
			})
		}
		$this.on("click",".order",function(){
				var b = $(this).hasClass(down);
				isorder_init(down,up);
				var temp = $(this).parents("th").attr("data-value");
				if(b){
					$(this).removeClass(down);
					$(this).addClass(up);
					start_order($this,temp,"desc");
					return;
				}
				start_order($this,temp,"asc");
				$(this).removeClass(up);
				$(this).addClass(down);
		})
	}
	
	function isorder_init(down,up){
		$.each($(".order"),function(){
			$(this).removeClass(up);
			$(this).removeClass(down);
			$(this).addClass(down);
		})
	}
	
	function start_order($this,column,type){
		$this.siblings("#order").val(column+":"+type);
		f5($this);
	}
	
	function init_load($this,obj,data){
		var json;
		var d = (data === undefined || data.length<1) ? {} : data;
		$.ajax({
			async:false,
			type:'post',
			url:obj.url,
			data:d,
			dataType:obj.dataType
		}).done(function(data){
			if(data.rows) json = data;
			else alert("json格式不对  请检测json是否包含 rows键值")
		}).error(function(msg){
			alert("提交失败");
		})
		return json;
	}
	
	function init_search($this,obj,data){
		if(obj.querydiv){
			var query = obj.querydiv;
			var temp = query.find("form");
			temp.submit(function(e){
				var json = temp.serializeJson();
				start_validity(json);
				
				var d = {};
				if(data === undefined)d = json;
				else {
					d = json;
					d.now_page = ""+data.now_page;
					d.row = ""+data.row;
				}
				
				//alert(JSON.stringify(d));
				
				d = {data:JSON.stringify(d)};
				obj.mypagin_config.row = obj.rowhomes.length == 0 ? obj.row : $this.nextAll("#pagina_div").find("#rowhome select").val();
				temp.formSubmit({url:temp.attr("action"),data: d,success:function(json){
					$this.nextAll("#pagina_div").remove();
					if(obj.autopagin)start_pagin(json,$this,obj);
					else start_pagin(json,$this,obj,json.total);
					var temp = json.rows.length;
					if(obj.showMsg) layer.msg("完成查询");//layer.msg("查询到"+temp+"条记录");
				}});
				return false;
			})
		}
	}

	function start_validity(json){
		$.each(json,function(i,val){
			var v = val.replace(/['\s\{\}]/g,"");
			json[i] = v;
		})
	}
	
	function json_spilt(json,now_page,row){
		var newjson = {rows:[]};
		var data = json.rows;
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
	
	function start_pagin(json,$this,obj,total){
		obj.mypagin_config.total_count = obj.autopagin == true ? json.rows.length : total;
		$this.after("<div id='pagina_div' style='text-align: center'></div>");
		$this.next().append(obj.pagination);
		$this.next().find("#pagination").mypagin(obj.mypagin_config,function(now_page,row){
			var new_json = obj.autopagin == true ? json_spilt(json,now_page,row) : pagin_help($this,obj,now_page,row);
			
			new Table($this,new_json.rows.length,obj).init(new_json,$this,obj);
		});
	}
	
	function pagin_help($this,obj,now_page,row){
		var query = obj.querydiv;
		var temp = query.find("form");
		var order = $this.siblings("#order").val();
		var d;
		var json = temp.serializeJson();
		start_validity(json);
		
		d = json;
		d.now_page = ""+now_page;
		d.row = ""+row;
		if(order){
			order = order.split(":");
			d.order = order[0];
			d.orderType = order[1];
		}
		d = {data:JSON.stringify(d)};
		return init_load($this,obj,d);
	}
	
	var Table = function($this,total,obj){
		$this.find("tbody").remove();
		$this.append("<tbody></tbody>");
		$this.find("#all_check").prop("checked",false);
		for(var i=0;i<total;i++){
			var tr = obj.tr;
			$.each($this.find("thead th"),function(){
				tr = $(tr).append("<td></td>")
				
			})
			$this.find("tbody").append(tr);
		}
		return this;
	}
	
	Table.prototype={
		selectdata:[],
		selectinit:function($this){
			var data = [];
			var $temp = this;
			var index = 0;
			$.each($this.find("thead th"),function(i){
				var se_temp = $(this).attr("data-select");
				var d_select = $(this).attr("data-url");
				if(se_temp && d_select){
					$.ajax({
						async:false,
						type:'get',
						url:d_select,
						dataType:'json'
					}).done(function(json){
						data[index] = json;
					}).error(function(msg){
						alert("网络异常");
					})
					index = index +1;
				}
			})
			return data;
		},
		selectset:function($this,obj){
			var $temp = this;
			$.each($this.find("tbody tr"),function(){
				var tr = $(this);
				var index = 0;
				$.each(tr.find("td select"),function(){
					var select = $(this);
					if(obj.select.type == 1)select.append("<option value='-1'>请选择</option>");
					var temp = $temp.selectdata[index] === undefined ? '': $temp.selectdata[index];
					if(temp){
						$.each(temp,function(temp,obj){
							if(!obj.value || !obj.text){
								alert("json格式不对  请检测json是否包含 value键值和 text 键值")
								return false;
							}else select.append("<option value="+obj.value+">"+obj.text+"</option>");
						})
					}
					var on = select.attr("data-on");
					if(on)select.val(on);
					index = index + 1;
				})
			})
		},
		btnset:function($this,obj){
			if(obj.btns.length>0){
				$.each($this.find("tbody tr"),function(){
					var $tr = $(this);
					$.each(obj.btns,function(i,val){
						$tr.find("td:last").append('<a class="'+val.isclass+' mybtns" name="'+val.name+'"><i class="'+val.icon+'"></i>'+val.value+'</a>'); 
					})
				})
			}
		},
		diyset:function($this,obj){
			if(obj.diys.length <= 0)return this;
			var tbody = $this.find("tbody");
			$.each(tbody.find("tr"),function(i){
				var tr = $(this);
				var d_i = 0;
				$.each($this.find("[data-diy]"),function(){
					var index = $(this).index();	
					var temp = obj.diys[d_i];
					if(temp)tr.find("td:eq("+index+")").append(temp);
					d_i = d_i + 1;
				}) 
			})
		},
		checkset:function($this,check){
			var tbody = $this.find("tbody");
			$.each(tbody.find("tr"),function(i){
				if(check == 'radio')$(this).find("td:first").append('<input type="radio" name="radio" />');
				if(check == 'check')$(this).find("td:first").append('<input type="checkbox" class="check" name="check" />');
			})
		},
		auto:function($this,check){
			var tbody = $this.find("tbody");
			$.each(tbody.find("tr"),function(i){
				var temp = i+1;
				var index = $this.find("[data-auto]").index();	
				$(this).find("td:eq("+index+")").append('<span>'+temp+'</span>');
			})
		},
		init:function(json,$this,obj){
			var bs = json.rows;
			if(!bs)return this;
			this.selectdata = (obj.select.json === undefined || obj.select.json.length == 0) ? this.selectinit($this) : obj.select.json;
			var $init = this;
			var check = '';
			var isbtn = false;
			var isdiy = false;
			var auto = false;
			for(var i=0;i<bs.length;i++){
				$.each(bs[i],function(key,val){
					$.each($this.find("thead th"),function(th_i){
						var d_check = $(this).attr("data-check");
							if(d_check)check = d_check;
						var d_text = $(this).attr("data-value");
						var d_auto = $(this).attr("data-auto");
							if(d_auto)auto = true;
						var d_hide = $(this).attr("data-hide");
						var d_edit = $(this).attr("data-edit");
						var d_select = $(this).attr("data-select");
						var on = $(this).attr("data-on");
						var btn = $(this).attr("data-btns");
							isbtn = btn == 'btns' ? true : false;
						var d_diy = $(this).attr("data-diy");
							if(d_diy == "true")isdiy = true;
						var tbody = $this.find("tbody");
						if(d_text && d_text == key){
							tbody.find("tr:eq("+i+") td:eq("+th_i+")").append('<span id="'+key+'">'+val+'</span>');
						}else if(d_hide && d_hide == key){
							$this.find("[data-hide]").hide();
							tbody.find("tr:eq("+i+") td:eq("+th_i+")").hide();
							tbody.find("tr:eq("+i+") td:eq("+th_i+")").append('<input id="'+key+'" type="hidden" value="'+val+'" name="'+key+'"></input>');
						}else if(d_edit && d_edit == key){
							var width = $(this).attr("width") === undefined ? "auto" : $(this).attr("width").replace(";","");
							tbody.find("tr:eq("+i+") td:eq("+th_i+")").append('<input class="edit_table" id="'+key+'" type="text" value="'+val+'" name="'+key+'"></input>');
							$this.find(".edit_table").css('width',width);
						}else if(d_select && d_select == key){
							var width = $(this).attr("width") === undefined ? "auto" : $(this).attr("width").replace(";","");
							var temp = tbody.find("tr:eq("+i+") td:eq("+th_i+")");
							temp.append('<select id="'+key+'" name="'+key+'"></select>');
							if(on){
								var v = tbody.find("tr:eq("+i+")").find("#"+on);
									v = v === undefined ?'-1' : v.val();
								temp.find("select").attr("data-on",v);
							}
							temp.find("#"+key).css('width',width);
						}else if(d_diy && d_diy == key){ 
							var diy = eval("obj.diys."+key);
							diy = $(diy).is("img") == true ? $(diy).prop("src",val):$(diy).html(val);
							tbody.find("tr:eq("+i+") td:eq("+th_i+")").append(diy);
						}
					})
				})
			}
			this.selectset($this,obj);
			if(check)this.checkset($this,check);
			if(isbtn)this.btnset($this,obj);
			if(isdiy)this.diyset($this,obj);
			if(auto)this.auto($this,obj);
			if(obj.funs)eval(obj.funs(bs));
			return this;
		}
	}
})(jQuery)