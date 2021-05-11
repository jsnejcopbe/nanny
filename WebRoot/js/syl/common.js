;(function($){
	/** 
	 * 常用普通再封住类
	 *   
	 * @param $
	 */
	$.extend({
		/**
		 * json　分割　（分页）
		 * 返回 {rows:[]};
		 */
		jsonSplit:function(json,now_page,row){
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
		},
		/**
		 * 强行post ajax
		 * type 默认json 传值为text
		 */
		myajax:function(option,type){
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
				if(msg)layer.msg("网络异常");
			})
		}
	})
})(jQuery)