// JavaScript Document
(function($) {
	var printAreaCount = 0;
	$.fn.printArea = function() {
		var ele = $(this);
		var idPrefix = "printArea_";
		removePrintArea(idPrefix + printAreaCount);
		printAreaCount++;
		var iframeId = idPrefix + printAreaCount;
		var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;';
		iframe = document.createElement('IFRAME');
		$(iframe).attr({
			style : iframeStyle,
			id : iframeId
		});
		document.body.appendChild(iframe);
		var doc = iframe.contentWindow.document;
		$(document).find("link").filter(function() {
			return $(this).attr("rel").toLowerCase() == "stylesheet";
		}).each(
				function() {
					doc.write('<link type="text/css" rel="stylesheet" href="'
							+ $(this).attr("href") + '" >');
				});
		doc.write('<div class="' + $(ele).attr("class") + '" style="'+$(ele).attr("style")+'">' + $(ele).html()
				+ '</div>');
		doc.body.style.zoom=0.8;
		doc.close();
		
		setTimeout("print()", 2000);
	};
	var removePrintArea = function(id) {
		$("iframe#" + id).remove();
	};
	
	var print = function(){
		var frameWindow = iframe.contentWindow;
		frameWindow.focus();
		frameWindow.print();
		frameWindow.close();
	};
})(jQuery);