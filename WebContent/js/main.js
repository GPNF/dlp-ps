$(document).ready(function(){
	
	var counter=0;
	$('#inspect-message').click(function(){
		counter++;
		if(counter===3){
			populateText();
			couter=0;
		}
		setTimeout(() => {
			counter=0;
		}, 1000);
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
	}
	
});