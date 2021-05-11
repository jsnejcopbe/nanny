
$(function(){
	

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
                "sURL":path+"upload/images.json",
                "sID":"file1",
                "contentType":"",
                "fnSuccess":function(data){
                	layer.closeAll("loading");
                	$("#fi").val(data.path);
                	document.getElementById("preshow").src =data.path;
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
 *提交数据
 */
function addProapp(type){
	
	var a=$("select[name='shoptype1']").val();
	var b=$("select[name='shoptype2']").val();
	var c =$("input[name='fi']").val();
	var data2=$("#pro2").serialize();

	var data=data2;
if(a!=null&&a!=-1&&c!="/nanny/images/defalut.jpg"){
	 layer.load(1, {shade: [0.8,'#333']});
	 if(type==0){
	 	url=path+'shop/addpcApply.html';
	 }else{
		 url=path+'supplier/addpcApply.html';
	 }
	$.ajax({
		type:"post",
		url:url,
		data:data,
		dataType: "json",
		 success: function(data){
			 if(data.msg!="success"){
        		 layer.msg(data.msg);
        		 layer.closeAll("loading");
        	 }else{
        		 layer.msg("成功提交");
        		 $("input[name='fi']").val(null);
        		document.getElementById("preshow").src =null;
        		layer.msg("成功提交");
        		layer.closeAll("loading");
        		  /*layer.msg("成功提交");
        		 location.reload(true);*/
        		 
        	 }
        },
         error:function(){
        	 layer.msg("提交失败");
         	}
		 })
	}else{
		layer.msg("您没选择类别 或 没上传图片");
		}	

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