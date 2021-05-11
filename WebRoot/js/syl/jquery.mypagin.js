/**
 *  @author Zombie
 *  2015/11/1 
 *  分页插件 v2.0
 *  安静的当一个普普通通简简单单的分页控件
 *  
 *  <div class="pagination pagination-centered" id="pagination">
 *	</div>
 */
;(function ($){
	$.fn.mypagin = function(options,callback){
		var defaultVal ={
			now_page: 1,//现在第几页
			total_count: 40,//总共记录有多少
			max_page:5,//最多连续显示几页 
		    row: 5,//一页显示几行
			rowhomes:[2,5,10],//可选的一页几行,
		    first:"首页", //第一个显示的内容
		    last:"尾页", //最后一个显示的内容 如果是数字等于最后一页
		    active: 'active',//活动时的class  主要关系于样式 
		    disabled: 'disabled',//失效时的class 主要关系于样式 
		    diyul:'<ul class="pagination">\
						<li class="prev"><a href="javascript:void(0);">← 上一页</a></li>\
						<li class="next"><a href="javascript:void(0);">下一页 →</a></li>\
			       </ul>',
		    type:0,//0 不开启可改变行数  1 开启
		    diyrowhome:'<div id="rowhome" style="width:100%; text-align: center;">\
					    	<select style="width:80px;">\
							</select>\
		    				<p>一页显示几行</p>\
					    </div>'
		}
		var div = this;
		var obj = $.extend({},defaultVal,options);
		//obj.last = isNaN(obj.last)?obj.last:Math.ceil(obj.total_count/obj.row);
		//obj.row = (obj.type==1&&obj.rowhomes.length>0) ? obj.rowhomes[0]:obj.row;
		
		var pagin = new Pagin(div,obj);
			pagin.paint(div,obj);
			callback(obj.now_page,obj.row);
			
			if(obj.type == 1)pagin.rowhome(div,obj,pagin,callback);

			div.on("click",".page",function(e){
				obj.now_page = $(this).attr("data-index");
				pagin = new Pagin(div,obj);
				pagin.paint(div,obj);
				callback(obj.now_page,obj.row);
			})
			
			div.on("click",".prev",function(e){
				obj.now_page = obj.now_page<=1?1:obj.now_page-1;
				pagin = new Pagin(div,obj);
				pagin.paint(div,obj);
				callback(obj.now_page,obj.row);
			})
			
			div.on("click",".next",function(e){
				var temp = Math.ceil(obj.total_count/obj.row);
				obj.now_page = obj.now_page>=temp?temp:parseInt(obj.now_page)+1;
				pagin = new Pagin(div,obj);
				pagin.paint(div,obj);
				callback(obj.now_page,obj.row);
			})
		return div;
	}
	var Pagin = function(div,obj){
		div.html("");
		var ul = obj.diyul;
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
				div.find(".next").before('<li ><span>...</span></li>');
				div.find(".next").before('<li data-index="'+all_page+'" class="page"><a href="javascript:void(0);">'+obj.last+'</a></li>');
			}
			if(obj.now_page >=obj.max_page && obj.now_page < parseInt(all_page)-parseInt(obj.max_page-2)){
				div.find(".next").before('<li data-index="1" class="page"><a href="javascript:void(0);">'+obj.first+'</a></li>');
				div.find(".next").before('<li ><span>...</span></li>');
				var i = parseInt(obj.now_page)-parseInt(obj.max_page/2);
				var j = i+obj.max_page;
				for(i;i<j;i++){
					div.find(".next").before('<li data-index="'+i+'" class="page"><a href="javascript:void(0);">'+i+'</a></li>');
				}
				div.find(".next").before('<li ><span>...</span></li>');
				div.find(".next").before('<li data-index="'+all_page+'" class="page"><a href="javascript:void(0);">'+obj.last+'</a></li>');
			}
			if(obj.now_page >= parseInt(all_page)-parseInt(obj.max_page-2)){
				div.find(".next").before('<li data-index="1" class="page"><a href="javascript:void(0);">'+obj.first+'</a></li>');
				div.find(".next").before('<li ><span>...</span></li>');
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
		},
		bind:function(pagin,div,obj,callback){
			
		},
		rowhome:function(div,obj,pagin,callback){
			div.after(obj.diyrowhome);
			$.each(obj.rowhomes,function(i,val){
				div.next("div").find("select").append('<option value="'+val+'">'+val+'</option>');
			})
			div.next("div").find("select").val(obj.row);
			div.next().on("change","select",function(e){
				obj.row = $(this).val();
				obj.now_page = 1;
				pagin = new Pagin(div,obj);
				pagin.paint(div,obj);
				callback(obj.now_page,obj.row);
			})
		}
	}
})(jQuery)