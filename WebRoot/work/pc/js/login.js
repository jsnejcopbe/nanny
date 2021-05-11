/**
 * 提交表单
 * 
 */
function upFrom() {
	if (validfn([ "isNotNull", "isTel" ], "#touchweb_form-username","手机格式有误，请重新输入") == false) {
		return;
	} else if (validfn([ "isNotNull" ], "#touchweb_form-password","密码格式有误，请重新输入") == false) {
		return;
	} else if (validfn([ "isNotNull" ], "#verifycode", "请输入验证码") == false) {
		return;
	}

	else {
		var data = $("#userform").serialize();
		// 组织ajax参数
		$.ajax({
			type : "POST",
			url : "/nanny/loginact.json",
			data : data,
			success : function(msg) {
				var data = eval('(' + msg + ')');
				layer.msg(data.msg);
				if(data.hasOwnProperty("success"))
					window.location.href = "/nanny/index.html";

				if (data.errorType !="codeError")
					changeCode();
			}
		});
	}

}
/**
 * 替换验证码
 */
function changeCode() {
	$("#reverifycode>img").attr("src","/nanny/verifycode.html?rad=" + Math.random());
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