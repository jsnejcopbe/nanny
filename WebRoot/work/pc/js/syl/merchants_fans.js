$(function(){
	 init();
});

function init(){
	
	var load = function(){
		jQuery.getScript(path+"js/syl/verify.js");
		jQuery.getScript(path+"js/syl/basis.js");
		jQuery.getScript(path+"js/syl/jquery.form.js");
		jQuery.getScript(path+"js/syl/jquery.mypagin.js");
		jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){
			
			$("#fans_table").mytable({
				url:path+"shop/fans/init.html",
				querydiv:$("#fans_query"),
				diys:{headImg:'<img class="headImg" src="">'}
			});
			
			var type = "";
			$("#plot_select").select({url:path+"shop/fans/getArea.html",data:type});
		});
	};
	
	var bind = function(){
		/*$("body").on("click","#search_btn",function(){
			$("#fans_query form").submit();
		})
		
		$("body").on("click","#plot_select",function(){
			
		})*/
	};
	
	new load();
	new bind();
}