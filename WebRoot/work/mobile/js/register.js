/**
 * 表单提交
 * 
 */

function upFromreg() {
	if (validfn([ "isNotNull", "isTel" ], "#register_form-phone","手机格式有误，请重新输入") == false) {
		return;
	} else if (validfn([ "isNotNull" ], "#register_form-phone-pwd","密码格式有误，请重新输入") == false) {
		return;
	} else if ($("#register_form-phone-repwd").val() != $(
			"#register_form-phone-pwd").val()) {
		alert("两次输入密码不符");
		return;
	} else {
		layer.load(1, {shade: [0.8,'#333']});
		var data = $("#userregform").serialize();
		// 组织ajax参数
		$.ajax({
			type : "POST",
			url : "/nanny/registeract.json",
			data : data,
			success : function(msg) {
				layer.closeAll('loading');
				var data = eval('(' + msg + ')');
				layer.msg(data.msg);
				setTimeout(" location.href='/nanny/index.html'",2000);
			}
		});
	}
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