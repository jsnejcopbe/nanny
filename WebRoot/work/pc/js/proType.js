var NEWCLASSHTML='<div class="price-box mix temp" id="">\
					 <div class="price-header bg-info">\
						<h3 style="padding:18px 5px 0px 5px">\
							<input id="className" class="form-control" type="text" value="" placeholder="请输入大类名称">\
						</h3>\
					 </div>\
					 <ul class="list-group features">\
						<li class="list-group-item js-noneclass">尚无小类</li>\
						<li class="list-group-item select">\
							<a class="btn btn-block bg-info text-white btn-lg js-addsecclass">新增小类 <i class="fa fa-plus"></i></a>\
						</li>\
					 </ul>\
				  </div>';

var NEWSECCLASSHTML='<li class="list-group-item items" id="">\
						<input id="className" type="text" style="width: 100%; border: none;" placeholder="请输入小类名称">\
					 </li>';


$(function(){
	$(document).on("click",".js-editclass",function(){
		var parent=$(this).parents("h3");
		var tmp=parent.html();
		parent.html('<input id="className" class="form-control" type="text" value="" placeholder="请输入大类名称">');//插入文本框
		parent.attr("style","padding:18px 5px 0px 5px");
		$("#className").focus();
		$("#className").one("blur",function(){delFunction(tmp);});
	});
	
	$(document).on("click",".js-addsecclass",function(){
		var par=$(this).parents(".select");
		par.before(NEWSECCLASSHTML);
		$("#className").focus();
		$("#className").one("blur",function(){delClassFunction();});
	});
	
	$(document).on("click",".js-editsecclass",function(){
		var parent=$(this).parent(".list-group-item");
		var tmp=parent.html();
		parent.html('<input id="className" class="form-control" type="text" value="" placeholder="请输入小类名称">');//插入文本框
		$("#className").focus();
		$("#className").one("blur",function(){delClassFunction(tmp);});
		
	});
	/*var parent=$(this).parent(".temp");
	alert($('ul .features').children().length);
	if($('ul.features').children().length<2){
		parent.html('<li class="list-group-item js-noneclass">尚无小类</li>');//插入  $(".temp ul>li")	
		
		document.getElementById("noo").style.display="block";
		}*/
	
	typ();
});

/*$(function() {
	var parent=$(this).parent(".features");
	if($(".features ul>li").length==1){
		parent.html('<li class="list-group-item js-noneclass">尚无小类</li>');//插入
		}
});*/


/**
 * 新增商品大类
 */
function addNewClass(){
//	$(".price-list .price-box:first").before(NEWCLASSHTML);
	$(".price-list").append(NEWCLASSHTML);
	$("#className").focus();
	$("#className").one("blur",function(){
		delFunction();
	});
}


/**
 * 新增大类处理函数
 */
function delFunction(html){

	var pid=$("#className").parents(".temp").attr("id");
	var className=$("#className").val();
	if(className!="")//有内容
	{
		$("#className").parents("h3").html(className+' <a class="text-white js-editclass" href="javascript:void()"><i class="fa fa-pencil-square-o"></i></a>');
		$(".price-box h3>input").remove();
		$(".price-box h3").removeAttr("style");
			
		
	var data="id="+pid+"&"+"name="+className;
		if(pid!=null&&pid!=""&&pid!="null"){
			var url="updtype.html";
		}else{
			var url="addtype.html";
		}
		
		$.ajax({
            type: "POST",
            url: url,
            data: data,
            dataType: "json",
            success: function(data){
            		 layer.msg("成功更改");
					location.reload(true);
            }
        });
	}else{//无内容
		if(html==null)
			$("#className").parents(".price-box").remove();
		else{
			$("#className").parents("h3").html(html);
			$(".price-box h3>input").remove();
			$(".price-box h3").removeAttr("style");
		}
	}
}
/**
 * 新增小类处理函数
 */
function delClassFunction(html){
	
	var id=$("#className").parents(".items").attr("id");
	var pid=$("#className").parents(".temp").attr("id");
	
	
	var className=$("#className").val();
	if(className!=""){
		$("#className").parents(".js-noneclass").remove();
//		$(".js-noneclass").remove();
		$("#className").parent(".list-group-item").html(className+' <a class="js-editsecclass" href="javascript:void(0)">编辑</a>');
		$("#className").remove();
		
		showtype(id,pid,className);
		
		
	
		
	}else{
		if(html==null)
			$("#className").parent(".list-group-item").remove();
		else{
			$("#className").parent(".list-group-item").html(html);
			$("#className").remove();
		}
	}

}


function showtype(id,pid,className) {
	if(id!=null&&id!=""&&id!="null"){
		var url="updtype.html";
		var data="id="+id+"&"+"name="+className;
		
	}else{
		var url="addtype.html";
		var data="pid="+pid+"&"+"name="+className;
	}
	
	$.ajax({
        type: "POST",
        url: url,
        data: data,
        async:false,
        dataType: "json",
        success: function(data){
        		 layer.msg("成功更改");
        	
				location.reload(true);
        }
    });
}

//循环ul 取得li个数 
function typ(){ 
	$.each($(".features"),function(){
		var size= $(this).find(".items").length;
		
		if(size<1){
			$(this).prepend('<li class="list-group-item js-noneclass">尚无小类</li>');
		}else if(size>5){
			for(var i=4;i<size;i++){
				//$(this).getElementsByTagName("li")[i].style.display = "none";
				//$(this).find("li")[i].hide();
				$(this).find(".items :eq("+i+")").hide();
				
			}
			$(this).find(".select").before('<li class="list-group-item show_btn"><a class="btn-block bg-success text-white" href="javascript:showitems('+$(this).attr("data-classid")+')">展示剩余...</a></li>');
			
		}
		
	});
	
}
//小类展示
function showitems(ID) {
	$.each($(".features"),function(){
		var size= $(this).find(".items").length;
		
		if(size>5 && $(this).attr("data-classid")==ID){
			
				$(this).find(".items").show();
				
			
			$(this).find(".show_btn a").prop("href","javascript:hideitems("+ID+")").html("收起");
			
		}
		
	});
	
}
//小类隐藏
function hideitems(ID) {
	$.each($(".features"),function(){
		 var size= $(this).find(".items").length;
		 if(size>5 && $(this).attr("data-classid")==ID){
			$(this).find(".show_btn").remove();
			for(var i=4;i<size;i++){
				//$(this).getElementsByTagName("li")[i].style.display = "none";
				//$(this).find("li")[i].hide();
				$(this).find(".items :eq("+i+")").hide();
				
			}
			$(this).find(".select").before('<li class="list-group-item show_btn"><a class="btn-block bg-success text-white" href="javascript:showitems('+ID+')">展示剩余...</a></li>');
			
		}
		
	});
}
	