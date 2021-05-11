

$( function(){update(datee)})


function calculation(op) {
	var month=$("#month").text();
	var days=$("#day").text();
	var years=$("#year").text();
	var date=years+"-"+month+"-"+days;
	
	 update(date,op);
}



function  update(date,op) {
	
    $.ajax({
        type: "post",
        url: "/nanny/shop/DateSurve.html",
        data:"datetime="+date+"&op="+op,
        dataType: "text",
        async:true,  
        success: function (msg) {
        	var data=eval('(' + msg + ')');
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