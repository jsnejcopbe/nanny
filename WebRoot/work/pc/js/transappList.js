var TRANSFERHTML='  <div>\
					${account}\
					<div class="mo-con clearfix">\
					<div class="col-xs-12 con-item">\
						<span class="pull-left">可提现金额</span>\
						<span class="pull-right imp">¥${canUserBalance }</span>\
					</div>\
					<div class="col-xs-12 con-item">\
						<span class="pull-left">不可用金额</span>\
						<span class="pull-right">¥${forbidBalance }</span>\
					</div>\
					<div class="col-xs-12 con-item clearfix">\
					<span class="col-xs-3">本次提现</span>\
					<div class="col-xs-9">\
						<input id="price" type="text" placeholder="单笔最高两万">\
					</div>\
					</div>\
					</div>\
					<div class="trans-btn">\
						<a href="javascript:upCashApp()">立即提现</a>\
					</div>\
					</div>';
var HASACCHTML='<div class="mo-con clearfix" style="margin-top: 0px;">\
					<div class="col-xs-12 con-item" onclick=location.href="'+BASEPATH+'/shop/banklist.html">\
						<span class="pull-left">提现账户</span>\
						<div class="pull-right">\
							<span>${bankName } 尾号${splitAcc} ${accName }</span>\
							<i class="iconfont-gl">&#xe602;</i>\
						</div>\
					</div>\
				</div>';
var ACCFORBIDHTML=' <div class="mo-con clearfix" style="margin-top: 0px;">\
						<div class="col-xs-12 con-item" onclick=location.href="'+BASEPATH+'/shop/banklist.html">\
							<span class="pull-left">该银行的提现功能已关闭，请选择其他银行</span>\
							<div class="pull-right">\
								<i class="iconfont-gl">&#xe602;</i>\
							</div>\
						</div>\
					</div>';
var NOTACCHTML='<div class="mo-con clearfix" style="margin-top: 0px;">\
					<div class="col-xs-12 con-item" onclick=location.href="'+BASEPATH+'/shop/banklist.html">\
					<span class="pull-left">设置提现账户</span>\
					<div class="pull-right">\
						<i class="iconfont-gl">&#xe602;</i>\
					</div>\
				</div>\
				</div>';


function showApp(){
	$("#transferModal .modal-body").html("");
	var param={
		"sURL":BASEPATH+"/shop/balmsgajax.json",
		"fnSuccess":function(data){
			var html1="";
			var html2="";
			$("#canUserBalance").val(data.canUserBalance);
			$("#forbidBalance").val(data.forbidBalance);
			
			if(data.hasOwnProperty("splitAcc")){
				if(data.acccount.isUse==0){
					html1=HASACCHTML.replace("${splitAcc}", data.splitAcc)
									.replace("${accName }",data.acccount.accName)
									.replace("${bankName }",data.acccount.bankName);
					$("#accountID").val(data.acccount.accID);
				}else
					html1=ACCFORBIDHTML;
			}
			else
				html1=NOTACCHTML;
			html2=TRANSFERHTML.replace("${account}", html1)
							  .replace("${canUserBalance }", data.canUserBalance)
			 				  .replace("${forbidBalance }", data.forbidBalance);
			
			$("#transferModal .modal-body").html(html2);
			$("#transferModal").modal();
			
//			layer.open({
//				type: 1,
//				title: false,
//				skin: 'layui-layer-rim', //加上边框
//				area: ['400px','400px'], //宽高
//				content: html2
//			});
		},
		"fnError":function(){
			layer.msg("发起申请失败，请稍后再试");
		}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 提交提现申请
 */
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
			layer.msg(data.msg);
			setTimeout("location.reload(true)", 1500);
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