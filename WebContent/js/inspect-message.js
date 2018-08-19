$(document).ready(function() {

	$('#inspect-btn').click(function() {
		var message = $('#inspect-message').val();
		if (message.length < 1) {
			return;
		}

		disableBtn('#inspect-btn');
		inspect(message);
	});

	var inspect = function(message) {

		var jsondata = {
			"message" : message
		};

		$.ajax({
			url : '/inspect',
			data : JSON.stringify(jsondata),
			type : 'post',
			cache : false,
			complete : function(data) {
				if (status === 'error' || !data.responseText) {
					console.log(data);
					resetDeIdentifyButton();
				} else {
					var message = data.responseText;
					$('#message').val(message);
					resetDeIdentifyButton();
				}
			}
		});
	};

	function disableBtn(btn) {
		var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
		$(btn).html(loading);
		$(btn).attr('disabled', 'disabled');
	};

	function resetDeIdentifyButton(button) {
		$('#inspect-btn').html('Inspect');
		$('#inspect-btn').removeAttr('disabled');
	}
	;

});