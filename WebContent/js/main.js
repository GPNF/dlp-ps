$(document).ready(function(){
	
	var counter=0;
	$('#inspect-message').click(function(){
		startCounting();
	});
	
	
	// Load topics into select list
	(function getTopics(){
		$.get('/topic/list', function(data){
			$('#topic-loading').remove();
			$(data.topics).each(function(index, element){
				console.log(element);
				var option = '<option value='+element+'>' +element+'</option>';
				$('#topic-name-select').append(option);
			});
		});
	})();
	// -------------------------------
	
	$('#publish-form').submit(function(){
		var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
			$('#publish-btn').html(loading);
			$('#publish-btn').attr('disabled', 'disabled');
	});
	
	var populateText = function(){
		
		$.get('https://randomuser.me/api/', function(response){
			
			var user = response.results[0];
			var name = user.name.first+" "+ user.name.last;
			var gender = user.gender;
			var email = user.email;
			var phone = user.phone;
			var dob = user.dob.date;
			var street = user.location.street;
			
			var message = "User Information-\nName- "+name+"\nGender- "+gender
			+"\nEmail- "+email+"\nPhone- "+phone+"\nDate of Birth- "+dob+"\nStreet- "+street;
			
			$('#inspect-message').val(message);
			
		});
	};
	
	var startCounting = function(){
		counter++;
		if(counter===3){
			populateText();
			couter=0;
		}
		setTimeout(() => {
			counter=0;
		}, 1000);
	};
	
	function disableBtn(btn) {
		var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
		$(btn).html(loading);
		$(btn).attr('disabled', 'disabled');
	};
	
});