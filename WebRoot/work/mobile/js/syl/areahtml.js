$(function(){
	$("#return_").prop("href",path+"salesman/index.html");
	
	layer.config({
	    extend: 'extend/layer.ext.js'
	});
	
	loadmap();
	init();
});


var map;
var size = 5;
var total;
var cityName;

var setData = function(now_page,row,address){
	var area = address === undefined ? "" : address.replace(/['\s\\[\\]]/g,"");
	$ajax({
		url:path+"salesman/adress/getAdresslist.html",
		data:{data:JSON.stringify({address:area})},
		done:function(json){
			total = json.rows.length;
			//alert(json.rows.length)
			if(total <= size)$(".pagin-area").hide();
			else $(".pagin-area").show();
			$.each(json_spilt(json, now_page, row).rows,function(i,val){
				var addName = val.addName === undefined ? "暂无小区名" : val.addName;
				var detAdd = val.detAdd === undefined ? "暂无地址" : val.detAdd;
				var div = '<div class="list-item clearfix">\
					<div class="col-xs-9 it-des">\
					<p>'+addName+'</p>\
					<p>'+detAdd+'</p>\
					</div>\
					<div class="col-xs-3">\
					<div class="it-btn">\
					<a class="item_edit" href="#" data-lon="'+val.lon+'" data-lat="'+val.lat+'" data-id="'+val.id+'">\
					<i class="iconfont-gl">&#xe635;</i>\
					<br>\
					<span>编辑地址</span>\
					</a>\
					</div>\
					</div>\
					</div>'
					$(".ap-list").append(div);
			})
		}
	})
	
	function json_spilt(json,now_page,row){
		var newjson = {rows:[]};
		var data = json.rows;
		for(var i=0;i<data.length;i++){
			var start,end;
			start = row*(now_page-1);
			end = row*now_page;
			if(i >= start && i < end){
				newjson.rows.push(data[i]);
			}
		}
		return newjson;
	}
}

var setDataArea = function(){
	$("#hide_search .list").html("");
	$ajax({
		url:path+"salesman/adress/getAdress.html",
		done:function(json){
			$("#hide_search .list-group-item").removeClass("active");
			$.each(json,function(i,val){
				var div = '<a href="#" data-id="'+val.id+'" class="list-group-item">'+val.detAdd+val.addName+'</a>';
				$("#hide_search .list").append(div);
			})
		}
	})
}

function init(){
	$("#suggestId").val("");
	$("body").on("focus","a",function(){
		$(this).blur();
	})

	var load = function(){
		new setData(1,size);
		//hide div load
		new setDataArea();
	}
	
	var bind = function(){
		$("#suggestId").keypress(function(e){
			if(e.which == 13){
				event.preventDefault();//阻止默认行为
				return false;
			}
		}) 
		
		//返回
		$("#return_").click(function(){
			$("#allmap , #add_address_query").hide();
			$("#two_con").show();
		})
		
		//绑定确定地址
		$("#add_address_query").on("click","a",function(){
			var ads=$("#suggestId").val();
			if(ads!=''){
				areaSearch();
			}else{
				layer.msg("查询小区不能为空！");
			}
		});
		
		//添加地址
		$("body").on("click",".bot-btn a",function(){
			$("#return_").prop("href",path+"salesman/arealist.html");
			$("#add_address_query input").val("");
			$("#add_address_query a").attr("data-id",'');
			$("#allmap , #add_address_query").show();
			$("#two_con").hide();
		})
		
		//加载更多
		$(".pagin-area").on("click","a",function(){
			var page = parseInt($(this).attr("data-page")) + 1;
			if(page > Math.ceil(total/size))return layer.msg("已无更多");
			$(this).attr("data-page",page);
			new setData(page,size);
		})
		
		//搜索
		$("#two_con .sec-inp").on("input propertyChange","input",function(){
			clear();
			new setData(1,size,$(this).val());
		})
		
		function clear(){
			$(".ap-list").html("");
			$(this).attr("data-page",1);
		}
		
		//===========================================================
		
		$("body").on("click",".item_edit",function(){
			var id = $(this).attr("data-id");
			$("#return_").prop("href",path+"salesman/arealist.html");
			$("#add_address_query a").attr("data-id",id);
			$("#add_address_query input").val($(this).parents(".list-item").find("p:last").html());
			$("#allmap , #add_address_query").show();
			$("#two_con").hide();
			
//			$("body .item_edit").removeClass("active");
//			$(this).addClass("active");
//			//自定页
//			layer.open({
//			    type: 1,
//			    title:'',
//			    skin: 'layui-layer-rim', //样式类名
//			    closeBtn: 0, //不显示关闭按钮
//			    shift: 2,
//			    area: ['220px', '300px'], //宽高
//			    shadeClose: true, //开启遮罩关闭
//			    content: $("#hide_search").html()
//			});
			
		})
		
		/*$("body").on("click",".layui-layer-content .list-group-item",function(){
				$(this).addClass("active");
				var id = $(this).attr("data-id");
				var shop_id = $(".item_edit.active").attr("data-id");
				
				$ajax({
					url:path+'salesman/adress/edit.html',
					data:{adressID:id,shopID:shop_id},
					done:function(msg){
						$(".layui-layer-shade").click();
						setTimeout(function(){
							layer.msg("修改成功");
							clear();
							new setData(1,size);
						}, 500);
					}
				},1)
		})*/
		
	}

	new load();
	new bind();
}

function $ajax(option,type){
	var defVal = {
			async:false,
			url:'',
			data:'',
			dataType:'json',
			done:function(json){

			}
	}
	var obj = $.extend({},defVal,option);
	obj.dataType = type=== undefined ? "json" : "text";
	$.ajax({
		async:obj.async,
		type:'post',
		url:obj.url,
		data:obj.data,
		dataType:obj.dataType
	}).done(obj.done)
	.error(function(msg){
		layer.msg("网络异常");
	})
}

var position = {};

//百度地图API功能
function loadmap() {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=inW9ZIFN8zK1hkLThLesXXYP&callback=mapinit";
	document.body.appendChild(script);
}

function G(id) {
	return document.getElementById(id);
}
function mapinit(){
	//var point = new BMap.Point(center.lng, center.lat); // 创建点坐标
	
	
	// 根据ip定位当前地址
	map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);

	function myFun(result){				//定位后的回调方法
		cityName = result.name;
		map.setCenter(cityName);
		//	var center = result.center;
		//	alert("当前定位城市:"+cityName);
	}
	var myCity = new BMap.LocalCity();//定位城市
	myCity.get(myFun);

	
	map.enableScrollWheelZoom();
	map.enableContinuousZoom();

	var ac = new BMap.Autocomplete( //建立一个自动完成的对象
	{
		"input" : "suggestId",
		"location" : map
	});

	ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
		var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province + _value.city + _value.district
			+ _value.street + _value.business;
		}
		str = "FromItem<br />index = " + e.fromitem.index
		+ "<br />value = " + value;

		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province + _value.city + _value.district
			+ _value.street + _value.business;
		}
		str += "<br />ToItem<br />index = " + e.toitem.index
		+ "<br />value = " + value;
		G("searchResultPanel").innerHTML = str;
	})

	var myValue;
	ac.addEventListener("onconfirm", function(e,result) { //鼠标点击下拉列表后的事件
		var _value = e.item.value;
		position.city = _value.city;
		position.area = _value.district;
		position.street = _value.street;
		
		myValue = _value.province + _value.city + _value.district
		+ _value.street + _value.business;
		G("searchResultPanel").innerHTML = "onconfirm<br />index = "
			+ e.item.index + "<br />myValue = " + myValue;

		map.clearOverlays(); //清除地图上所有覆盖物
		function myFun() {
			var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMap.Marker(pp)); //添加标注
			
			position.lon = pp.lng;//经度 lon
			position.lat = pp.lat;//纬度
			
			var myGeo=new BMap.Geocoder();
			myGeo.getLocation(new BMap.Point(pp.lng,pp.lat),function(result){
				position.province = result.addressComponents.province;
				
				position.detAdd = position.province + position.city + position.area + _value.business;
			});
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
			onSearchComplete : myFun
		});
		local.search(myValue);
	})
 
}

