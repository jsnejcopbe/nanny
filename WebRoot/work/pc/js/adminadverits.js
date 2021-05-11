
$(function(){
        $("body").on("change","#upfile",function(){
             
         var oParam={
                    "sURL":"/nanny/upload/images.json",
                    "sID":"upfile",
                    "contentType":"",
                    "fnSuccess":function(data){
                    $("#fi").val(data.path);
                    document.getElementById("preshow").src = data.path;	
                     alert("上传成功"); 
                    $(".fileinput-remove-button").click();
                    },
                    "fnError":function(){                   
                     alert("上传失败");
                     }
                 };
                     new g_fnFileUpload(oParam);
                        });
                       
         });

$("body").on("click","#bank_sta a",function(){
	var id = $(this).attr("data-id");
	$("#hide_type").val(id);
	$("#bank_name").find("form").submit();
	})


function editbank(id,isuser) {
		if(id!=null){
		var name= document.getElementById("name_"+id).textContent;
		var img= document.getElementById("img_"+id).src;
		
 		$("input[name='bid']").val(id);
 		 $("input[name='bmname']").val(name); 
 		 $("input[name='fi']").val(img);
 		 document.getElementById("preshow").src =img ;
 		 $("#bt").val(isuser);
 		 	}else{
 		 		brand=null;
 		 		$("input[name='bid']").val(null); 
		 		$("input[name='bmname']").val(null); 
				 $("input[name='fi']").val("/nanny/images/defalut.jpg");
				  document.getElementById("preshow").src ="/nanny/work/pc/images/defalut.jpg" ;	
 		 	}
 		 $("#myModal").modal();
		
 }

	
	
	
	

//添加新广告
function updbanks() {
	var data=$("#editba").serialize();
	var jumpSrc=$("#jumpSrc").val();
	if(jumpSrc==null){
		layer.msg("跳转地址不能为空");
	}
	else{
		var url="/nanny/admin/adveritsinsert.json";
		$.ajax({
             type: "POST",
             url: url,
             data: data,
             dataType: "json",
             success: function(data){
            		 layer.msg("成功提交");
            		 parent.location.reload();
            }
         });
	}
}
//停用
function Disable(e){
	var Disid=e;
	$.ajax({
		 type:"POST",
		 url:BASEPATH+"/admin/operation.json",
		 data:"Disid="+Disid,
		 success:function(msg){
			 var data=eval('('+msg+')');
			 layer.msg(data.msg);
			 location.reload();
		 }
	
	})
	
}

//启用
function Enable(e){
	var Enid=e;
	$.ajax({
		 type:"POST",
		 url:BASEPATH+"/admin/operation.json",
		 data:"Enid="+Enid,
		 success:function(msg){
			 var data=eval('('+msg+')');
			 layer.msg(data.msg);
			 location.reload();
		 }
	
	})
	
}

//删除
function Delete(e){
	
	var Delid=e;
	$.ajax({
		 type:"POST",
		 url:BASEPATH+"/admin/operation.json",
		 data:"Delid="+Delid,
		 success:function(msg){
			 var data=eval('('+msg+')');
			 layer.msg(data.msg);
			 location.reload();
		 }
	
	})
	}


//保存编辑
function preservation(){
	var data=$("#pro1").serialize();
	if(jumpSrc==null){
		layer.msg("跳转地址不能为空");
	}
	else{
		
	$.ajax({
		type:"post",
		url:BASEPATH+"/admin/editpreservation.json", 
		data:data,
		success:function(msg){
			var dataa=eval('('+msg+')');
			layer.msg(dataa.msg);
		}

	})
}
}
//全局点击
function yan(){
	$("#bank1").hide();
};
function xian(){
	$("#bank1").show();
};

$("#qj").click(function(){
	$(".isShow").hide();
});
$("#qj2").click(function(){
	$(".isShow").show();
});

//$("#siy").on("input propertychange",function(){siyong();})
//
//function siyong(){
//	var siy=$("#siy").val();
//	var url=ASEPATH+"/admin/merchant.json";
//	$.ajax({
//		type:"post",
//		url:url,
//		data:"siy="+siy,
//		success:function(msg){
//			var data2=eval('('+msg+')');
//			var data=data3.msg;
//			var div="<select class="form-control" id="bank" name="bank">"+
//			" <c:forEach items="+data+" var="d">"
//			+"<option value="+d.id+">"+d.shop_name+"</option>"
//			+"</c:forEach>"
//			+"</select>";
//			$("#bank").html(div);
//			$("#bank").css("display","block");
//		}
//	error:function(){
//		layer.msg("未找到相关店名");
//	}
//	})
//}




new g_fnImgCheck();