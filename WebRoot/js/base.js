var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); };
	}
};

/**
 * Ajax封装
 * @param sURL
 * @param Data
 * @param fnSuccess
 * @param fnError
 * @returns
 */
var g_fnAjaxUpload=function(oParam){
	var async= oParam.async==null?true:oParam.async;
	$.ajax({
		type : "POST",
		url  : oParam.sURL,
		data : oParam.Data,
		async : async,
		success : function(rtnData){			
			var data=eval('(' + rtnData + ')');
			oParam.fnSuccess(data);
		},
		error   : function(){
			if(oParam.fnError!=null)
				oParam.fnError();
			else
				$.flytip("上传失败");
		}
	});
};

/**
 * 文件上传封装
 * @param sURL
 * @param sID
 * @param sContentType
 * @param fnSuccess
 * @param fnError
 * @returns
 */
var g_fnFileUpload=function(oParam){
	$.ajaxFileUpload({
		url : oParam.sURL,
		secureuri:false,
		fileElementId:oParam.sID,
		data:{fileName:$("#"+oParam.sID).val(),id:'id',contentType:oParam.sContentType},
		success: function (data, status){
			var json=eval("(" + data.body.textContent + ")");
			oParam.fnSuccess(json);
		},
		error  : function(data, status, e){		
			if(fnError!=null)
				oParam.fnError(data, status, e);
			else
				$.flytip("上传失败");
		}
	});
};

/**
 * 分页封装
 */
var Pagin=Class.create();
Pagin.prototype={
	initialize:function(option){		
		this.setOption(option);
		Pagin.prototype.dealFuction=this.option.dealFun;
		if(this.option.nowPage==1)
			this.init();
		else
			this.selectPage();		
	},
	setOption:function(option){
		if(option!=null)
			this.option=option; 
		else
			option={
				size : 10,
				perPage:5,
				total:1,
				nowPage:1,
				dealFun:function(){
					alert("请设置处理函数");  
					return;
				}};
	},
	init:function(){
		var totalPage=Math.ceil((this.option.total)/(this.option.size));//计算总页数
		var html="<ul class='pagination pagination-sm'><li class='disabled'><a href='javascript:void(0)'>上一页</a></li>" +
				 "<li class='active'><a href='javascript:void(0)'>1</a></li>";
		for(var i=2;i<=totalPage;i++)
		{	
			if(i>5){
				html=html+"<li><a href='javascript:void(0)' class='js-pagenum' id='lastpage' name='6'" +
						  " onclick='Pagin.prototype.dealFuction("+this.option.size+", 6)'>...</a></li>";
				break;
			}				
			else
				html=html+"<li><a href='javascript:void(0)' class='js-pagenum' " +
						  " onclick='Pagin.prototype.dealFuction("+this.option.size+", "+i+")'>"+i+"</a></li>";
		}
		if(totalPage>1)
			html=html+"<li><a href='javascript:void(0)' onclick='Pagin.prototype.dealFuction("+this.option.size+", 2)'>下一页  </a></li></ul>";
		else
			html=html+"<li class='disabled'><a href='javascript:void(0)'>下一页  </a></li></ul>";
		$(".js-pagin").html(html);		
	},
	selectPage:function(){
		var firstPage=parseInt(this.option.nowPage/this.option.perPage);
		var totalPage=Math.ceil((this.option.total)/(this.option.size));//计算总页数
		var nextPage=parseInt(this.option.nowPage)+parseInt(1);
		var html;
		if(this.option.nowPage==1)//是否为第一页
			html="<ul class='pagination pagination-sm'><li class='disabled'><a href='javascript:void(0)'>上一页</a></li>" ;  
		else
			html="<ul class='pagination pagination-sm'><li><a href='javascript:void(0)' onclick='Pagin.prototype.dealFuction("+this.option.size+", "+(this.option.nowPage - 1)+")'>上一页</a></li>" ;
		for(var i=parseInt(firstPage*5);i<=totalPage;i++)
		{	
			if(i==0)
				continue;
			if(i>(parseInt(firstPage*5)+parseInt(5))){
				html=html+"<li><a href='javascript:void(0)' class='js-pagenum' id='lastpage' name='"+(parseInt(firstPage*5)+parseInt(6))+"' " +
						  "onclick='Pagin.prototype.dealFuction("+this.option.size+", "+(parseInt(firstPage*5)+parseInt(6))+")'>...</a></li>";
				break;
			}				
			else{
				if(i==this.option.nowPage)
					html=html+"<li class='active'><a href='javascript:void(0)' class='js-pagenum' " +
					  "onclick='Pagin.prototype.dealFuction("+this.option.size+", "+i+")'>"+i+"</a></li>";
				else
					html=html+"<li><a href='javascript:void(0)' class='js-pagenum' " +
					  "onclick='Pagin.prototype.dealFuction("+this.option.size+", "+i+")'>"+i+"</a></li>";
			}				
		}
		if(this.option.nowPage==totalPage)//是否为最后一页
			html=html+"<li class='disabled'><a href='javascript:void(0)'>下一页  </a></li></ul>";
		else
			html=html+"<li><a href='javascript:void(0)' onclick='Pagin.prototype.dealFuction("+this.option.size+", "+nextPage+")'>下一页  </a></li></ul>";
		$(".js-pagin").html(html);
	},
	dealFuction:function(size,page){
		alert("原型");
	}
};

/**
 * 日期处理函数集合
 */
