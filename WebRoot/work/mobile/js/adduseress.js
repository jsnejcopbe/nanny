var ATTRITEMHTML='<li class="list-group-item" id="${alid}">\
						<a class="addname" href="javascript:save(${aid})">\
							<span class="sp1">${addName}</span>\
							<span class="sp sp2">${detAdd}</span>\
							<input type="hidden"  id="city" name="city" value="${city}">\
						</a>\
					</li>';  




$(function(){
	$.ajax({
        type: "get",
        url: UR+"/sessAreaID.html",
        dataType: "json",
        success: function (date) {
        	
        	//var data=date.msg;       	
        	  $("#address").val(date.betadd);
              $("#community").val(date.addname);
              $("#areaID").val(date.areaID);
        },
        error: function () {
            layer.msg('操作有误!');
        }
    });
})



/**
 * 搜索提交
 */
function psot()
  {
  	  layer.load(1, { shade: [0.8,'#333']});
  	   var data=$("#posa").serialize();
	  $.ajax({
            type: "get",
            url: UR+"/global_Name.html",
            data:data,
            dataType: "json",
            success: function (date) {
                layer.closeAll("loading");
                
              var attr=date.area;
   			 var html="";
   			 for(var i=0;i<attr.length;i++){
   				 html+=ATTRITEMHTML.replace("${addName}", attr[i].addName)
   				 				   .replace("${detAdd}", attr[i].detAdd)
   				 				   .replace("${city}", attr[i].city)
   				 				   .replace("${alid}", attr[i].id)
   				 				   .replace("${aid}", attr[i].id);
   			 }
   			
   			document.getElementById("ulist").innerHTML=html;
            },
            error: function () {
                layer.msg('操作有误!');
            }
        });
  }





function areashow() {
		$("#area").show();
    	$("#addre").hide();
	}
    
    function save(id) {
    	
    	var sp1= $("#"+id).find("a .sp1").text();
    	var sp2= $("#"+id).find("a .sp2").text();
    	var sp3= $("#"+id).find("input[name='city']").val();
        $("#address").val(sp3);
        $("#community").val(sp1);
        $("#areaID").val(id);
    	
    	$("#area").hide();
    	$("#addre").show();
	}
    
    
    
    //判断密码是否为空
    function paww() {
    	 var pa=$("#passw").val();
    	 if(pa==''||pa==null){
    		 layer.msg("密码不能为空！");
    	 }
	}
    //判断手机格式
    function tele() {
    	 var te=$("#tel").val();
    	 var re = /^1\d{10}$/;
    	      if (re.test(te)) {
    	    	
    	      	} else {
    	    	  layer.msg("手机格式错误");
    	      }
	}
	  /**
	 * 新增地址提交
	 */
	function updateUsers()
	  {
		if($("#recName").val()!=''&&$("#address").val()!=''&&$("#tel").val()!=''&&$("#community").val()!=''&&$("#doorplate").val()!=''){
	  	  layer.load(1, { shade: [0.8,'#333']});
	  	  var url;
	  	  var pa=$("#passw").val();
	  	  var sh=$("#shop").val();
	  	  if(pa!='null'&&sh==''){
	  		  	url=UR+"/newuseress.html";
	  	  	  }else{
	  	  		url=UR+"/users/adduseress.html";
	  		  }
		  $.ajax({
                type: "post",
                url:url,
                data:"areaID="+$("#areaID").val()+"&recName="+$("#recName").val()+
                	 "&address="+$("#address").val()+"&tel="+$("#tel").val()+"&shop="+$("#shop").val()+
                	 "&community="+$("#community").val()+"&doorplate="+$("#doorplate").val()+"&passw="+$("#passw").val(),
                dataType: "text",
                success: function (data) {
                	
                	var data=eval('(' + data + ')');
                    layer.msg("新增成功，您可以使用");
                    
                    if(data.msg!="0"){
                    		location.href =UR+"/users/userIndex.html";
                    	}else{
                    		if(data.userId!="null"){
                    			addpronew(data.userId);
                    			
                    		}else{
                    			location.href =UR+"/users/shopcar.html";
                    		}
                    		
                    	}
                },
                error: function () {
                	layer.closeAll("loading");
                    layer.msg('操作有误!');
                }
            });
		}else{
			layer.closeAll("loading");
			 layer.msg('请填完成信息，以便提供更好的服务!');
		}
	  }
	
function addpronew(userId) {
	$.ajax({
        type: "post",
        url: UR+"/users/adddprofornew.json",
        data:"userID="+userId,
        dataType: "json",
        success: function (data) {
           
        	
        	location.href =UR+"/users/shopcar.html";
        },
        error: function () {
        	layer.closeAll("loading");
            layer.msg('操作有误!');
        }
    });

}