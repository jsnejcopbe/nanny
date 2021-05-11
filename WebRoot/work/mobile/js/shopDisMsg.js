var DISCONHTML='<div class="clearfix con-item">\
					<div class="col-xs-2 item-left">\
						<img src="${headImg }">\
					</div>\
					<div class="col-xs-10 item-right">\
						<p>\
							<span>${nickName }</span>\
							<span>\
								${starList}\
							</span>\
						</p>\
						<div class="con-des">${con }</div>\
						<p class="con-time">${createTime }</p>\
					</div>\
				</div>';
var page=1;

/**
 * 分页
 */
function pagin(){
	page++;
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/store-dis-"+$("#shopID").val()+".json",
		"Data":"type=json&page="+page,
		"fnSuccess":function(rtnData){
			var array=rtnData.data;
			var html="";
			layer.closeAll('loading');
			for(var i=0;i<array.length;i++)
			{
				html+=DISCONHTML.replace("${headImg }", array[i].headImg)
								.replace("${nickName }", array[i].nickName)
								.replace("${starList}", scoreDefine(array[i].score))
								.replace("${con }", array[i].con==null?"":array[i].con)
								.replace("${createTime }", array[i].createTime);
			}
			$(".dis-con").append(html);
			if(array.length<$("#pageSize").val())
				$(".pagin").css("display","none");
		},
		"fnError"  :function(){layer.closeAll('loading');layer.msg("查询失败");}
	};
	new g_fnAjaxUpload(param);
}

/**
 * 评分判断
 */
function scoreDefine(score){
	var html="";
	for(var i=1;i<6;i++)
	{
		if(score=="null" || i>score)
			html+='<i class="iconfont-gl">&#xe650;</i>';
		else
			html+='<i class="iconfont-gl checked">&#xe650;</i>';
	}
	return html;
}