;(function($){
	$.extend({
		brand:function(options,callback){
			var defVal = {
				open:null,
				size:5,
				url:'',
				width:"20%"
			}
			
			var obj = $.extend({},defVal,options);
			var size = obj.size;
			
			var modal_div = '<div class="modal fade" id="brand_modal">\
								<div class="modal-dialog">\
									<div class="modal-content">\
										<div class="modal-header" style="height: 50px;">\
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">\
												<span aria-hidden="true">&times;</span>\
											</button>\
											<h4 class="modal-title">商品品牌</h4>\
										</div>\
										<div class="modal-body" style="height: auto; min-height: 340px;">\
											<ul class="list-inline gallery-items" id="Grid">\
											</ul>\
										</div>\
										<div class="modal-footer" style="height: 50px;">\
											<a class="btn btn-info btn-block" href="javascript:;" id="load_more">加载更多</a>\
									    </div>\
									</div>\
								</div>\
							 </div>';
			$("#brand_modal").remove();
			$("body").prepend(modal_div);
			
			var brand = $("#brand_modal");
			var brands = "";
			
			if(!open)return;
			$("body").on("click",obj.open,function(){
				$("#brand_modal").modal().css({
					'overflow-y':"hidden"
				});
			})
			
			//初始化加载所有品牌
			$.ajax({
				type:'get',
				url: obj.url,
				dataType:'json'
			}).done(function(json){
				var bs = brand.find("#Grid");
				bs.attr("data-id",1);
				var data = json_spilt(json,1,size);
				brand_add_html(bs,data);
				brands = json;
			}) 
			
			$("#brand_modal").on("click","#load_more",function(){
				var bs = brand.find("#Grid");
					var i = parseInt(bs.attr("data-id"));
					var temp = Math.ceil(brands.length/size);
					if(i >= temp){
						layer.msg("已无更多");
						return;
					}
					i = i + 1;
					bs.attr("data-id",i);
					var data = json_spilt(brands,i,size);
					brand_add_html(bs,data);
			})
			
			$("#brand_modal").on("click","#Grid li",function(){
				var id = $(this).attr("data-id");
				var name = $(this).find("h2").html();
				callback(id,name);
				$("#brand_modal .close").click();
			})
			
			//添加品牌内容
			function brand_add_html(bs,data){
				$.each(data.rows,function(i,val){
					var li = '<li class="mix dogs  mix_all brand_li" style="display: inline-block; opacity: 1;" data-id="'+val.id+'" data-name="puffy">\
										<div class="panel panel-cascade panel-gallery ">\
											<div class="panel-heading">\
												<h2 class="panel-title">'+val.name+'</h2>\
											</div>\
											<div class="panel-body nopadding">\
												<img src="'+val.icon+'" alt="">\
											</div>\
										</div>\
							  </li>'
					bs.prepend(li);
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
		}
	})
})(jQuery)