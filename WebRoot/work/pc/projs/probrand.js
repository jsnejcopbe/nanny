var brand;
$(function(){
        $("#upfile").on("change","#file1",function(){
             
         var oParam={
                    "sURL":"/nanny/upload/images.json",
                    "sID":"file1",
                    "contentType":"",
                    "fnSuccess":function(data){
                    $("#fi").val(data.path);
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
					document.getElementById("imgPreview").innerHTML = "<img src='"+path+"' width='100' height='80'/>";
				}
			}
		}
		
function editbr(id) {
		if(id!=null){
		var name= document.getElementById("name_"+id).textContent;
		var img= document.getElementById("img_"+id).src;
		
		 $("input[name='id']").val(id); 
		 brand=id;
		 $("input[name='bname']").val(name); 
		 $("input[name='fi']").val(img);
		 document.getElementById("preshow").src =img ;	
		 	}else{
		 		brand=null;
		 		$("input[name='id']").val(null); 
		 		$("input[name='bname']").val(null); 
				 $("input[name='fi']").val("/nanny/images/defalut.jpg");
				  document.getElementById("preshow").src ="/nanny/work/pc/images/defalut.jpg" ;	
		 	}
		 $("#brandEdit").modal();
		
}

function upd() {
	var data=$("#editb").serialize();
	if(brand==null){
		var url="addbrand.html";
	}else{
		var url="updbrand.html";
	}
		$.ajax({
             type: "POST",
             url: url,
             data: data,
             dataType: "json",
             success: function(data){
            	 if(data.msg!="success"){
            		 layer.msg(data.msg);
            		 
            	 }else{
            		 layer.msg("成功提交");
            		 location.reload(true);
            	 }
             		
         
             }
         });
	}
		