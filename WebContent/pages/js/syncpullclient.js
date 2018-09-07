/*
 * Handles refresh icon show/hide on button.
 * 
 * */

$(document).ready(function() {

	$('#pull-form').submit(function(e) {
		var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
		$('#pull-msg-btn').html(loading);
		$('#pull-msg-btn').attr('disabled', 'disabled');
	});

});
