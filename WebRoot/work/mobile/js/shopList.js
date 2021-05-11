var html='<div class="row shop-item">\
			<a href="${path}/store-${id }.html">\
				<div class="col-xs-3 item-logo">\
					<img src="${icon }">\
				</div>\
				<div class="col-xs-9 item-con">\
					<p class="con-name">${name }</p>\
					<p class="con-tel">${tel }</p>\
					<p class="con-add">${detAdd }</p>\
				</div>\
			</a>\
		  </div>';
var page=1;


function conPagin(){
	page=page+1;
	layer.load(1, {shade: [0.8,'#333']});
	var param={
		"sURL":BASEPATH+"/shoplist.json",
		"Data":"type=json&page="+page,
		"fnSuccess":function(data){
			layer.closeAll('loading');
			for(var i=0;i<data.length;i++)
			{
				var appendHtml= html.replace("${path}", BASEPATH)
									.replace("${id }",data[i].id)
									.replace("${icon }",data[i].shop_icon)
									.replace("${name }",data[i].shop_name)
									.replace("${tel }",data[i].tel)
									.replace("${detAdd }",data[i].detAdd);
				$(".js-showmore").before(appendHtml);
			}
			if(data.length<20){
				layer.msg("已是最后一条");
				$(".js-showmore").css("display","none");
			}
		},
		"fnError":function(){
			layer.closeAll('loading');
			layer.msg("查询失败");
		}
	};
	new g_fnAjaxUpload(param);
}