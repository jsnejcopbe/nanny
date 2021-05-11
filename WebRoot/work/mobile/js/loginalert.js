var LOGINHTML='<div style="margin-top: -10px;height: 100%;background-color: #f5f5f5;">\
					<div class="touchweb-com_header ">\
						<h1 style="margin-top:10px;">登录</h1>\
						<div class="rightBox">\
							<a id="register_btn" href="javascript:toRegister()"class="right rbtn"> 注册 </a>\
						</div>\
					</div>\
					<div class="touchweb_page-login">\
						<div class="login_box">\
							<form id="userform">\
							<div class="form_item">\
								<label class="na-icon-my" for="touchweb_form-username"></label>\
								<div class="input_box">\
									<input type="text" id="touchweb_form-username" name="nickName"\
										placeholder="邮箱/手机/用户名" maxlength="11"> <span\
										class="icon_delete na-icon-delete"></span>\
								</div>\
							</div>\
							<div class="form_item">\
								<label class="na-icon-password" for="touchweb_form-password"></label>\
								<div class="input_box">\
									<input type="password" id="touchweb_form-password"\
										name="password" placeholder="请输入密码"> <span\
										class="icon_delete na-icon-delete"></span>\
								</div>\
							</div>\
							   <div class="form_item">\
								    <label class="na-icon-pencil" for="touchweb_form-password"></label>\
									<input class="input_box" name="verifycode" id="verifycode" type="text"\
										placeholder="请输入验证码" maxlength="4">\
									<a id="reverifycode" href="javascript:changeCode()">\
										<img id="m_captcha" class="pic_verification" alt=""\
										src="/nanny/verifycode.html" height="43">\
									</a>\
								</div>\
							</div>\
							<div class="remember_login">\
								<input id="touchweb_form-checkbox" type="checkbox" checked>\
								<label for="touchweb_form-checkbox">两周内记住登录</label> <a\
									href="javascript: void(0);" class="forgot_password">忘记密码？</a>\
							</div>\
							<div class="login_btn">\
								<a id="login-btn" href="javascript:upFrom()" class="btn">登录</a>\
								<a href="javascript:layer.closeAll()" class="btn btn-closebtn">关闭</a>\
							</div>\
						</form>\
						</div>\
					</div>\
			   </div>';

/**
 * 展示登录界面
 */
function showLogin(){
	var panel=layer.open({
			    type: 1,
			    title: false,
			    skin: 'layui-layer-rim', //加上边框
			    area: ['100%','100%'], //宽高
			    content: LOGINHTML.replace("${shopID}", $("#shopID").val())
			});
	layer.full(panel);
}

/**
 * 替换验证码
 */
function changeCode() {
	$("#reverifycode>img").attr("src","/nanny/verifycode.html?rad=" + Math.random());
}

/**
 * 用户登录
 */
function upFrom() {
	if ((validfn([ "isNotNull", "isTel" ], "#touchweb_form-username","手机格式有误，请重新输入")&&
		 validfn([ "isNotNull" ], "#touchweb_form-password","密码格式有误，请重新输入") &&
		 validfn([ "isNotNull" ], "#verifycode", "请输入验证码")) == false)
		return;
	var data = $("#userform").serialize();
	$.ajax({
		type : "POST",
		url : "/nanny/loginact.json",
		data : data,
		success : function(msg) {
			var data = eval('(' + msg + ')');
			if(data.hasOwnProperty("success"))
			{
				if(data.shopID==null){
					layer.closeAll();
					$("#userID").val(data.userID);
					addToShop();
				}else{
					layer.msg("检测到您为商家，为防止刷单，您无法进行操作");
					changeCode();
				}
			}
			else
				changeCode();
		},
		error: function(){layer.msg("登录失败，请联系管理员");}
	});
}

function toRegister(){
	$("#userID").val("none");
	addToShop();
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