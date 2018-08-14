$(document).ready(function() {

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
	            { "data": "message" },
	            { "data": "subscriptionId" },
	            { "data": "subscriberName" },
	            { "data": "ackId" }	            
	        ]
	    } );
	} );

});