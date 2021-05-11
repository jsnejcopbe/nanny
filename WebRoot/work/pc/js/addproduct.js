var ADDNEWTAGHTML='<div class="tag-drop">\
						<input id="newTag" type="text" value="" placeholder="请输入标签名称">\
						<i class="fa fa-sort-down"></i>\
				   </div>\
				   <ul class="drop-item">${lihtml}</ul>';

var ADDNEWATTRHTML='<form class="addatrcon" id="pro4">\
						${item}\
						<div class="atr-group">\
							<div class="group-btn">\
								<a href="javascript:addNewLine()" class="btn btn-success btn-block">再加一行</a>\
							</div>\
							<div class="group-btn">\
								<a href="javascript:saveNewLine()" class="btn btn-info btn-block">保存设置</a>\
							</div>\
						</div>\
					</form>';

var ATTRITEMHTML='<div class="atr-group attr">\
						<label>属性名</label>\
							<input class="form-control" type="text"  name="buteName" placeholder="请输入属性名" value="${attrName}">\
						<label>描述</label>\
							<input class="form-control" type="text"  name="buteDes" placeholder="描述内容" value="${attrCon}">\
						<a class="btn btn-danger js-removeline" href="javascript:void(0)">删除该行</a>\
					</div>';
var ATTRHTML='<div class="atr-group attr">\
					<label>属性名</label>\
						<input class="form-control" type="text" value="" name="buteName" placeholder="请输入属性名">\
					<label>描述</label>\
						<input class="form-control" type="text" value="" name="buteDes" placeholder="描述内容">\
					<a class="btn btn-danger js-removeline" href="javascript:void(0)">删除该行</a>\
					</div>';

$(function(){
	UM.getEditor('myEditor');
	$(document).on("click",".js-removetag",function(){
		$(this).parents("span").remove();
	});
	$(document).on("click",".js-removeline",function(){
		var itemLength=$(".atr-group").length;
		if(itemLength==2)
			return;
		else
			$(this).parents(".atr-group").remove();
	});

var query = $("#selid");

//联动类别
var select_url = path+'admin/init_type.html';
query.find("[name='shoptype1']").select({url:select_url});
query.on("change","[name='shoptype1']",function(e){
	var type = $(this).val();
	query.find("[name='shoptype2']").select({url:select_url,data:type})
})

});

$(function(){
    $("#upfile").on("change","#file1",function(){
     layer.load(1, {shade: [0.8,'#333']});
     var oParam={
                "sURL":"/nanny/upload/images.json",
                "sID":"file1",
                "contentType":"",
                "fnSuccess":function(data){
                	layer.closeAll("loading");
                	$("#fi").val(data.path);
                	$(".fileinput-remove-button").click();
                },
                "fnError":function(){  
                	layer.closeAll("loading");
                	alert("上传失败");
                 }
             };
                 new g_fnFileUpload(oParam);
                    });
                   
     });
     
	//上传文件格式不对提示
	function PreviewImage(imgFile) {
		var preshow = document.getElementById("preshow");
		preshow.style.display = "none";

		var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
		if (!pattern.test(imgFile.value)) {
			alert("系统仅支持jpg/jpeg/png/gif/bmp格式的照片！");
			imgFile.focus();
		} else {
			var path;
			if (document.all)//IE
			{
				imgFile.select();
				path = document.selection.createRange().text;
				document.getElementById("imgPreview").innerHTML = "";
				document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
						+ path + "\")";//使用滤镜效果
			} else//FF
			{
				path = URL.createObjectURL(imgFile.files[0]);
				document.getElementById("imgPreview").innerHTML = "<img src='"+path+"' style='width:100%; height: 100%;' id='preshow' />";
			}
		}
	}

/**
 * 新增标签
 */
function addNewTag(){
	//ajax查询标签 组织<li></li>数据 例子
	var html="";
	for(var i=0;i<5;i++)
		html+="<li>标签"+i+"</li>";	
	showTagSelect(html);
}

/**
 * 展示标签添加选项框
 */
function showTagSelect(html){
	$(".js-addnewtag").css("display","none");
	$(".js-addnewtag").before(ADDNEWTAGHTML.replace("${lihtml}", html));
}


