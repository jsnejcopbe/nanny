function hideClassSor(id){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/shop/hideclass.json",
		"Data":"classID="+id,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			setTimeout('location.reload(true)', 1000);
		},
		"fnError":function(){
			layer.closeAll('loading');
			layer.msg("操作失败");
		}
	};
	new g_fnAjaxUpload(param);
}


function showClassSor(id){
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/shop/showclass.json",
		"Data":"classID="+id,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			setTimeout('location.reload(true)', 1000);
		},
		"fnError":function(){
			layer.closeAll('loading');
			layer.msg("操作失败");
		}
	};
	new g_fnAjaxUpload(param);
}

