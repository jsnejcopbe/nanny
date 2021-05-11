;(function($){
	$.fn.extend({
		/**
		 * @param $ 下拉框设置选项  
		 * 			first 是否需要设置下拉的默认选项
		 */
		select:function(options){
			var defVal = {
				first:{value:"-1",text:"全部"},
				url:'',
				data:'',
				type:0
			}
			var obj = $.extend({},defVal,options);
			var $this = this;
			$.ajax({
				async:false,
				type:"post",
				url:obj.url,
				data:{type:obj.data},
				dataType:'json'
			}).done(function(data){
				if(obj.type == 0){
					if(obj.first)$this.html("<option value="+obj.first.value+">"+obj.first.text+"</option>");
					$.each(data,function(i,val){
						if(val.s_value && val.s_text)$this.append("<option value="+val.s_value+">"+val.s_text+"</option>");
					})
				}
				else if(obj.type == 1){
					$this.append('<li><a data-value="'+obj.first.value+'" href="javascript:;">'+obj.first.text+'</a></li>');
					$.each(data,function(i,val){
						if(val.s_value && val.s_text)$this.append('<li><a data-value="'+val.s_value+'" href="javascript:;">'+val.s_text+'</a></li>');
					})
				}
			})
			return $this;
		},
		/**
		 * @param $ 下拉框获取选项html值和依据html设置选中
		 */
		Shtml:function(val){
			if(!val){
				var temp = this.val();
				return this.find("[value='"+temp+"']").html();
			}
			var $this = this;
			$.each(this.find("option"),function(i,v){
				if($(this).html() == val){
					$this.val($(this).val());
					//$this.find("option[value='"+$(this).val()+"']").attr("selected",true);
					return false;
				}
			})
			return $this;
		},
		/**
		 * @param $ 图片的下拉框
		 */
		
		/**
		 * @param $ 表单序列化成json格式
		 */
		serializeJson:function(){
			 var serializeObj={};
	         var array=this.serializeArray();
	         var str=this.serialize();
	         $(array).each(function(){
	             if(serializeObj[this.name]){
	                if($.isArray(serializeObj[this.name])){
	                   serializeObj[this.name].push(this.value);
	                }else{
	                   serializeObj[this.name]=[serializeObj[this.name],this.value];
	                }
	                }else{
	                   serializeObj[this.name]=this.value;
	                } 
	            });  
	         return serializeObj;
		},
		/** data-verify
		 * @param 表单验证 依赖layer
 		 *  msg:[] 提示信息
		 *  
		 *  layer.tips 的配置
		 *  type 
		 *  bg
		 *  time 
		 */
		verifys:function(options){
			var $this = this;
			var defVal = {
				msg:[],
				type:3,
				bg:"#3595CC",
				time:2000
			}
			var obj = $.extend({},defVal,options);
			var b = false;
			$.each($this.find("[data-verify]"),function(i){
				var id = $(this).attr("id");
				var name = $(this).attr("name");
				var verify_type = $(this).attr("data-verify");
				var val = $(this).val();
				var select = id ===undefined ? $this.find("[name='"+name+"']:eq("+i+")"): $this.find("#"+id);
				if(verify_type != "required"){
					if(!eval($.required(val))){
						layer.tips("不能为空", select, {
						    tips: [obj.type, obj.bg],
						    time: obj.time
						});
						b = false;
						return false;
					}
				}
				b = eval("$."+verify_type+"('"+val+"')");
				if(!b){
					var temp = obj.msg.length ==1 ? obj.msg[0]:obj.msg[i];
					if(!temp)return false;
					layer.tips(temp, select, {
					    tips: [obj.type, obj.bg],
					    time: obj.time
					});
					return false;
				}
			})
			return b;
		},
		/**
		 * @param $ 表单的 ajax提交 需要有 jquery.form.js 支持
		 */
		formSubmit:function(options){
			var $this = this;
			var defVal = {
				type:'post',
				async:true,
				url:'',
				data:{data:JSON.stringify($this.serializeJson())},
				dataType:'json',
				success:function(msg){
					alert("提交成功 msg:"+msg);
				},
				error:function(msg){
					alert("提交失败");
				}
			}
			var obj = $.extend({},defVal,options);
			this.ajaxSubmit(obj);
		},
		/**
		 * @param $ 分页控件 
		 *   1 在有bootstrap的基础上使用
		 *   <div class="pagination pagination-centered" id=""> 
		 *	 <div>
		 */
		pagintion:function(options,callback){
			var defaultVal ={
					now_page: 1,//现在第几页
					total_count: 20,//总共记录有多少
					max_page:5,//最大一次显示几页
				    row: 5,//一页显示几行
				    first:1, //第一个显示的内容
				    last:1, //最后一个显示的内容 如果是数字等于最后一页
				    active: 'active',//活动时的class  主要关系于样式 
				    disabled: 'disabled'//失效时的class 主要关系于样式 
				}
				var div = this;
				var obj = $.extend({},defaultVal,options);
				obj.last = isNaN(obj.last)?obj.last:Math.ceil(obj.total_count/obj.row);
				var pagin = new Pagin(div,obj);
				pagin.paint(div,obj);
				
				div.on("click",".page",function(e){
					obj.now_page = $(this).attr("data-index");
					pagin = new Pagin(div,obj);
					pagin.paint(div,obj);
					callback(obj.now_page,obj.total_count);
				})
				
				div.on("click",".prev",function(e){
					obj.now_page = obj.now_page<=1?1:obj.now_page-1;
					pagin = new Pagin(div,obj);
					pagin.paint(div,obj);
					callback(obj.now_page,obj.total_count);
				})
				
				div.on("click",".next",function(e){
					var temp = Math.ceil(obj.total_count/obj.row);
					obj.now_page = obj.now_page>=temp?temp:obj.now_page+1;
					pagin = new Pagin(div,obj);
					pagin.paint(div,obj);
					callback(obj.now_page,obj.total_count);
				})
				
				var Pagin = function(div,obj){
					div.html("");
					var ul = '<ul>\
									<li class="prev"><a href="javascript:void(0);">← 上一页</a></li>\
									<li class="next"><a href="javascript:void(0);">下一页 →</a></li>\
							  </ul>';
					div.append(ul);
					var all_page = Math.ceil(obj.total_count/obj.row);
					if(all_page <= obj.max_page){
						for(var i=0;i<all_page;i++){
							div.find(".next").before('<li data-index="'+parseInt(i+1)+'" class="page"><a href="javascript:void(0);">'+parseInt(i+1)+'</a></li>');
						}
					}else{
						if(obj.now_page<obj.max_page){
							for(var i=0;i<obj.max_page;i++){
								div.find(".next").before('<li data-index="'+parseInt(i+1)+'" class="page"><a href="javascript:void(0);">'+parseInt(i+1)+'</a></li>');
							}
							div.find(".next").before('<li class="page"><span>...</span></li>');
							div.find(".next").before('<li data-index="'+all_page+'" class="page"><a href="javascript:void(0);">'+obj.last+'</a></li>');
						}
						if(obj.now_page >=obj.max_page && obj.now_page < parseInt(all_page)-parseInt(obj.max_page-2)){
							div.find(".next").before('<li data-index="1" class="page"><a href="javascript:void(0);">'+obj.first+'</a></li>');
							div.find(".next").before('<li class="page"><span>...</span></li>');
							var i = parseInt(obj.now_page)-parseInt(obj.max_page/2);
							var j = i+obj.max_page;
							for(i;i<j;i++){
								div.find(".next").before('<li data-index="'+i+'" class="page"><a href="javascript:void(0);">'+i+'</a></li>');
							}
							div.find(".next").before('<li class="page"><span>...</span></li>');
							div.find(".next").before('<li data-index="'+all_page+'" class="page"><a href="javascript:void(0);">'+obj.last+'</a></li>');
						}
						if(obj.now_page >= parseInt(all_page)-parseInt(obj.max_page-2)){
							div.find(".next").before('<li data-index="1" class="page"><a href="javascript:void(0);">'+obj.first+'</a></li>');
							div.find(".next").before('<li class="page"><span>...</span></li>');
							var i = parseInt(all_page)-parseInt(obj.max_page-1);
							for(i;i<=all_page;i++){
								div.find(".next").before('<li data-index="'+i+'" class="page"><a href="javascript:void(0);">'+i+'</a></li>');
							}
						} 
					}
					//取消奇怪的虚线
					div.find("a").each(function(){
						$(this).focus(function(){
							this.blur();
						})
					})
				}
				Pagin.prototype ={
					init:function(div,obj){
						div.find(".next").removeClass(obj.disabled);
						div.find(".prev").removeClass(obj.disabled);
						$.each(div.find("li"),function(){
							$(this).removeClass(obj.active);
						})
					},
					paint:function(div,obj){
						this.init(div,obj);
						div.find("[data-index='"+obj.now_page+"']").addClass(obj.active);
						if(obj.now_page == Math.ceil(obj.total_count/obj.row))div.find(".next").addClass(obj.disabled);
						if(obj.now_page == 1)div.find(".prev").addClass(obj.disabled); 
					}
				}
			return div;
		}
		
	})
})(jQuery)