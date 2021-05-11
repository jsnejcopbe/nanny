// Author: Vijay Kumar
// Template: Cascade - Flat & Responsive Bootstrap Admin Template
// Version: 1.0
// Bootstrap version: 3.0.0
// Copyright 2013 bootstrapguru
// www: http://bootstrapguru.com
// mail: support@bootstrapguru.com
// You can find our other themes on: https://bootstrapguru.com/themes/



// jQuery $('document').ready(); function 
$('document').ready(function() {
	$('.btn-nav-toggle-responsive').click(function() {
        $('.left-sidebar').toggleClass('show-fullsidebar');
    });
	
	selectDefine();
	
	// Sidebar dropdown
    $('ul.nav-list').accordion();

    $('.settings-toggle').click(function(e) {
        e.preventDefault();
        $('.right-sidebar').toggleClass('right-sidebar-hidden');
    });

    $('.left-sidebar .nav > li > ul > li.active').parent().css('display', 'block');

    $('.left-sidebar .nav > li a span').hover(function() {
        var icon = $(this).parent().find('i');
        icon.removeClass('animated shake').addClass('animated shake');
        var wait = window.setTimeout(function() {
                icon.removeClass('animated shake');

            },
            1300
        );
    });
	
	$('li.nav-toggle > button').click(function(e) {
        e.preventDefault();
        $('.hidden-minibar').toggleClass("hide");
        $('.site-holder').toggleClass("mini-sidebar");
        if ($('.toggle-left').hasClass('fa-angle-double-left')) {
            $('.toggle-left').removeClass('fa-angle-double-left').addClass('fa-angle-double-right');
        } else {
            $('.toggle-left').removeClass('fa-angle-double-right').addClass('fa-angle-double-left');
        }


        if ($('.site-holder').hasClass('mini-sidebar')) {
            $('.sidebar-holder').tooltip({
                selector: "a",
                container: "body",
                placement: "right"
            });
            $('li.submenu ul').tooltip('destroy');
        } else {
            $('.sidebar-holder').tooltip('destroy');
        }
    });
});

function selectDefine(){
	$('ul.nav-list li a').each(function(){
			if($($(this))[0].href==String(window.location)) {
				
				$(this).parent().addClass('active');
				
			}
	
	});

	$('ul.nav-list li ul li a').each(function(){
		
			if($($(this))[0].href==String(window.location)) {
				
				$(this).parent().addClass('active');
				
			}
	
	});
}