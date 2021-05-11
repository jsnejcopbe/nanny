$(function(){
	$(".js-totranrec").on("click",function(){
		location.href=BASEPATH+"/users/transrec.html";
	});
});

function upCashApp(){
	if((validfn(["isNum","isNotNull"], "#price", "请输入数字")&&
		validfn([ "isNotZero"], "#price","输入金额应大于1"))==false)
		return;
	if($("#canUserBalance").val()<1){
		layer.msg("您的可用余额小于1元");
		return;
	}
	if($("#price").val()<1){
		layer.msg("输入金额应大于1");
		return;
	}
	if($("#accountID").val()==""){
		layer.msg("您尚未设置收款账户");
		return;
	}
	
	
	layer.load(1, {shade: [0.8,'#333']});
	var data="userID="+$("#userID").val()+"&accountID="+$("#accountID").val()+"&price="+$("#price").val();
	var param={
		"sURL":BASEPATH+"/users/addtransferapp.json",
		"Data":data,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			layer.msg(data.msg);
			setTimeout("location.href='"+BASEPATH+"/index.html'", 1500);
		},
		"fnError":function(){layer.closeAll('loading');layer.msg("提交失败，请联系管理员");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 表单验证
 * 
 * @param checkList
 * @param selector
 * @param errorMsg
 * @returns
 */
function validfn(checkList, selector, errorMsg) {
	var valid = new Validform({
		"validList" : checkList,
		"checkID" : selector,
		"errorMsg" : errorMsg
	});
	return valid.validFun();
}