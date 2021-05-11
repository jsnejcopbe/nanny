// Call this from the developer console and you can control both instances
var calendars = {};
var classList=["bg-gr","bg-bl","bg-mr","bg-pi","bg-lp","bg-lg","bg-dg"];
moment.locale("zh-cn");//时间类库本地化
var currentMonth = moment().format('YYYY-MM');
var nextMonth    = moment().add('month', 1).format('YYYY-MM');

var HTML='<div class="clndr-controls">\
		    <div class="clndr-previous-button">&lt;</div>\
		    <div class="clndr-next-button">&gt;</div>\
		    <div class="current-month"><%= year %> <%= month %></div>\
		  </div>\
		  <div class="clndr-grid">\
		    <div class="days-of-the-week clearfix">\
		      <% _.each(daysOfTheWeek, function(day) { %>\
		        <div class="header-day"><%= day %></div>\
		      <% }); %>\
		    </div>\
		    <div class="days">\
		      <% _.each(days, function(day) { %>\
		        <div class="<%= day.classes %>" id="<%= day.id %>"><span class="day-number"><%= day.day %></span></div>\
		      <% }); %>\
		    </div>\
		  </div>\
		  <div class="event-listing">\
		    <div class="block-item block-years">\
				<p class="item-time"><span id="year"></span>年</p>\
		      <p class="item-data">总营业额：<span id="yearmo"></span></p>\
			  </div>\
		    <div class="block-item block-month">\
				<p class="item-time"><span id="month"></span>月</p>\
		      <p class="item-data"><span>月订单数：<span id="moor"></span></span>&nbsp;&nbsp;<span>月营业额：<span id="momo"></span></span></p>\
			  </div>\
			  <div class="block-item block-day">\
				<p class="item-time"><span id="day"></span>日</p>\
		      <p class="item-data"><span>日订单数：<span id="dayor"></span></span>&nbsp;&nbsp;<span>日营业额：<span id="daymo"></span></span></p>\
			  </div>\
		  </div>'

	
$(document).ready( function() {
	

    var thisMonth = moment().format('YYYY-MM');
   
    
    calendars.clndr1 = $('.cal1').clndr({
        template: HTML,
        clickEvents: {
            click: function (target) {
            	if((target.element.className).indexOf("adjacent-month")==-1){
//            		var className=$(".days .today").attr("class");
            	
            		$(".days div").removeClass("today");
//            		if(className!=null && className!="")
//            			target.element.className=className;
//            		else
//            			target.element.className+=" today";
            		
            		target.element.className+=" today";
               
            		update(target.date._i);
            		}
            },
            today: function () {
                console.log('Cal-1 today');
            },
            nextMonth: function () {
            	setTableClass();
            },
            previousMonth: function () {
            	setTableClass();
            },
            onMonthChange: function () {
            	setTableClass();
            },
            nextYear: function () {
                console.log('Cal-1 next year');
            },
            previousYear: function () {
                console.log('Cal-1 previous year');
            },
            onYearChange: function () {
                console.log('Cal-1 year changed');
            },
            nextInterval: function () {
                console.log('Cal-1 next interval');
            },
            previousInterval: function () {
                console.log('Cal-1 previous interval');
            },
            onIntervalChange: function () {
                console.log('Cal-1 interval changed');
            }
        },
        showAdjacentMonths: true,
        adjacentDaysChangeMonth: false,
        forceSixRows: true
    });
    update(datee);
    // Bind all clndrs to the left and right arrow keys
    $(document).keydown( function(e) {
        // Left arrow
        if (e.keyCode == 37) {
            calendars.clndr1.back();
            calendars.clndr2.back();
            calendars.clndr3.back();
        }

        // Right arrow
        if (e.keyCode == 39) {
            calendars.clndr1.forward();
            calendars.clndr2.forward();
            calendars.clndr3.forward();
        }
    });
    
    setTableClass();
});

function setTableClass(){
	var i=0;
	$(".header-day").each(function(){
		$(this).addClass(classList[i]);
		i++;
	});
}



function  update(date,op) {
	var html1="";

    $.ajax({
        type: "post",
        url: "/nanny/shop/DateSurve.html",
        data:"datetime="+date+"&op="+op,
        dataType: "text",
        async:true,  
        success: function (msg) {
        	var data=eval('(' + msg + ')');
        	 
        	//alert("日："+data.dayor+","+data.daymo+";月："+data.moor+","+data.momo+";年:"+data.yearmo);	
        	/*html1+=HTML.replace("${dayor}", data.dayor)
        			  .replace("${daymo}", data.daymo)
        			  .replace("${moor}", data.moor)
        			  .replace("${momo}", data.momo)
        			  .replace("${yearmo}", data.yearmo)
        			  .replace("${year}", data.year)
        			  .replace("${month}", data.month)
        			  .replace("${day}", data.day);*/
        	$("#daymo").text(data.daymo);
        	$("#dayor").text(data.dayor);
        	$("#moor").text(data.moor);
        	$("#momo").text(data.momo);
        	$("#yearmo").text(data.yearmo);
        	$("#year").text(data.year);
        	$("#month").text(data.month);
        	$("#day").text(data.day);
        },
        error: function () {
            layer.msg('操作有误!');
        }
    });
    
    }