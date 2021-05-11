
 	function addNewLine(){
	var html= ' <div  class="form-horizontal" id="innerdiv">\
					金额设定:<input type="text"  class="myInputcss" id="cash11" name="cash11" style="width: 100px;">\
				返现积分:<input type="text"   class="myInputcss" id="integral11" name="integral11" style="width: 100px" >\
				<input type="hidden" name="number" id="number" >\
				<button type="button" id="delt" class="btn btn-default" style="background-color: #C9302C;color: #FFFFFF" >删   除\
					</div>';

 		$(".modal-body").find("#middiv").append(html);

}
 	
 	
	function upsys(){
	var data = $("#sform").serialize();
	if(!data.replace(/[^0-9.]/g,'')){
		layer.msg("请输入数字");
		return;
	}
	else{
		$.ajax({
		 type: "POST",
		 url: "${pageContext.request.contextPath}/admin/upcash.html",
		 data: data,
		 dataType: "json",
		 success: function(data){
				 layer.msg(data.msg);
				 location.reload("true",1000);
				
				 },
		 error: function(){layer.msg("操作失败");}
		});
		}
	}
	
function upForm(){
	

	//var data =$("#setform").serialize();
	var arr=new Array();
	($("#middiv").find(".form-horizontal")).each(function(){
		var obj=new Object();
		obj.cash11=$(this).find("#cash11").val();
		obj.integral11=$(this).find("#integral11").val();
		arr.push(obj);
	});
		$.ajax({
		 type: "POST",
		 url: "${pageContext.request.contextPath}/admin/setform.html",
		 data: "arr="+JSON.stringify(arr),
		 dataType: "json",
		 success: function(data){
				 layer.msg(data.msg);
				 location.reload("true",1000);
				
				 },
		 error: function(){layer.msg("操作失败");}
		});
}	

function delSet(id){
	
	
	$(".modal-body_"+id).remove();
 	
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/admin/delSet.json",
		data : "id="+id,
		dataType : "text",
	
		error : function() {
			setTimeout("location.reload(true)", 3000);
			layer.msg(data.msg);
		}
	});
	
}

function updpass() {
	$("#myPass").modal("show");
}

function updpass1() {
	$("#myPass1").modal("show");
}


$(function(){
	
	$(document).on("click",".btn",function(){
		$(this).parents(".form-horizontal").remove();
	
	});
	});

