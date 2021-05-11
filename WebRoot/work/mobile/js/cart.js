// JavaScript Document
//基于localStorage购物车实现方案
utils = {
	setObjParam : function(key,value){
		localStorage.setItem(key,JSON.stringify(value));
	},
	getObjParam : function(key){
		return JSON.parse(localStorage.getItem(key));
	},
	removeItem : function(key){
		localStorage.removeItem(key);
	},
	clear : function(){
		localStorage.clear();
	}
	
};

//商品总订单 总单价,总单产品数量,店铺集合
orders = {
	'pay' : 0,
	'num' : 0,
	'vds' : ''
}

//单个店铺购买的所有产品 店铺ID,店铺购买总价,店铺所有产品
vd = {
	'id' : 0,
	'pay' : 0,
	'product' : '',
	'vimg':''
}

//产品详情属性,商品ID,商品名称,商品种类,商品种类值,商品单价,商品折后价,商品数量,商品颜色,#商品分类 ,商品所属店铺,店铺名称
product = {
	'id' : 0,
	'name' : '',
	'type' : 2,
	'typev' : '',
	'price' : 0.00,
	'sprice' : 0.00,
	'num' : 0,
	'color' : '',
	'pid' : 0,
	'vname' : ''
};

//构建产品字符串
productstr = function(product){
	return [{'id':product.id,'name':product.name,'type':product.type,'typev':product.typev,'price':product.price,'sprice':product.sprice,'num':product.num,'pid':product.pid,'color':product.color,'vname':product.vname}];
}

//创建店铺子单
addvd = function(product){
	return vd = {'id' : product.pid,'pay' : product.sprice * product.num, 'vimg':product.vimg, 'product' : productstr(product)};
}

//购物车
cart = {
	addproduct : function(product){
		//添加物品到购物车
		var shoppingCart = utils.getObjParam("shoppingCart");
		if(shoppingCart == null || shoppingCart == ''){
			//首次加入购物车 >> 创建总单
			utils.setObjParam('shoppingCart',{'pay' : product.sprice * product.num,'num' : product.num,'vds' : [addvd(product)]});
		}else{
			//判断是否已在购物车
			var result = cart.existproduct(product);
			if(result == 1){
				Popup.noticeTis('该物品已在购物车');
			}else if(result == 2){
				Popup.noticeTis('程序有误,刷新后重试');
			}else{
//				//可添加
				var _orders = cart.getproductlist();
				if(_orders != null && _orders != '' && _orders.vds.length > 0){
					var _add = 0;
					for(var i in _orders.vds){
						if(_orders.vds[i].id == product.pid){
							//产品push
							_orders.vds[i].product.push(product)
							_add = 1;
						}
					}
					if(_add == 0){
						//Popup.noticeTis('店铺不存在,需要创建店铺')
						_orders.vds.push(addvd(product));
					}
//					//价格叠加
					_orders.vds[i].pay += (product.num*product.price);
					_orders.pay += (product.num*product.price);
					_orders.num+=product.num;
					utils.setObjParam('shoppingCart',_orders);
				}else{
					Popup.noticeTis('程序有误,刷新后重试');
				}
			}
		}
	},
	updateproductnum : function(product){
		//修改某项产品的数量
		if(cart.existproduct(product) != 1){
			cart.addproduct(product);
		}else{
			//修改产品数量 价格
			var _orders = cart.getproductlist();
			if(_orders != null && _orders != '' && _orders.vds.length > 0){
				for(var i in _orders.vds){
					if(_orders.vds[i].id == product.pid){
						//产品push
						for(var j in _orders.vds[i].product){
							if(_orders.vds[i].product[j].id == product.id){
								var num = (product.num - _orders.vds[i].product[j].num);
								_orders.vds[i].product[j].num = product.num;
								//价格叠加
								_orders.vds[i].pay += (num*product.price);
								_orders.pay += (num*product.price);
								_orders.num+=num;
							}
						}
					}
				}
				utils.setObjParam('shoppingCart',_orders);
			}else{
				Popup.noticeTis('程序有误,刷新后重试');
			}
		}
	},
	getproductlist : function(){
		//获取购物车所有产品
		var shoppingCart = utils.getObjParam('shoppingCart');
		if(shoppingCart == '' || shoppingCart == null){
			return null;
		}else{
			//转化数据
	        return shoppingCart; 
		}
	},
	existproduct : function(product){
		//商品是否存在购物车
		var _orders = cart.getproductlist();
		if(_orders != null && _orders != '' && _orders.vds.length > 0){
			for(var i in _orders.vds){
				if(_orders.vds[i].id == product.pid){
					for(var j in _orders.vds[i].product){
						if(_orders.vds[i].product[j].id == product.id){
							return 1;
						}
					}
				}
			}
			return 0;
		}else{
//			//数据异常
			return 2;
		}
	},
	deleteproduct : function(product){
		if(cart.existproduct(product) == 1){
			var _orders = cart.getproductlist();
			if(_orders != null && _orders != '' && _orders.vds.length > 0){
				for(var i in _orders.vds){
					if(_orders.vds[i].id == product.pid){
						for(var j in _orders.vds[i].product){
							if(_orders.vds[i].product[j].id == product.id){
								_orders.vds[i].product.splice(j,1);
								//价格叠加
								_orders.vds[i].pay -= (product.num*product.price);
								_orders.pay -= (product.num*product.price);
								_orders.num-=product.num;
							}
						}
						if(!_orders || !_orders.vds[i].product || _orders.vds[i].product.length < 1){
							_orders.vds.splice(i,1);
						}
					}
				}
				if(!_orders || !_orders.vds || _orders.vds.length < 1){
					_orders = null;
				}
				utils.setObjParam('shoppingCart',_orders);
			}else{
				Popup.noticeTis('程序有误,刷新后重试');
			}
		}else{
			//删除出错
		}
	}
};


