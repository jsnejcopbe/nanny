$(document).ready(function(){
	$('#bid').bind('change',function(){
		var extend = $("#bid option").not(function(){ return !this.selected }).attr('title');
		if (extend == 1)$('.city').removeClass('hide');else $('.city').addClass('hide')();
	});
	$('.i-fmdel').bind('click',function(){$(this).prev().val('');});
	$('.ma-mycard').bind('click',function() {window.location.href = path + '/mpPersonal/findMpOpenAccountsInfoList.xhtml';});
	$('#j-add-card').bind('click',function() {window.location.href = path + '/mpPersonal/addMpOAI.xhtml';});
	$('.i-del').bind('click',function() {
		var tis = $(this).parent().parent().next().children().last().children().first().html();
		$('#del').val($(this).attr('delid'));
		confirmFailReset('提示','您是否要删除尾号为' + tis + '的银行卡','删除');
	});
	$('.bind').bind('click',function() {
		$('.ma-cardlist >li').removeClass('chose');
		$(this).parent().parent().parent().addClass('chose');
		updateBindBankCard($(this).next().children().first().attr('delId'), $(this));
	});
	$('.inner').bind('click',function() {window.location.href = path + '/mpPersonal/addMpOAI.xhtml?Id=' + $(this).prev().children().last().children().first().attr('delId');});
	$('#pr').bind('change',function() {findCityByPr($(this).val());});
	$('.zfb').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=0&tc=0';});
	$('.zfb_acc').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=0&tc=0';});
	$('.yb').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=1&tc=0';});
	$('.yb_acc').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=1&tc=0';});
	$('#helptip .i-close').bind('click',function() {$(this).parent().parent().hide();});
	$('#settpwd .i-close').bind('click',function() {$(this).parent().parent().hide();});
	$('#urgenttip .i-close').bind('click',function() {$(this).parent().parent().hide();});
	$('.ma-pop').bind('click',function(e) {if (e && e.stopPropagation)e.stopPropagation();else window.event.cancelBubble = true;});
	$('.i-tip').bind('click',function() {$('.i-close').parent().parent().show();});
	$('.yb_trade').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=1&tc=1';});
	$('.zfb_trade').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=0&tc=1';});
	$('#j-ma-cashbox-item').bind('click',function() {window.location.href = path + '/mpPersonal/findMpOpenAccountsInfoList.xhtml';});
	$('#money').bind('input propertychange',function() {MoneyCount();});
	$('#j-tr-data-clickmore').bind('click',function() {dataMore();});
	$('.tcs').bind('click',function() {window.location.href = path + '/mpPersonal/findMpOpenAccountsInfoList.xhtml';});
	$('.trs').bind('click',function() {window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=1&tc=1';});
	$('#rechargeAmount').bind('input propertychange',function() {countRechargeAmount();});
	$('#tasking').bind('click',function() {window.location.href = path + '/mpPersonal/findCRs.xhtml?account_type=' + $.trim($('#type').val()) + '&status=1';});
	$('#tc_all').bind('click',function() {window.location.href = path + '/mpPersonal/findCRs.xhtml?account_type=' + $.trim($('#account_type').val()) + '&status=0';});
	$('#tc_ing').bind('click',function() {window.location.href = path + '/mpPersonal/findCRs.xhtml?account_type=' + $.trim($('#account_type').val()) + '&status=1'});
	$('#tc_suc').bind('click',function() {window.location.href = path + '/mpPersonal/findCRs.xhtml?account_type=' + $.trim($('#account_type').val()) + '&status=2'});
	$('#tc_fal').bind('click',function() {window.location.href = path + '/mpPersonal/findCRs.xhtml?account_type=' + $.trim($('#account_type').val()) + '&status=3'});
	$('#j-tc-data-clickmore').bind('click',function() {TcDataMore();});
	$('#frozening').bind('click',function() {window.location.href = path + '/mpPersonal/findHRs.xhtml?accounttype=' + $.trim($('#type').val()) + '&status=2'});
	$('#hr_all').bind('click',function() {window.location.href = path + '/mpPersonal/findHRs.xhtml?accounttype=' + $.trim($('#accounttype').val()) + '&status=4'});
	$('#hr_ing').bind('click',function() {window.location.href = path + '/mpPersonal/findHRs.xhtml?accounttype=' + $.trim($('#accounttype').val()) + '&status=0'});
	$('#hr_suc').bind('click',function() {window.location.href = path + '/mpPersonal/findHRs.xhtml?accounttype=' + $.trim($('#accounttype').val()) + '&status=1'});
	$('#hr_fal').bind('click',function() {window.location.href = path + '/mpPersonal/findHRs.xhtml?accounttype=' + $.trim($('#accounttype').val()) + '&status=2'});
	$('#j-hr-data-clickmore').bind('click',function() {HrDataMore();});
	$('#notHaving').bind('click',function() {window.location.href = path + '/mpPersonal/findHRs.xhtml?accounttype=' + $.trim($('#type').val()) + '&status=0'})
	$('body').on({'click':function(){Urgent(this);}},'.ma-speedbox');
	$('#j-true-urgent').bind('click',function(){$('#j-ser-tip').hide();$('#j-wait').show();$('.i-close').hide();urgentwait(this,1);});
	$('#j-yb-pay-urgent').bind('click',function(){$('#j-ser-tip').hide();$('#j-wait').show();$('.i-close').hide();urgentwait(this,2);});
	$('#j-re-yb-amount').bind('click',function(){window.location.href = path + '/mpPersonal/totaskDetail.xhtml?type=1&tc=0'});
	$('#j-ms-yb-amount').bind('click',function(){Urgent_bak(this);});
	$('#j-my-yb-tc').bind('click',function(){window.location.href = path + '/mpPersonal/totaskcash.xhtml?type=1';});;
});

Urgent = function (ele){
	$('#j-ser-tip').show();
	$('#j-succ').hide();
	$('#j-fa').hide();
	$('#j-wait').hide();
	var id = $(ele).attr('vid');
	var money = $(ele).attr('v');
	$('.allmoney').html(money);
	var sx = (Number(money)*Number(0.63/100)).toFixed(2);
	if (Number(sx) < 0.01)sx = 0.01;
	$('.procedures').html(-sx);
	var urgent = (Number(money)*Number(0.2/100)).toFixed(2);
	if (Number(urgent) < 0.01)urgent = 0.01;
	$('.urgent').html(-urgent);
	$('.amount').html((Number(money) - urgent - sx).toFixed(2));
	$('#j-true-urgent').val(id);
	$('#urgenttip').show();
}

Urgent_bak = function (ele){
	$('#j-ser-tip').show();
	$('#j-succ').hide();
	$('#j-fa').hide();
	$('#j-wait').hide();
	var id = $(ele).attr('vid');
	var money = $('.allmoney').html();
	var sx = (Number(money)*Number(0.63/100)).toFixed(2);
	if (Number(sx) < 0.01)sx = 0.01;
	$('.procedures').html(-sx);
	var urgent = (Number(money)*Number(0.2/100)).toFixed(2);
	if (Number(urgent) < 0.01)urgent = 0.01;
	$('.urgent').html(-urgent);
	$('.amount').html((Number(money) - urgent - sx).toFixed(2));
	$('#j-yb-pay-urgent').val(id);
	$('#urgenttip').show();
}

urgentwait = function(ele,type){
	var id = $(ele).val();
	$.ajax({
		url: '/vip/urgentser.xhtml',
		type: 'POST',
		dataType: 'json',
		data: {'id': id},
		async: true,
		cache: false,
		success: function(msg) {
			if(msg.msg == 1){
				$('#j-wait').hide();
				$('#j-succ').show();
				if(type == 1){
					$('li div[vid="' + id + '"]').parent().remove();
				}else{
					$('.txt').remove();
					$('#j-success').html('您的资金已到账');
					$('#j-jj-tip').remove();
					$('#j-ms-yb-amount').remove();
				}
			}else if(msg.msg == -1){
				$('.fc-gray').html('登录超时,请重新登录');
				$('#j-wait').hide();
				$('#j-fa').show();
			}else if(msg.msg == -2){
				$('.fc-gray').html('您无权操作此数据');
				$('#j-wait').hide();
				$('#j-fa').show();
			}else if(msg.msg == -3){
				$('.fc-gray').html('您的加急申请失败，今天系统加急额度已用完');
				$('#j-wait').hide();
				$('#j-fa').show();
			}else if(msg.msg == -4){
				$('.fc-gray').html('您的加急申请失败，今天系统加急额度已用完');
				$('#j-wait').hide();
				$('#j-fa').show();
			}else if(msg.msg == -5){
				$('.fc-gray').html('数据异常,刷新后重试');
				$('#j-wait').hide();
				$('#j-fa').show();
			}else if(msg.msg == -6){
				$('.fc-gray').html('订单未确认，不可加急');
				$('#j-wait').hide();
				$('#j-fa').show();
			}
			$('.i-close').show();
		}
	});
}

countRechargeAmount = function() {
	var rechargeAmount = $.trim($('#rechargeAmount').val());
	var sx = (Number(rechargeAmount) * Number(0.63 / 100)).toFixed(2);
	if (Number(sx) < 0.01)sx = 0.01;
	$('.ma-sxf').html(sx);
	$('.ma-num').html((Number(rechargeAmount) - Number(sx)).toFixed(2))
};
checkPar = function() {
	var rechargeAmount = $.trim($('#rechargeAmount').val());
	if (rechargeAmount != '' && Number(rechargeAmount) >= 2 && Number(rechargeAmount) <=20000) {
		noticeIcon('转至易宝支付安全交易通道');
		return true
	} else {
		noticeDefault('支付金额应在2-20000元之间');
		return false
	}
};
dataMore = function() {
	var start = $.trim($('#start').val());
	var type = $.trim($('#type').val());
	$.ajax({
		url: '/mpPersonal/findMATS.xhtml',
		type: 'POST',
		dataType: 'json',
		data: {'start': start,'type': type},
		async: false,
		cache: false,
		success: function(msg) {
			var html = "";
			for (var i = 0; i < msg.data.length; i++) {
				html += '<li><div><span class="num ' + jj(msg.data[i].trade_type) + '">' + fh(msg.data[i].trade_type, msg.data[i].income, msg.data[i].pay) + '</span><span class="tle">' + ttype(msg.data[i].trade_type) + '</span></div><div class="row"><span class="time">' + msg.data[i].addTimeBak + '</span><span class="money">余额：' + msg.data[i].balance + '</span></div></li>'
			}
			$('.ma-record-list').append(html);
			$('#start').val(Number(start) + 1);
			if (msg.data.length != 5)$('#j-tr-data-clickmore').hide();
		}
	});
};

TcDataMore = function() {
	var start = $.trim($('#start').val());
	var account_type = $.trim($('#account_type').val());
	var status = $.trim($('#status').val());
	$.ajax({
		url: '/mpPersonal/finMoreMCRs.xhtml',
		type: 'POST',
		dataType: 'json',
		data: {'start': start,'account_type': account_type,'status': status},
		async: false,
		cache: false,
		success: function(msg) {
			var html = "";
			for (var i = 0; i < msg.data.length; i++) {
				html += '<li><a href="' + path + '/mpPersonal/toMTCOD.xhtml?id=' + msg.data[i].id + '"><div><span class="tle">提现' + msg.data[i].task_cash + '元</span></div><div class="row"><span class="time">' + ss(msg.data[i].status) + '</span><span class="money">' + msg.data[i].addTime + '</span></div></a></li>'
			}
			$('.ma-record-list').append(html);
			$('#start').val(Number(start) + 1);
			if (msg.data.length != 5)$('#j-tc-data-clickmore').hide();
		}
	})
};
HrDataMore = function() {
	var start = $.trim($('#start').val());
	var accounttype = $.trim($('#accounttype').val());
	var status = $.trim($('#status').val());
	$.ajax({
		url: '/mpPersonal/finMoreMHRs.xhtml',
		type: 'POST',
		dataType: 'json',
		data: {'start': start,'accounttype': accounttype,'status': status},
		async: false,
		cache: false,
		success: function(msg) {
			var html = "";
			for (var i = 0; i < msg.data.length; i++) {
				html += '<li>' + ssb(msg.data[i].status,msg.data[i].id,msg.data[i].pay) + '<div class="oh"><span class="tle">' + ssa(msg.data[i].type) + ' ' + msg.data[i].pay + '元</span></div><div class="row"><span class="time">' + sst(msg.data[i].status) + '</span><span class="money">' + msg.data[i].addtime + '</span></div></li>'
			}
			$('.ma-record-list').append(html);
			$('#start').val(Number(start) + 1);
			if (msg.data.length != 5)$('#j-hr-data-clickmore').hide();
		}
	})
};
ssa = function(type) {
	var me = '';
	if (type == 1)me = '一键支付';else me = '购物';
	return me
};
sst = function(status) {
	var me = '';
	if (status == 0) {
		me = '未到账';
	} else if (status == 1) {
		me = '已处理';
	} else if (status == 2) {
		me = '冻结中';
	}
	return me
};

ssb = function (status,id,pay){
	if(status == 0){
		return '<div class="ma-speedbox" v="' + pay + '" vid="' + id + '"><button class="ma-btn-speed">我要加急</button></div>'
	}
	return '';
}

ss = function(status) {
	var me = '';
	if (status == 0 || status == 1) {
		me = '正在处理';
	} else if (status == 2) {
		me = '成功';
	} else if (status == 3 || status == 4) {
		me = '失败';
	}
	return me
};
jj = function(trade_type) {
	var arr = [1, 3, 5, 8, 9, 10];
	if (arr.indexOf(trade_type) > 0)return 'num-jia';else return 'num-jian';
};
fh = function(trade_type, income, pay) {
	var arr = [1, 3, 5, 8, 9, 10];
	if (arr.indexOf(trade_type) > 0)return '+' + income; else return '-' + pay;
};
ttype = function(trade_type) {
	var html;
	if (trade_type == 1) {
		html = '推荐返利';
	} else if (trade_type == 2) {
		html = '提现';
	} else if (trade_type == 3) {
		html = '提现失败';
	} else if (trade_type == 5) {
		html = '充值';
	} else if (trade_type == 6) {
		html = '消费';
	} else if (trade_type == 8) {
		html = '购物退款';
	} else if (trade_type == 9) {
		html = '出售产品';
	} else if (trade_type == 10) {
		html = '抽成';
	} else if (trade_type == 12) {
		html = '手续费';
	} else {
		html = '其他';
	}
	return html
};
checkBankCardContent = function() {
	var bid = $('#bid').val();
	var name = $('#name').val();
	var num = $('#num').val();
	var ci = $('#ci').val();
	var flag = false;
	if (bid != 0) {
		if ($('#bid option').not(function(){return !this.selected;}).attr('title') == 1 && ci == '') {
			noticeDefault('请填写正确的开户地址');
		} else {
			if (name != '') {
				if (num != '') {flag = true;} else {noticeDefault('银行卡号不可为空');}
			} else {noticeDefault('持卡人不可为空');}
		}
	} else {
		noticeDefault('开户行不可为空');
	}
	return flag
};

updateBindBankCard = function(id, ele) {
	$.ajax({
		url: "/mpPersonal/updateCheckedMoais.xhtml",
		type: "POST",
		dataType: "json",
		data: {"id": id},
		async: false,
		cache: false,
		success: function(msg) {
			if (msg.result == 1) {
				noticeIcon('修改成功');
				setTimeout(function(){window.location.reload(true);},2000);
			} else if (msg.result == 2) {
				noticeDefault('参数异常,刷新可能解决问题');
			} else if (msg.result == -1) {
				noticeDefault('参数异常,刷新可能解决问题');
			}
		}
	})
};
findCityByPr = function(pcode) {
	$.ajax({
		url: "/mpPersonal/findMpcity.xhtml",
		type: "POST",
		dataType: "json",
		data: {"pcode": pcode},
		async: false,
		cache: false,
		success: function(msg) {
			if (msg.result == -1) {
				noticeDefault('参数异常,刷新可能解决问题');
			} else if (msg.result == 1) {
				$('#ci').empty();
				for (var i = 0; i < msg.data.length; i++) {
					$('#ci').append('<option value="' + msg.data[i].code + '">' + msg.data[i].name + '</option>');
				}
			}
		}
	})
};
MoneyCount = function() {
	var money = $.trim($('#money').val());
	var sx;
	if (money != '') {
		var type = $('#type').val();
		if (type == 0) {
			sx = (Number(money) * Number(0.005)).toFixed(2);
			if (Number(sx) < 1) {
				sx = 1
			} else if (Number(sx) >= 25) {
				sx = 25
			}
			$('.ma-poundagemoney').html(sx);
			$('.ma-actualmoney').html((Number(money) - Number(sx)).toFixed(2))
		} else {
//			sx = (Number(money) - Number(2)).toFixed(2);
//			if (Number(sx) < 2) {
				sx = 2
//			} else if (Number(sx) >= 10) {
//				sx = 10
//			}
			$('.ma-poundagemoney').html(sx);
			$('.ma-actualmoney').html((Number(money) - Number(sx)).toFixed(2));
		}
	} else {
		$('#fee').html(0);
		$('#actualAccount').html(0);
		noticeDefault('提现金额不合法!!!');
	}
};

taskCashCheck = function (){
	var balance = $.trim($('#balance').val());
	var money = $.trim($('#money').val());
	var type = $.trim($('#type').val());
	var pwd = $.trim($('#pwd').val());
	var flag = false;
	if (type == 1) {
		if (Number(balance) > 0) {
			if (Number(money) > 2) {
				if (Number(balance) >= Number(money)) {
					if(NumberFormat(pwd,6)){
						flag = true;
					}else{
						noticeDefault('请填写正确的交易密码');
					}
				} else {
					noticeDefault('易宝账户金额超过提现金额');
				}
			} else {
				noticeDefault('输入的金额不合法');
			}
		} else {
			noticeDefault('易宝账户金额过低');
		}
	} else {
		if (Number(balance) > 0) {
			if (Number(money) > 1) {
				if (Number(balance) >= Number(money)) {
					if(NumberFormat(pwd,6)){
						flag = true;
					}else{
						noticeDefault('请填写交易密码');
					}
				} else {
					noticeDefault('支付宝账户金额超过提现金额');
				}
			} else {
				noticeDefault('输入的金额不合法');
			}
		} else {
			noticeDefault('支付宝账户金额过低');
		}
	};
	return flag;
}// JavaScript Document