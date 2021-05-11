var endIndex;
$(function(){
	$(".che-btn").on("click",function(){
		var className=$(this).attr("class");
		var id=$(this).attr("id");
		if(className.indexOf("btn-checked")!=-1){
			$("#"+id).removeClass("btn-checked");
			$("#"+id+" i").attr("class","icon-check-empty");
		}else{
			$("#"+id).addClass("btn-checked");
			$("#"+id+" i").attr("class","icon-check");
		}
	});
	
	$(".inv-inp").on("input propertychange",function(){
		var value=$(this).val();
		var parent=$(this).parents(".con-item");
		if(($("#js-inc").attr("class")).indexOf("btn-checked")!=-1)
		{
			endIndex=parent.attr("data-index");
			autoInput(".inv-inp",value);
		}	
	});
	
	$(".pri-inp").on("input propertychange",function(){
		var value=$(this).val();
		var parent=$(this).parents(".con-item");
		if(($("#js-pri").attr("class")).indexOf("btn-checked")!=-1)
		{
			endIndex=parent.attr("data-index");
			autoInput(".pri-inp",value);
		}	
	});
});

/**
 * 自动填充
 * @param selector
 * @param value
 * @returns
 */
function autoInput(selector,value){
	$(selector).each(function(){
		var nowIndex=$(this).parents(".con-item").attr("data-index");
		if(nowIndex>endIndex)
			$(this).val(value);
	});
}

/**
 * 表单提交
 */
function upForm(){
	var data=dataCheck($("#proEditForm").serializeJson());
	if(formCheck()==true){
		layer.load(1, {shade: [0.8,'#333']});
		$.ajax({
			type:'post',
			url:BASEPATH+'/product/batch_inven.html',
			data:{data:JSON.stringify(data)},
			success:function(data){
				layer.closeAll('loading');
				layer.msg("修改成功");
				setTimeout('location.href=BASEPATH+"/shop/productList.html"', 1000);
			},
			error  : function(){layer.closeAll('loading');layer.msg("更新失败");}
		});
	}
}

/**
 * 表单检查
 */
function formCheck(){
	var error=0;
	$(".con-item").each(function(){
		var index=$(this).attr("data-index");
		var invValue=$(this).find(".inv-inp").val();
		var priValue=$(this).find(".pri-inp").val();
		if(invValue==""){
			layer.tips("请输入库存数量", $(".con-item[data-index='"+index+"'] .inv-inp"),{
				tips: [1, '#3595CC'],
				time: 4000,
				tipsMore: true
			});
			error++;
		}
		if(priValue=="")
		{
			layer.tips("请输入库存数量", $(".con-item[data-index='"+index+"'] .pri-inp"),{
				tips: [1, '#3595CC'],
				time: 4000,
				tipsMore: true
			});
			error++;
		}
	});
	
	if(error>0)
		return false;
	else
		return true;
}

/**
 * 数据检查
 * @param data
 * @returns
 */
function dataCheck(data){
	var aa=data.ids;
	var bb=aa.length;
	if(!((data.ids) instanceof Array))
	{
		data.ids=[data.ids];
		data.inventorys=[data.inventorys];
		data.proPrice=[data.proPrice];
		return data;
	}else 
		return data;
}