//店内搜索
$('.m-search').on('focus','input',function(){
	$('.m-pagecont').hide();
	$('.fixed-menu').hide();
	//$('.store-detail-hyh').hide();
});

$('.m-search').on('blur','input',function(){
	var val = $(this).val();
	if(!val){
		$('.m-pagecont').show();
		$('.fixed-menu').show();
		$('#search').hide();
	}
});

$('.m-search').on('keyup','input',function(){  
	    var oEvent = window.event;  
	    if (oEvent.keyCode == 13) {  
	        $('.m-search .ic-search').trigger('click');
	    }
	});

$('.m-search').on('click','.ic-search',function(){
	var key = $(this).parent().find('input').val();
	if($.trim(key) == ""){
		Popup.noticeTis('未输入搜索产品');
		return;
	}
	$.ajax({
		url : path + "/notfilter/productsearch.xhtml",
		type : "POST",
		dataType:"json",
		data : {'vdId' : $('#mmsid').val(),'searchName' : key},
		cache : false,
		success:function(data){
			if(data.result == 1){
				if(data.obj){				
					var arr = data.obj.aosps;
					var len = arr.length;
					var html = '';
					for(var i = 0; i < len; i++){
						var obj = arr[i];
						var price = '';
						if(obj.lowprices == obj.athigh){
							price = '<span class="curPrice">￥' + obj.lowprices  + '</span>';
						}else{
							price = '<span class="curPrice">￥' + obj.lowprices  + ' - ￥' + obj.athigh  + ' </span>';
						}
						html += '	<div class="item" data-info="'+obj.id+'">'
								+'		<div class="itemInfo fixed" >'
								+'			<div class="imgbox flex-center"><img src="' + vdimg + '/PRO_DETAIL_' + obj.imgid + '.jpg-cut148" onerror="this.src=\'http://7x2wkc.com1.z0.glb.clouddn.com/vdproductdefault.jpg\'" class="_porimg" href="javascript:void(0);"></div>'
								+'			<div class="infobox">'
								+'				<h1>' + obj.productname + '</h1>'
								+'				<p class="price">'
								+				price		
								+'				</p>'
								+'				<p><span>总销量：' + obj.sales  + '</span></p>'
								+'			</div>'
								+'		</div>'
								+'	</div>';
					}
					if(html){
						$('#search').find('.storeManage-goodsbox-list').html(html);
					}
				}
				$('#search').show();
			}else if(data.result == -1){
				Popup.noticeTis('未输入搜索产品');
			}
		}
	});
});

$('#search').on('click','.item',function(){
	window.location.href = vd_bak + '/p/' + $(this).attr('data-info');
});

