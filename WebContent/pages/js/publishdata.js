$(document).ready(function() {

	$(document).ready(function() {
	    $('#publish-datatable').DataTable( {
	        "ajax": "/publishdata",
	        "order": [[ 3, 'desc' ]],
	        "columns": [
	            { "data": "messageId" },
	            { "data": "message" },
	            { "data": "topicName" },
	            { "data": "publishTime" }
	        ]
	    } );
	} );

});