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
var ATTRITEMHTML='<div class="atr-group">\
						<label>属性名</label>\
						<input class="form-control" type="text"  name="buteName" placeholder="请输入属性名" value="${attrName}">\
						<label>描述</label>\
						<input class="form-control" type="text"  name="buteDes" placeholder="描述内容" value="${attrCon}">\
						<a class="btn btn-danger js-removeline" href="javascript:void(0)">删除该行</a>\
				  </div>';

/**
 * 页面初始化
 */
$(function(){
	
	$(document).on("click",".js-removetag",function(){
		$(this).parents("span").remove();
	});
	$(document).on("click",".js-removeline",function(){
		$(this).parents(".atr-group").remove();
	});
	
	$("#upfile").on("change","#file1",function(){
		layer.load(1, {shade: [0.8,'#333']});
	     var oParam={
	                "sURL":path+"upload/images.json",
	                "sID":"file1",
	                "contentType":"",
	                "fnSuccess":function(data){
	                	layer.closeAll("loading");
	                	$("#fi").val(data.path);
	                	alert("上传成功"); 
	                	$(".fileinput-remove-button").click();
	                },
	                "fnError":function(){ 
	                	layer.closeAll("loading");
	                	alert("上传失败");
	                 }
	             };
	                 new g_fnFileUpload(oParam);
	        });
	down();

});
function down() {
	
	
	var query = $("#selid");
	
//联动类别
	var select_url = path+'admin/init_type.html';
	
	query.find("[name='shoptype1']").select({url:select_url});
	
	
	query.on("change","[name='shoptype1']",function(e){
		var type = $(this).val();
		query.find("[name='shoptype2']").select({url:select_url,data:type});
		
	});
	
	query.find("[name='shoptype1']").val(parid);
	query.find("[name='shoptype1']").change();
	query.find("[name='shoptype2']").val(s_value);
}

     
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



function createHtml() {

	var data=document.getElementById("code").value;
	 if(data4!=null){
		 
		 var attr=data4;
		 var html="";
		 for(var i=0;i<attr.buteDes.length;i++){
			 html+=ATTRITEMHTML.replace("${attrName}", attr.buteName[i])
			 				   .replace("${attrCon}", attr.buteDes[i]);
		 }
		
		 showAttrAdd( ADDNEWATTRHTML.replace("${item}", html));
		 
	 }else{

	$.ajax({
		type:"post",
		url:path+'admin/show_attribute.json',
		data:"code="+data,
		dataType: "json",
		 success: function(data){
			 var attr=data.msg;
			 var html="";
			 for(var i=0;i<attr.length;i++){
				 html+=ATTRITEMHTML.replace("${attrName}", attr[i].attrName)
				 				   .replace("${attrCon}", attr[i].attrDes);
			 }
			
			 showAttrAdd( ADDNEWATTRHTML.replace("${item}", html));
			 
        },
         error:function(){
        	 layer.msg("提交失败");
         	}
		 });
	 }
}

/**
 * 展示属性编辑
 * @param userID
 */
function showAttrAdd(ss)
{
	
		 layer.open({
			    type: 1,
			    title :"编辑商品属性",
			    skin: 'layui-layer-rim', //加上边框
			    area: ['600px','400px'], //宽高
			    content: ss
			});
}

/**
 * 新增一行
 */
function addNewLine(){
	var html='<div class="atr-group">\
				<label>属性名</label>\
				<input class="form-control" type="text" value="" name="buteName" placeholder="请输入属性名">\
				<label>描述</label>\
				<input class="form-control" type="text" value="" name="buteDes" placeholder="描述内容">\
				<a class="btn btn-danger js-removeline" href="javascript:void(0)">删除该行</a>\
			  </div>';
	$(".addatrcon .atr-group:last").before(html);
}
var data4;
function saveNewLine() {
	 data4=$("#pro4").serializeJson();
	 layer.msg("保存成功");
	 setTimeout(function(){
		 layer.closeAll();
	 }, 1000);
	 
	
}

/**
 *提交数据
 */
function updPro(id){
	
	var data1=$("#pro1").serialize();
	var data2=$("#pro2").serialize();
	var data3=$("#pro3").serialize();
	
	
//	var data=data1+'&'+data2+'&'+data3+'&prodes='+UM.getEditor('myEditor').getAllHtml();
	var data=data1+'&'+data2+'&'+data3;
	
	 
	$.ajax({
		type:"post",
		url:path+'admin/upd_product.html',
		data:"data4="+JSON.stringify(data4)+"&"+data+"&id="+id,
		/*data:{data4:JSON.stringify(data4),data:data},*/
		dataType: "json",
		 success: function(data){
			 if(data.msg!="success"){
        		 layer.msg(data.msg);
        	 }else{
        		 if(shopid!='0'){
        			 window.location.href=path+"shop/productList.html?cs=true";
        		 }else{
        			 
        			 window.location.href=path+"admin/productlist.html?cs=true";
        		 }
       		    }
       		/*layer.msg("成功提交");
        		 location.reload(true);*/
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
