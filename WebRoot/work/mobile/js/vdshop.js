$(function(){
	var tab = $('.storeManage-seller-list .mainMenu li');
	$('.storeManage-seller-list .mainMenu li').on('click',function(){
		$(this).addClass('cur').siblings('li').removeClass('cur');
		var tabIndex = tab.index(this);
		$('.storeManage-goodsbox-list').eq(tabIndex).show().siblings('.storeManage-goodsbox-list').hide();
//		var subMenu = $('.storeManage-seller-list .subMenu');
//		if(tabIndex==2){
//			subMenu.hide();
//			$('.storeManage-fix-add').hide();
//			$('.newClassfy').show();
//		}else if(tabIndex==1){
//			subMenu.eq(1).show();
//			subMenu.eq(0).hide();
//			$('.storeManage-fix-add').show();
//			$('.newClassfy').hide();
//		}else if(tabIndex == 0){
//			subMenu.eq(1).hide();
//			subMenu.eq(0).show();
//			$('.storeManage-fix-add').show();
//			$('.newClassfy').hide();
//		}
	});
});