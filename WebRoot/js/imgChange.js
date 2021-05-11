(function($) {
	$.fn.extend({
				// 获得图片信息
				getImgmsg : function(src) {

					// 打印隐藏的DIV
					$("body")
							.append(
									'<div class="imgin"  style="display:none;position:fixed;width:100%;height:100%;background:#000;z-index:2;top:0;left:0;opacity:0.7!important;">\
							</div>\
							<div class="imgout" style="display:none;position:fixed;top:30%;margin-top:-150px;background:#000;z-index:3;left:25%;max-width: 50%;">\
							<img src='+ src + ' style="max-width: 100%;">\
							</div>');

					$('.imgin').css("display","block");
					$('.imgout').css("display","block");

					$('.imgin').click(function() {
						$(".imgout").remove();
						$(".imgin").remove();
					

					});
					
					
				},

				// 插件名称 - paddingList 将可选择的变量传递给方法
				imgChange : function(options) {

					// 参数和默认值//设置默认值并用逗号隔开
					var defaults = {
						width : 50,
						height : 50
					};

					var options = $.extend(defaults, options);
					// 遍历匹配元素的集合
					return this.each(function() {

						var o = options;

						// 将元素集合赋给变量 本例中是 ul对象
						var obj = $(this);

						// 得到ul中的img对象
						var items = $("img", obj);
						
						// 添加click()事件到items对应的图像
						$("body").on("click",".content img",function(){
							var width = $(this).width();
							var src = $(this).attr("src");
							
							if (width != o.width) {
								
								$(this).getImgmsg(src);
								
							}
						});
//						items.click(
//
//						function() {
//
//						});

					});
				}
			});
})(jQuery);
