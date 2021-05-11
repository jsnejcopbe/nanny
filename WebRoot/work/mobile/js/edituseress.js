var ATTRITEMHTML='<li class="list-group-item" id="${alid}">\
						<a class="addname" href="javascript:save(${aid})">\
							<span class="sp1">${addName}</span>\
							<span class="sp sp2">${detAdd}</span>\
							<input type="hidden"  id="city" name="city" value="${city}">\
							</a>\
					</li>';  

/**
 * 搜索提交
 */
function psot()
  {
  	  layer.load(1, { shade: [0.8,'#333']});
  	   var data=$("#posa").serialize();
	  $.ajax({
            type: "post",
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
   			 //alert(html);
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
    
    
	  /**
	 * 编辑地址提交
	 */
	function updateUsers()
	  {
	  	  layer.load(1, { shade: [0.8,'#333']});
		  $.ajax({
                type: "post",
                url: UR+"/users/upduseress.html",
                data:"areaID="+$("#areaID").val()+"&recName="+$("#recName").val()+
                	 "&address="+$("#address").val()+"&tel="+$("#tel").val()+
                	 "&community="+$("#community").val()+"&doorplate="+$("#doorplate").val()+"&id="+$("#id").val(),
                dataType: "text",
                success: function (msg) {
                    layer.closeAll("loading");
                	var data=eval('(' + msg + ')');
                    layer.msg("更改成功！");
                    //window.history.back(-1); 
                    location.href =UR+"/users/useraddress.html";
                },
                error: function () {
                    layer.msg('操作有误!');
                }
            });
	  }
	  
	  
