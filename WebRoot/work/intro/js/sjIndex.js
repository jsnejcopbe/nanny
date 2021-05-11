/* 
* @Author: Administrator
* @Date:   2016-03-09 17:30:21
* @Last Modified by:   Administrator
* @Last Modified time: 2016-03-10 17:02:11
*/

'use strict';
$(function(){
	// 置顶
	$( '.top').click(function(e) { 
		$('html,body').animate({scrollTop:0},500)
	});
	/*----------------特效-------------*/
	$('a[href*=#]').click(function() {  
			if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
				var $target = $(this.hash);  
				$target = $target.length && $target || $('[name=' + this.hash.slice(1) + ']');  
				if ($target.length) {
					var targetOffset = $target.offset().top;
					$('html,body').animate({
						scrollTop: targetOffset
					},1000);
					return false;
				}
			}
		});
})