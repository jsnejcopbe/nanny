$(function() {
			page();
		});

		$("#btn_serch").click(function() {
			$("#forrec").html("");
			$("#pageIndex").val("");
			page();

		});

		function page() {

			var logmin = $("#logmin").val();
			var logmax = $("#logmax").val();
			var status = $("#state").val();
			var pain = $("#pageIndex").val();
			var pageSize = $("#pageSize").val();

			var pageIndex;
			if (pain != "") {
				pageIndex = parseInt(pain) + 1;
			} else {
				pageIndex = pain;
			}

			$
					.ajax({
						type : "post",
						data : "pageIndex=" + pageIndex + "&status=" + status
								+ "&logmin=" + logmin + "&logmax=" + logmax,
						url : path + "/users/userAccountFy.json",
						dataType : "text",
						success : function(msg) {
							var data = eval('(' + msg + ')');

							var pageIndex = data.rec.nowPage;

							var pageSize = data.rec.size;

							var userid = data.userID;

							var array = data.rec.rec;

							$("#pageIndex").val(pageIndex);
							$("#pageSize").val(pageSize);

							for ( var i = 0; i < array.length; i++) {
								var html = '<div class="list-item clearfix">\
					<div class="col-xs-9 item-left type2">\
						<p id="oP_'+array[i].id+'"></p>\
						<p class="item-time"><span style="padding-right: 20px;">'
										+ array[i].des
										+ '  </span> '
										+ array[i].createTime
										+ '</p>\
					</div>\
					<div class="col-xs-3 item-right type2" id="div_'+array[i].id+'"></div></div>';

								$("#forrec").append(html);

								if (array[i].userID == userid) {
									if (array[i].oname == null) {
										html = '<span>系统</span>';
									}
									if (array[i].oname != null) {
										html = '<span><img alt="" src="'+array[i].oname+'" width="20px">'
												+ array[i].oname + '</span>';
									}
									$("#oP_" + array[i].id).append(html);
								}

								if (array[i].otherSide == userid) {
									if (array[i].uname == null) {
										html = '<span>系统</span>';
									}
									if (array[i].uname != null) {
										html = '<span><img alt="" src="'+array[i].uimg+'" width="20px">'
												+ array[i].uname + '</span>';
									}
									$("#oP_" + array[i].id).append(html);
								}
								

								if (userid == array[i].otherSide) {
									html = '<p class="item-money">'
											+ array[i].money + '</p>';
									$("#div_" + array[i].id).append(html);
								}

								if (userid == array[i].userID) {
									html = '<p class="item-money">-'
											+ array[i].money + '</p>';
									$("#div_" + array[i].id).append(html);
								}

								html = '<p class="item-sta">' + array[i].stats
										+ '</p>';
								$("#div_" + array[i].id).append(html);
							}

							if (array.length == 0) {
								layer.msg('已无更多!');
							}
						},
						error : function() {
							layer.closeAll("loading");
							layer.msg('网络有误!');
						}
					});
		}