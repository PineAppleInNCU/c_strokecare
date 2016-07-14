var theNewScript = document.createElement("script");
theNewScript.type = "text/javascript";
theNewScript.src = "js/jquery-1.8.2.min.js";
document.getElementsByTagName("head")[0].appendChild(theNewScript);
// jQuery MAY OR MAY NOT be loaded at this stage

$(document).on('vmousedown', '#login_btn', function () {
	$('#login_img').attr('src','img/u12_mouseDown.png');
});

$(document).on('vmouseup', '#login_btn', function () {
	$('#login_img').attr('src','img/u12.png');
});

$(document).on('vmousecancel', '#login_btn', function () {
	$('#login_img').attr('src','img/u12.png');
});
  
$(document).on('vmousedown', '#ready_img', function () {
	$('#ready_img').attr('src','img/ready_mouseDown.png');
});

$(document).on('vmouseup', '#ready_img', function () {
	$('#ready_img').attr('src','img/ready.png');
});

$(document).on('vmousecancel', '#ready_img', function () {
	$('#ready_img').attr('src','img/ready.png');
});


