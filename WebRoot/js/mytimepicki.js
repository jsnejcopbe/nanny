$(".timepicki-input").keydown( function(keyevent){
					var len = $(this).val().length;

					// Allow: backspace, delete, tab, escape, enter and .
					if ($.inArray(keyevent.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
					     // Allow: Ctrl+A
					    (keyevent.keyCode == 65 && keyevent.ctrlKey === true) || 
					     // Allow: home, end, left, right
					    (keyevent.keyCode >= 35 && keyevent.keyCode <= 39)) {
						 // let it happen, don't do anything
						 return false;
					}
					// Ensure that it is a number and stop the keypress
					if ((keyevent.shiftKey || (keyevent.keyCode < 48 || keyevent.keyCode > 57)) && 
					(keyevent.keyCode < 96 || keyevent.keyCode > 105) || len==2 ) {
					   // keyevent.preventDefault();
					    return false;
					}

		});

var settings = {
		format_output: function(tim, mini, meri) {
			if(settings.show_meridian){
				return tim + ":" + mini + " " + meri;
			}else{
				return tim + ":" + mini;
			}
		},
		increase_direction: 'down',
		custom_classes: '',
		min_hour_value: 1,
		max_hour_value: 24,
		show_meridian: true,
		step_size_hours: '1',
		step_size_minutes: '1',
		overflow_minutes: false,
		disable_keyboard_mobile: false,
		reset: false
	};

function change_time(cur_ele, direction) {
	var cur_cli = "time";
	var cur_time = Number(ele_next.find("." + cur_cli + " .ti_tx input").val());
	var ele_st = Number(settings.min_hour_value);
	var ele_en = Number(settings.max_hour_value);
	var step_size = Number(settings.step_size_hours);
	if ((cur_ele && cur_ele.hasClass('action-next')) || direction === 'next') {
		if (cur_time + step_size > ele_en) {
			var min_value = ele_st;
			if (min_value < 10) {
				min_value = '0' + min_value;
			} else {
				min_value = String(min_value);
			}
			ele_next.find("." + cur_cli + " .ti_tx input").val(min_value);
		} else {
			cur_time = cur_time + step_size;
			if (cur_time < 10) {
				cur_time = "0" + cur_time;
			}
			ele_next.find("." + cur_cli + " .ti_tx input").val(cur_time);
		}
	} else if ((cur_ele && cur_ele.hasClass('action-prev')) || direction === 'prev') {
		if (cur_time - step_size <= 0) {
			var max_value = ele_en;
			if (max_value < 10) {
				max_value = '0' + max_value;
			} else {
				max_value = String(max_value);
			}
			ele_next.find("." + cur_cli + " .ti_tx input").val(max_value);
		} else {
			cur_time = cur_time - step_size;
			if (cur_time < 10) {
				cur_time = "0" + cur_time;
			}
			ele_next.find("." + cur_cli + " .ti_tx input").val(cur_time);
		}
	}
}

function change_mins(cur_ele, direction) {
	var cur_cli = "mins";
	var cur_mins = Number(ele_next.find("." + cur_cli + " .mi_tx input").val());
	var ele_st = 0;
	var ele_en = 59;
	var step_size = Number(settings.step_size_minutes);
	if ((cur_ele && cur_ele.hasClass('action-next')) || direction === 'next') {
		if (cur_mins + step_size > ele_en) {
			ele_next.find("." + cur_cli + " .mi_tx input").val("00");
			if(settings.overflow_minutes){
				change_time(null, 'next');
			}
		} else {
			cur_mins = cur_mins + step_size;
			if (cur_mins < 10) {
				ele_next.find("." + cur_cli + " .mi_tx input").val("0" + cur_mins);
			} else {
				ele_next.find("." + cur_cli + " .mi_tx input").val(cur_mins);
			}
		}
	} else if ((cur_ele && cur_ele.hasClass('action-prev')) || direction === 'prev') {
		if (cur_mins - step_size <= -1) {
			ele_next.find("." + cur_cli + " .mi_tx input").val(ele_en + 1 - step_size);
			if(settings.overflow_minutes){
				change_time(null, 'prev');
			}
		} else {
			cur_mins = cur_mins - step_size;
			if (cur_mins < 10) {
				ele_next.find("." + cur_cli + " .mi_tx input").val("0" + cur_mins);
			} else {
				ele_next.find("." + cur_cli + " .mi_tx input").val(cur_mins);
			}
		}
	}
}

function change_meri(cur_ele, direction) {
	var cur_cli = "meridian";
	var ele_st = 0;
	var ele_en = 1;
	var cur_mer = null;
	cur_mer = ele_next.find("." + cur_cli + " .mer_tx input").val();
	if ((cur_ele && cur_ele.hasClass('action-next')) || direction === 'next') {
		if (cur_mer == "上午") {
			ele_next.find("." + cur_cli + " .mer_tx input").val("下午");
		} else {
			ele_next.find("." + cur_cli + " .mer_tx input").val("上午");
		}
	} else if ((cur_ele && cur_ele.hasClass('action-prev')) || direction === 'prev') {
		if (cur_mer == "上午") {
			ele_next.find("." + cur_cli + " .mer_tx input").val("下午");
		} else {
			ele_next.find("." + cur_cli + " .mer_tx input").val("上午");
		}
	}
}


var ele_next = $(".layui-layer");

//handle clicking on the arrow icons
var cur_next = ele_next.find(".action-next");
var cur_prev = ele_next.find(".action-prev");

$(".action-prev,.action-next").unbind();
$(".action-prev,.action-next").click(function(){
	var cur_ele = $(this);
	if (cur_ele.parent().attr("class") == "time") {
		change_time(cur_ele);
	} else if (cur_ele.parent().attr("class") == "mins") {
		change_mins(cur_ele);
	} else {
		if(settings.show_meridian){
			change_meri(cur_ele);
		}
	}
})
 