/**
 * 查询地址
 */
function areaSearch(){
	layer.load(1, {shade: [0.8,'#333']});
	var myGeo = new BMap.Geocoder();
	
	map.clearOverlays();
	
	myGeo.getPoint($("#suggestId").val(), function(point){
		if (point) {
			map.centerAndZoom(point, 16);
			map.addOverlay(new BMap.Marker(point));
			
			myGeo.getLocation(point, function(rs){
				var result=rs.addressComponents;
				position.area=result.district;
				position.city=result.city;
				position.street=result.street;
				position.detAdd=rs.address;
				position.province=result.province;
				position.lat=point.lat;
				position.lon=point.lng;
				$("#suggestId").val(rs.address);
				
				layer.closeAll('loading');
				
				addNewAdd();
			});
		}else{
			layer.closeAll('loading');
			layer.msg("您选择地址没有解析到结果!");
		}
	},cityName);
}

/**
 * 添加新地址
 */
function addNewAdd(){
	var id = $(this).attr("data-id");
	if(id)position.id = id;
	layer.prompt({
	    formType: 0,
	    title: '请输入小区名'
	}, function(value, index, elem){
	    if(!value)return layer.msg("小区名不能为空");
	    position.addName = value;
	    $ajax({
			url:path+"salesman/adress/add.html",
			data:{data:JSON.stringify(position)},
			done:function(msg){
				if(!msg){
					if(!id)layer.msg("添加成功",{icon:1});
					else layer.msg("修改成功",{icon:1});
					
					$("#allmap , #add_address_query").hide();
					$("#two_con").show();
					layer.close(index);
					new setDataArea();
					clear();
					new setData(1,size);
				}
			}
		},1);
	});
}

/*function map(){
	map.addEventListener("click",function(e){
//		map.clearOverlays();
		var pp = e.point;
		console.log(pp);
//		map.centerAndZoom(pp, 18);
//		map.addOverlay(new BMap.Marker(pp));
//		var _value = e.item.value;
//		myValue = _value.province + _value.city + _value.district
//		+ _value.street + _value.business;
		//setPlace();
	})

	function clearAll() {
		for (var i = 0; i < overlays.length; i++) {
			map.removeOverlay(overlays[i]);
		}
		overlays.length = 0
	}
}*/