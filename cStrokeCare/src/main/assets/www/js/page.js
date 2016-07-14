$(document).bind('pageinit', function(){
    //tabpageHeight = $('.ui-page-active').height() - $('.ui-page-active').children('div[data-role=header]').height();
    //$('.tab-page').css('height', tabpageHeight);
	$.mobile.buttonMarkup.hoverDelay = 0;
    $('.tab-page').hide();
	$('.tab-container').find("div:first").show();



$(document).delegate('.ui-navbar ul li > a', 'click', function () {
    $(this).closest('.ui-navbar').find('a').removeClass('ui-btn-active');
    $(this).addClass('ui-btn-active');
    $('#' + $(this).attr('data-href')).show().siblings('.tab-page').hide();
    
	//$(this).closest('.ui-navbar').find('a').removeClass('ui-bar-c')
	//$(this).addClass('ui-bar-c');
});
});

function clickButton(){
	alert("unable");
}