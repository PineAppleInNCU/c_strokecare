;(function ($) {
				var checkboxes = $('input[type=checkbox]');
				var submitButt = $('#submitButton');
				submitButt.addClass('ui-disabled');

				$(document).on('click', 'input:checkbox', function(event) {
						if($(this).is(":checked")) {
								$('#submitButton').removeClass('ui-disabled');
						} else {
								$('#submitButton').addClass('ui-disabled');
						}
				});

				$(document).on('click', '#checkall', function(event) {
						console.log('lala');
						$('input:checkbox').prop('checked', true);
						$('input:checkbox').checkboxradio('refresh');

						if($('input:checkbox').length !== 0) {
								$('#submitButton').removeClass('ui-disabled');
						}
				});

				$(document).on('click', '#uncheckall', function(event) {
						$('input[type=checkbox]').prop('checked', false);
						$('input[type=checkbox]').checkboxradio('refresh');

						$('#submitButton').addClass('ui-disabled');
				});
})(jQuery);

