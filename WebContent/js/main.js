$(document).ready(function(){

	// Load topics into select list
	(function getTopics(){
		$.get('/api/topic/list', function(data){
			if(data.topics.length>1){
				var allOption = '<option value='+data.topics+'>All</option>';
				$('#topic-name-select').append(allOption);
			}

			$(data.topics).each(function(index, element){
				console.log(element);
				var option = '<option value= "'+element+'" >' +element+'</option>';
				$('#topic-name-select').append(option);
			});
		});
	})();
	// -------------------------------
	
	$('#publish-form').submit(function(e){
		
		console.log("Submitting form");
		var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
			$('#publish-btn').html(loading);
			$('#publish-btn').attr('disabled', 'disabled');
	});

	
	function disableBtn(btn) {
		var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
		$(btn).html(loading);
		$(btn).attr('disabled', 'disabled');
	};
	
});