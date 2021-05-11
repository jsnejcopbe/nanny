$(function(){
	countInit();
	new g_fnImgCheck();
	$("#proCarList").val(JSON.stringify(shopCarPro));
	$(".btn-add").on("click",function(){proCount("+");});
	$(".btn-del").on("click",function(){proCount("-");});
	$("#p_go").on("click",function(){$("#storeJumpForm").submit();});
});

/**
 * 商品加减
 * @param type
 */
function proCount(type)
{
	var nowCount=$(".con-cou").val();
	if(type=="-"){
		if(nowCount>0)
			$(".con-cou").val(nowCount-1);
	}else
		$(".con-cou").val(parseInt(nowCount)+parseInt(1));
}

/**
 * 添加到购物车
 */
function updateshop(){
	createData();
	$("#proCarList").val(JSON.stringify(shopCarPro));
	$("#storeJumpForm").submit();
}

/**
 * 数量初始化
 */
function countInit(){
	for(var i=0;i<shopCarPro.length;i++)
	{
		if(shopCarPro[i].proID==$("#proID").val() && !shopCarPro[i].hasOwnProperty("ise")){
			$(".con-cou").val(shopCarPro[i].count);
			break;
		}
	}
}

/**
 * 组织数据
 */
function createData(){
	var price=$("#product_price").text();
	var proData=new Object();
	proData.proID=$("#proID").val();
	proData.price=$.trim(price.replace("￥",""));
	proData.count=$(".con-cou").val();
	
	for(var i=0;i<shopCarPro.length;i++)
	{
		if(shopCarPro[i].proID==$("#proID").val() && !shopCarPro[i].hasOwnProperty("ise"))
		{
			shopCarPro.remove(i);
			break;
		}
	}
	
	if(proData.count>0)
		shopCarPro.push(proData);
}