var g_fnTimeFunction={
	dateCompare:function(startdate,enddate){
		//比较日期大小
		var arr=startdate.split("-"); 
		var starttime=new Date(arr[0],arr[1],arr[2]);
		var starttimes=starttime.getTime(); 
		var arrs=enddate.split("-");  
		var lktime=new Date(arrs[0],arrs[1],arrs[2]); 
		var lktimes=lktime.getTime();  
		if(starttimes>=lktimes)  
			return false;
		else
			return true;
	},
	dateValidform:function(startdate,enddate){
		//日期验证
		if(!this.dateCompare(startdate, enddate))
			return msg="起始日期不能大于结束日期";		
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=parseInt(myDate.getMonth())+parseInt(1);
		var day=myDate.getDate();
		var nowDate=year+"-"+month+"-"+day;
		if(!this.dateCompare(startdate, nowDate))
			return msg="起始日期不能大于当前日期";
	},
	getNowDate:function(){
		var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + date.getSeconds();
	    return currentdate;
	}
};

/**
 * 验证方法集合
 */
var Validform=Class.create();
Validform.prototype={
	initialize:function(option){
		//构造方法
		this.setOption(option);
//		this.validFun();
	},
	regBox:{
        regEmail : /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,//邮箱
        regName : /^[a-z0-9_-]{3,16}$/,//用户名
        regMobile : /^0?1[3|4|5|7|8][0-9]\d{8}$/,//手机
        regTel : /^0[\d]{2,3}-[\d]{7,8}$/,
        regNum : /^[0-9]*$/ 
    },
    setOption:function(option){
    	if(option!=null)
    		this.option=option;
    	else
    		this.option={
    			validList:[],
    	    	checkID:null,
    	    	errorMsg:"错误",
    	    	defineType:"and"
    	 };
    },
    isNum:function(){
    	if(!this.regBox.regNum.test($(this.option.checkID).val()))
			return false;
		else
			return true;
    },
	isTel:function(){
		if(!this.regBox.regMobile.test($(this.option.checkID).val()))
			return false;
		else
			return true;
	},
	isMail:function(){
		if(!this.regBox.regEmail.test($(this.option.checkID).val()))
			return false;
		else
			return true;
	},
	isName:function(){
		if(!this.regBox.regName.test($(this.option.checkID).val()))
			return false;
		else
			return true;
	},
	isNotNull:function(){
		if($(this.option.checkID).val()=="" || $(this.option.checkID).val()==null)
			return false;
		else
			return true;
	},
	isNotZero:function(){
		if($(this.option.checkID).val()==0)
			return false;
		else
			return true;
	},
	isTooLong:function(){
		var maxLength=$(this.option.checkID).attr("data-length");
		var con=$(this.option.checkID).val();
		if(con.length>maxLength)
			return false;
		else
			return true;
	},
	validFun:function(){
		var checkList=this.option.validList;
		var resultAll=true;
		//验证函数
		for(var i=0;i<checkList.length;i++)
		{
			var resultSingle=eval("this."+checkList[i]+"()");
			if(i==0)
				resultAll=resultSingle;
			else{
				if(this.option.defineType=="and" || this.option.defineType==null)
					resultAll=resultAll & resultSingle;
				else
					resultAll=resultAll || resultSingle;
			}			
		}
		if(resultAll==false)
		{
			layer.tips(this.option.errorMsg, this.option.checkID,{
				tips: [1, '#3595CC'],
				time: 4000,
				tipsMore: true
			});
//			$("#"+this.option.checkID).focus();
			return false;
		}else
			return true;
	}
};


/**
 * 保留小数点后二位
 * @param floatvar
 * @returns
 */
var changeTwoDecimal= function(floatvar)
{
	var f_x = parseFloat(floatvar);
	if (isNaN(f_x))
	{
		alert('function:changeTwoDecimal->parameter error');
		return false;
	}
	var f_x = Math.round(floatvar*100)/100;
	return f_x;
};

/**
 * 图片加载完成处理
 * @param delFun
 * @returns
 */
var g_fnImgCheck=function(delFun){
	//图片加载完成处理
	$('img').imagesLoaded(function() {
		for(var i=0;i<this.length;i++)
			if ((typeof this[i].naturalWidth != "undefined" && this[i].naturalWidth == 0) || !this[i].complete)
				this[i].src = "/nanny/images/defalut.jpg";
		if(delFun!=null)
			delFun.call();
   });
};

/**
 * 数组删除
 * @param dx
 * @returns {Boolean}
 */
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i];
        }
    }
    this.length-=1;
};

var g_WifiLocation=(function(){
	return{
		main:function(callBackFun){
			if(callBackFun!=null)
				this._callBack=callBackFun;
			
			var ua = window.navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i) == 'micromessenger'){
				//微信的地理位置信息
				wx.getLocation({
				    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				    success: function (res) {
				    	
				    	var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				        var speed = res.speed; // 速度，以米/每秒计
				        var accuracy = res.accuracy; // 位置精度
				   
				    	g_WifiLocation._callBack(latitude+","+longitude);
				    }
				});
			} else if (navigator.geolocation){
				navigator.geolocation.getCurrentPosition(this._fnSuccess,this._fnError,{enableHighAccuracy: true,maximumAge:1000});
			}else{
				layer.msg("无法获得授权,请手动输入地址");
				this._callBack("");
			}
		},
		_fnSuccess:function(position){
			g_WifiLocation._callBack(position.coords.latitude+","+position.coords.longitude);
		},
		_fnError:function(err){
			layer.msg("无法定位,请手动输入地址");
			g_WifiLocation._callBack("");
		},
		_callBack:function(str){
			return str;
		}
	};
}());