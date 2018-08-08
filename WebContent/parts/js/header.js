$(document).ready(function() {
	var pathname = window.location.pathname;
	
	//Active link based on path.
	$('.navbar-nav > a[href="'+pathname+'"]').addClass('active');
	
	//Other than path, other urls also need to be handled
	if(pathname==='/' || pathname==='/index.jsp'){
		$('#publish').addClass('active');
	}
	if(pathname==='/pullmessage'){
		$('#pull-data').addClass('active');
	}

		
	$('#top-header .navbar-nav a').click(function() {

		console.log('Clicked link on header');
		var foundElem =  $('#top-header .navbar-nav').find('a.active');
		$.each(foundElem, function(index,value){
			$(value).removeClass('active');
		});
		$(this).parent('a').addClass('active');
	});
});