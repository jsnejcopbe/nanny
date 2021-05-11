;(function($){
	/** 
	 * 可扩展的验证 verify
	 *   email 邮箱
	 *   number 数字
	 *   range 范围
	 *   required 必须
	 *   
	 * @param $
	 */
	$.extend({
		required:function(str){
			if(str)return true;
			return false;
		},
		phone:function(str){// /^0?1[3|4|5|8][0-9]\d{8}$/
			var reg = /^(1)[0-9]{10}$/;
			if(reg.test(str))return true;
			return false;
		},
		email:function(str){
			var reg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(reg.test(str))return true;
			return false;
		},
		number:function(str){
			var reg = /^[+-]?\d+\.?\d{0,2}$/;
			if(reg.test(str))return true;
			return false;
		},
		range:function(str){
			return false;
		},
		qq:function(str){
			return true;
		}
	})
})(jQuery)