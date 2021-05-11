$(function () {
	layer.config({
	    extend: 'extend/layer.ext.js'
	});
	
    select1();
    $('#province').bind("change", select2);
    $('#city').bind("change", select3);
    
    
	$("body").on("change","#head_file",function(){
	     layer.load(1, {shade: [0.8,'#333']});
	     var oParam={
	                "sURL":path+"/upload/images.json",
	                "sID":"head_file",
	                "contentType":"",
	                "fnSuccess":function(data){
	                	layer.closeAll("loading");
	                	$("#simg").val(data.path);
	                	//document.getElementById("fimg").src =data.path;
	                	$("#shop_img").prop("src",data.path);
	                	
	                
	                },
	                "fnError":function(){  
	                	layer.closeAll("loading");
	                	alert("上传失败");
	                 }
	             };
	                 new g_fnFileUpload(oParam);
	                    });
});

function select1() {
    $.ajax(
    {
        type: "post",
        url:  path+"supplier/getProvince.html",
        dataType : "json",
        success: function (data) {
        	  
        	var da=data.msg;
        
            for (var i = 0; i < da.length; i++) {
                $("#province").append("<option value=" + da[i].s_value + ">" + da[i].s_text + "</option>");
            }
            select2();
        }
    })
};

function select2() {
    $("#city").html("");
    $.ajax(
    {
        type: "post",
        dataType : "json",
        url: path+"supplier/getCity.html",
        data: {"pid":$('#province').val() },
        success: function (data) {
        	
        	var da=data.msg;
        	
            for (var i = 0; i < da.length; i++) {
                $("#city").append("<option value=" + da[i].s_value + ">" + da[i].s_text + "</option>");
            }
            select3();
        }
    })
};

function select3() {
    $("#area").html("");
    $.ajax(
    {
        type: "post",
        dataType : "json",
        url: path+"supplier/getArea.html",
        data: {"cityid":$('#city').val() },
        success: function (data) {
        	var da=data.msg;
        	
            for (var i = 0; i < da.length; i++) {
                $("#area").append("<option value=" + da[i].s_value + ">" + da[i].s_text + "</option>");
            }
        }
    })
};





function addglo() {
	/*layer.prompt({title: '配送备注', formType: 2}, function(text){*/
		var province=$('#province').val();
		var city= $('#city').val();
		var area= $("#area").val();
				$.ajax({
					type : "post",
					url : path+"supplier/addglobal.html",
					data : "province="+province+"&city="+city+"&area="+area,
					dataType : "text",
					success : function(msg) {
						var data = eval('(' + msg + ')');
							layer.closeAll("loading");
							layer.msg("添加成功");	
							location.reload(true);
					},
					error : function() {
						layer.msg('操作有误!');
					}
				});
	
}

function delglo(id){
	layer.load(1, {shade : [ 0.8, '#333' ]});
	$.ajax({
		type : "post",
		url : path+"supplier/delglobal.html",
		data : "gid="+id,
		dataType : "text",
		success : function(msg) {
			var data = eval('(' + msg + ')');
				layer.closeAll("loading");
				layer.msg("删除成功");
				location.reload(true);
		},
		error : function() {
			layer.closeAll("loading");
			layer.msg('操作有误!');
		}
	});
}


function updpass() {
	layer.load(1, {shade : [ 0.8, '#333' ]});
	var data = $("#editpass").serialize();
	$.ajax({
		type : "post",
		url : path+"supplier/editinfo.html",
		data : data,
		dataType : "text",
		success : function(msg) {
			var data = eval('(' + msg + ')');
			
				layer.closeAll("loading");
				
				layer.msg("修改成功");
				//$("#editpass").modal("hide");
				//document.getElementById("editpass").reset();
				location.reload(true);
			//setTimeout(location.reload(true), 3000);
		},
		error : function() {
			layer.msg('操作有误!');
		}
	});
}