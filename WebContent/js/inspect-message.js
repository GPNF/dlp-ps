$(document).ready(function(){
	
	$('#inspect-btn').click(function(){
		var message = $('#inspect-message').val();
		if(message.length<1){
			return;
		}
		inspect(message);
	});
	
	var inspect = function(message){
		
		
		
		var jsondata = {"message":message};
		
		
		$.ajax
        (
            {
                url:'/inspect',
                data:JSON.stringify(jsondata),
                type:'post',
                cache:false,
                complete:function(data){
                	console.log('Logging');
                	console.log(data);
                	if (status === 'error' || !data.responseText) {
                        handleError();
                    }
                    else {
                        var message = data.responseText;
                        $('#message').val(message);
                    }
                	
                	
                	
                }
            }
        );
		
	}
	
});