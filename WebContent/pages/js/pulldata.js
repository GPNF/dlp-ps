$(document).ready(function() {
	
	$('#loading-div, #loading').addClass('visible').hide().delay(500).fadeIn(300);

	$(document).ready(function() {
		$('#pull-datatable').DataTable( {
	        "ajax": "/pulldata",
	        "order": [[ 0, 'desc' ]],
	        columnDefs: [
	            { width: '20%', targets: 0 }
	        ],
	        fixedColumns: true,
	        
	        "columns": [
	        	{ "data": "pullTime" },
	            { "data": "publishTime" },
	            { "data": "messageId" },
	            { "data": function(d){
	            	var psMsgData = JSON.stringify(d);
	            	return JSON.parse(psMsgData).message;
	            	} 
	            },
	            { "data": "subscriptionId" },
	            { "data": "globalTransactionId" },
	            { "data": "ackId"}	            
	        ],
	        "initComplete": function(settings, json) {
	        	$('#loading').remove();
	            $('#pull-data *').addClass('visible').hide().fadeIn(500);
	          },
	        mark: true
	    } );
	} );

});