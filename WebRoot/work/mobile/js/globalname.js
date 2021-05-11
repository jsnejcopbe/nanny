var LISTCON='<div class="add-con"><ul class="list-group">${list}</ul></div>';

var ITEMHTML='<li class="list-group-item pre-add-list">\
			  	<a href="'+BASEPATH+'/areaJump.html?areaid=${id}">\
					<div class="det-name">${detName}</div>\
					<div class="det-add">${detAdd}</div>\
				</a>\
			  </li>';

$(function(){
	$("#addname").on("input propertychange",function(){$(".add-con").remove();areaQuery();});
});

/**
 * html5定位
 */
function getPostion(){
	$(".js-location").addClass("disabled");
	$(".js-location").html("<i class='icon-spinner icon-spin'></i> 定位中，请稍后.....");
	
	g_WifiLocation.main(function(str){
		if(str=="")
			return;
		location.href=BASEPATH+"/shopsearch.html?lonStr="+str+"&city="+$("input[name='city']").val();
		
		
//		var param={
//			"sURL":	BASEPATH+"/getmyposition.json",
//			"Data": "lonStr="+str+"&city="+$("input[name='city']").val(),
//			"fnSuccess":function(data){
//				if(data.hasOwnProperty("addID")){
//					location.href=BASEPATH+"/areaJump.html?areaid="+data.addID;
//				}else{
//					layer.msg(data.msg);	
//				}
//			},
//			"fnError":function(){
//				layer.msg("定位失败,请手动输入");
//				$(".js-location").removeClass("disabled");
//				$(".js-location").html("定位到当前地区");
//			}
//		};
//		new g_fnAjaxUpload(param);
	});
}

/**
 * 地址查询
 */
function areaQuery(){
	var city=$("input[name='city']").val();
	var name=$("#addname").val();
	if(name.length>1)
	{
		var param={
			"sURL":BASEPATH+"/ajaxqueryarea.html",
			"Data":"city="+city+"&name="+name,
			"fnSuccess" : function(data){
				var html="";
				for(var i=0;i<data.length;i++)
					html+=ITEMHTML.replace("${detName}", data[i].addName)
								  .replace("${detAdd}", data[i].detAdd)
								  .replace("${id}", data[i].id);
				$(".inputArea").append(LISTCON.replace("${list}", html));
			},
			"fnError" : function(){return;}
		};
		new g_fnAjaxUpload(param);
	}
}