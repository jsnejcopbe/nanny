var add_det="";
var province="";
var city="";
var area="";
var lon="";
var lat="";
var nowId="";

$(function(){
	$(".map-con td>a").on("click",function(){
		$(".js-query").click();
		var parent=$(this).parents(".map-con");
		var pos=$.trim(parent.find(".con-pos").text());
		add_det=$.trim(parent.find(".con-add").text());
		province=$.trim(parent.find(".con-province").text());
		city=$.trim(parent.find(".con-city").text());
		area=$.trim(parent.find(".con-area").text());
		lat=pos.split(",")[0];
		lon=pos.split(",")[1];
		nowId=parent.find(".con-id").val();
		$("#addName").val(parent.find(".con-name").text());
		createParamData();
	});
	$(".js-closemodal").on("click",function(){closeModal();});
});

/**
 * 读取城市和省份
 * @param select
 * @param value
 */
function setArea(select,value)
{
	$(select).val(value);
	if(select.indexOf("province")!=-1)
		document.getElementById("city").value="";
	$("#sq_submit").click();
}

/**
 * 获得地图上的参数
 * @param param
 */
function getPos(param){
	
	add_det=param.add_det;
	province=param.province;
	city=param.city;
	area=param.area;
	lon=param.lon;
	lat=param.lat;
}


/**
 * 上传地图数据
 * @param name
 */
function upMapData(){
	if(validfn(["isNotNull"], "#addName", "请输入地址名称") == false)
		return;
	if(add_det=="" || province=="" || city=="" || area=="" || lon=="" || lat=="")
	{
		layer.msg("请选择一个地址");
		return;
	}
	layer.load(1, {shade : [ 0.8, '#333' ]});
	 
	//组织数据
	var data="add_det="+add_det+"&province="+province+"&city="+city+
			 "&area="+area+"&lon="+lon+"&lat="+lat+"&name="+$("#addName").val();
	if(nowId!="")
		data=data+"&nowId="+nowId;
	var upparam={
		"sURL":"/nanny/admin/addnewmap.json",
		"Data":data,
		"fnSuccess":function(data){
			layer.closeAll("loading");
			layer.msg(data.msg);
			closeModal();
			location.reload(true);
		},
		"fnError"  :function(){
			layer.closeAll("loading");
			layer.msg("添加失败,请联系管理员");
		}
	};
	new g_fnAjaxUpload(upparam);
}

function closeModal(){
	$("#mapModal").modal("hide");
	clear();
}

/**
 * 清空数据
 */
function clear(){
	document.getElementById("addName").value="";
	nowId="";
}

/**
 * 表单验证
 * @param checkList
 * @param selector
 * @param errorMsg
 * @returns
 */
function validfn(checkList,selector,errorMsg){
	var valid=new Validform({
		"validList":checkList,
		"checkID"  :selector,
		"errorMsg" :errorMsg
	});
	return valid.validFun();
}

function createParamData(){
	var param=new Object();
	param.add_det=add_det;
	param.province=province;
	param.city=city;
	param.area=area;
	param.lon=lon;
	param.lat=lat;
	document.getElementById("mapIframe").contentWindow.setData(param);
}