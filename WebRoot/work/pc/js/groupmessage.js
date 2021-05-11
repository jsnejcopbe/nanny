$(document).ready(function(){
		 layer.config({
	         extend: "extend/layer.ext.js"
	     });
	});
	//弹出text 文本框 
	function qunfaXX(){
		
		layer.prompt({title:"输入群发的消息<span style='color:red'> *注:用户每月最多接收4条</span>",formType:2},function(text){
			var tx=text;
			var array=new Array();
			
			$("input[id='chee']").each(function () {
//				var elem=$("input[name='originID']");
				if($(this).prop("checked")){
					//alert($(this).val());
					array.push($(this).val());
				}
			});
//			alert(JSON.stringify(array));
			if(array.length!=0){
			$.ajax({
				type:"POST",
				url:"/nanny/shop/sentMessage.json",
				data:"array="+JSON.stringify(array)+"&text="+tx,
//				dataType:"text",
				success:function(adata){
					var data=eval("("+adata+")");
					if(data.errcode==0){
						layer.msg('发送成功！');
					}else{
						layer.msg('发送失败！');
					}
				},
				error:function(){
					  layer.alert("error, 发送失败 ");
					}
				});
			}else{
				layer.msg("请选择群发用户");
			}
		    
		});
	}
	
	//全选
	/*  $("table thead th input:checkbox").on("click" , function(){
		 if($("")){
			 
		 }
		$(this).closest("table").find("tr > td:first-child input:checkbox").prop("checked",$("table thead th input:checkbox").prop("checked"));
    });  */
	
	//全选
// 	$("table thead th input:checkbox").on("click" , function(){
	function chooses(){
	    if($("#all1").prop("checked")){
	    	$("#fans_table input:checkbox").each(function(){
	    		if(!$(this).prop("checked") && !$(this).prop("disabled"))
	    			$(this).prop("checked",true);
	    		else
	    			$(this).prop("checked",false);
	    		
	    	});
	    	
//	    var objs = window.document.getElementsByTagName("input");
//   	   		for(var i=0;i<objs.length;i++){
//    	     if (objs[i].type == "checkbox" && objs[i].disabled==false){
//    	      objs[i].checked = true;      
//    	     }
//    	   }
	    	
	    }
	    else{
	    	$("#fans_table input:checkbox").each(function(){
	    		if($(this).prop("checked") && !$(this).prop("disabled"))
	    			$(this).prop("checked",false);
	    		else
	    			$(this).prop("checked",true);
	    	});
	    }
//	    var objs = window.document.getElementsByTagName("input");
//	    	for(var i=0;i<objs.length;i++){
//	    	     if (objs[i].type == "checkbox" && objs[i].disabled==false){
//	    	      objs[i].checked = false;      
//	    	     }
//	    	   }
//	  	  }
	}
// 	);
	   
	//分页
	new Pagin({
	    size : $("#pageSize").val(),
	    perPage : 5, 
	    total  : $("#pageCount").val(),
	    nowPage: $("#pageIndex").val(),
	    dealFun:function(size,page){
		//有Form表单时候使用这个顶替action跳转方向,
		$("#pform").attr("action","groupmessage.html?pageIndex="+page+"&pageSize="+size);
		$("#pform").submit();
		
// 	     location.href="groupmessage.html?pageIndex="+page+"&pageSize="+size;	//正常页面div
	}
});
    
	