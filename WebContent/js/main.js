$(document).ready(function(){
	
	var counter=0;
	$('#message').click(function(){
		counter++;
		if(counter===3){
			populateRandomQuote();
			couter=0;
		}
		setTimeout(() => {
			counter=0;
		}, 2000);
	});
	
	var populateRandomQuote = function(){
		$.get('https://talaikis.com/api/quotes/random/', function(data){
			var quote = data.quote;
			$('#message').val(quote);
		});
	}
	
});