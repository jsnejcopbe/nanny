$(function(){
	//抽屉左侧栏
	$("[data-action='aside-menu']").on('touchend mouseup',function(e){
		$('#j-m-aside,#j-m-wrapbox').toggleClass('m-open');
		$("#j-m-aside,#j-m-wrapbox").addClass("m-anim");
		$('.m-side-overlay').toggleClass('active');
		$(".cardbox").toggle();
		$(".i-up").toggle();
		$(".circleR").toggle();
		$("#j-page-popbox-ic").removeClass("showCur m-animat");
		$("#j-link-popbox-ic").removeClass("showCur m-animat");
		event.preventDefault();//阻止默认行为
	});
	
	$('#j-content-qr').hide();
	$('#j-link-qr').show();
	
	//二维码
//	$("[name='twoCode']").on('click',function(){
//		var dataType = $(this).attr('data-type');
//		$("[name='twoCode']").removeClass('active');
//		$(this).addClass('active');
//		if(dataType == "1"){
//			$('#j-content-qr').hide();
//			$('#j-link-qr').show();
//		}else{
//			
//		}
//		event.preventDefault();
//	});
	//二维码弹窗关闭
	$("[data-action='dailog-close']").on('click',function(){
		$('#j-m-qrpop').removeClass('flex-center active');
		$('.m-wrapbox').off('touchmove');
		//		$('.animatebox').show();
		event.preventDefault();
	});
	//抽屉右侧栏
	$("[data-action='aside2']").on('touchend mouseup',function(e){
		$('#j-m-rside,#j-m-wrapbox').toggleClass('m-open2');
		$("#j-m-rside,#j-m-wrapbox,.m-rside-btn").addClass("m-anim");
		$('.m-side-overlay2,.m-rside-btn').toggleClass('active');
		$(".cardbox").toggle();
		$(".i-up").toggle();
		$(".circleR").toggle();
		$("#j-page-popbox-ic").removeClass("showCur m-animat");
		$("#j-link-popbox-ic").removeClass("showCur m-animat");
		event.preventDefault();//阻止默认行为
	});
	
	// 弹出-二维码
	$("[data-action='dailog-qr']").on('click',function(){
		$('#j-m-qrpop').toggleClass('flex-center active');
	});
	

	// 弹出-二维码
//	$("[data-action='dailog-qr']").on('click',function(){
//		//$('#mpContentQRCode').attr('src',$('#mpContentQRCodeUrl').val());
//		//$('.animatebox').hide();
////		if(qrflg[3] != 1){
////			$('.link-content-qr').show();
////			$('#j-content-qr').hide();
////			$('#j-link-qr').hide();
////			qrflg[3] = 1;
////		}
//		$('#j-m-qrpop').toggleClass('flex-center active');
//	});
	
	
//	var loadqrInterval = loadingqr();
//	var qrflg = new Array(); //判断qr img是否加载过1为加载过  其他为未加载       数组[0 - 2]为添加好友   [3] 链接内容二维码
//	// 弹出-电话 popUp 
//	$("[data-action='popUp']").on('click',function(){
//		$('#j-popupTel').toggleClass('active');
//		event.preventDefault();
//	});
//	
//	// 关闭贴片
//	$("[data-action='adclose']").on('click',function(){
//		$('#j-m-cardadd').hide();
//		event.preventDefault();
//	});	
	
//	//收藏名片
//	$('#j-i-add-store').on('click',function(){
//		$.ajax({
//			url : path + "/mprs/addstore.xhtml",
//			type : "post",
//			dataType : "json",
//			data : {"phoneIdEcr" : $("#phoneIdEcr").val()},
//			success : function(msg){
//				console.info($("#phoneIdEcr").val());
//				if(msg.result == 1){
//					$('#i-sc').addClass('i-end');
//					noticeIcon('收藏成功');
//				}else if(msg.result == -1){
//					noticeDefault('找不到要收藏的名片');
//				}else if(msg.result == -2){
//					noticeDefault('已收藏过');
//				}else if(msg.result == -3){
//					noticeDefault('不能收藏自己');
//				}else{
//					noticeDefault('错误');
//				}
//			},
//			error : function(){
//				loginTips();
//			}
//		});
//	  	event.preventDefault();
//	});
//	
//	//保存桌面
//	$('#phoneDesktop').on('click',function(){
//		showSaveTips();
//		event.preventDefault();
//	});
//	
//	//登录
//	loginTips = function(){
//		var phoneIdEcr = $('#phoneIdEcr').val();
//		var html = "<div class=\"m-login\">";
//        html += "<div>已有二维码名片账号</div>";
//        html += "<div class=\"mt-10\"><a class=\"btn-login\" href=\""+ path+"/login/turnLogin.xhtml?requestURL="+path+"/"+phoneIdEcr+"/szone"+"\">登录名片</a></div>";
//        html += "<div class=\"mt-20 fs-14\">还没有名片账号？，<a href=\""+path + "/jsp/login/register.jsp?recId="+phoneIdEcr+"\" class=\"m-link\">创建名片>></a></div>";
//        html +="</div>";
//		whiteNoticeDefault(html);
//	};
//	
//	//退出
//	$('.i-out').on('click',function(){
//		window.location.href = path + '/login/loginOut.xhtml';
//		event.preventDefault();
//	});
//	
//	//设置
//	$('.i-set').on('click',function(){
//		window.location.href = path + '/mpServiceServices/installUM.xhtml';
//		event.preventDefault();
//	});
//	
//	//交换名片
//	$('#j-i-add-swap').on('click',function(){
//		$.ajax({
//			url : path + '/mprs/sendEM.xhtml',
//			type : "post",
//			dataType : "json",
//			cache : false,
//			data : {'id':$('#phoneIdEcr').val(),type:3,'gid':0}, 
//			success: function(msg){
//				if(msg.result != null){
//					if(msg.result == 1){
//						noticeIcon('操作成功');
//						setTimeout(function(){window.location.reload();},2000);
//					}else if(msg.result == -1){
//						noticeDefault('参数异常');
//					}else if(msg.result == -2){
//						noticeDefault('已互相收藏,无需交换');
//					}else if(msg.result == -3){
//						noticeDefault('已发送过请求,请等待审核');
//					}else if(msg.result == -4){
//						noticeDefault('不能与自己交换');
//					}else if(msg.result == 2){
//						noticeDefault('等待对方审核');
//					}
//				}else{
//					noticeDefault('系统出错');
//				}
//			},
//			error : function(){
//				loginTips();
//			}
//		})
//	});
//	
//	//分享
//	$('#phoneShare').on('click',function(){
//		showShareTipsOnly();
//		event.preventDefault();
//	});
//	

//	$('#mpContentQRCode').on('load',function(){
//		$('.link-content-qr').hide();
//		$('#j-content-qr').show();
//		$('#j-link-qr').hide();
//		$('.m-wrapbox').on('touchmove',function(){
//			return false;
//		});
//	});
//		mapSkip = function(){
//		selectMap();
//	}
//	selectMap = function(){
//		var phoneIdEcr = $("#phoneIdEcr").val();
//		if (phoneIdEcr != "" && phoneIdEcr) {
//			$.ajax({
//				url: path + "/login/mapInfo.xhtml",
//				dataType: "json",
//				type: "post",
//				cache: false,
//				data: {
//					"phoneIdEcr": phoneIdEcr
//				},
//				success: function(msg){
//					if (msg.result == 1) {
//						var woner = msg.woner;
//						if (woner.edition_type == 1 && (woner.vip_level != 0 || woner.tempVip > 0)) {
//							window.location.href = path + "/mpPersonal/driveMap.xhtml?id=" + phoneIdEcr;
//						}
//						else {
//							$('.map').show();
//						}
//					}
//				}
//			});
//		}
//	}
//	//导航点
//	baiduMap = function(){ //百度
//		lbs_geo_map();
//		var sitl = setInterval(function(){
//			if(lbs_geo_map_end != 'no' || IsPC()){
//				clearInterval(sitl);
//				var phoneIdEcr = $("#phoneIdEcr").val();
//				if(phoneIdEcr != "" && phoneIdEcr){
//					$.ajax({
//						url: path + "/login/mapInfo.xhtml",
//						dataType : "json",
//						type : "post",
//						cache : false,
//						data :{"phoneIdEcr" :phoneIdEcr},
//						success : function(msg){
//							if(msg.result == 1){
//								var woner = msg.woner;
//								if(woner.edition_type == 1 && (woner.vip_level != 0 || woner.tempVip > 0 )){
//									if (currentPosition == null) {
//										window.location.href = path +"/mpPersonal/driveMap.xhtml?id="+phoneIdEcr;
//									}else{
//										window.location.href = path +"/mpPersonal/driveMap.xhtml?id="+phoneIdEcr+"&lat="+currentPosition.lat+"&lng="+currentPosition.lng;
//									}
//								}else {
//									if(msg.companyMapPoint != null && msg.companyMapPoint != undefined){
//										var companyMapPoint = msg.companyMapPoint;
//										if(currentPosition == null){
//											encodeUriString('http://api.map.baidu.com/marker?location='+companyMapPoint.lat +','+companyMapPoint.lng +'&title='+companyMapPoint.name +'&content='+companyMapPoint.provinceName + companyMapPoint.cityName + companyMapPoint.areaName +companyMapPoint.address+',联系电话:'+companyMapPoint.contactNumber+'&output=html&src=搜企网');
//										}else{
//											//百度地图
//											encodeUriString('http://api.map.baidu.com/direction?origin=latlng:'+currentPosition.lat +','+currentPosition.lng +'|name:我的位置&destination=latlng:'+companyMapPoint.lat +','+companyMapPoint.lng + '|name:'+companyMapPoint.name+'&mode=driving&region='+companyMapPoint.cityName+'&output=html&src=搜企网');
//										}
//									}else{
//										noticeDefault("此用户尚未填写地址信息");
//									}
//								}
//							}else{
//								noticeDefault("错误");
//							}
//						},
//						error : function(){
//							noticeDefault("系统出错");
//						}			
//					})
//				}
//			}
//		},300);
//	};
//	qqMap = function(){ //腾讯
//		var phoneIdEcr = $("#phoneIdEcr").val();
//		if(phoneIdEcr != "" && phoneIdEcr){
//			$.ajax({
//				url: path + "/login/mapInfo.xhtml",
//				dataType : "json",
//				type : "post",
//				cache : false,
//				data :{"phoneIdEcr" :phoneIdEcr},
//				success : function(msg){
//					if(msg.result == 1){
//						var woner = msg.woner;
//						if(woner.edition_type == 1 && (woner.vip_level != 0 || woner.tempVip > 0 )){
//							window.location.href = path +"/mpPersonal/driveMap.xhtml?id="+phoneIdEcr;
//						}else {
//							if(msg.companyMapPoint != null && msg.companyMapPoint != undefined){
//								var companyMapPoint = msg.companyMapPoint;
//								//腾讯地图
//								qq.maps.convertor.translate(new qq.maps.LatLng(companyMapPoint.lat,companyMapPoint.lng), 3, function(res){
//							        latlng = res[0];
//									companyMapPoint.lat = latlng.lat;
//									companyMapPoint.lng = latlng.lng;
//									encodeUriString('http://apis.map.qq.com/uri/v1/routeplan?type=drive&to='+companyMapPoint.name+'&tocoord='+companyMapPoint.lat +','+companyMapPoint.lng+'&policy=0&referer=搜企地图导航');
//							    });
//							}else{
//								noticeDefault("此用户尚未填写地址信息");
//							}
//						}
//					}else{
//						noticeDefault("错误");
//					}
//				},
//				error : function(){
//					noticeDefault("系统出错");
//				}			
//			})
//		}
//	};
//	gaodeMap = function(){ //高德地图
//		lbs_geo_map();
//		var sitl = setInterval(function(){
//			if(lbs_geo_map_end != 'no' || IsPC()){
//				clearInterval(sitl);
//				var phoneIdEcr = $("#phoneIdEcr").val();
//				if(phoneIdEcr != "" && phoneIdEcr){
//					$.ajax({
//						url: path + "/login/mapInfo.xhtml",
//						dataType : "json",
//						type : "post",
//						cache : false,
//						data :{"phoneIdEcr" :phoneIdEcr},
//						success : function(msg){
//							if(msg.result == 1){
//								var woner = msg.woner;
//								if(woner.edition_type == 1 && (woner.vip_level != 0 || woner.tempVip > 0 )){
//									if (currentPosition == null) {
//										window.location.href = path +"/mpPersonal/driveMap.xhtml?id="+phoneIdEcr;
//									}else{
//										window.location.href = path +"/mpPersonal/driveMap.xhtml?id="+phoneIdEcr+"&lat="+currentPosition.lat+"&lng="+currentPosition.lng;
//									}
//								}else {
//									if(msg.companyMapPoint != null && msg.companyMapPoint != undefined){
//										var companyMapPoint = msg.companyMapPoint;
//										if(currentPosition == null){
//											var companyMapPoint = msg.companyMapPoint;
//											$.ajax({
//												url: path + "/notfilter/baiduTogaodeMap.xhtml",
//												dataType : "json",
//												type : "post",
//												cache : false,
//												data :{ "lat" :companyMapPoint.lat,
//														"lng" :companyMapPoint.lng},
//												success : function(msg){
//													var reqinfo = msg.reqmsg;
//													var obj = JSON.parse(reqinfo);
//													if(obj.info == "ok"){
//														encodeUriString('http://m.amap.com/?mk='+obj.locations + ',' + companyMapPoint.Name);
//													}else{
//														alert('位置信息出错');
//													}
//												}
//											});
//										}else{
//											var companyMapPoint = msg.companyMapPoint;
//											var companyMapPointStr = null;
//											var currentPositionStr = null;
//											$.ajax({ //转换定位点
//												url: path + "/notfilter/baiduTogaodeMap.xhtml",
//												dataType : "json",
//												type : "post",
//												cache : false,
//												async:false,
//												data :{ "lat" :currentPosition.lat,
//														"lng" :currentPosition.lng},
//												success : function(msg){
//													var reqinfo = msg.reqmsg;
//													var obj = JSON.parse(reqinfo);
//													if(obj.info == "ok"){
//														currentPositionStr = obj.locations;
//													}
//												}
//											});	
//											$.ajax({ //转换公司导航点
//												url: path + "/notfilter/baiduTogaodeMap.xhtml",
//												dataType : "json",
//												type : "post",
//												cache : false,
//												async:false,
//												data :{ "lat" :companyMapPoint.lat,
//														"lng" :companyMapPoint.lng},
//												success : function(msg){
//													var reqinfo = msg.reqmsg;
//													var obj = JSON.parse(reqinfo);
//													if(obj.info == "ok"){
//														companyMapPointStr = obj.locations;
//													}
//												}
//											});	
//											encodeUriString('http://m.amap.com/?from='+currentPositionStr+'(我的位置)&to='+companyMapPointStr+'('+companyMapPoint.Name+')&type=0&opt=0');									
//										}
//									}else{
//										noticeDefault("此用户尚未填写地址信息");
//									}
//								}
//							}else{
//								noticeDefault("错误");
//							}
//						},
//						error : function(){
//							noticeDefault("系统出错");
//						}			
//					})
//				}
//			}
//		},300);
//	};
//	
//	encodeUriString = function(uriString){
//		uriString = encodeURI(uriString);
//		window.location.href = uriString;
//	};
	
//	// 弹出-添加好友二维码
//	$("[data-action='dailog-addfriends']").on('click',function(){
//		if($(this).attr('data-type') == 'qq'){
//			if(qrflg[0] != 1){
//				$('.loading-qr-addfriendsqr').show();
//				$('.addfriendsqrimg').hide();
//			}
//			addfriendsqr($(this).attr('data-type'));
//		}
//		if($(this).attr('data-type') == 'weixin'){
//			if(qrflg[1] != 1){
//				$('.loading-qr-addfriendsqr').show();
//				$('.addfriendsqrimg').hide();
//			}
//			addfriendsqr($(this).attr('data-type'));
//		}
//		if($(this).attr('data-type') == 'weibo'){
//			if(qrflg[2] != 1){
//				$('.loading-qr-addfriendsqr').show();
//				$('.addfriendsqrimg').hide();
//			}
//			addfriendsqr($(this).attr('data-type'));
//		}
//		$('.animatebox').hide();
//	});
//	$('#closeaddfriendsqrpop').on('click',function(){
//		$('#j-m-addfriendsqrpop').toggleClass('flex-center active');
//		$('.animatebox').show();
//		$('.m-wrapbox').off('touchmove');
//		//二维码长按温馨提示隐藏
//		$('.m-popTit .box div').hide();
//		$('.question').attr('data-flag','hide');
//	});
//	$('#addfriendsqr').on('load',function(){
//		$('.loading-qr-addfriendsqr').hide();
//		$('.addfriendsqrimg').show();
//		$('.m-wrapbox').on('touchmove',function(){
//			event.preventDefault();
//		});
////		clearInterval(loadqrInterval);
//	})
//	function addfriendsqr(addfriendsqr){
//		if(addfriendsqr == 'qq'){
//			$('#addfriendsqr').attr('src',$('#qq_addfriendsqr').val());
//			qrflg[0] = 1;
//		}else if(addfriendsqr == 'weixin'){
//			$('#addfriendsqr').attr('src',$('#weixin_addfriendsqr').val());
//			qrflg[1] = 1;
//		}else if(addfriendsqr == 'weibo'){
//			$('#addfriendsqr').attr('src',$('#weibo_addfriendsqr').val());
//			qrflg[2] = 1;
//		}
//		$('#j-m-addfriendsqrpop').toggleClass('flex-center active');
//	}
//	
//	
//	$("[data-action='dailog-close']").on('click',function(){
//		$('#j-m-qrpop').removeClass('flex-center active');
//		$('.m-wrapbox').off('touchmove');
////		$('.animatebox').show();
//		event.preventDefault();
//	});
////	
//	//抽屉右侧栏
//	$("[data-action='aside2']").on('touchend mouseup',function(e){
//		$('#j-m-rside,#j-m-wrapbox').toggleClass('m-open2');
//		$("#j-m-rside,#j-m-wrapbox,.m-rside-btn").addClass("m-anim");
//		$('.m-side-overlay2,.m-rside-btn').toggleClass('active');
//		$(".cardbox").toggle();
//		$(".i-up").toggle();
//		$(".circleR").toggle();
//		$("#j-page-popbox-ic").removeClass("showCur m-animat");
//		$("#j-link-popbox-ic").removeClass("showCur m-animat");
//		event.preventDefault();//阻止默认行为
//	});
//	
//	IsPC = function(){	//判断是否是PC
//		var userAgentInfo = navigator.userAgent;
//		var Agents = ["Android", "iPhone",
//					"SymbianOS", "Windows Phone",
//					"iPad", "iPod"];
//		var flag = true;
//		for (var v = 0; v < Agents.length; v++) {
//			if (userAgentInfo.indexOf(Agents[v]) > 0) {
//				flag = false;
//				break;
//			}
//		}
//		return flag;
//	}
//	
//	$("[data-action='custom']").on('click',function(){
//		$('#j-custom1,#j-customMore').toggleClass('active');
//		$('#j-custom1,#j-customMore').addClass('m-anim');
//		$('#j-custom-overlay1').toggleClass('active');
//		$('#j-custom-overlay2,#j-custom2').removeClass('active');
//		$('.m-wrap').css({'overflow':'hidden'});
//		event.preventDefault();// 阻止默认行为
//	})
//	$("[data-action='custom2']").on('click',function(){
//		$('#j-custom2').toggleClass('active');
//		$('#j-custom2').addClass('m-anim');
//		$('#j-custom-overlay2').toggleClass('active');
//		$('#j-custom-overlay1,#j-custom1').removeClass('active');
//		$('.m-wrap').css({'overflow':'hidden'});
//		event.preventDefault();// 阻止默认行为
//	});
//	//跳转到企业空间
//	$('[data-active="getinCompanySpace"]').on('click',function(){
//		window.location.href = wap + "/searchDetail.xhtml?nid2=" + $(this).attr("data"); 
//	});
//	
//	function loadingqr(){
//		var count = 0;
//		var elem2;
//		var elem3; 
//		var setinterval = setInterval(function(){
//		   elem2 = $('.loading-qr').get(0);	//第一个   内容链接二维码等待
//		   elem3= $('.loading-qr').get(1);	//第二个   添加好友二维码等待
//	//	   lem2.attr('style','-webkit-transform: scale(0.5) rotate('+count+'deg);');
//		   elem2.style.MozTransform = 'scale(0.5) rotate('+count+'deg)';
//		   elem2.style.WebkitTransform = 'scale(0.5) rotate('+count+'deg)';
//		   elem3.style.MozTransform = 'scale(0.5) rotate('+count+'deg)';
//		   elem3.style.WebkitTransform = 'scale(0.5) rotate('+count+'deg)';
//		   if (count==360) { count = 0 }
//		   count+=45;
//		 },100);
//		 return setinterval;
//	}
//	
//	//长按二维码温馨提示
//	$('.question').on('click',function(){
//		if($(this).attr('data-flag') == 'show'){
//			$('.m-popTit .box div').hide();
//			$(this).attr('data-flag','hide');
//		}else{
//			$('.m-popTit .box div').show();
//			$(this).attr('data-flag','show');
//		}
//	});
//	
	
});