function showarrt() {
	
 if(data4 !=null){
	    //0.获得数据
		 var attr=data4;
		 var html="";
		 var count;//循环次数
		 
		 var attrNameList=data4.buteName;//属性名集合
		 var attrValueList=data4.buteDes;//属性值集合
		 //1.判断类型
		 if(attrNameList instanceof Array)
			 count=attrNameList.length;
		 else
			 count=1;
			
		 //2.平装html
		 for(var i=0;i<count;i++)
		 {
			 var name= count==1?attrNameList:attrNameList[i];
			 var value= count==1?attrValueList:attrValueList[i];
			 html+=ATTRITEMHTML.replace("${attrName}", name)
			   				   .replace("${attrCon}", value);
		 }
		 
		 //3.输出html
		 
//		 
//		 
//		 if(lh == 1){
//			 $.each(attr,function(i,val){
//				 attr[i] = [val+""];
//					//form[i] = ["+val+"];
//			 })
//		 } 
//		 
//		
//		 for(var i=0;i<lh;i++){
//			 html+=ATTRITEMHTML.replace("${attrName}", attr.buteName[i])
//			 				   .replace("${attrCon}", attr.buteDes[i]);
//		 }
//		
		 showAttrAdd( ADDNEWATTRHTML.replace("${item}", html));
		 
	 }else{
		 showAttrAdd( ADDNEWATTRHTML.replace("${item}", ATTRHTML));
	 }
}

/**
 * 展示属性添加
 * @param userID
 */
function showAttrAdd(gg)
{
	layer.open({
	    type: 1,
	    title :"添加商品属性",
	    skin: 'layui-layer-rim', //加上边框
	    area: ['600px','400px'], //宽高
	    content: gg
	});
}

/**
 * 新增一行
 */
function addNewLine(){
	var html='<div class="atr-group attr">\
				<label>属性名</label>\
				<input class="form-control" type="text" value="" name="buteName" placeholder="请输入属性名">\
				<label>描述</label>\
				<input class="form-control" type="text" value="" name="buteDes" placeholder="描述内容">\
				<a class="btn btn-danger js-removeline" href="javascript:void(0)">删除该行</a>\
			  </div>';
	$(".addatrcon .atr-group:last").before(html);
}
var data4;
var lh;
function saveNewLine() {
	
	  var lh=$("#pro4 .attr").length;
	
	if(lh==1&& $("#pro4 .attr input[name='buteName']").val()=="" && $("#pro4 .attr input[name='buteDes']").val()=="")
		layer.closeAll();
	else{
		data4=$("#pro4").serializeJson();
	 	layer.msg("保存成功");
	 	setTimeout("layer.closeAll()",1000);
	}
}

/**
 *提交数据
 */
function addPro(){
	
	
	var data1=$("#pro1").serialize();
	var data2=$("#pro2").serialize();
	var data3=$("#pro3").serialize();
	
	var data=data1+'&'+data2+'&'+data3;
	
	
	$.ajax({
		type:"post",
		url:path+'admin/add_product.html',
		data:"data4="+JSON.stringify(data4)+"&"+data,
		/*data:{data4:JSON.stringify(data4),data:data},*/
		dataType: "json",
		 success: function(data){
			 if(data.msg!="success"){
        		 layer.msg(data.msg);
        	 }else{
        		 var r=confirm("成功提交,是否继续添加？");
        		  if (r==true)
        		    {
        			  layer.msg("成功提交,可以继续操作");
        			  $(".anchor li").removeClass("active");
        			  $(".anchor li:first").addClass("active");
        			  $("#step-3").removeClass("active in");
        			  $("#step-1").addClass("active in");
        		    }
        		  else
        		    {
        			  window.location.href=path+"admin/productlist.html";
        		    }
        		  /*layer.msg("成功提交");
        		 location.reload(true);*/
        		 
        	 }
        },
         error:function(){
        	 layer.msg("提交失败");
         	}
		 })
	/*.done(function(msg){
		alert("ok");
	})*/
//	$.ajax({
//             type: "POST",
//             url: url,
//             data: data,
//             dataType: "json",
//             success: function(data){
//            	 if(data.msg!="success"){
//            		 layer.msg(data.msg);
//            	 }else{
//            		 layer.msg("成功提交");
//            		 /*location.reload(true);*/
//            	 }
//             },
//             error:function(){
//            	 layer.msg("提交失败");
//             	}
//         });
	
}
function clear(){
	layer.confirm('确认要清空吗？',function(index){
	document.getElementById("pro1").reset();
	
	$("input[name='fi']").val(null);
	document.getElementById("preshow").src =null;
	UM.getEditor('myEditor').setContent("");
	
	document.getElementById("pro3").reset();
	
	});
	/*location.reload(true);*/
}


//输入框限定
function clearNoNum(obj)
{
 //先把非数字的都替换掉，除了数字和.
 obj.value = obj.value.replace(/[^\d.]/g,"");
 //必须保证第一个为数字而不是.
 obj.value = obj.value.replace(/^\./g,"");
 //保证只有出现一个.而没有多个.
 obj.value = obj.value.replace(/\.{2,}/g,".");
 //保证.只出现一次，而不能出现两次以上
 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}