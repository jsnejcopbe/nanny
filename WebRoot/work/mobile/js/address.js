// JavaScript Document
//收货地址管理
$(function(){
	var text = '';
	//省市区获取
	$(".area").bind(click,function(){//获取省
		$('.addname').text("选择所在地区 (省份)");
		$('.province').show().html('');
		$('.city').hide().html('');
		$('.districtORcounty').hide().html('');
		$.ajax({
			url : path + "/notfilter/getProvinceANDcityANDdistrictORcounty.xhtml",
			type : "POST",
			dataType:"json",
			data :{
				'type' : 2,
				'parentid' : 100000
			},
			cache : false,
			success : function(msg){
					if(msg.result == 1){
						var list = msg.list;
						for(var i = 0;i<list.length;i++){
							$('.province').append(getCityHtml(list[i]));
						}
						$(".mp-popboxbg").addClass("active");
					}else if(msg.result == -2){//最后一级了
						selectSSX();
					}else{
						Popup.noticeTis('参数错误');
					}
				},
			error : function(){
			}
		});
	});
	
	$('.province').on('click','li',function(){	//获取市
		text += $(this).html(); //保存省
		$('.addname').text("选择所在地区 (市)");
		$('.province').hide().html('');
		$('.city').show().html('');
		$('.districtORcounty').hide().html('');
		$('#province').val($(this).html());
		$.ajax({
			url : path + "/notfilter/getProvinceANDcityANDdistrictORcounty.xhtml",
			type : "POST",
			dataType:"json",
			data :{
				'type' : 3,
				'parentid' : $(this).attr('data-regionId')
			},
			cache : false,
			success : function(msg){
					if(msg.result == 1){
						var list = msg.list;
						for(var i = 0;i<list.length;i++){
							$('.city').append(getCityHtml(list[i]));
						}
						$(".mp-popboxbg").addClass("active");
					}else if(msg.result == -2){
						selectSSX();
					}else{
						Popup.noticeTis('参数错误');
					}
				},
			error : function(){
			}
		});
	});
	
	$('.city').on('click','li',function(){	//获取区/县
		text += $(this).html();  //保存市
		$('.addname').text("选择所在地区 (区/县)");
		$('.province').hide().html('');
		$('.city').hide().html('');
		$('.districtORcounty').show().html('');
		$('#city').val($(this).html());
		$.ajax({
			url : path + "/notfilter/getProvinceANDcityANDdistrictORcounty.xhtml",
			type : "POST",
			dataType:"json",
			data :{
				'type' : 4,
				'parentid' : $(this).attr('data-regionId')
			},
			cache : false,
			success : function(msg){
					if(msg.result == 1){
						var list = msg.list;
						for(var i = 0;i<list.length;i++){
							$('.districtORcounty').append(getCityHtml(list[i]));
						}
						$(".mp-popboxbg").addClass("active");
					}else if(msg.result == -2){
						$('#area').val('');
						selectSSX();
					}else{
						Popup.noticeTis('参数错误');
					}
				},
			error : function(){
			}
		});
	});
	
	$('.districtORcounty').on('click','li',function(){
		text += $(this).html(); // 保存县
		$('#area').val($(this).html());
		selectSSX();
	});
	
	var getCityHtml = function(obj){
		var html = '';
		html += '<li data-level="'+obj.level+'" data-regionId="'+obj.code+'" data-parent="'+obj.pcode+'">'+obj.name+'</li>';
		return html;
	};
	
	var selectSSX = function(){
		//将最后的text赋值到标签
		$(".addressShow").html(text);
		$('.mp-popboxbg').removeClass('active');
		text ='';
	}
	
	// 默认地址按钮
	$(".adr-check").bind('click',function(){
		$(this).toggleClass("false");
	})
	
	$(".map-addressSelect > h1 > .iconfont").click(function(){
		//清除已存在的地址
		$('#province').val('');
		$('#city').val('');
		$('#area').val('');
		$('.addressShow').html('--省份  --城市  --地区&nbsp; <i class="iconfont addmore">&#xe602;</i>');
		text='';
		$('.mp-popboxbg').removeClass('active');
	})
	
	//保存收货地址
	$('#j-address-save').on('click',function(){
		var name = $.trim($('#name').val());
		var mobile = $.trim($('#mobile').val());
		var province = $.trim($('#province').val());
		var city = $.trim($('#city').val());
		var area = $.trim($('#area').val());
		var address = $.trim($('#address').val());
		var addressShow = $.trim($('.addressShow').html());
		var checked = $('.adr-check').hasClass('false')?0:1;
		var id = $('#id').val();
		if(name != ''){
			if(mobile != '' && checkMobile(mobile)){
				if(addressShow != ''){

					if(address != ''){
						var that = Popup.noticeFullScreen('正在保存',function(){
							Popup.noticeTis('保存失败')
						},0);
						$.ajax({
							url : path + "/vd/addaddress.xhtml",
							type : "POST",
							dataType:"json",
							data :{'name':name,
								   'mobile':mobile,
								   'province':province,
								   'city':city,
								   'area':area,
								   'address':address,
								   'addressShow':addressShow,
								   'checked':checked,
								   'id':id},
							cache : false,
							success : function(msg){
								$(that).remove();
								if(msg.result == 1){//新增/保存
									$('.noStatebox').remove();
									var id = msg.id;
									var name = $.trim($('#name').val());
									var mobile = $.trim($('#mobile').val());
									var data_info = 'address'+$('#id').val();
									var addivs = $('.address-mana div[class="address-label"]');
									var dfl = !$('.adr-check').hasClass('false');
									for(var i = 0;i<addivs.length;i++){
										var $t = $(addivs[i]);
										if($t.attr('data-info') != data_info && dfl){
											$t.find('div i').eq(0).removeClass('moren');
											$t.find("div div[class='address'] span").remove();
										}
									}
									var newadd = "<div class='address-label' data-info='address"+id+"'><div class='address-item'>"
										+"<i class='iconfont"+(checked==1?" moren ":" ")+"iposition'>&#xe616;</i><p>"
										+"<span class='name'>"+name+"</span><span class='tel'>"+mobile+"</span></p><div class='address'>"
										+(checked==1?"<span>默认地址</span>":"")+province+city+(area?area:"")+address
										+"</div><i class='iconfont bianji' data-info='"+id+"'>&#xe612;</i></div></div>";
									$('.address-mana').append(newadd);
									var divs = $('.add-message .label-box');
									for(var i=0;i<divs.length-1;i++){
										if(i==2){
											$(".map-addressSelect > h1 > .iconfont").trigger('click');
										}else{
											$(divs[i]).find('input').val('');
										}
									}
									if($('.null-address')){
										$('.null-address').hide();
									}
									$('.address-tit').show();
									Popup.noticeTis('保存成功');
									$('[data-action="back"]').trigger('click');
								}else if(msg.result == 2){//编辑/更新
									var data_info = 'address'+$('#id').val();
									var name = $.trim($('#name').val());
									var mobile = $.trim($('#mobile').val());
									var addivs = $('.address-mana div[class="address-label"]');
									for(var i = 0;i<addivs.length;i++){
										var $t = $(addivs[i]);
										if($t.attr('data-info')==data_info){
											var sp = '';
											if($('.adr-check').hasClass('false')){
												$t.find('div i').eq(0).removeClass('moren');
												$t.find("div div[class='address'] span").remove();
											}else{
												$t.find('div i').eq(0).addClass('moren');
												sp = "<span>默认地址</span>&nbsp;";
											}
											$t.find("div p span[class='name']").text(name);
											$t.find("div p span[class='tel']").text(mobile);
											$t.find("div div[class='address']").html(sp+province+city+(area?area:"")+address);
											var divs = $('.add-message .label-box');
											for(var j=0; j < divs.length-1; j++){
												if(j==2){
													$(".map-addressSelect > h1 > .iconfont").trigger('click');
												}else{
													$(divs[j]).find('input').val('');
												}
											}
										}else{
											if(!$('.adr-check').hasClass('false')){
												$t.find('div i').eq(0).removeClass('moren');
												$t.find("div div[class='address'] span").remove();
											}
										}
									}
									$('[data-action="back"]').trigger('click');
									Popup.noticeTis('更新成功');
								}else if(msg.result == 0){
									Popup.noticeTis('添加失败,刷新后重试');
								}else if(msg.result == -1){
									Popup.noticeTis('收货人不合法');
								}else if(msg.result == -2){
									Popup.noticeTis('电话参数不合法');
								}else if(msg.result == -3){
									Popup.noticeTis('所在地区有误');
								}else if(msg.result == -4){
									Popup.noticeTis('详细地址有误');
								}else if(msg.result == -5){
									Popup.noticeTis('数据异常');
								}
							}
						});
					}else{
						Popup.noticeTis('详细地址不可为空');
					}
				}else{
					Popup.noticeTis('选择地区不可为空');
				}
			}else{
				Popup.noticeTis('电话参数不合法');
			}
		}else{
			Popup.noticeTis('收货人不可为空');
		}
	});
	
	//获取收货地址
	$('.address-mana').on('click',".address-label div i[class='iconfont bianji']",function(){
		var id = $(this).attr('data-info');
		var dfl = $(this).parent().find('i').eq(0).hasClass('moren');
		if(id != '' && id > 0){
			$.ajax({
				url : path + "/vd/findaddress.xhtml",
				type : "POST",
				dataType:"json",
				data :{'id':id},
				cache : false,
				success : function(msg){
					if(msg.result == -1){
						Popup.noticeTis('参数不合法');
					}else{
						$('#name').val(msg.address.name);
						$('#mobile').val(msg.address.mobile);
						$('#province').val(msg.address.province);
						$('#city').val(msg.address.city);
						$('#area').val(msg.address.area);
						$('#address').val(msg.address.address);
						$('#id').val(msg.address.id);
						$('.addressShow').html(msg.address.province + msg.address.city + msg.address.area);
						if(dfl){
							$('.adr-check').removeClass('false');
						}else{
							$('.adr-check').addClass('false');
						}
						$('.addressShow').html(msg.address.province + msg.address.city + msg.address.area);
					}
				}
			});
		}else{
			Popup.noticeTis('参数不合法');
		}
	});
	
	//删除收货地址
	$('#j-address-del').on('click',function(){
		var id = $('#id').val();
		if(id != '' && (/^\d*$/).test(id)){
			$.ajax({
				url : path + "/vd/deladdress.xhtml",
				type : "POST",
				dataType:"json",
				data :{'id':id},
				cache : false,
				success : function(msg){
					if(msg.result == -1){
						Popup.noticeTis('参数不合法');
					}else{
						var addivs = $('.address-mana div[class="address-label"]');
						var data_info = 'address'+id;
						for(var i = 0;i<addivs.length;i++){
							var $t = $(addivs[i]);
							if($t.attr('data-info')==data_info){
								$t.remove();
								break;
							}
						}
						var divs = $('.address-label');
						if(divs.length < 1){
							$('.null-address').show();
							$('.address-tit').hide();
						}
						Popup.noticeTis('删除成功');
						$('[data-action="back"]').trigger('click');
					}
				}
			});
		}
	});
	
});

